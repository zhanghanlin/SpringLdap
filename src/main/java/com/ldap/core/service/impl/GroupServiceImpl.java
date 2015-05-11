/**
 * Project Name:SpringLdap
 * File Name:UnitServiceImpl.java
 * Package Name:com.ldap.core.service.impl
 * Date:2015-4-27下午1:13:43
 *
 */

package com.ldap.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.stereotype.Service;

import com.ldap.core.bean.Group;
import com.ldap.core.service.ExtendService;
import com.ldap.core.service.GroupService;
import com.ldap.core.util.GroupAttributesMapper;
import com.ldap.util.StringUtils;

/**
 * ClassName:UnitServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015-4-27 下午1:13:43 <br/>
 * 
 * @author zhanghanlin
 * @version
 * @since JDK 1.7
 * @see
 */
@Service("groupService")
public class GroupServiceImpl extends ExtendService<Group> implements GroupService {

    Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);

    @Override
    public boolean create(Group t) {
        try {
            List<String> memberOfGroup = t.getMemberOfGroup();
            if ((memberOfGroup != null) && !memberOfGroup.isEmpty()) {
                // Base
                BasicAttribute ocattr = new BasicAttribute("objectClass");
                for (String oc : objectClass) {
                    ocattr.add(oc);
                }
                // User
                BasicAttribute memberAttributes = new BasicAttribute("uniquemember");
                for (String member : memberOfGroup) {
                    memberAttributes.add(getUserDn(member));
                }
                Attributes attrs = new BasicAttributes();
                attrs.put(memberAttributes);
                attrs.put(ocattr);
                attrs.put("cn", StringUtils.trimToEmpty(t.getName()));
                attrs.put("description", StringUtils.trimToEmpty(t.getDescription()));
                ldapTemplate.bind(getGroupDn(t.getName()), null, attrs);
                logger.info("create success ： {}", t.toString());
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error("create error ： {} \r\n {}", t.toString(), e);
            return false;
        }
    }

    @Override
    public boolean delete(String name) {
        try {
            ldapTemplate.unbind(getGroupDn(name));
            logger.info("delete success , name ： {}", name);
            return true;
        } catch (Exception e) {
            logger.error("delete error , name  ： {} \r\n {}", name, e);
            return false;
        }
    }

    @Override
    public boolean update(Group t) {
        return false;
    }

    @Override
    public Group search(String name) {
        return null;
    }

    @Override
    public void assignUser(String uid, String groupName) {
        try {
            ModificationItem[] item = new ModificationItem[] { new ModificationItem(DirContext.ADD_ATTRIBUTE, new BasicAttribute("uniqueMember", getUserDn(uid))) };
            ldapTemplate.modifyAttributes(getGroupDn(groupName), item);
            logger.info("assignUser success , userName ： {}, groupName : {}", uid, groupName);
        } catch (Exception e) {
            logger.error("assignUser error , userName ： {}, groupName : {} \r\n {}", uid, groupName, e);
        }
    }

    @Override
    public void removeUser(String uid, String groupName) {
        try {
            ModificationItem[] item = new ModificationItem[] { new ModificationItem(DirContext.REMOVE_ATTRIBUTE, new BasicAttribute("uniqueMember", getUserDn(uid))) };
            ldapTemplate.modifyAttributes(getGroupDn(groupName), item);
            logger.info("removeUser success , userName ： {}, groupName : {}", uid, groupName);
        } catch (Exception e) {
            logger.error("assignUser error , userName ： {}, groupName : {} \r\n {}", uid, groupName, e);
        }
    }

    @Override
    public List<Group> getGroup(String uid) {
        List<Group> list = new ArrayList<Group>();
        try {
            AndFilter andFilter = new AndFilter();
            andFilter.and(new EqualsFilter("objectclass", objectClass[0]));
            andFilter.and(new EqualsFilter("uniqueMember", getUserDn(uid)));
            List<Group> search = ldapTemplate.search("", andFilter.encode(), new GroupAttributesMapper());
            if ((search != null) && !search.isEmpty()) {
                for (Group t : search) {
                    list.add(t);
                }
            }
            logger.info("getGroup success userName ： {}, size: {}", uid, list.size());
        } catch (Exception e) {
            logger.error("getGroup error userName ： {} \r\n {}", uid, e);
        }
        return list;
    }
}