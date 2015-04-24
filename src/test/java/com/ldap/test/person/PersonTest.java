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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ldap.core.bean.Person;
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
public class PersonTest {

    Logger logger = LoggerFactory.getLogger(PersonTest.class);

    @Autowired
    PersonService personService;

    @Test
    public void testGetAll() {
        List<Person> persons = personService.getAll();
        for (Person p : persons) {
            logger.info(p.toString());
        }
        Assert.assertNotNull(persons);
    }

    @Test
    public void testGetByUserId() {
        Person person = personService.getByUserId("10000");
        logger.info(person.toString());
        Assert.assertNotNull(person);
    }

    @Test
    public void testGetByCommonName() {
        List<Person> persons = personService.getByCommonName("updateTest");
        for (Person p : persons) {
            logger.info(p.toString());
        }
        Assert.assertNotNull(persons);
    }

    @Test
    public void testAdd() {
        Person person = new Person();
        person.setUserId(UUID.randomUUID().toString());
        person.setCommonName("T.Test");
        person.setSurName("T");
        person.setTelephone("8888");
        person.setMail("a@b.com");
        person.setOrganizationalUnit("People");
        person.setPassword("111111");
        boolean res = personService.add(person);
        Assert.assertTrue(res);
    }

    @Test
    public void testDeleteByUid() {
        boolean res = personService.deleteByUid("42a43e25-439e-4074-9d80-dae6f8814ebb");
        Assert.assertTrue(res);
    }

    @Test
    public void testUpdate() {
        Person person = personService.getByUserId("10000");
        System.out.println(person.toString());
        person.setCommonName("updateTest");
        boolean res = personService.update(person);
        Assert.assertTrue(res);
    }

    @Test
    public void teatGetDnByUserId() {
        String dn = personService.getDnByUserId("10000");
        logger.info(dn);
    }
}
