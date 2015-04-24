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

import com.ldap.core.bean.Person;

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
public class PersonAttributesMapper implements AttributesMapper<Object> {

    @Override
    public Object mapFromAttributes(Attributes attributes) throws NamingException {
        Person person = new Person();
        Attribute userId = attributes.get("uid");
        if (userId != null) {
            person.setUserId(userId.get().toString());
        }
        Attribute commonName = attributes.get("cn");
        if (commonName != null) {
            person.setCommonName(commonName.get().toString());
        }
        Attribute surName = attributes.get("sn");
        if (surName != null) {
            person.setSurName(surName.get().toString());
        }
        Attribute mail = attributes.get("mail");
        if (mail != null) {
            person.setMail(mail.get().toString());
        }
        Attribute telephone = attributes.get("telephoneNumber");
        if (telephone != null) {
            person.setTelephone(telephone.get().toString());
        }
        Attribute organizationalUnit = attributes.get("ou");
        if (organizationalUnit != null) {
            person.setOrganizationalUnit(organizationalUnit.get().toString());
        }
        return person;
    }
}