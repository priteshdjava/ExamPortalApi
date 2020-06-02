package com.nx.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.nx.exception.FileStorageException;
import com.nx.exception.MyFileNotFoundException;

@Service
public class FileStorageService {
	
	@Autowired
	private MessageSource messageSource;
	
    @Autowired
    public FileStorageService(Environment environment) {

        try {
        	Files.createDirectories(Paths.get(environment.getProperty("file.user-profile-dir")));
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }
    
    //For Profile Image Store
    public String storeFile(MultipartFile file, Long id, String pathValue,String serviceType) throws Exception {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String extention = FilenameUtils.getExtension(fileName);
        String url = null;
        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Long imageId = (new Date()).getTime();
            Path targetLocation = Paths.get(pathValue).resolve(id.toString().concat("_").concat(imageId.toString())+"."+extention);
            Files.deleteIfExists(targetLocation);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            if(serviceType.equals("Passport"))
            {	
            	url = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/passport/downloadProfileImage/")
                    .path(id.toString().concat("_").concat(imageId.toString())+"."+extention)
                    .toUriString();
            }
            else if(serviceType.equals("BirthReport"))
            {	
            	url = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/api/birthReport/downloadProfileImage/")
                        .path(id.toString().concat("_").concat(imageId.toString())+"."+extention)
                        .toUriString();
            }
            else if(serviceType.equals("DeathReport"))
            {	
            	url = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/api/deathReport/downloadProfileImage/")
                        .path(id.toString().concat("_").concat(imageId.toString())+"."+extention)
                        .toUriString();
            }
            else if(serviceType.equals("Visa"))
            {	
            	url = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/api/visa/downloadProfileImage/")
                        .path(id.toString().concat("_").concat(imageId.toString())+"."+extention)
                        .toUriString();
            }
            return url;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
    
    //Passport Application Document Upload
    public String storeFile(MultipartFile file, Long passportApplicationId,Long userId, String pathValue) throws Exception {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String extention = FilenameUtils.getExtension(fileName);
        
        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Long imageId = (new Date().getTime());
            String.valueOf(imageId);
            Path targetLocation = Paths.get(pathValue).resolve(passportApplicationId.toString().concat("_").concat(userId.toString()).concat("_").concat(String.valueOf(imageId))+"."+extention);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(pathValue)
                    .path(passportApplicationId.toString().concat("_").concat(userId.toString()).concat("_").concat((String.valueOf(imageId)))+"."+extention)
                    .toUriString();
            return url;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
    public Resource loadFileAsResource(String fileName,String dirpath) {
        try {
            Path filePath = Paths.get(dirpath).resolve(fileName).normalize();

            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException(messageSource.getMessage("embassy.exception.filenotfound", null,LocaleContextHolder.getLocale()) + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException(messageSource.getMessage("embassy.exception.filenotfound", null,LocaleContextHolder.getLocale()) + fileName, ex);
        }
    }
    
}
