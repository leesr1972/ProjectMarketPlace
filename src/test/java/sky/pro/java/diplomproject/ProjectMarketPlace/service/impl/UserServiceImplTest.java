package sky.pro.java.diplomproject.ProjectMarketPlace.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import sky.pro.java.diplomproject.ProjectMarketPlace.dto.ResponseWrapperUserDto;
import sky.pro.java.diplomproject.ProjectMarketPlace.dto.UserDto;
import sky.pro.java.diplomproject.ProjectMarketPlace.mappers.UsersMapper;
import sky.pro.java.diplomproject.ProjectMarketPlace.model.Users;
import sky.pro.java.diplomproject.ProjectMarketPlace.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static sky.pro.java.diplomproject.ProjectMarketPlace.ConstantsForTests.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Spy
    private UsersMapper usersMapper;
    @Spy
    private Authentication authentication;

    @InjectMocks
    private UserServiceImpl out;

    @Test
    void getUser() {
        when(userRepository.findById(any())).thenReturn(Optional.of(USER_1));
        out.getUser(1L);
        verify(userRepository, times(1)).findById(any());
        verify(usersMapper, times(1)).userToUserDto(any());
    }

    @Test
    void findByUsername() {
        when(userRepository.findByUsername(any())).thenReturn(USER_1);
        out.findByUsername(USER_NAME_1);
        verify(userRepository, times(1)).findByUsername(any());
    }

    @Test
    void getUsers() {
        List<Users> usersList = new ArrayList<>(List.of(USER_1, USER_2));
        List<UserDto> userDtoList = new ArrayList<>(List.of(USER_DTO_1, USER_DTO_2));
        when(userRepository.findAll()).thenReturn(usersList);
        when(usersMapper.listUsersToListUserDto(anyList())).thenReturn(userDtoList);
        ResponseWrapperUserDto wrapper = out.getUsers().getBody();
        assert wrapper != null;
        assertEquals(2, wrapper.getCount());
        assertEquals(userDtoList, wrapper.getResults());
    }

    @Test
    void updateUser() {
        when(userRepository.findByUsername(any())).thenReturn(USER_1);
        when(usersMapper.userToUserDto(any())).thenReturn(USER_DTO_2);
        UserDto userDto = out.updateUser(USER_DTO_2, authentication).getBody();
        assertEquals(USER_DTO_2, userDto);
    }
}