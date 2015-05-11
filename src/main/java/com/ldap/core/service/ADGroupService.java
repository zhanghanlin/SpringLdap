/**
 * Project Name:SpringLdap
 * File Name:PersionService.java
 * Package Name:com.ldap.core.service
 * Date:2015-4-23下午5:10:17
 *
 */

package com.ldap.core.service;

import java.util.List;

import com.ldap.core.bean.ad.ADGroup;

/**
 * ClassName:PersionService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015-4-23 下午5:10:17 <br/>
 * 
 * @author zhanghanlin
 * @version
 * @since JDK 1.7
 * @see
 */
public interface ADGroupService {

    static final String objectClass[] = { "organizationalUnit", "top" };

    public boolean create(ADGroup t);

    /**
     * 
     * 查询
     * 
     * @author zhanghanlin
     * @param s
     * @return
     * @since JDK 1.7
     */
    public List<ADGroup> searchAll();
}
