/**
 * Project Name:SpringLdap
 * File Name:Authenticate.java
 * Package Name:com.ldap.core.authenticate
 * Date:2015-4-24下午2:49:20
 *
 */

package com.ldap.core.handler;

import javax.naming.NameNotFoundException;
import javax.naming.directory.DirContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapUtils;

import com.ldap.core.service.impl.UserServiceImpl;

/**
 * 验证用户有效性Handler. <br/>
 * Date: 2015-4-24 下午2:49:20 <br/>
 * 
 * @author zhanghanlin
 * @version
 * @since JDK 1.7
 * @see
 */
public class AuthenticateHandler {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    LdapTemplate ldapTemplate;

    /**
     * 根据用户名验证登陆信息
     * 
     * @author zhanghanlin
     * @param userName
     * @param password
     * @return
     * @since JDK 1.7
     */
    public boolean authenticate(String userName, String password) throws NameNotFoundException {
        DirContext dirContext = null;
        try {
            dirContext = ldapTemplate.getContextSource().getContext(userService.getUserDn(userName), password);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            if (dirContext != null) {
                LdapUtils.closeContext(dirContext);
            }
        }
    }
}
