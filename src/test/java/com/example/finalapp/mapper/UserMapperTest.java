package com.example.finalapp.mapper;

import com.example.finalapp.dto.user.UserDto;
import com.example.finalapp.mapper.user.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class UserMapperTest {
    @Autowired
    UserMapper userMapper;

    UserDto userDto;

    @BeforeEach
    void setUp(){
        userDto = new UserDto();
        userDto.setLoginId("test");
        userDto.setPassword("1234");
        userDto.setEmail("test@naver.com");
        userDto.setGender("M");
        userDto.setAddress("노원구");
        userDto.setAddressDetail("123호");
        userDto.setZipcode("12345");

        userMapper.insertUser(userDto);
    }

    @Test
    void selectId(){
//        given
//        when
        Long userId = userMapper.selectId("test", "1234").get();
//        then
        assertThat(userId).isEqualTo(userDto.getUserId());
    }
}













