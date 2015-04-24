/**
 * Project Name:SpringLdap
 * File Name:Test.java
 * Package Name:com.ldap.test
 * Date:2015-4-23下午5:35:14
 *
 */

package com.ldap.test.handler;

import javax.naming.directory.DirContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ldap.core.service.PersonService;

/**
 * ClassName:Test <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015-4-23 下午5:35:14 <br/>
 * 
 * @author zhanghanlin
 * @version
 * @since JDK 1.7
 * @see
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "file:src/main/resources/conf_spring/applicationContext.xml" })
public class AuthenticateHandlerTest {

    @Autowired
    PersonService personService;

    @Autowired
    LdapTemplate ldapTemplate;

    @Test
    public void authenticate() {
        String userName = "10000";
        String password = "1111111";
        String personDn = personService.getDnByUserId(userName);
        System.out.println("personDn = " + personDn);
        DirContext dirContext = null;
        try {
            dirContext = ldapTemplate.getContextSource().getContext(personDn, password);
            System.out.println(dirContext != null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dirContext != null) {
                LdapUtils.closeContext(dirContext);
            }
        }
    }
}
