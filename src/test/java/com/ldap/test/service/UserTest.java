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

    static String[] users = new String[] { "zhaoyi", "qianer", "sunsan", "lisi", "zhouwu", "wuliu", "zhengqi", "wangba" };

    static String defaultPwd = "111111";

    /**
     * 首个用户
     * 
     * @author zhanghanlin
     * @param group
     * @return
     * @since JDK 1.7
     */
    static String getFirstUser() {
        return users[0];
    }

    @Test
    public void testACreate() {
        User user = new User();
        user.setTelephoneNumber("8888");
        user.setUserPassword(defaultPwd);
        for (String s : users) {
            user.setUid(s);
            user.setCn(s);
            user.setMail(s + "@domain.cn");
            user.setSn(s);
            userService.create(user);
        }
        Assert.assertTrue(true);
    }

    @Test
    public void testBSearch() {
        User user = userService.search("211");
        logger.info(user.toString());
        Assert.assertNotNull(user);
    }

    @Test
    public void testCUpdate() {
        User user = userService.search(getFirstUser());
        logger.info("before update : " + user.toString());
        user.setCn("update cn");
        boolean res = userService.update(user);
        logger.info("after update : {} ", userService.search(getFirstUser()).toString());
        Assert.assertTrue(res);
    }

    @Test
    public void testDGetDn() {
        String dn = userService.getDn("uid", getFirstUser());
        logger.info(dn);
        Assert.assertNotNull(dn);
    }

    @Test
    public void testEIsVaild() {
        boolean res = userService.isValid(getFirstUser(), defaultPwd);
        Assert.assertTrue(res);
    }

    @Test
    public void testFUpdatePwd() {
        String userName = getFirstUser();
        String password = "123456";
        // 验证原始密码
        boolean isValid = userService.isValid(getFirstUser(), defaultPwd);
        if (isValid) {
            boolean res = userService.updatePwd(getFirstUser(), password);
            logger.info("update pwd : {}", res);
        } else {
            logger.info("updatePwd fail , user vaild fail, userName ： {}", userName);
        }
        isValid = userService.isValid(getFirstUser(), password);
        logger.info("update pwd after valid: {}", isValid);
        Assert.assertTrue(isValid);
    }

    @Test
    public void testGDelete() {
        boolean res = true;
        for (String s : users) {
            if (!res) {
                break;
            }
            res = userService.delete(s);
        }
        Assert.assertTrue(res);
    }
}
