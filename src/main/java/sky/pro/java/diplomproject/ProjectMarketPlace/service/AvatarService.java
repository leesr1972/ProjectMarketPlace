package sky.pro.java.diplomproject.ProjectMarketPlace.service;

import org.springframework.web.multipart.MultipartFile;
import sky.pro.java.diplomproject.ProjectMarketPlace.model.Avatar;

import java.io.IOException;

public interface AvatarService {
    Avatar uploadAvatar(Long userId, MultipartFile file) throws IOException;
    Avatar getAvatarById (Long avatarId);
}
