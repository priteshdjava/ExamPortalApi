package com.nx.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.nx.entity.User;
import com.nx.payload.ForgotPasswordRequest;
import com.nx.payload.ResetPasswordRequest;
import com.nx.payload.SignupRequest;

public interface UserService extends IFinder<User> , IService<User>{

	Page<User> search(Pageable pageable, String searchText);

	boolean existsByUsername(String username) throws Exception;

	boolean existsByEmail(String email) throws Exception;

	void registerUser(SignupRequest signUpRequest) throws Exception;

	void processForgotPassword(ForgotPasswordRequest useremail) throws Exception;

	void resetPassword(ResetPasswordRequest resetPasswordRequest) throws Exception;
	
	public void saveUser(User user) throws Exception;
	
	public void uploadUserProfile(MultipartFile file,Long userId) throws Exception;
	
	public Page<User> fetchAllUsers(Integer pageNo, Integer pageSize, String sortBy) throws Exception;

}
