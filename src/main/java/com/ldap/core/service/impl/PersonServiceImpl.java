/**
 * Project Name:SpringLdap
 * File Name:PersonServiceImpl.java
 * Package Name:com.ldap.core.service.impl
 * Date:2015-4-23下午5:11:25
 *
 */

package com.ldap.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.stereotype.Service;

import com.ldap.core.bean.Person;
import com.ldap.core.service.PersonService;
import com.ldap.core.util.PersonAttributesMapper;
import com.ldap.util.StringUtils;

/**
 * ClassName:PersonServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015-4-23 下午5:11:25 <br/>
 * 
 * @author zhanghanlin
 * @version
 * @since JDK 1.7
 * @see
 */
@Service("personService")
public class PersonServiceImpl implements PersonService {

    @Autowired
    LdapTemplate ldapTemplate;

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<Person> getAllPerson() {
        List<Person> list = new ArrayList<Person>();
        try {
            List search = ldapTemplate.search("", "(objectClass=person)", new PersonAttributesMapper());
            list.addAll(search);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return list;
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<Person> findPersonByCommonName(String commonName) {
        List<Person> list = new ArrayList<Person>();
        try {
            AndFilter andFilter = new AndFilter();
            andFilter.and(new EqualsFilter("objectclass", "person"));
            andFilter.and(new EqualsFilter("cn", commonName));
            List search = ldapTemplate.search("", andFilter.encode(), new PersonAttributesMapper());
            list.addAll(search);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean addPerson(Person person) {
        try {
            // 基类设置
            BasicAttribute ocattr = new BasicAttribute("objectClass");
            ocattr.add("top");
            ocattr.add("person");
            ocattr.add("uidObject");
            ocattr.add("inetOrgPerson");
            ocattr.add("organizationalPerson");
            // 用户属性
            Attributes attrs = new BasicAttributes();
            attrs.put(ocattr);
            attrs.put("cn", StringUtils.trimToEmpty(person.getCommonName()));
            attrs.put("sn", StringUtils.trimToEmpty(person.getSurName()));
            attrs.put("mail", StringUtils.trimToEmpty(person.getMail()));
            attrs.put("telephoneNumber", StringUtils.trimToEmpty(person.getTelephone()));
            attrs.put("userPassword", StringUtils.trimToEmpty(person.getPassword()));
            ldapTemplate.bind("uid=" + person.getUserId().trim(), null, attrs);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
