package ru.afanasev.restcodemark.model;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer>{
	
	Optional<Role>findByName(String name);

}
