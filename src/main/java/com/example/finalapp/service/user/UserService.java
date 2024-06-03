package com.example.finalapp.service.user;

import com.example.finalapp.dto.user.UserDto;
import com.example.finalapp.dto.user.UserSessionDto;
import com.example.finalapp.mapper.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;

    public void registerUser(UserDto userDto) {
        userMapper.insertUser(userDto);
    }

    public Long findId(String loginId, String password) {
        return userMapper.selectId(loginId, password)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원 정보"));
    }

    public UserSessionDto findLoginInfo(String loginId, String password) {
        return userMapper.selectLoginInfo(loginId, password)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원 정보"));
    }
}










