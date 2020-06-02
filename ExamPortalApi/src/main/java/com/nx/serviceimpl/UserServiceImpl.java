package com.nx.serviceimpl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.nx.entity.RoleName;
import com.nx.entity.UrlBuilder;
import com.nx.entity.User;
import com.nx.exception.AppException;
import com.nx.payload.ForgotPasswordRequest;
import com.nx.payload.ResetPasswordRequest;
import com.nx.payload.SignupRequest;
import com.nx.payload.UpdateUserPasswordRequest;
import com.nx.repository.UserRepository;
import com.nx.security.JwtTokenProvider;
import com.nx.service.BasicService;
import com.nx.service.EmailService;
import com.nx.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class UserServiceImpl extends BasicService<User, UserRepository> implements UserService {

	@Value("${mail.fromname}")
	private String fromName;

	@Value("${mail.subject}")
	private String mailSubject;

	@Value("${mail.text}")
	private String mailText;
	
	@Value("${file.user-profile-dir}")
	private String userProfileUploadDirectory;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	private EmailService emailService;
	
	@Autowired
	JwtTokenProvider tokenProvider;
	
	@Autowired
	private UrlBuilder urlBuilder;
	
	@Override
	public Page<User> search(Pageable pageable, String searchText) {
		String queriableText = new StringBuilder("%").append(searchText).append("%").toString();
		return repository.search(pageable, queriableText);
	}
	
	@Override
	public boolean existsByUsername(String username) throws Exception {
		return repository.existsByUserName(username);
	}

	@Override
	public boolean existsByEmail(String email) throws Exception {
		return repository.existsByEmail(email);
	}

	@Override
	public void registerUser(@Valid SignupRequest signUpRequest) throws Exception {
		// Creating user's account
		User user = new User();
		user.setUserName(signUpRequest.getUserName());
		user.setFirstName(signUpRequest.getFirstName());
		user.setLastName(signUpRequest.getLastName());
		user.setGender(signUpRequest.getGender());
		user.setDob(signUpRequest.getDob());
		user.setEmail(signUpRequest.getEmail());
		user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
		user.setRole(RoleName.User);
		repository.save(user);		
	}

	@Override
	public void processForgotPassword(ForgotPasswordRequest useremail) throws Exception {
		User user = repository.findByEmail(useremail.getemail());
		String tokenStr = Jwts.builder()
				/*.setAudience(useremail.getemail())
				.setSubject(Long.toString(1))
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + 300000))
				.signWith(SignatureAlgorithm.HS512, "926D96C90030DD58429D2751AC1BDBBC")
				.compact();*/
				.setSubject(user.getId().toString())
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + 300000))
				.signWith(SignatureAlgorithm.HS512, "926D96C90030DD58429D2751AC1BDBBC")
				.compact();
		user.setResetToken(tokenStr);
		repository.save(user);
		emailForgotPassword(useremail, tokenStr);
	}

	@Async
	private void emailForgotPassword(ForgotPasswordRequest useremail, String tokenStr) throws Exception {
		SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
		passwordResetEmail.setFrom(fromName);
		passwordResetEmail.setTo(useremail.getemail());
		passwordResetEmail.setSubject(mailSubject);
		passwordResetEmail.setText(mailText + tokenStr);
		emailService.sendEmail(passwordResetEmail);	
	}

	@Override
	public void resetPassword(ResetPasswordRequest resetPasswordRequest) throws Exception {
		
		User user = repository.findByResetToken(resetPasswordRequest.getToken());
		
		if(null!=user)
		{
			user.setPassword(passwordEncoder.encode(resetPasswordRequest.getPassword()));
			user.setResetToken("");
			repository.save(user);
			emailResetPassword(user.getEmail());
		}
		else
		{
			throw new AppException("User with token "+resetPasswordRequest.getToken()+" not found");
		}
	}

	@Async
	private void emailResetPassword(String useremail) throws Exception {
		SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
		passwordResetEmail.setFrom(fromName);
		passwordResetEmail.setTo(useremail);
		passwordResetEmail.setSubject("Password reset successfully");
		passwordResetEmail.setText("Your password reset successfully..");
		emailService.sendEmail(passwordResetEmail);
	}

	@Override
	public void saveUser(User user) throws Exception {
		
		
	}

	@Override
	public void uploadUserProfile(MultipartFile file, Long userId) throws Exception {
		// TODO Auto-generated method stub
		if(!file.isEmpty() && file!=null && !file.getOriginalFilename().equals(""))
		{	
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			String extention = FilenameUtils.getExtension(fileName);
			String physicalimagename = new Date().getTime()+"."+extention;
			String url = urlBuilder.buildUrlForUserProfile(physicalimagename);
			Path targetLocation = Paths.get(userProfileUploadDirectory).resolve(physicalimagename);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			
			Optional<User> user = repository.findById(userId);
			if (user.isPresent()) {
				User u = user.get();
				u.setProfileImageUrl(url);
				repository.save(u);
			}
		}
	}

	@Override
	public Page<User> fetchAllUsers(Integer pageNo, Integer pageSize, String sortBy) throws Exception {
		
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		Page<User> userList = repository.findAll(paging);	
		return userList;
		
	}

}
