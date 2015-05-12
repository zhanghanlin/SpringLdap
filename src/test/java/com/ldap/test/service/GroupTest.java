/**
 * Project Name:SpringLdap
 * File Name:Test.java
 * Package Name:com.ldap.test
 * Date:2015-4-23下午5:35:14
 *
 */

package com.ldap.test.service;

import java.util.Collections;
import java.util.Comparator;
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

import com.ldap.core.bean.Group;
import com.ldap.core.service.impl.GroupServiceImpl;

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
public class GroupTest {

    Logger logger = LoggerFactory.getLogger(GroupTest.class);

    @Autowired
    GroupServiceImpl adGroupService;

    @Test
    public void testASearchAll() {
        List<Group> list = adGroupService.searchAll();
        Assert.assertNotNull(list);
        Collections.sort(list, new Comparator<Group>() {
            @Override
            public int compare(Group o1, Group o2) {
                return o1.getLength() - o2.getLength();
            }
        });
        for (Group t : list) {
            System.out.println(t.toString());
            adGroupService.create(t);
        }
    }
}
