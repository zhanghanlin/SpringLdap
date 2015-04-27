/**
 * Project Name:SpringLdap
 * File Name:UserNotFoundException.java
 * Package Name:com.ldap.core.exception
 * Date:2015-4-27上午11:47:39
 *
 */

package com.ldap.core.exception;

/**
 * 自定义Runtime. <br/>
 * Date: 2015-4-27 上午11:47:39 <br/>
 * 
 * @author zhanghanlin
 * @version
 * @since JDK 1.7
 * @see
 */
public class UserNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -8754521890050014063L;

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Throwable cause) {
        super(cause);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}