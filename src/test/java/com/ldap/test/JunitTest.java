/**
 * Project Name:SpringLdap
 * File Name:Test.java
 * Package Name:com.ldap.test
 * Date:2015-4-23下午5:35:14
 *
 */

package com.ldap.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ldap.core.bean.Person;
import com.ldap.core.service.impl.PersonServiceImpl;

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
public class JunitTest {

    @Autowired
    PersonServiceImpl personService;

    @Test
    public void testLdap() {
        Person person = new Person();
        person.setUserId("10000");
        person.setCommonName("test");
        person.setSurName("t");
        person.setTelephone("8888");
        person.setMail("a@b.com");
        person.setPassword("111111");
        // boolean a = personService.addPerson(person);
        // System.out.println(a);
        // List<Person> list = personService.getAllPerson();
        List<Person> list = personService.findPersonByCommonName("test");
        System.out.println(list.size());
    }
}
