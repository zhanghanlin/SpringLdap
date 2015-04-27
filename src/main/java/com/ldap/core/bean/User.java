/**
 * Project Name:SpringLdap
 * File Name:User.java
 * Package Name:com.ldap.core.service
 * Date:2015-4-23下午5:03:50
 *
 */

package com.ldap.core.bean;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

/**
 * 用户. <br/>
 * Date: 2015-4-23 下午5:03:50 <br/>
 * 
 * @author zhanghanlin
 * @version
 * @since JDK 1.7
 * @see
 */
public class User extends Object implements Serializable {

    private static final long serialVersionUID = 1828583374399189679L;

    private String userName;
    private String surName;
    private String commonName;
    private String mail;
    private String telephone;
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
