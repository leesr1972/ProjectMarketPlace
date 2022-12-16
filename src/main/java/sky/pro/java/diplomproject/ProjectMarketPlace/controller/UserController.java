package sky.pro.java.diplomproject.ProjectMarketPlace.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sky.pro.java.diplomproject.ProjectMarketPlace.dto.NewPasswordDto;
import sky.pro.java.diplomproject.ProjectMarketPlace.dto.ResponseWrapperUserDto;
import sky.pro.java.diplomproject.ProjectMarketPlace.dto.UserDto;
import sky.pro.java.diplomproject.ProjectMarketPlace.model.Avatar;
import sky.pro.java.diplomproject.ProjectMarketPlace.model.Users;
import sky.pro.java.diplomproject.ProjectMarketPlace.service.AvatarService;
import sky.pro.java.diplomproject.ProjectMarketPlace.service.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final AvatarService avatarService;

    /**
     * Получение списка всех пользователей.
     * @return - список всех пользователей.
     */
    @Operation(summary = "Получение всех пользователей", description = "", tags={ "Пользователи" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "*/*",
                            schema = @Schema(implementation = ResponseWrapperUserDto.class))),

            @ApiResponse(responseCode = "401", description = "Unauthorized"),

            @ApiResponse(responseCode = "403", description = "Forbidden"),

            @ApiResponse(responseCode = "404", description = "Not Found") })
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/me")
    public ResponseEntity<ResponseWrapperUserDto> getUsers(){
        return userService.getUsers();
    }

    /**
     * Редактирование данных пользователя.
     * @param userDto - изменения в данных пользователя.
     * @param authentication - аутентификация текущего пользователя.
     * @return - изменный пользователь.
     */
    @Operation(
            summary = "Редактирование данных пользователя", description = "",
            tags={ "Пользователи" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(schema = @Schema(implementation = UserDto.class))),
                    @ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "401", description = "Unauthorised"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    @PatchMapping("/me")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto,
                                              Authentication authentication){
        LOGGER.info("Was invoked method of UserController for update user.");
        return userService.updateUser(userDto, authentication);
    }

    @Operation(
            summary = "Изменения пароля пользователя", description = "", tags={ "Пользователи" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(schema = @Schema(implementation = NewPasswordDto.class))),
                    @ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "401", description = "Unauthorised"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    @PostMapping("/set_password")
    public ResponseEntity<NewPasswordDto> setPassword(@RequestBody NewPasswordDto newPasswordDto){
        LOGGER.info("Was invoked method of UserController for change password of user.");
        return userService.setPassword(newPasswordDto);
    }

    @Operation(
            summary = "Получение пользователя по его идентификатору",
            description = "", tags={ "Пользователи" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(schema = @Schema(implementation = UserDto.class))),
                    @ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "401", description = "Unauthorised"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    @PreAuthorize("@userServiceImpl.getUser(#id).getBody().getEmail() == " +
            "authentication.principal.username or hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Integer id){
        LOGGER.info("Was invoked method of UserController for update avatar of user.");
        return userService.getUser(id.longValue());
    }

    @Operation(
            summary = "Обновление аватара пользователя", description = "", tags={ "Пользователи" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(schema = @Schema(implementation = UserDto.class))),
                    @ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "401", description = "Unauthorised"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void getImage(@Parameter(description = "Передаем новое изображение")
                         @RequestPart(value = "image") @Valid MultipartFile file,
                         Authentication authentication, HttpServletResponse response)
            throws IOException {
        LOGGER.info("Was invoked method of UserController for update avatar of user.");
        Users user = userService.findByUsername(authentication.getName());
        Avatar avatar = avatarService.uploadAvatar(user.getId(), file);
//        Path path = Path.of(avatar.getFilePath());
//        try (
//                InputStream is = Files.newInputStream(path);
//                OutputStream os = response.getOutputStream()
//        ) {
//            response.setStatus(200);
//            response.setContentType(avatar.getMediaType());
//            response.setContentLength(Math.toIntExact(avatar.getFileSize()));
//            is.transferTo(os);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }
}
