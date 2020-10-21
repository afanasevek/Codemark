package ru.afanasev.restcodemark.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.afanasev.restcodemark.model.Role;
import ru.afanasev.restcodemark.model.User;
import ru.afanasev.restcodemark.model.UserRepository;
import ru.afanasev.restcodemark.model.dto.SuccessDto;
import ru.afanasev.restcodemark.model.dto.SuccessFalseDto;
import ru.afanasev.restcodemark.model.dto.UserByIdDto;
import ru.afanasev.restcodemark.model.dto.UserDtoRequest;
import ru.afanasev.restcodemark.model.dto.UserWithoutRoleDto;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class ControllerTest {

	private final MockMvc mocMvc;

	private final UserRepository userRepository;

	private final ObjectMapper objectMapper;

	@Autowired
	public ControllerTest(MockMvc mocMvc, UserRepository userRepository, ObjectMapper objectMapper) {
		this.mocMvc = mocMvc;
		this.userRepository = userRepository;
		this.objectMapper = objectMapper;
	}

	@BeforeEach
	public void setUp() {
		User user1 = new User();
		user1.setLogin("test1");
		user1.setPassword("password1");
		user1.setUsername("test1");
		Role role1 = new Role();
		role1.setId(1);
		role1.setName("user");
		user1.addRole(role1);
		userRepository.save(user1);

		User user2 = new User();
		user2.setLogin("test2");
		user2.setPassword("password2");
		user2.setUsername("test2");
		Role role2 = new Role();
		role2.setId(2);
		role2.setName("user");
		user2.addRole(role2);
		userRepository.save(user2);
	}

	@Test
	void testGetAllUsers() throws Exception {

		List<UserWithoutRoleDto> usersDtos = new ArrayList<>();

		UserWithoutRoleDto user = new UserWithoutRoleDto();
		user.setLogin("test1");
		user.setPassword("password1");
		user.setUsername("test1");
		usersDtos.add(user);
		UserWithoutRoleDto user2 = new UserWithoutRoleDto();
		user2.setLogin("test2");
		user2.setPassword("password2");
		user2.setUsername("test2");
		usersDtos.add(user2);

		mocMvc.perform(get("/api/users/all")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(objectMapper.writeValueAsString(usersDtos)));
	}

	@Test
	void testGet1User() throws Exception {

		UserByIdDto user = new UserByIdDto();
		user.setLogin("test2");
		user.setPassword("password2");
		user.setUsername("test2");
		user.addRole("user");

		mocMvc.perform(get("/api/users/test2")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(objectMapper.writeValueAsString(user)));
	}

	@Test
	void testGetUser() throws Exception {

		UserByIdDto user = new UserByIdDto();
		user.setLogin("test2");
		user.setPassword("password2");
		user.setUsername("test2");
		user.addRole("user");

		mocMvc.perform(get("/api/users/test2")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(objectMapper.writeValueAsString(user)));
	}

	@Test
	void testInsertUserIncorrectLogin() throws Exception {

		UserDtoRequest request = new UserDtoRequest();
		request.setLogin("");
		request.setPassword("fdsDSf4");
		request.setUsername("name");

		SuccessFalseDto falseDto = new SuccessFalseDto();
		falseDto.addError("login length isnt enouth");

		mocMvc.perform(post("/api/users/add").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(request))).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(objectMapper.writeValueAsString(falseDto)));
	}

	@Test
	void testInsertUserIncorrectPassword() throws Exception {

		UserDtoRequest request = new UserDtoRequest();
		request.setLogin("login123");
		request.setPassword("fd6");
		request.setUsername("name");

		SuccessFalseDto falseDto = new SuccessFalseDto();
		falseDto.addError("password isnt correct");

		mocMvc.perform(post("/api/users/add").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(request))).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(objectMapper.writeValueAsString(falseDto)));
	}

	@Test
	void testInsertUserIncorrectUsername() throws Exception {

		UserDtoRequest request = new UserDtoRequest();
		request.setLogin("loginddd");
		request.setPassword("fdsfD4");
		request.setUsername("");

		SuccessFalseDto falseDto = new SuccessFalseDto();
		falseDto.addError("username isnt enouth");

		mocMvc.perform(post("/api/users/add").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(request))).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(objectMapper.writeValueAsString(falseDto)));
	}

	@Test
	void testInsertUserCorrect() throws Exception {
		UserDtoRequest request = new UserDtoRequest();
		request.setLogin("login");
		request.setPassword("fdDg45");
		request.setUsername("user");

		SuccessDto trDto = new SuccessDto();

		mocMvc.perform(post("/api/users/add").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(request))).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(objectMapper.writeValueAsString(trDto)));
	}

	@Test
	void testDeleteUser() throws Exception {
		UserByIdDto user = new UserByIdDto();
		user.setLogin("test2");
		user.setPassword("password2");
		user.setUsername("test2");
		user.addRole("user");

		mocMvc.perform(delete("/api/users/test2")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(objectMapper.writeValueAsString(user)));
	}

	@Test
	void testChangeUserIncorrectLogin() throws Exception {

		UserDtoRequest request = new UserDtoRequest();
		request.setLogin("test345");
		request.setPassword("fdsDSf4");
		request.setUsername("name");
		request.addRole("adminushka");
		SuccessFalseDto falseDto = new SuccessFalseDto();
		falseDto.addError("User not exists");

		mocMvc.perform(put("/api/users/change").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(request))).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(objectMapper.writeValueAsString(falseDto)));
	}

	@Test
	void testChangeUserIncorrectPassword() throws Exception {

		UserDtoRequest request = new UserDtoRequest();
		request.setLogin("test2");
		request.setPassword("fd6");
		request.setUsername("name");

		SuccessFalseDto falseDto = new SuccessFalseDto();
		falseDto.addError("password isnt correct");

		mocMvc.perform(put("/api/users/change").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(request))).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(objectMapper.writeValueAsString(falseDto)));
	}

	@Test
	void testChangeUserIncorrectUsername() throws Exception {

		UserDtoRequest request = new UserDtoRequest();
		request.setLogin("test2");
		request.setPassword("fdsfD4");
		request.setUsername("");

		SuccessFalseDto falseDto = new SuccessFalseDto();
		falseDto.addError("username isnt enouth");

		mocMvc.perform(put("/api/users/change").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(request))).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(objectMapper.writeValueAsString(falseDto)));
	}

	@Test
	void testChangeUserCorrect() throws Exception {
		UserDtoRequest request = new UserDtoRequest();
		request.setLogin("test2");
		request.setPassword("fdDg45");
		request.setUsername("user211");

		SuccessDto trDto = new SuccessDto();

		mocMvc.perform(put("/api/users/change").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(request))).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(objectMapper.writeValueAsString(trDto)));
	}
}
