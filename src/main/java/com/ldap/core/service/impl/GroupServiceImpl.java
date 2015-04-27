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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;
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

    @Autowired
    LdapTemplate ldapTemplate;

    @Override
    public boolean create(Group t) {
        try {
            List<String> memberOfGroup = t.getMemberOfGroup();
            if ((memberOfGroup != null) && !memberOfGroup.isEmpty()) {
                // Base
                BasicAttribute ocattr = new BasicAttribute("objectClass");
                ocattr.add("top");
                ocattr.add(objectClass);
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
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(String name) {
        try {
            ldapTemplate.unbind(getGroupDn(name));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Group t) {

        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Group search(String userName) {

        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void assignUser(String userName, String groupName) {
        try {
            ModificationItem[] item = new ModificationItem[] { new ModificationItem(DirContext.ADD_ATTRIBUTE, new BasicAttribute("uniqueMember", getUserDn(userName))) };
            ldapTemplate.modifyAttributes(getGroupDn(groupName), item);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUser(String userName, String groupName) {
        try {
            ModificationItem[] item = new ModificationItem[] { new ModificationItem(DirContext.REMOVE_ATTRIBUTE, new BasicAttribute("uniqueMember", getUserDn(userName))) };
            ldapTemplate.modifyAttributes(getGroupDn(groupName), item);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getDn(String name) {
        EqualsFilter f = new EqualsFilter("cn", name);
        List<Object> result = ldapTemplate.search("", f.toString(), new AbstractContextMapper<Object>() {
            @Override
            protected Object doMapFromContext(DirContextOperations ctx) {
                return ctx.getNameInNamespace();
            }
        });
        if (result.size() != 1) {
            throw new RuntimeException("Group not found or not unique");
        }
        return (String) result.get(0);
    }

    @Override
    public List<Group> getGroup(String userName) {
        List<Group> list = new ArrayList<Group>();
        try {
            AndFilter andFilter = new AndFilter();
            andFilter.and(new EqualsFilter("objectclass", "groupOfUniqueNames"));
            andFilter.and(new EqualsFilter("uniqueMember", getUserDn(userName)));
            List<Object> search = ldapTemplate.search("", andFilter.encode(), new GroupAttributesMapper());
            if ((search != null) && !search.isEmpty()) {
                for (Object o : search) {
                    list.add((Group) o);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
