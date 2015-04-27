/**
 * Project Name:SpringLdap
 * File Name:ExtendService.java
 * Package Name:com.ldap.core.service
 * Date:2015-4-24下午1:32:46
 *
 */

package com.ldap.core.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.NamingException;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.ldap.filter.EqualsFilter;

/**
 * Base Service. <br/>
 * Date: 2015-4-24 下午1:32:46 <br/>
 * 
 * @author zhanghanlin
 * @version
 * @since JDK 1.7
 * @see
 */
public abstract class ExtendService<T> {

    Logger logger = LoggerFactory.getLogger(ExtendService.class);

    @Autowired
    protected LdapTemplate ldapTemplate;

    /**
     * 创建
     * 
     * @author zhanghanlin
     * @param t
     * @return
     * @since JDK 1.7
     */
    public abstract boolean create(T t);

    /**
     * 
     * 删除
     * 
     * @author zhanghanlin
     * @param s
     * @return
     * @since JDK 1.7
     */
    public abstract boolean delete(String s);

    /**
     * 
     * 更新
     * 
     * @author zhanghanlin
     * @param t
     * @return
     * @since JDK 1.7
     */
    public abstract boolean update(T t);

    /**
     * 
     * 查询
     * 
     * @author zhanghanlin
     * @param s
     * @return
     * @since JDK 1.7
     */
    public abstract T search(String s);

    /**
     * 
     * 得到DN
     * 
     * @author zhanghanlin
     * @param s
     * @return
     * @since JDK 1.7
     */
    public String getDn(String key, String val) {
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

    public String getGroupDn(String s) {
        return new StringBuffer().append("cn=").append(s).append(",ou=Group").toString();
    }

    public String getUserDn(String s) {
        return new StringBuffer().append("uid=").append(s).append(",ou=People").toString();
    }
}
