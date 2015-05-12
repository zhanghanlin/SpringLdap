/**
 * Project Name:SpringLdap
 * File Name:ADGroup.java
 * Package Name:com.ldap.core.bean
 * Date:2015-5-11上午11:07:50
 *
 */

package com.ldap.core.bean;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

/**
 * ClassName:ADGroup <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015-5-11 上午11:07:50 <br/>
 * 
 * @author zhanghanlin
 * @version
 * @since JDK 1.7
 * @see
 */
public class Group extends Object implements Serializable {

    private static final long serialVersionUID = -3844968720304042156L;

    private String name; // 组名称
    private String ou; // 组名称
    private String distinguishedName; // dn
    private String[] ouList; // 组集合
    public int length;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOu() {
        return ou;
    }

    public void setOu(String ou) {
        this.ou = ou;
    }

    public String getDistinguishedName() {
        return distinguishedName;
    }

    public void setDistinguishedName(String distinguishedName) {
        this.distinguishedName = distinguishedName;
    }

    public String[] getOuList() {
        return ouList;
    }

    public void setOuList(String[] ouList) {
        this.ouList = ouList;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
