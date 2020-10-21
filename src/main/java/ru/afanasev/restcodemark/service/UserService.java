package ru.afanasev.restcodemark.service;

import java.util.List;

import ru.afanasev.restcodemark.model.dto.SuccessDto;
import ru.afanasev.restcodemark.model.dto.UserByIdDto;
import ru.afanasev.restcodemark.model.dto.UserDtoRequest;
import ru.afanasev.restcodemark.model.dto.UserWithoutRoleDto;

public interface UserService {

	public List<UserWithoutRoleDto> gettAllUsers();

	public UserByIdDto getUserById(String login);

	public SuccessDto insertUser(UserDtoRequest request);

	public UserByIdDto deleteUserById(String login);

	public SuccessDto changeUser(UserDtoRequest request);
}
