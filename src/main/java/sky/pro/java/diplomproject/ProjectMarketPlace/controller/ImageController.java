package sky.pro.java.diplomproject.ProjectMarketPlace.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import sky.pro.java.diplomproject.ProjectMarketPlace.model.Avatar;
import sky.pro.java.diplomproject.ProjectMarketPlace.model.Images;
import sky.pro.java.diplomproject.ProjectMarketPlace.service.AvatarService;
import sky.pro.java.diplomproject.ProjectMarketPlace.service.ImageService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class ImageController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageController.class);
    private final ImageService imageService;
    private final AvatarService avatarService;

    /**
     * "Метод получения изображения по идентификатору.
     */
    @Operation(summary = "Получение изображения по его идентификатору",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            })
    @GetMapping(value = "/getImage/{imageId}")
    public void getImage(@PathVariable Long imageId, HttpServletResponse response) {
        LOGGER.info("Was invoked method of ImageController for get image of Ads.");
        Images image = imageService.getImageById(imageId);
        Path path = Path.of(image.getFilePath());
        try (
                InputStream is = Files.newInputStream(path);
                OutputStream os = response.getOutputStream()
        ) {
            response.setStatus(200);
            response.setContentType(image.getMediaType());
            response.setContentLength(Math.toIntExact(image.getFileSize()));
            is.transferTo(os);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * "Метод получения аватара по идентификатору.
     */
    @Operation(summary = "Получение аватара по его идентификатору",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            })
    @GetMapping(value = "/getAvatar/{avatarId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getAvatar(@PathVariable Long avatarId, HttpServletResponse response) {
        LOGGER.info("Was invoked method of ImageController for get avatar of user.");
        Avatar avatar = avatarService.getAvatarById(avatarId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
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
