package ru.afanasev.RestCodemark.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ManyToAny;

@Entity
@Table(name = "users")
public class User {

	@Id
	private String login;

	private String username;

	private String password;

	@ManyToMany(mappedBy = "usersSet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Role> rolesSet = new HashSet<>();

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRolesSet() {
		return rolesSet;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void addRole(Role role) {
		rolesSet.add(role);
		role.getUsersSet().add(this);

	}

	public void removeRole(Role role) {
		rolesSet.remove(role);
		role.getUsersSet().remove(this);
	}

}
