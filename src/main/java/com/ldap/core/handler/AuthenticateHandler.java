/**
 * Project Name:SpringLdap
 * File Name:Authenticate.java
 * Package Name:com.ldap.core.authenticate
 * Date:2015-4-24下午2:49:20
 *
 */

package com.ldap.core.handler;

import javax.naming.directory.DirContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapUtils;

import com.ldap.core.service.PersonService;

/**
 * ClassName:Authenticate <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015-4-24 下午2:49:20 <br/>
 * 
 * @author zhanghanlin
 * @version
 * @since JDK 1.7
 * @see
 */
public class AuthenticateHandler {

    @Autowired
    PersonService personService;

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
    public boolean authenticate(String userName, String password) {
        String personDn = personService.getDnByUserId(userName);
        DirContext dirContext = null;
        try {
            dirContext = ldapTemplate.getContextSource().getContext(personDn, password);
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
