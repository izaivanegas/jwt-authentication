package com.example.jwtauthentication.service;

import com.example.jwtauthentication.entity.UserInfo;
import com.example.jwtauthentication.repository.UserInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder encoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userDetails = userInfoRepository.findByName(username);
        //Esto es como me la sabia
        //return new UserInfoDetails(userDetails.get());
        return userDetails.map(UserInfoDetails::new).orElseThrow(()-> new UsernameNotFoundException("Usuario no encontrado para "+ username));
    }


    public String addUser(UserInfo userInfo){
        log.info("addUser...................");
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        userInfoRepository.save(userInfo);
        return "User Added Sucessfully";
    }

}
