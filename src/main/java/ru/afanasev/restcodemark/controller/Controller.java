package ru.afanasev.restcodemark.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.afanasev.restcodemark.model.dto.SuccessDto;
import ru.afanasev.restcodemark.model.dto.UserByIdDto;
import ru.afanasev.restcodemark.model.dto.UserDtoRequest;
import ru.afanasev.restcodemark.model.dto.UserWithoutRoleDto;
import ru.afanasev.restcodemark.service.UserService;

@RestController
@RequestMapping("api/users/")
public class Controller {

	private final UserService userService;

	@Autowired
	public Controller(UserService userService) {

		this.userService = userService;
	}

	@GetMapping("all")
	public ResponseEntity<List<UserWithoutRoleDto>> getAllusers() {

		return new ResponseEntity<List<UserWithoutRoleDto>>(userService.gettAllUsers(), HttpStatus.OK);

	}

	@GetMapping("{id}")
	public ResponseEntity<UserByIdDto> getUser(@PathVariable String id) {
		UserByIdDto user = userService.getUserById(id);

		return new ResponseEntity<UserByIdDto>(user, HttpStatus.OK);

	}

	@PostMapping("add")
	public ResponseEntity<SuccessDto> insertUser(@RequestBody UserDtoRequest request) {

		return new ResponseEntity<>(userService.insertUser(request), HttpStatus.OK);

	}

	@DeleteMapping("{id}")
	public ResponseEntity<UserByIdDto> deleteUser(@PathVariable String id) {
		UserByIdDto user = userService.deleteUserById(id);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<UserByIdDto>(user, HttpStatus.OK);
	}

	@PutMapping("change")
	public ResponseEntity<SuccessDto> changeUser(@RequestBody UserDtoRequest request) {

		return new ResponseEntity<>(userService.changeUser(request), HttpStatus.OK);

	}
}
