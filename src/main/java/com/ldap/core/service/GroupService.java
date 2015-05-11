/**
 * Project Name:SpringLdap
 * File Name:PersionService.java
 * Package Name:com.ldap.core.service
 * Date:2015-4-23下午5:10:17
 *
 */

package com.ldap.core.service;

import java.util.List;

import com.ldap.core.bean.Group;

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
public interface GroupService {

    static final String objectClass[] = { "groupOfUniqueNames", "top" };

    /**
     * 
     * 为该组分配用户
     * 
     * @author zhanghanlin
     * @param uid
     * @param groupName
     * @since JDK 1.7
     */
    public void assignUser(String uid, String groupName);

    /**
     * 
     * 移除该组用户
     * 
     * @author zhanghanlin
     * @param uid
     * @param groupName
     * @since JDK 1.7
     */
    public void removeUser(String uid, String groupName);

    /**
     * 
     * 查询用户所属组
     * 
     * @author zhanghanlin
     * @param userName
     * @return
     * @since JDK 1.7
     */
    public List<Group> getGroup(String userName);
}
