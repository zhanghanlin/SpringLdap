/**
 * Project Name:SpringLdap
 * File Name:PersionService.java
 * Package Name:com.ldap.core.service
 * Date:2015-4-23下午5:10:17
 *
 */

package com.ldap.core.service;

import java.util.List;

import com.ldap.core.bean.Person;

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
public interface PersonService {

    static final String objectClass = "person";

    /**
     * 新增用户
     * 
     * @author zhanghanlin
     * @param person
     * @return
     * @since JDK 1.7
     */
    public boolean add(Person person);

    /**
     * 得到所有人员信息
     * 
     * @author zhanghanlin
     * @return
     * @since JDK 1.7
     */
    public List<Person> getAll();

    /**
     * 根据CN查询
     * 
     * @author zhanghanlin
     * @param commonName
     * @return
     * @since JDK 1.7
     */
    public List<Person> getByCommonName(String commonName);

    /**
     * 根据uid查询
     * 
     * @author zhanghanlin
     * @param userId
     * @return
     * @since JDK 1.7
     */
    public Person getByUserId(String userId);

    /**
     * 删除用户
     * 
     * @author zhanghanlin
     * @param attr
     * @param attrVal
     * @return
     * @since JDK 1.7
     */
    public boolean deleteByUid(String uid, String ou);

    /**
     * 更新
     * 
     * @author zhanghanlin
     * @param person
     * @return
     * @since JDK 1.7
     */
    public boolean update(Person person);

    /**
     * 根据CommonName获取Dn
     * 
     * @author zhanghanlin
     * @param userId
     * @return
     * @since JDK 1.7
     */
    public String getDnByUserId(String userId);
}
