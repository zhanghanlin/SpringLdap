/**
 * Project Name:SpringLdap
 * File Name:Group.java
 * Package Name:com.ldap.core.bean
 * Date:2015-4-27下午1:14:35
 *
 */

package com.ldap.core.bean;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

/**
 * 部门. <br/>
 * Date: 2015-4-27 下午1:14:35 <br/>
 * 
 * @author zhanghanlin
 * @version
 * @since JDK 1.7
 * @see
 */
public class Group extends Object implements Serializable {

    private static final long serialVersionUID = -7734938456409710440L;
    private String name;
    private String description;
    private List<String> memberOfGroup;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getMemberOfGroup() {
        return memberOfGroup;
    }

    public void setMemberOfGroup(List<String> memberOfGroup) {
        this.memberOfGroup = memberOfGroup;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
