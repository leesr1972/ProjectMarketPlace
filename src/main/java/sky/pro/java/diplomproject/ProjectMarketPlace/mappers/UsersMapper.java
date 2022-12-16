package sky.pro.java.diplomproject.ProjectMarketPlace.mappers;

import org.mapstruct.*;
import sky.pro.java.diplomproject.ProjectMarketPlace.dto.NewPasswordDto;
import sky.pro.java.diplomproject.ProjectMarketPlace.dto.RegisterReqDto;
import sky.pro.java.diplomproject.ProjectMarketPlace.dto.UserDto;
import sky.pro.java.diplomproject.ProjectMarketPlace.model.Users;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsersMapper {

    @Mapping(target = "email", source = "username")
    UserDto userToUserDto(Users user);

    Users userDtoToUser (UserDto userDto);

    Users registerReqDtoToUser(RegisterReqDto registerReqDto);

    List<UserDto> listUsersToListUserDto(List<Users> usersList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromUserDto(UserDto userDto, @MappingTarget Users user);

    @Mapping(target = "password",source = "newPassword")
    void updatePassword(NewPasswordDto newPasswordDto, @MappingTarget Users user);
}
