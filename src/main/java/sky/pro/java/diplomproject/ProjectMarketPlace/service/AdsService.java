package sky.pro.java.diplomproject.ProjectMarketPlace.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import sky.pro.java.diplomproject.ProjectMarketPlace.dto.AdsDto;
import sky.pro.java.diplomproject.ProjectMarketPlace.dto.CreateAdsDto;
import sky.pro.java.diplomproject.ProjectMarketPlace.dto.FullAdsDto;
import sky.pro.java.diplomproject.ProjectMarketPlace.dto.ResponseWrapperAdsDto;

import java.io.IOException;

public interface AdsService {
    ResponseEntity<AdsDto> saveAds(CreateAdsDto createAdsDto, MultipartFile file,
                                   Authentication authentication) throws IOException;
    ResponseEntity<ResponseWrapperAdsDto> getAllAds();
    ResponseEntity<AdsDto> removeAdsById(Long adsId);
    ResponseEntity<FullAdsDto> getAdsById(Long adsId);
    ResponseEntity<AdsDto> updateAds(Long adsId, AdsDto adsDto);
    ResponseEntity<ResponseWrapperAdsDto> getAdsMe(Authentication authentication);
    void updateAdsImage(Long adsId, MultipartFile file) throws IOException;
}
