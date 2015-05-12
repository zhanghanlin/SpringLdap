/**
 * Project Name:SpringLdap
 * File Name:Test.java
 * Package Name:com.ldap.test
 * Date:2015-4-23下午5:35:14
 *
 */

package com.ldap.test.service;

import java.util.List;

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
import com.ldap.core.service.impl.ADUserServiceImpl;
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
public class ADUserTest {

    Logger logger = LoggerFactory.getLogger(ADUserTest.class);

    @Autowired
    ADUserServiceImpl adUserService;
    @Autowired
    UserServiceImpl userService;

    @Test
    public void testASearchAll() {
        List<User> list = adUserService.searchAll();
        Assert.assertNotNull(list);
        for (User t : list) {
            System.out.println(t.toString());
            if (!StringUtils.isNumeric(t.getUid())) {
                continue;
            }
            boolean res = userService.create(t);
            if (!res) {
                break;
            }
        }
    }
}
