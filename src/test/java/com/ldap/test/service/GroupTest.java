/**
 * Project Name:SpringLdap
 * File Name:Test.java
 * Package Name:com.ldap.test
 * Date:2015-4-23下午5:35:14
 *
 */

package com.ldap.test.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

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

    static HashMap<String, String[]> groups = new HashMap<String, String[]>();

    static {
        groups.put("OA", new String[] { "zhaoyi", "qianer", "sunsan", "lisi" });
        groups.put("HR", new String[] { "zhouwu", "wuliu", "zhengqi", "wangba" });
    }

    /**
     * 部门下首个用户
     * 
     * @author zhanghanlin
     * @param group
     * @return
     * @since JDK 1.7
     */
    static String getFirstUser(String group) {
        return groups.get(group)[0];
    }

    @Test
    public void testACreate() {
        boolean res = true;
        for (Entry<String, String[]> entry : groups.entrySet()) {
            Group group = new Group();
            group.setName(entry.getKey());
            group.setDescription(entry.getKey() + " System");
            group.setMemberOfGroup(Arrays.asList(entry.getValue()));
            if (!res) {
                break;
            }
            res = groupService.create(group);
        }
        Assert.assertTrue(res);
    }

    @Test
    public void testBRemoveUser() {
        String group = "HR";
        groupService.removeUser(getFirstUser(group), group);
    }

    @Test
    public void testCAssignUser() {
        String group = "HR";
        groupService.assignUser(getFirstUser(group), group);
    }

    @Test
    public void testDGetGroup() {
        List<Group> list = groupService.getGroup(getFirstUser("HR"));
        Assert.assertNotNull(list);
        for (Group g : list) {
            logger.info(g.toString());
        }
    }

    @Test
    public void testDGetDn() {
        for (Entry<String, String[]> entry : groups.entrySet()) {
            logger.info(entry.getKey() + " DN : {}", groupService.getDn("cn", entry.getKey()));
        }
    }

    @Test
    public void testEDelete() {
        for (Entry<String, String[]> entry : groups.entrySet()) {
            groupService.delete(entry.getKey());
        }
    }
}
