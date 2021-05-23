package com.lalalala.s1.member;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class MemberVO {
	private String username;
	
	private String password1;

	@Length(max = 10, min = 2)
	private String password;
	
	
	@NotEmpty
	private String name;
	@NotEmpty
	private String email;
	@NotEmpty
	private String phone;

}