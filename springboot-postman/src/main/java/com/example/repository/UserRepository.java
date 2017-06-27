package com.example.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.User;
import com.example.exception.UserException;
  
/** 
 * Created by steadyjack on 2017/3/22. 
 * �䵱dao��UserRepository 
 */  
@Repository  
public class UserRepository {  
  
    @Autowired  
    private JdbcTemplate jdbcTemplate;  
  
    /** 
     * ��ȡ�û��б� 
     * @return 
     * @throws Exception 
     */  
    @Transactional(readOnly = true)  
    public List<User> getUserList() throws Exception{  
        List<User> userList=jdbcTemplate.query("select id,name,email from users",new UserRowMapper());  
        System.out.println(userList);  
        return userList;  
    }  
  
    /** 
     * �����û�id��ȡ�û� 
     * @param id 
     * @return 
     * @throws Exception 
     */  
    @Transactional(readOnly = true)  
    public User getUserById(Integer id) throws  Exception{  
        //queryForObject:�Ҳ����ᱨ�쳣  query:�Ҳ�����Null  
        //User user=jdbcTemplate.queryForObject("select id,name,email from users where id=?",new Object[]{id},new UserRowMapper());  
        List<User> userList=jdbcTemplate.query("select id,name,email from users where id=?",new Object[]{id},new UserRowMapper());  
        User user=null;  
        if (!userList.isEmpty()){  
            user=userList.get(0);  
        }  
        System.out.println(user);  
        return user;  
    }  
  
    /** 
     * �����û����� 
     * @param user 
     * @return 
     * @throws Exception 
     */  
    public int saveUser(final User user) throws  Exception{  
        int resRow=jdbcTemplate.update("INSERT INTO users(id,name,email) VALUES(NULL,?,?)",new Object[]{  
           user.getName(),user.getEmail()  
        });  
        System.out.println("���������¼����  "+resRow);  
        return resRow;  
    }  
  
    /** 
     * �����û�����-��ֹsqlע�� 
     * @param user 
     * @return 
     * @throws Exception 
     */  
    public int saveUserWithSafe(final User user) throws  Exception{  
        int resRow=jdbcTemplate.update("INSERT INTO users(id,name,email) VALUES(NULL,?,?)", new PreparedStatementSetter() {  
            public void setValues(PreparedStatement ps) throws SQLException {  
                ps.setString(1,user.getName());  
                ps.setString(2,user.getEmail());  
            }  
        });  
        System.out.println("���������¼����  "+resRow);  
        return resRow;  
    }  
  
    /** 
     * �����û�����-��ֹsqlע��-���Է��ظ�����¼��������ע����Ҫָ�������� 
     * @param user 
     * @return 
     * @throws Exception 
     */  
    @Transactional(rollbackFor=UserException.class)  
    public int saveUserWithKey(final User user) throws  Exception{  
        final String sql="INSERT INTO users(id,name,email) VALUES(NULL,?,?)";  
        KeyHolder keyHolder=new GeneratedKeyHolder();  
        int resRow=jdbcTemplate.update(new PreparedStatementCreator() {  
           
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {  
                PreparedStatement ps=conn.prepareStatement(sql,new String[]{"id"}); //ָ�� id Ϊ����  
                ps.setString(1,user.getName());  
                ps.setString(2,user.getEmail());  
                return ps;  
            }  
        },keyHolder);  
        System.out.println("���������¼����  "+resRow+" ����: "+keyHolder.getKey());  
        return Integer.parseInt(keyHolder.getKey().toString());  
    }  
  
    /** 
     * �����û���Ϣ 
     * @param user 
     * @return 
     */  
    public int updateUser(final User user) throws  Exception{  
        String sql="update users set name=?,email=? where id=?";  
        int resRow=jdbcTemplate.update(sql, new PreparedStatementSetter() {  
           
            public void setValues(PreparedStatement preparedStatement) throws SQLException {  
                preparedStatement.setString(1,user.getName());  
                preparedStatement.setString(2,user.getEmail());  
                preparedStatement.setInt(3,user.getId());  
            }  
        });  
        System.out.println("���������¼����  "+resRow);  
        return resRow;  
    }  
  
    /** 
     * ɾ���û� 
     * @param user 
     * @return 
     * @throws Exception 
     */  
    public int deleteUser(final User user) throws  Exception{  
        int resRow=jdbcTemplate.update("DELETE FROM users WHERE id=?", new PreparedStatementSetter() {  
           
            public void setValues(PreparedStatement ps) throws SQLException {  
                ps.setInt(1,user.getId());  
            }  
        });  
        System.out.println("���������¼����  "+resRow);  
        return resRow;  
    }  
  
    /** 
     * �����û��������û�-�����ж��û��Ƿ���� 
     * @param user 
     * @return 
     * @throws Exception 
     */  
    public User getUserByUserName(final User user) throws Exception{  
        String sql="select id,name,email from users where name=?";  
        List<User> queryList=jdbcTemplate.query(sql,new UserRowMapper(),new Object[]{user.getName()});  
        if (queryList!=null && queryList.size()>0){  
            return queryList.get(0);  
        }else{  
            return null;  
        }  
    }  
  
    /** 
     * ��ȡ��¼�� 
     * @return 
     * @throws Exception 
     */  
    public Integer getCount() throws  Exception{  
        String sql="select count(id) from users";  
        //jdbcTemplate.getMaxRows();  
        Integer total=jdbcTemplate.queryForObject(sql,Integer.class);  
        System.out.println("���������¼����  "+total);  
        return total;  
    }  
  
    //��������ģ����ѯ֮��Ŀ����Լ����Բ�� jdbcTemplate ��ʹ���ĵ�  
  
  
}  
  
/** 
 * ��ӳ�� 
 */  
class UserRowMapper implements RowMapper<User>{  
  
   
    public User mapRow(ResultSet resultSet, int i) throws SQLException {  
        User user=new User();  
        user.setId(resultSet.getInt("id"));  
        user.setName(resultSet.getString("name"));  
        user.setEmail(resultSet.getString("email"));  
        return user;  
    }  
  
}  