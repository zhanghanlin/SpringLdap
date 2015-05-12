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
import com.ldap.util.StringUtils;

/**
 * Date: 2015-4-23 下午5:35:14 <br/>
 * 
 * @author zhanghanlin
 * @version
 * @since JDK 1.7
 * @see
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "file:src/main/resources/conf_spring/applicationContext*.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserTest {

    Logger logger = LoggerFactory.getLogger(UserTest.class);

    @Autowired
    UserServiceImpl userService;

    static String defaultPwd = "111111";
    static String testUid = StringUtils.leftPad("5001", 5, "0");

    @Test
    public void testACreate() {
        User user = new User();
        user.setTelephoneNumber("8888");
        user.setUserPassword(defaultPwd);
        user.setUid(testUid);
        String name = "test";
        user.setSn(name);
        user.setCn(name);
        user.setMail(name + "@domain.cn");
        user.setDescription("OU=其他部门,OU=民生电子商务有限责任公司");
        boolean res = userService.create(user);
        Assert.assertTrue(res);
    }

    @Test
    public void testBSearch() {
        User user = userService.search(testUid);
        logger.info(user.toString());
        Assert.assertNotNull(user);
    }

    @Test
    public void testCUpdate() {
        User user = userService.search(testUid);
        logger.info("before update : " + user.toString());
        user.setCn("test2");
        boolean res = userService.update(user);
        logger.info("after update : {} ", userService.search(testUid));
        Assert.assertTrue(res);
    }

    @Test
    public void testDGetDn() {
        String dn = userService.getDnByUid(testUid);
        logger.info(dn);
        Assert.assertNotNull(dn);
    }

    @Test
    public void testEIsVaild() {
        boolean res = userService.isValid(testUid, defaultPwd);
        Assert.assertTrue(res);
    }

    @Test
    public void testFUpdatePwd() {
        String password = "123456";
        // 验证原始密码
        boolean isValid = userService.isValid(testUid, defaultPwd);
        if (isValid) {
            boolean res = userService.updatePwd(testUid, password);
            logger.info("update pwd : {}", res);
        } else {
            logger.info("updatePwd fail , user vaild fail, userName ： {}", testUid);
        }
        isValid = userService.isValid(testUid, password);
        logger.info("update pwd after valid: {}", isValid);
        Assert.assertTrue(isValid);
    }

    @Test
    public void testGDelete() {
        boolean res = userService.delete(testUid);
        Assert.assertTrue(res);
    }
}
