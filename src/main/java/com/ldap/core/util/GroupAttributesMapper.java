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

import com.ldap.core.bean.Group;
import com.ldap.util.Constants;
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
public class GroupAttributesMapper implements AttributesMapper<Group> {

    @Override
    public Group mapFromAttributes(Attributes attributes) throws NamingException {
        Group group = new Group();
        Attribute name = attributes.get("name");
        if (name != null) {
            group.setName(name.get().toString());
        }
        Attribute ou = attributes.get("ou");
        if (ou != null) {
            group.setOu(ou.get().toString());
        }
        Attribute description = attributes.get("distinguishedName");
        if (description != null) {
            String dn = description.get().toString();
            if (StringUtils.isNotBlank(dn)) {
                dn = dn.replace("," + Constants.BASE_DN, "");
            }
            String[] ouList = dn.replace("OU=", "").split(",");
            group.setDistinguishedName(dn);
            group.setOuList(ouList);
            group.setLength(ouList.length);
        }
        return group;
    }
}