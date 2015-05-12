/**
 * Project Name:SpringLdap
 * File Name:GroupServiceImpl.java
 * Package Name:com.ldap.core.service.impl
 * Date:2015-5-11上午11:07:10
 *
 */

package com.ldap.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.NamingException;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.stereotype.Service;

import com.ldap.core.bean.User;
import com.ldap.core.service.ADUserService;
import com.ldap.core.util.ADUserAttributesMapper;

/**
 * ClassName:GroupServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015-5-11 上午11:07:10 <br/>
 * 
 * @author zhanghanlin
 * @version
 * @since JDK 1.7
 * @see
 */
@Service("adUserService")
public class ADUserServiceImpl implements ADUserService {

    Logger logger = LoggerFactory.getLogger(ADUserServiceImpl.class);

    @Autowired
    protected LdapTemplate adTemplate;

    @Override
    public List<User> searchAll() {
        List<User> search = new ArrayList<User>();
        try {
            AndFilter andFilter = new AndFilter();
            andFilter.and(new EqualsFilter("objectclass", "User"));
            search = adTemplate.search("", andFilter.encode(), new ADUserAttributesMapper());
            logger.info("searchAll size : {}", search.size());
        } catch (NamingException e) {
            logger.error("searchAll error : {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("searchAll error : {}", e.getMessage(), e);
        }
        return search;
    }
}