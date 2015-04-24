/**
 * Project Name:SpringLdap
 * File Name:User.java
 * Package Name:com.ldap.core.service
 * Date:2015-4-23下午5:03:50
 *
 */

package com.ldap.core.bean;

import java.io.Serializable;

/**
 * ClassName:User <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015-4-23 下午5:03:50 <br/>
 * 
 * @author zhanghanlin
 * @version
 * @since JDK 1.7
 * @see
 */
public class Person extends Object implements Serializable {

    private static final long serialVersionUID = 1828583374399189679L;

    private String userId;
    private String surName;
    private String commonName;
    private String mail;
    private String telephone;
    private String organizationalUnit;
    private String password;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getOrganizationalUnit() {
        return organizationalUnit;
    }

    public void setOrganizationalUnit(String organizationalUnit) {
        this.organizationalUnit = organizationalUnit;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "{'Person':{'uid':'" + userId + "','sn':'" + surName + "','cn':'" + commonName + "','mail':'" + mail + "','telephone':'" + telephone + "'}}";
    }
}
