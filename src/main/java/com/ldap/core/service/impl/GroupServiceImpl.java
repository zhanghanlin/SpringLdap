/**
 * Project Name:SpringLdap
 * File Name:GroupServiceImpl.java
 * Package Name:com.ldap.core.service.impl
 * Date:2015-5-11上午11:07:10
 *
 */

package com.ldap.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.NamingException;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.stereotype.Service;

import com.ldap.core.bean.Group;
import com.ldap.core.service.GroupService;
import com.ldap.core.util.GroupAttributesMapper;
import com.ldap.util.StringUtils;

/**
 * ClassName:GroupServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015-5-11 上午11:07:10 <br/>
 * 
 * @author zhanghanlin
 * @version
 * @since JDK 1.7
 * @see
 */
@Service("adGroupService")
public class GroupServiceImpl implements GroupService {

    Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);

    @Autowired
    protected LdapTemplate adTemplate;
    @Autowired
    protected LdapTemplate ldapTemplate;

    @Override
    public boolean create(Group t) {
        try {
            BasicAttribute ocattr = new BasicAttribute("objectClass");
            for (String oc : objectClass) {
                ocattr.add(oc);
            }
            BasicAttribute ouattr = new BasicAttribute("ou");
            for (String ou : t.getOuList()) {
                ouattr.add(ou);
            }
            Attributes attrs = new BasicAttributes();
            attrs.put(ocattr);
            attrs.put(ouattr);
            attrs.put("ou", StringUtils.trimToEmpty(t.getOu()));
            attrs.put("description", StringUtils.trimToEmpty(t.getDistinguishedName()));
            ldapTemplate.bind(t.getDistinguishedName(), null, attrs);
            logger.info("create success ： {}", t.toString());
            return true;
        } catch (Exception e) {
            logger.error("create error ： {} \r\n {}", t.toString(), e);
            return false;
        }
    }

    @Override
    public List<Group> searchAll() {
        List<Group> search = new ArrayList<Group>();
        try {
            AndFilter andFilter = new AndFilter();
            andFilter.and(new EqualsFilter("objectclass", "organizationalUnit"));
            search = adTemplate.search("", andFilter.encode(), new GroupAttributesMapper());
            logger.info("searchAll size : {}", search.size());
        } catch (NamingException e) {
            logger.error("searchAll error : {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("searchAll error : {}", e.getMessage(), e);
        }
        return search;
    }
}