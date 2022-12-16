package sky.pro.java.diplomproject.ProjectMarketPlace.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import sky.pro.java.diplomproject.ProjectMarketPlace.dto.NewPasswordDto;
import sky.pro.java.diplomproject.ProjectMarketPlace.dto.ResponseWrapperUserDto;
import sky.pro.java.diplomproject.ProjectMarketPlace.dto.UserDto;
import sky.pro.java.diplomproject.ProjectMarketPlace.model.Users;

public interface UserService {
    ResponseEntity<UserDto> getUser(Long id);
    Users findByUsername(String username);
    ResponseEntity<ResponseWrapperUserDto> getUsers();
    ResponseEntity<UserDto> updateUser(UserDto userDto, Authentication authentication);
    ResponseEntity<NewPasswordDto> setPassword(NewPasswordDto newPasswordDto);
    void setAdminInUsers();
}
