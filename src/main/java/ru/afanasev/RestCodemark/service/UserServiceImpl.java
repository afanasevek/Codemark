package ru.afanasev.RestCodemark.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

import org.aspectj.bridge.MessageWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.afanasev.RestCodemark.model.Role;
import ru.afanasev.RestCodemark.model.User;
import ru.afanasev.RestCodemark.model.UserRepository;
import ru.afanasev.RestCodemark.model.Dto.SuccessDto;
import ru.afanasev.RestCodemark.model.Dto.SuccessFalseDto;
import ru.afanasev.RestCodemark.model.Dto.UserByIdDto;
import ru.afanasev.RestCodemark.model.Dto.UserDtoRequest;
import ru.afanasev.RestCodemark.model.Dto.UserWithoutRoleDto;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final Pattern pattern = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]))");

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {

		this.userRepository = userRepository;
	}

	@Override
	public List<UserWithoutRoleDto> gettAllUsers() {
		List<UserWithoutRoleDto> listUsersDto = new ArrayList<>();

		List<User> findAllUsers = userRepository.findAll();

		if (findAllUsers.isEmpty()) {
			return listUsersDto;
		}
		for (User user : userRepository.findAll()) {
			UserWithoutRoleDto userWithoutRoleDto = new UserWithoutRoleDto();
			userWithoutRoleDto.setLogin(user.getLogin());
			userWithoutRoleDto.setPassword(user.getPassword());
			userWithoutRoleDto.setUsername(user.getUsername());
			listUsersDto.add(userWithoutRoleDto);
		}
		return listUsersDto;
	}

	@Override
	public UserByIdDto getUserById(String login) {

		UserByIdDto userByIdDto = getUser(login);
		if (userByIdDto == null) {
			return null;
		}

		return userByIdDto;
	}

	@Override
	public SuccessDto insertUser(UserDtoRequest request) {

		SuccessFalseDto successDto = isCorrect(request);
		Optional<User> findUser = userRepository.findById(request.getLogin());

		if (successDto == null) {
			if (findUser.isPresent()) {
				SuccessFalseDto userExists = new SuccessFalseDto();
				userExists.addError("User exists");
				return userExists;
			}
			User user = new User();
			user.setLogin(request.getLogin());
			user.setPassword(request.getPassword());
			user.setUsername(request.getUsername());
			request.getRoles().stream().forEach(r -> {
				Role role = new Role();
				role.setName(r);
				user.addRole(role);
			});
			userRepository.save(user);
			return new SuccessDto();

		} else {
			if (findUser.isPresent()) {

				successDto.addError("User exists");
			}
			return successDto;
		}

	}

	@Override
	public UserByIdDto deleteUserById(String login) {
		UserByIdDto userByIdDto = getUser(login);
		if (userByIdDto == null) {
			return null;
		}
		userRepository.deleteById(login);
		return userByIdDto;
	}

	@Override
	public SuccessDto changeUser(UserDtoRequest request) {
		SuccessFalseDto successDto = isCorrect(request);
		Optional<User> findUser = userRepository.findById(request.getLogin());
		if (successDto == null) {
			if (!findUser.isPresent()) {
				SuccessFalseDto userExists = new SuccessFalseDto();
				userExists.addError("User not exists");
				return userExists;
			}
			findUser.get().setPassword(request.getPassword());
			findUser.get().setUsername(request.getUsername());
			request.getRoles().stream().forEach(r -> {
				Role role = new Role();
				role.setName(r);
				findUser.get().addRole(role);
			});
			userRepository.save(findUser.get());

			return new SuccessDto();
		} else {
			if (!findUser.isPresent()) {

				successDto.addError("User not exists");
			}
			return successDto;
		}

	}

	private SuccessFalseDto isCorrect(UserDtoRequest request) {

		SuccessFalseDto falseDto = new SuccessFalseDto();
		if (request.getPassword().length() == 0) {
			falseDto.addError("password length isnt enouth");

		}
		if (request.getLogin().length() == 0) {
			falseDto.addError("login length isnt enouth");

		}
		if (request.getUsername().length() == 0) {
			falseDto.addError("username isnt enouth");
		}
		if (pattern.matcher(request.getPassword()).find()) {
			falseDto.addError("password isnt correct");
		}

		if (falseDto.getErrors().isEmpty()) {
			return null;
		}
		return falseDto;
	}

	private UserByIdDto getUser(String login) {
		Optional<User> user = userRepository.findById(login);
		if (user.isEmpty()) {
			return null;
		}
		UserByIdDto userByIdDto = new UserByIdDto();
		userByIdDto.setLogin(user.get().getLogin());
		userByIdDto.setUsername(user.get().getUsername());
		userByIdDto.setPassword(user.get().getPassword());
		if (!user.get().getRolesSet().isEmpty()) {
			user.get().getRolesSet().stream().forEach(o -> userByIdDto.addRole(o.getName()));
		}
		return userByIdDto;
	}

}
