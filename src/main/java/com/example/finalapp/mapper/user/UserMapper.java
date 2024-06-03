package com.example.finalapp.mapper.user;

import com.example.finalapp.dto.user.UserDto;
import com.example.finalapp.dto.user.UserSessionDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface UserMapper {
    void insertUser(UserDto userDto);

    Optional<Long> selectId(@Param("loginId") String loginId,
                            @Param("password") String password);

    Optional<UserSessionDto> selectLoginInfo(@Param("loginId") String loginId,
                                             @Param("password") String password);
}
















