/**
 * Project Name:SpringLdap
 * File Name:Test.java
 * Package Name:com.ldap.test
 * Date:2015-4-23下午5:35:14
 *
 */

package com.ldap.test.service;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ldap.core.bean.User;
import com.ldap.core.service.impl.UserServiceImpl;

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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserTest {

    Logger logger = LoggerFactory.getLogger(UserTest.class);

    @Autowired
    UserServiceImpl userService;

    static String userName = "zhaoyi";
    static String cn = "testAdd";

    @Test
    public void testACreate() {
        User user = new User();
        user.setTelephone("8888");
        user.setPassword("111111");
        String[] un = new String[] { "zhaoyi", "qianer", "sunsan", "lisi", "zhouwu", "wuliu", "zhengqi", "wangba" };
        for (String s : un) {
            user.setUserName(s);
            user.setCommonName(s);
            user.setMail(s + "@minshengec.cn");
            user.setSurName(s);
            userService.create(user);
        }
        Assert.assertTrue(true);
    }

    @Test
    public void testBSearch() {
        User user = userService.search(userName);
        logger.info(user.toString());
        Assert.assertNotNull(user);
    }

    @Test
    public void testCUpdate() {
        User user = userService.search(userName);
        logger.info("before update : " + user.toString());
        user.setCommonName("updateTest2");
        boolean res = userService.update(user);
        logger.info("after update : " + userService.search(userName).toString());
        Assert.assertTrue(res);
    }

    @Test
    public void testDGetDnByUserId() {
        String dn = userService.getUserDn(userName);
        logger.info(dn);
        Assert.assertNotNull(dn);
    }

    @Test
    public void testEDelete() {
        boolean res = userService.delete(userName);
        Assert.assertTrue(res);
    }
}
