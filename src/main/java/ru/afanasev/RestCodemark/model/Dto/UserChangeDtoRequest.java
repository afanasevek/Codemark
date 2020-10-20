package ru.afanasev.RestCodemark.model.Dto;

import java.util.HashSet;
import java.util.Set;

public class UserChangeDtoRequest {
	private String username;
	private String password;
	private Set<String> roles = new HashSet<>();

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void addRole(String role) {
		roles.add(role);

	}

	public void removeRole(String role) {
		roles.remove(role);

	}
}
