/**
 * Project Name:SpringLdap
 * File Name:UserAction.java
 * Package Name:com.ldap.action
 * Date:2015-5-12下午3:37:17
 *
 */

package com.ldap.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ldap.core.service.impl.UserServiceImpl;

/**
 * ClassName:UserAction <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015-5-12 下午3:37:17 <br/>
 * 
 * @author zhanghanlin
 * @version
 * @since JDK 1.7
 * @see
 */
@Controller
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserServiceImpl userService;

    @RequestMapping(value = "/vaild")
    @ResponseBody
    public Map<String, Object> vaild(String login, String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        boolean res = userService.isValid(login, password);
        map.put("status", res ? 0 : 1);
        if (res) {
            map.put("result", userService.search(login));
        }
        return map;
    }
}
