package com.example.expensetrackerapi.repositories;

import com.example.expensetrackerapi.domain.User;
import com.example.expensetrackerapi.exceptions.EtAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.beans.BeanProperty;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class UserRepositoryImpl implements UserRepository {

    private static final String SQL_CREATE = "INSERT INTO ET_USERS(USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD) VALUES(NEXTVAL('ET_USERS_SEQ'), ?, ?, ?, ?)";
    private static final String SQL_COUNT_BY_EMAIL = "SELECT COUNT(*) FROM ET_USERS WHERE EMAIL = ?";
    private static final String SQL_FIND_BY_ID = "SELECT USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD " +
            "FROM ET_USERS WHERE USER_ID = ?";
    @Autowired
    JdbcTemplate jdbcTemplate;


    @Override
    public Integer create(String firstName, String lastName, String email, String password) throws EtAuthException {
//        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
      try{
          KeyHolder keyHolder=new GeneratedKeyHolder();
          jdbcTemplate.update(connection->{
              PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
              ps.setString(1, firstName);
              ps.setString(2, lastName);
              ps.setString(3, email);
              ps.setString(4, password);
              return ps;
          },keyHolder);
          return (Integer) keyHolder.getKeys().get("USER_ID");
      }catch(Exception e){
          throw new EtAuthException("Invalid details, ");
      }
    }


    @Override
    public User findByEmailAndPassword(String email, String password) throws EtAuthException {
        return null;
    }

    @Override
    public Integer getCountByEmail(String email) {
        System.out.println("i got heee ===============");
        Integer integer1= jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL, Integer.class, email);
        System.out.println("Integer1 is ==========" + integer1);
        if (integer1  == 0)
            return 0;
        return integer1;
    }

    @Override
    public User findById(Integer userId) {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID,userRowMapper, new Object[]{userId} );
    }

    private RowMapper<User> userRowMapper = ((rs, rowNum) -> {
        return new User(rs.getInt("USER_ID"),
                rs.getString("FIRST_NAME"),
                rs.getString("LAST_NAME"),
                rs.getString("EMAIL"),
                rs.getString("PASSWORD"));
    });
}
