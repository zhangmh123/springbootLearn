springboot��������ܼ򵥣�ֻ��Ҫһ��ע��@Transactional �Ϳ����ˡ���Ϊ��springboot���Ѿ�Ĭ�϶�jpa��jdbc��mybatis��������������������������ʱ�������Ĭ�Ͽ�������Ȼ���������Ҫ��������orm������beatlsql������Ҫ�Լ�������ص������������
׼���׶�

����һƪ���µĴ���Ϊ���ӣ���springboot����mybatis����һƪ�����ǻ���ע����ʵ��mybatis�����ݷ��ʲ㣬��ƪ���»���xml����ʵ�֣�����������ʽ����
��������

��pom�ļ�������mybatis����������

<dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>1.3.0</version>
</dependency>

 

����MySQL ����

<dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.0.29</version>
        </dependency>

  

   

��ʼ�����ݿ�ű�

-- create table `account`
# DROP TABLE `account` IF EXISTS
CREATE TABLE `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `money` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
INSERT INTO `account` VALUES ('1', 'aaa', '1000');
INSERT INTO `account` VALUES ('2', 'bbb', '1000');
INSERT INTO `account` VALUES ('3', 'ccc', '1000');


��������Դ

spring.datasource.url=jdbc:mysql://localhost:3306/test
spring.datasource.username=root
spring.datasource.password=123456
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
mybatis.mapper-locations=classpath*:mybatis/*Mapper.xml
mybatis.type-aliases-package=com.forezp.entity



ͨ������mybatis.mapper-locations��ָ��mapper��xml�ļ����λ�ã����Ƿ���resources/mybatis�ļ��µġ�mybatis.type-aliases-package��ָ�������ݿ�ӳ���ʵ������ڰ���

�������ϲ��裬springboot�Ϳ���ͨ��mybatis�������ݿ�����
����ʵ����


public class Account {
    private int id ;
    private String name ;
    private double money;

    getter..
    setter..

  }

 


���ݷ���dao ��

�ӿڣ�

public interface AccountMapper2 {
   int update( @Param("money") double money, @Param("id") int  id);
}

   

mapper:

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.forezp.dao.AccountMapper2">


    <update id="update">
        UPDATE account set money=#{money} WHERE id=#{id}
    </update>
</mapper>

  

service��

@Service
public class AccountService2 {

    @Autowired
    AccountMapper2 accountMapper2;

    @Transactional
    public void transfer() throws RuntimeException{
        accountMapper2.update(90,1);//�û�1��10�� �û�2��10��
        int i=1/0;
        accountMapper2.update(110,2);
    }
}

  

@Transactional���������񣬲����һ��ת�˷������û�1��10�飬�û�2��10�顣���û�1��10 ��֮���׳��쳣�����û�2��10��Ǯ����ִ�У�����ע��@Transactional֮�������˵�Ǯ��û��������������@Transactional���û�1����10���û�2û�����ӣ���û�в����û�2 �����ݡ��ɼ�@Transactionalע�⿪�������
����

springboot ��������ܼ򵥣�ֻ��Ҫ��һ��ע��Ϳ����ˣ�ǰ�����õ���jdbctemplate, jpa, mybatis�����ֳ�����orm��