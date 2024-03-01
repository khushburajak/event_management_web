package com.example.webassignment.event_management.pojo;

import lombok.*;
import com.example.webassignment.event_management.entity.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPojo {
    private Integer id;
    private String name;
    private String email;
    private String number;
    private String address;
    private String password;
    private String OTP;
    public UserPojo(User user){
        this.id=user.getId();
        this.name= user.getName();
        this.email= user.getEmail();
        this.number= user.getNumber();
        this.address= user.getAddress();
        this.password= user.getPassword();
        this.OTP = user.getOTP();
    }
}
