package ru.afanasev.restcodemark.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String name;

	@ManyToMany(fetch = FetchType.LAZY)
	private Set<User> usersSet = new HashSet<>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<User> getUsersSet() {
		return usersSet;
	}

	public void addUser(User user) {
		usersSet.add(user);
		user.getRolesSet().add(this);
	}

	public void removeUser(User user) {
		usersSet.remove(user);
		user.getRolesSet().remove(this);

	}

	


	
	
}
