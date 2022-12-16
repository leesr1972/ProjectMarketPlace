package sky.pro.java.diplomproject.ProjectMarketPlace.service;

import org.springframework.web.multipart.MultipartFile;
import sky.pro.java.diplomproject.ProjectMarketPlace.model.Ads;
import sky.pro.java.diplomproject.ProjectMarketPlace.model.Images;

import java.io.IOException;

public interface ImageService {
    void uploadImage(Long adsId, MultipartFile file) throws IOException;
    Images getImageById(Long id);
    Images getImagesByAds(Ads ads);
    void removeImagesByAds(Ads ads);
}
