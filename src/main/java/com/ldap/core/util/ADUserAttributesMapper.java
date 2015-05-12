/**
 * Project Name:SpringLdap
 * File Name:PersonAttributesMapper.java
 * Package Name:com.ldap.core.util
 * Date:2015-4-23下午5:07:26
 *
 */

package com.ldap.core.util;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

import org.springframework.ldap.core.AttributesMapper;

import com.ldap.core.bean.User;
import com.ldap.util.StringUtils;

/**
 * ClassName:PersonAttributesMapper <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015-4-23 下午5:07:26 <br/>
 * 
 * @author zhanghanlin
 * @version
 * @since JDK 1.7
 * @see
 */
public class ADUserAttributesMapper implements AttributesMapper<User> {

    @Override
    public User mapFromAttributes(Attributes attributes) throws NamingException {
        User user = new User();
        Attribute sAMAccountName = attributes.get("sAMAccountName");
        if (sAMAccountName != null) {
            user.setUid(sAMAccountName.get().toString());
        }
        Attribute sn = attributes.get("sn");
        if (sn != null) {
            user.setSn(sn.get().toString());
        }
        Attribute cn = attributes.get("cn");
        if (cn != null) {
            user.setCn(cn.get().toString());
        }
        Attribute mail = attributes.get("mail");
        if (mail != null) {
            user.setMail(mail.get().toString());
        }
        Attribute mobile = attributes.get("mobile");
        if (mobile != null) {
            user.setTelephoneNumber(mobile.get().toString());
        }
        Attribute description = attributes.get("distinguishedName");
        if (description != null) {
            String dn = description.get().toString();
            StringBuffer sb = new StringBuffer();
            for (String ou : dn.split(",")) {
                if (ou.toUpperCase().contains("OU=")) {
                    sb.append(ou).append(",");
                }
            }
            if (StringUtils.isNotBlank(sb)) {
                user.setDescription(sb.substring(0, sb.length() - 1).toString());
            }
        }
        return user;
    }
}