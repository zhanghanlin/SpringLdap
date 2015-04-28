/**
 * Project Name:SpringLdap
 * File Name:PersionService.java
 * Package Name:com.ldap.core.service
 * Date:2015-4-23下午5:10:17
 *
 */

package com.ldap.core.service;

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
public interface UserService {

    static final String objectClass = "person";

    /**
     * 
     * 修改密码
     * 
     * @author zhanghanlin
     * @param userName
     * @param oldPwd
     * @param passpord
     * @return
     * @since JDK 1.7
     */
    public boolean updatePwd(String userName, String passpord);

    /**
     * 
     * 验证用户
     * 
     * @author zhanghanlin
     * @param userName
     * @param password
     * @return
     * @since JDK 1.7
     */
    public boolean isValid(String userName, String password);
}
