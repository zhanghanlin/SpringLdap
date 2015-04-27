# SpringLdap
* 本项目为测试使用Spring操作LDAP

* 主要POM依赖
```Java
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-context</artifactId>
			<version>3.2.9.RELEASE</version>
		</dependency>
		<dependency>
			<groupId>org.springframework.ldap</groupId>
			<artifactId>spring-ldap-core</artifactId>
			<version>2.0.2.RELEASE</version>
		</dependency>
```
# SpringLdap
* 本项目为测试使用Spring操作LDAP

* 主要POM依赖
```Java
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-context</artifactId>
			<version>3.2.9.RELEASE</version>
		</dependency>
		<dependency>
			<groupId>org.springframework.ldap</groupId>
			<artifactId>spring-ldap-core</artifactId>
			<version>2.0.2.RELEASE</version>
		</dependency>
```

* LDAP服务器部署完毕后,需要执行初始化数据	src/main/bin/init.ldif(本文件对应该项目)
```java
dn: dc=domain,dc=cn
dc: domain
objectClass: top
objectClass: domain

dn: ou=People,dc=domain,dc=cn
ou: People
objectClass: top
objectClass: organizationalUnit

dn: ou=Group,dc=domain,dc=cn
ou: Group
objectClass: top
objectClass: organizationalUnit
```
* 执行脚本
```base
/usr/local/openLDAP/bin/ldapadd -x -W -D "cn=Manager,dc=domain,dc=cn" -f /usr/local/openLDAP/etc/openldap/init.ldif
```
