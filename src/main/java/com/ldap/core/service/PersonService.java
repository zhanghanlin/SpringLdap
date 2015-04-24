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

    /**
     * 新增用户
     * 
     * @author zhanghanlin
     * @param person
     * @return
     * @since JDK 1.7
     */
    public boolean addPerson(Person person);

    /**
     * 得到所有人员信息
     * 
     * @author zhanghanlin
     * @return
     * @since JDK 1.7
     */
    public List<Person> getAllPerson();

    /**
     * 根据CN查询
     * 
     * @author zhanghanlin
     * @param commonName
     * @return
     * @since JDK 1.7
     */
    public List<Person> getPersonByCommonName(String commonName);

    /**
     * 根据uid查询
     * 
     * @author zhanghanlin
     * @param userId
     * @return
     * @since JDK 1.7
     */
    public Person getPersonByUserId(String userId);

    /**
     * 删除用户
     * 
     * @author zhanghanlin
     * @param attr
     * @param attrVal
     * @return
     * @since JDK 1.7
     */
    public boolean deletePersonByUid(String uid);

    /**
     * 更新
     * 
     * @author zhanghanlin
     * @param person
     * @return
     * @since JDK 1.7
     */
    public boolean updatePerson(Person person);
}
