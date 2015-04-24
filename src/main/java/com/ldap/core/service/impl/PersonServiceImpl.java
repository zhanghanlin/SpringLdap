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
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Service;

import com.ldap.core.bean.Person;
import com.ldap.core.service.ExtendService;
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
public class PersonServiceImpl extends ExtendService<Person> implements PersonService {

    @Autowired
    LdapTemplate ldapTemplate;

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<Person> getAll() {
        List<Person> list = new ArrayList<Person>();
        try {
            List search = ldapTemplate.search("", "(objectClass=" + objectClass + ")", new PersonAttributesMapper());
            list.addAll(search);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return list;
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<Person> getByCommonName(String commonName) {
        List<Person> list = new ArrayList<Person>();
        try {
            AndFilter andFilter = new AndFilter();
            andFilter.and(new EqualsFilter("objectclass", objectClass));
            andFilter.and(new EqualsFilter("cn", commonName));
            List search = ldapTemplate.search("", andFilter.encode(), new PersonAttributesMapper());
            list.addAll(search);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean add(Person person) {
        try {
            // 基类设置
            BasicAttribute ocattr = new BasicAttribute("objectClass");
            ocattr.add("top");
            ocattr.add(objectClass);
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
            attrs.put("ou", StringUtils.trimToEmpty(person.getOrganizationalUnit()));
            attrs.put("userPassword", StringUtils.trimToEmpty(person.getPassword()));
            ldapTemplate.bind("uid=" + person.getUserId().trim(), null, attrs);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Person getByUserId(String userId) {
        Person person = new Person();
        try {
            AndFilter andFilter = new AndFilter();
            andFilter.and(new EqualsFilter("objectclass", objectClass));
            andFilter.and(new EqualsFilter("uid", userId));
            List<Object> search = ldapTemplate.search("", andFilter.encode(), new PersonAttributesMapper());
            if ((search != null) && !search.isEmpty()) {
                person = (Person) search.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return person;
    }

    @Override
    public boolean deleteByUid(String uid) {
        try {
            ldapTemplate.unbind(LdapUtils.newLdapName("uid=" + uid));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Person person) {
        try {
            ModificationItem[] item = new ModificationItem[] { new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("uid", person.getUserId().trim())),
                    new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("sn", person.getSurName().trim())),
                    new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("cn", person.getCommonName().trim())),
                    new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("mail", person.getMail().trim())),
                    new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("telephoneNumber", person.getTelephone().trim())) };
            ldapTemplate.modifyAttributes(LdapUtils.newLdapName("uid=" + person.getUserId().trim()), item);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getDnByUserId(String userId) {
        EqualsFilter f = new EqualsFilter("uid", userId);
        List<Object> result = ldapTemplate.search("", f.toString(), new AbstractContextMapper<Object>() {
            @Override
            protected Object doMapFromContext(DirContextOperations ctx) {
                return ctx.getNameInNamespace();
            }
        });
        if (result.size() != 1) {
            throw new RuntimeException("User not found or not unique");
        }
        return (String) result.get(0);
    }
}
