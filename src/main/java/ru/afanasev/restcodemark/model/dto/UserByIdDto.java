package ru.afanasev.restcodemark.model.dto;

import java.util.HashSet;
import java.util.Set;

public class UserByIdDto extends UserWithoutRoleDto {

	private Set<String> roles = new HashSet<>();

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
