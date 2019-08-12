package com.bit.chatroom.service;

import com.bit.chatroom.dao.AccountDao;
import com.bit.chatroom.entity.user;

import java.sql.SQLException;

public class AccountService {
    private AccountDao accountDao = new AccountDao();

    //用户注册
    public boolean userLogin(String username, String password) {
        user user6 = null;
        try {
            user6 = accountDao.userLogin(username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (user6 == null) {
            return false;
        }
        return true;
    }



    //用户登录
    public boolean userRegister(String username,String password)
    {user user7=new user();
    user7.setUsername(username);
    user7.setPassword(password);
    return accountDao.userRegister(user7);
    }
}

