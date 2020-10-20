package ru.afanasev.RestCodemark.model.dto;

import java.util.ArrayList;
import java.util.List;

import ru.afanasev.RestCodemark.model.Role;

public class SuccessFalseDto extends SuccessDto{

	public SuccessFalseDto() {
		setSuccess("false");
	}
	private List<String>errors = new ArrayList<>();
	
	
	
	public List<String> getErrors() {
		return errors;
	}



	public void addError(String error) {
		errors.add(error);
	}

}
