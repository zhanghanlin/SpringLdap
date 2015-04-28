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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.NamingException;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.stereotype.Service;

import com.ldap.core.bean.User;
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

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

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
            logger.info("create success ： {}", t.toString());
            return true;
        } catch (Exception e) {
            logger.error("create error ： {} \r\n {}", t.toString(), e);
            return false;
        }
    }

    @Override
    public boolean delete(String userName) {
        try {
            ldapTemplate.unbind(getUserDn(userName));
            logger.info("delete success , userName ： {}", userName);
            return true;
        } catch (Exception e) {
            logger.error("delete error , userName  ： {} \r\n {}", userName, e);
            return false;
        }
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
            logger.info("update success ： {}", t.toString());
            return true;
        } catch (Exception e) {
            logger.error("update error ： {} \r\n {}", t.toString(), e);
            return false;
        }
    }

    @Override
    public User search(String userName) {
        User user = new User();
        try {
            AndFilter andFilter = new AndFilter();
            andFilter.and(new EqualsFilter("objectclass", objectClass));
            andFilter.and(new EqualsFilter("uid", userName));
            List<Object> search = ldapTemplate.search("", andFilter.encode(), new UserAttributesMapper());
            if ((search != null) && !search.isEmpty()) {
                user = (User) search.get(0);
            }
            logger.info("search success userName ： {}, return: {}", userName, user.toString());
        } catch (NamingException e) {
            logger.error("search error userName ： {} \r\n {}", userName, e);
        } catch (Exception e) {
            logger.error("search error userName ： {} \r\n {}", userName, e);
        }
        return user;
    }

    @Override
    public boolean isValid(String userName, String password) {
        try {
            AndFilter filter = new AndFilter();
            filter.and(new EqualsFilter("uid", userName));
            boolean valid = ldapTemplate.authenticate(getUserDn(userName), filter.toString(), password);
            logger.info("isValid success userName ： {}, isValid: {}", userName, valid);
            return valid;
        } catch (NamingException e) {
            logger.info("isValid fail userName ： {} \r\n {}", userName, e);
            return false;
        } catch (Exception e) {
            logger.info("isValid error userName ： {} \r\n {}", userName, e);
            return false;
        }
    }

    @Override
    public boolean updatePwd(String userName, String passpord) {
        try {
            ModificationItem[] item = new ModificationItem[] { new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("userPassword", passpord)) };
            ldapTemplate.modifyAttributes(getUserDn(userName), item);
            logger.info("updatePwd success , userName ： {}", userName);
            return true;
        } catch (Exception e) {
            logger.error("updatePwd error , userName ： {} \r\n {}", userName, e);
            return false;
        }
    }
}