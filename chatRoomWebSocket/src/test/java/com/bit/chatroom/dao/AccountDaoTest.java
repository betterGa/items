package com.bit.chatroom.dao;

import com.bit.chatroom.entity.user;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class AccountDaoTest {
private AccountDao accountDao=new AccountDao();

@Test
    public void userLogin() throws SQLException {
user user5=accountDao.userLogin("test1","123");
System.out.println(user5);
Assert.assertNotNull(user5);
}

    @Test
    public void userRegister() {
        user user4=new user();
        user4.setUsername("test1");
        user4.setPassword("123");
        boolean isSuccess=accountDao.userRegister(user4);
        Assert.assertEquals(true,isSuccess);

    }
}