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
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.ldap.util.StringUtils;

/**
 * 用户. <br/>
 * Date: 2015-4-23 下午5:03:50 <br/>
 * 
 * @author zhanghanlin
 * @version
 * @since JDK 1.7
 * @see
 */

@JSONType(ignores = { "ou", "description", "dn" })
public class User extends Object implements Serializable {

    private static final long serialVersionUID = 1828583374399189679L;

    private String uid; // UNIX账户的名称
    private String sn; // 姓
    private String cn; // 全名
    private String mail; // 邮箱
    private String telephoneNumber; // 手机号
    private String userPassword; // 密码
    private String description; // 用于存储DN

    @JSONField(name = "userName")
    public String getUid() {
        return StringUtils.leftPad(uid, 5, "0");
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @JSONField(name = "surName")
    public String getSn() {
        if (StringUtils.isBlank(sn)) {
            return cn;
        }
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    @JSONField(name = "commonName")
    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @JSONField(name = "telephone")
    public String getTelephoneNumber() {
        if (StringUtils.isBlank(telephoneNumber)) {
            telephoneNumber = "0";
        }
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getOu() {
        return StringUtils.isNotBlank(description) ? description.split(",") : new String[] {};
    }

    public String getDn() {
        return "UID=" + getUid() + "," + description;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
