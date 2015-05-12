/**
 * Project Name:SpringLdap
 * File Name:PersionService.java
 * Package Name:com.ldap.core.service
 * Date:2015-4-23下午5:10:17
 *
 */

package com.ldap.core.service;

import com.ldap.core.bean.User;

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

    static final String objectClass[] = { "person", "top", "uidObject", "inetOrgPerson", "organizationalPerson" };

    /**
     * 创建
     * 
     * @author zhanghanlin
     * @param t
     * @return
     * @since JDK 1.7
     */
    public boolean create(User t);

    /**
     * 
     * 删除
     * 
     * @author zhanghanlin
     * @param s
     * @return
     * @since JDK 1.7
     */
    public boolean delete(String s);

    /**
     * 
     * 更新
     * 
     * @author zhanghanlin
     * @param t
     * @return
     * @since JDK 1.7
     */
    public boolean update(User t);

    /**
     * 
     * 查询
     * 
     * @author zhanghanlin
     * @param s
     * @return
     * @since JDK 1.7
     */
    public User search(String s);

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
