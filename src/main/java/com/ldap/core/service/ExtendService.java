/**
 * Project Name:SpringLdap
 * File Name:ExtendService.java
 * Package Name:com.ldap.core.service
 * Date:2015-4-24下午1:32:46
 *
 */

package com.ldap.core.service;

/**
 * Base Service. <br/>
 * Date: 2015-4-24 下午1:32:46 <br/>
 * 
 * @author zhanghanlin
 * @version
 * @since JDK 1.7
 * @see
 */
public abstract class ExtendService<T> {

    /**
     * 创建
     * 
     * @author zhanghanlin
     * @param t
     * @return
     * @since JDK 1.7
     */
    public abstract boolean create(T t);

    /**
     * 
     * 删除
     * 
     * @author zhanghanlin
     * @param s
     * @return
     * @since JDK 1.7
     */
    public abstract boolean delete(String s);

    /**
     * 
     * 更新
     * 
     * @author zhanghanlin
     * @param t
     * @return
     * @since JDK 1.7
     */
    public abstract boolean update(T t);

    /**
     * 
     * 查询
     * 
     * @author zhanghanlin
     * @param s
     * @return
     * @since JDK 1.7
     */
    public abstract T search(String s);

    /**
     * 
     * 得到DN
     * 
     * @author zhanghanlin
     * @param s
     * @return
     * @since JDK 1.7
     */
    public abstract String getDn(String s);

    public String getGroupDn(String s) {
        return new StringBuffer().append("cn=").append(s).append(",ou=Group").toString();
    }

    public String getUserDn(String s) {
        return new StringBuffer().append("uid=").append(s).append(",ou=People").toString();
    }
}
