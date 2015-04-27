/**
 * Project Name:SpringLdap
 * File Name:Test.java
 * Package Name:com.ldap.test
 * Date:2015-4-23下午5:35:14
 *
 */

package com.ldap.test.service;

import java.util.Arrays;
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
public class GroupTest {

    Logger logger = LoggerFactory.getLogger(GroupTest.class);

    @Autowired
    GroupServiceImpl groupService;

    @Test
    public void testACreate() {
        Group group = new Group();
        group.setName("OA");
        group.setDescription("OA System");
        String[] unOa = new String[] { "zhaoyi", "qianer", "sunsan", "lisi" };
        group.setMemberOfGroup(Arrays.asList(unOa));

        boolean oa = groupService.create(group);

        group.setName("HR");
        group.setDescription("HR System");
        String[] unHr = new String[] { "zhouwu", "wuliu", "zhengqi", "wangba" };
        group.setMemberOfGroup(Arrays.asList(unHr));

        boolean hr = groupService.create(group);
        Assert.assertTrue(oa && hr);
    }

    @Test
    public void testBRemoveUser() {
        groupService.removeUser("zhaoyi", "HR");
    }

    @Test
    public void testCAssignUser() {
        groupService.assignUser("zhaoyi", "HR");
    }

    @Test
    public void testDGetGroup() {
        List<Group> list = groupService.getGroup("zhaoyi");
        Assert.assertNotNull(list);
        for (Group g : list) {
            logger.info(g.toString());
        }
    }

    @Test
    public void testEDelete() {
        groupService.delete("OA");
        groupService.delete("HR");
    }
}
