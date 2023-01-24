package com.bogdan.supportportal.service;

import com.bogdan.supportportal.domain.User;
import com.bogdan.supportportal.exception.domain.EmailExistException;
import com.bogdan.supportportal.exception.domain.EmailNotFoundException;
import com.bogdan.supportportal.exception.domain.UsernameExistException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    User register(String firstName, String lastName, String username, String email, String password) throws EmailExistException, UsernameExistException, jakarta.mail.MessagingException;

    List<User> getUsers();

    User findUserByUsername(String username);

    User findUserByEmail(String email);

    User addNewUser(String firstName, String lastName, String username,
                    String email, String role, boolean isNonLocked,
                    boolean isActive, MultipartFile profileImage) throws EmailExistException, UsernameExistException, IOException;

    User updateUser(String currentUsername, String newFirstName, String newLastName, String newUsername,
                    String newEmail, String role, boolean isNonLocked,
                    boolean isActive, MultipartFile profileImage) throws EmailExistException, UsernameExistException, IOException;

    void deleteUser(long id);

    void resetPassword(String email) throws EmailNotFoundException, jakarta.mail.MessagingException;

    User updateProfileImage(String username, MultipartFile profileImage) throws EmailExistException, UsernameExistException, IOException;
}
