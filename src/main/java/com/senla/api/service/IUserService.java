package com.senla.api.service;

import com.senla.model.dto.AdDto;
import com.senla.model.dto.UserDto;
import com.senla.model.dto.UserLoginDto;
import com.senla.model.dto.UserProfileDto;

import java.util.List;

public interface IUserService {

    void registration(UserDto userDto);

    void editLogin(UserLoginDto userLoginDto);

    void editProfile(UserProfileDto userProfileDto);

    UserProfileDto getById(Long id);

    List<AdDto> filterClosedByUserId(Long id);
}
