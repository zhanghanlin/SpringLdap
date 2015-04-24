/**
 * Project Name:SpringLdap
 * File Name:Test.java
 * Package Name:com.ldap.test
 * Date:2015-4-23下午5:35:14
 *
 */

package com.ldap.test.person;

import java.util.List;
import java.util.UUID;

import org.junit.Assert;
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
public class PersonTest {

    @Autowired
    PersonServiceImpl personService;

    @Test
    public void testGetAllPerson() {
        List<Person> persons = personService.getAllPerson();
        Assert.assertNotNull(persons);
    }

    @Test
    public void testGetPersonByUserId() {
        Person person = personService.getPersonByUserId("10000");
        Assert.assertNotNull(person);
    }

    @Test
    public void testGetPersonByCommonName() {
        List<Person> persons = personService.getPersonByCommonName("test");
        Assert.assertNotNull(persons);
    }

    @Test
    public void testAddPerson() {
        Person person = new Person();
        person.setUserId(UUID.randomUUID().toString());
        person.setCommonName("T.Test");
        person.setSurName("T");
        person.setTelephone("8888");
        person.setMail("a@b.com");
        person.setPassword("111111");
        boolean res = personService.addPerson(person);
        Assert.assertTrue(res);
    }
}
