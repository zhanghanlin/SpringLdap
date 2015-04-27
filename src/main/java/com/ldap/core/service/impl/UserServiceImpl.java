/**
 * Project Name:SpringLdap
 * File Name:PersonServiceImpl.java
 * Package Name:com.ldap.core.service.impl
 * Date:2015-4-23下午5:11:25
 *
 */

package com.ldap.core.service.impl;

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

import com.ldap.core.bean.User;
import com.ldap.core.exception.UserNotFoundException;
import com.ldap.core.service.ExtendService;
import com.ldap.core.service.UserService;
import com.ldap.core.util.UserAttributesMapper;
import com.ldap.util.StringUtils;

/**
 * ClassName:PersonServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015-4-23 下午5:11:25 <br/>
 * 
 * @author zhanghanlin
 * @version
 * @since JDK 1.7
 * @see
 */
@Service("userService")
public class UserServiceImpl extends ExtendService<User> implements UserService {

    @Autowired
    LdapTemplate ldapTemplate;

    @Override
    public boolean create(User t) {
        try {
            // Base
            BasicAttribute ocattr = new BasicAttribute("objectClass");
            ocattr.add("top");
            ocattr.add(objectClass);
            ocattr.add("uidObject");
            ocattr.add("inetOrgPerson");
            ocattr.add("organizationalPerson");
            // User
            Attributes attrs = new BasicAttributes();
            attrs.put(ocattr);
            attrs.put("cn", StringUtils.trimToEmpty(t.getCommonName()));
            attrs.put("sn", StringUtils.trimToEmpty(t.getSurName()));
            attrs.put("mail", StringUtils.trimToEmpty(t.getMail()));
            attrs.put("telephoneNumber", StringUtils.trimToEmpty(t.getTelephone()));
            attrs.put("userPassword", StringUtils.trimToEmpty(t.getPassword()));
            ldapTemplate.bind(getUserDn(t.getUserName()), null, attrs);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(String userName) {
        try {
            ldapTemplate.unbind(getUserDn(userName));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean update(User t) {
        try {
            ModificationItem[] item = new ModificationItem[] { new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("uid", t.getUserName())),
                    new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("sn", t.getSurName())),
                    new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("cn", t.getCommonName())),
                    new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("mail", t.getMail())),
                    new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("telephoneNumber", t.getTelephone())) };
            ldapTemplate.modifyAttributes(getUserDn(t.getUserName()), item);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User search(String userName) throws UserNotFoundException {
        User user = new User();
        try {
            AndFilter andFilter = new AndFilter();
            andFilter.and(new EqualsFilter("objectclass", objectClass));
            andFilter.and(new EqualsFilter("uid", userName));
            List<Object> search = ldapTemplate.search("", andFilter.encode(), new UserAttributesMapper());
            if ((search != null) && !search.isEmpty()) {
                user = (User) search.get(0);
            }
        } catch (UserNotFoundException ue) {
            throw new UserNotFoundException(userName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public String getDn(String userName) {
        EqualsFilter f = new EqualsFilter("uid", userName);
        List<Object> result = ldapTemplate.search("", f.toString(), new AbstractContextMapper<Object>() {
            @Override
            protected Object doMapFromContext(DirContextOperations ctx) {
                return ctx.getNameInNamespace();
            }
        });
        if (result.size() != 1) {
            throw new RuntimeException("User not found or not unique");
        }
        return (String) result.get(0);
    }
}
