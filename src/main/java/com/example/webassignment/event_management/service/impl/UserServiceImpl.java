package com.example.webassignment.event_management.service.impl;



import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.example.webassignment.event_management.config.PasswordEncoderUtil;
import com.example.webassignment.event_management.entity.User;
import com.example.webassignment.event_management.exception.AppException;
import com.example.webassignment.event_management.pojo.PasswordChangePojo;
import com.example.webassignment.event_management.pojo.UserPojo;
import com.example.webassignment.event_management.repo.UserRepo;
import com.example.webassignment.event_management.service.UserService;

import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final JavaMailSender getJavaMailSender;
    private final ThreadPoolTaskExecutor taskExecutor;

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "\\images\\user-documents\\";

    @Autowired
    @Qualifier("emailConfigBean")
    private Configuration emailConfig;
    @Override
    public void sendEmail() {
        try {
            Map<String, String> model = new HashMap<>();

            MimeMessage message = getJavaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            Template template = emailConfig.getTemplate("emailTemp.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

            mimeMessageHelper.setTo("sendfrom@yopmail.com");
            mimeMessageHelper.setText(html, true);
            mimeMessageHelper.setSubject("Registration");
            mimeMessageHelper.setFrom("sendTo@yopmail.com");

            taskExecutor.execute(new Thread() {
                public void run() {
                    getJavaMailSender.send(message);
                }
            });
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(UserPojo userPojo) {
        User user= new User();
        user.setEmail(userPojo.getEmail());
        user.setName(userPojo.getName());
        user.setAddress(userPojo.getAddress());
        user.setNumber(userPojo.getNumber());
        user.setPassword(PasswordEncoderUtil.getInstance().encode(userPojo.getPassword()));
        userRepo.save(user);
    }

    @Override
    public List<User> fetchAll() {
        return userRepo.findAll();
    }

    @Override
    public User fetchById(Integer id) {
        return userRepo.findById(id).orElseThrow(()->new RuntimeException("Not Found"));
    }

    @Override
    public User findByEmail(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new AppException("Invalid User email", HttpStatus.BAD_REQUEST));

        user= User.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .number(user.getNumber())
                .address(user.getAddress())
                .build();

        return user;
    }


    @Override
    public void deleteAccount(Integer id) {
        userRepo.deleteById(id);
    }

    @Override
    public void deleteById(Integer id) {
        userRepo.deleteById(id);
    }

    @Override
    public String update(UserPojo userpojo) {
        User user = new User();
        if(userpojo.getId()!=null){
            user.setId(userpojo.getId());
        }
        user.setName(userpojo.getName());
        user.setEmail(userpojo.getEmail());
        user.setNumber(userpojo.getNumber());
        user.setAddress(userpojo.getAddress());
        user.setPassword(PasswordEncoderUtil.getInstance().encode(userpojo.getPassword()));
        userRepo.save(user);
        return "created";
    }

    @Override
    public void changePassword(PasswordChangePojo passwordChangePojo) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Optional<User> optionalUser = userRepo.findByEmail(passwordChangePojo.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (encoder.matches(passwordChangePojo.getOldPassword(), user.getPassword())) {
                if (passwordChangePojo.getNewPassword().equals(passwordChangePojo.getRepeatPassword())) {
                    user.setPassword(encoder.encode(passwordChangePojo.getNewPassword()));
                    userRepo.save(user);
                } else {
                    throw new AppException("New Password doesn't match.", HttpStatus.BAD_REQUEST);
                }

            } else {
                throw new AppException("Old Password doesn't match existing password.", HttpStatus.BAD_REQUEST);
            }
        }

    }
    @Override
    public void processPasswordResetRequest(String email) {
        Optional<User> optionalUser = userRepo.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String OTP = generateOTP();
            user.setOTP(OTP);
            userRepo.save(user);
            sendOTPEmail(email, OTP);
        }
    }

    @Override
    public void resetPassword(String email, String OTP, String password) {
        User user = userRepo.findByEmailAndOTP(email, OTP);
        if (user != null) {
            if (password == null) {
                throw new IllegalArgumentException("Password cannot be null");
            }
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(password);
            user.setPassword(encodedPassword);
            user.setOTP(null);
            userRepo.save(user);
        } else {
            throw new RuntimeException();
        }
    }

    private String generateOTP() {
        return String.format("%06d", new Random().nextInt(1000000));
    }

    private void sendOTPEmail(String email, String OTP) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset OTP");
        message.setText("Your OTP for resetting your password is: " + OTP);
        getJavaMailSender.send(message);
    }

    public String getImageBase64(String fileName) {
        if (fileName!=null) {
            String filePath = System.getProperty("user.dir") + "/images/books/";
            File file = new File(filePath + fileName);
            byte[] bytes;
            try {
                bytes = Files.readAllBytes(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return Base64.getEncoder().encodeToString(bytes);
        }
        return null;
    }

    
}
