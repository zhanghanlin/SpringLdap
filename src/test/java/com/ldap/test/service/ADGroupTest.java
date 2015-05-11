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

import com.ldap.core.bean.ad.ADGroup;
import com.ldap.core.service.impl.ADGroupServiceImpl;

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
public class ADGroupTest {

    Logger logger = LoggerFactory.getLogger(ADGroupTest.class);

    @Autowired
    ADGroupServiceImpl adGroupService;

    @Test
    public void testASearchAll() {
        List<ADGroup> list = adGroupService.searchAll();
        Assert.assertNotNull(list);
        Collections.sort(list, new Comparator<ADGroup>() {
            @Override
            public int compare(ADGroup o1, ADGroup o2) {
                return o1.getLength() - o2.getLength();
            }
        });
        for (ADGroup t : list) {
            System.out.println(t.toString());
            adGroupService.create(t);
        }
    }
}
