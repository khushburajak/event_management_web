package com.example.webassignment.event_management.service;


import java.util.List;

import com.example.webassignment.event_management.entity.User;
import com.example.webassignment.event_management.pojo.PasswordChangePojo;
import com.example.webassignment.event_management.pojo.UserPojo;

public interface UserService {

    void sendEmail();

    void saveUser(UserPojo userPojo);
    List<User> fetchAll();
    User fetchById(Integer id);
    User findByEmail(String email);
    void changePassword(PasswordChangePojo passwordChangePojo);
    void deleteAccount(Integer id);

    void deleteById(Integer id);

    String update(UserPojo userPojo);
    void processPasswordResetRequest(String email);

    void resetPassword(String email, String OTP, String password);
}

