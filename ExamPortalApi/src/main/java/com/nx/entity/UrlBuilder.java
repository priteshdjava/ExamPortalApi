package com.nx.entity;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class UrlBuilder {
	
	//private String dowloadDocumentPassport = "/downloadUploadDocument/passportService/";
	private String dowloadUserProfile = "/downloadProfileImage/userService/";
	
	public String buildUrlForUserProfile(String imageName)throws Exception
	{
		StringBuilder builder = new StringBuilder();
		builder.append(dowloadUserProfile);
		builder.append(imageName);
		return builder.toString();
	}
	public String appendContextPath(String url)throws Exception
	{
		StringBuilder builder = new StringBuilder();
		builder.append(ServletUriComponentsBuilder.fromCurrentContextPath().toUriString());
		builder.append(url);
		return builder.toString();
	}
}
