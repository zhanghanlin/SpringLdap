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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.NamingException;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.stereotype.Service;

import com.ldap.core.bean.User;
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
public class UserServiceImpl implements UserService {

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    protected LdapTemplate ldapTemplate;

    @Override
    public boolean create(User t) {
        try {
            // Base
            BasicAttribute ocattr = new BasicAttribute("objectClass");
            for (String oc : objectClass) {
                ocattr.add(oc);
            }
            BasicAttribute ouattr = new BasicAttribute("ou");
            String[] ou = t.getOu();
            if ((ou == null) || (ou.length == 0)) {
                logger.info("create error : user dn is null");
                return false;
            }
            for (int i = 1; i < ou.length; i++) {
                ouattr.add(ou[i]);
            }
            // User
            Attributes attrs = new BasicAttributes();
            attrs.put(ocattr);
            attrs.put(ouattr);
            attrs.put("cn", StringUtils.trimToEmpty(t.getCn()));
            attrs.put("sn", StringUtils.trimToEmpty(t.getSn()));
            attrs.put("mail", StringUtils.trimToEmpty(t.getMail()));
            attrs.put("telephoneNumber", StringUtils.trimToEmpty(t.getTelephoneNumber()));
            attrs.put("userPassword", StringUtils.trimToEmpty(t.getUserPassword()));
            attrs.put("uid", StringUtils.trimToEmpty(t.getUid()));
            attrs.put("description", StringUtils.trimToEmpty(t.getDescription()));
            ldapTemplate.bind(t.getDn(), null, attrs);
            logger.info("create success ： {}", t.toString());
            return true;
        } catch (Exception e) {
            logger.error("create error ： {} \r\n {}", t.toString(), e);
            return false;
        }
    }

    @Override
    public boolean delete(String uid) {
        try {
            ldapTemplate.unbind(getDnByUid(uid));
            logger.info("delete success , userName ： {}", uid);
            return true;
        } catch (Exception e) {
            logger.error("delete error , userName  ： {} \r\n {}", uid, e);
            return false;
        }
    }

    @Override
    public boolean update(User t) {
        try {
            ModificationItem[] item = new ModificationItem[] { new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("sn", t.getSn())),
                    new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("cn", t.getCn())),
                    new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("mail", t.getMail())),
                    new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("telephoneNumber", t.getTelephoneNumber())) };
            ldapTemplate.modifyAttributes(getDnByUid(t.getUid()), item);
            logger.info("update success ： {}", t.toString());
            return true;
        } catch (Exception e) {
            logger.error("update error ： {} \r\n {}", t.toString(), e);
            return false;
        }
    }

    @Override
    public User search(String uid) {
        User user = null;
        try {
            AndFilter andFilter = new AndFilter();
            andFilter.and(new EqualsFilter("objectclass", objectClass[0]));
            andFilter.and(new EqualsFilter("uid", uid));
            List<User> search = ldapTemplate.search("", andFilter.encode(), new UserAttributesMapper());
            if ((search != null) && !search.isEmpty()) {
                user = search.get(0);
            }
            if (user != null) {
                logger.info("search success uid ： {}, return: {}", uid, user.toString());
            } else {
                logger.info("search is null uid ： {}", uid);
            }
        } catch (NamingException e) {
            logger.error("search error userName ： {} \r\n {}", uid, e);
        } catch (Exception e) {
            logger.error("search error userName ： {} \r\n {}", uid, e);
        }
        return user;
    }

    @Override
    public boolean isValid(String uid, String password) {
        try {
            AndFilter filter = new AndFilter();
            filter.and(new EqualsFilter("uid", uid));
            boolean valid = ldapTemplate.authenticate(getDnByUid(uid), filter.toString(), password);
            logger.info("isValid success userName ： {}, isValid: {}", uid, valid);
            return valid;
        } catch (NamingException e) {
            logger.info("isValid fail userName ： {} \r\n {}", uid, e);
            return false;
        } catch (Exception e) {
            logger.info("isValid error userName ： {} \r\n {}", uid, e);
            return false;
        }
    }

    @Override
    public boolean updatePwd(String uid, String passpord) {
        try {
            ModificationItem[] item = new ModificationItem[] { new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("userPassword", passpord)) };
            ldapTemplate.modifyAttributes(getDnByUid(uid), item);
            logger.info("updatePwd success , userName ： {}", uid);
            return true;
        } catch (Exception e) {
            logger.error("updatePwd error , userName ： {} \r\n {}", uid, e);
            return false;
        }
    }

    /**
     * 
     * 根据UID查询DN
     * 
     * @author zhanghanlin
     * @param uid
     * @return
     * @since JDK 1.7
     */
    public String getDnByUid(String uid) {
        return getUserDn("uid", uid).replace(",DC=domain,DC=cn", "");
    }

    /**
     * 根据邮箱查询DN
     * 
     * @author zhanghanlin
     * @param mail
     * @return
     * @since JDK 1.7
     */
    public String getDnByMail(String mail) {
        return getUserDn("mail", mail);
    }

    /**
     * 
     * 根据指定的属性值查询DN
     * 
     * @author zhanghanlin
     * @param key
     * @param val
     * @return
     * @since JDK 1.7
     */
    public String getUserDn(String key, String val) {
        String dn = "";
        try {
            EqualsFilter f = new EqualsFilter(key, val);
            List<Object> result = ldapTemplate.search("", f.toString(), new AbstractContextMapper<Object>() {
                @Override
                protected Object doMapFromContext(DirContextOperations ctx) {
                    return ctx.getNameInNamespace();
                }
            });
            if (result.size() == 1) {
                dn = result.get(0).toString();
            }
            logger.info("getDn success key ： {}, val : {}, dn: {}", key, val, dn);
        } catch (NamingException e) {
            logger.error("getDn error key ： {}, val : {} \r\n {}", key, val, e);
        } catch (Exception e) {
            logger.error("getDn error key ： {}, val : {} \r\n {}", key, val, e);
        }
        return dn;
    }
}