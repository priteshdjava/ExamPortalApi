package com.nx;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {

	public static void main(String args[])
	{
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		System.out.println("Your new Passowrd is :-"+bCryptPasswordEncoder.encode("pritesh"));
	}
}
