package ru.afanasev.RestCodemark.service;

import java.util.List;

import ru.afanasev.RestCodemark.model.dto.SuccessDto;
import ru.afanasev.RestCodemark.model.dto.UserByIdDto;
import ru.afanasev.RestCodemark.model.dto.UserDtoRequest;
import ru.afanasev.RestCodemark.model.dto.UserWithoutRoleDto;

public interface UserService {

	public List<UserWithoutRoleDto> gettAllUsers();

	public UserByIdDto getUserById(String login);
	
	public SuccessDto insertUser(UserDtoRequest request);
	
	public UserByIdDto deleteUserById(String login);
	
	public SuccessDto changeUser(UserDtoRequest request);
}
