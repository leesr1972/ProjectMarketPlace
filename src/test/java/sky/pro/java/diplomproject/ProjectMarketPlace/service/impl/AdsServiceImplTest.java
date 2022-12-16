package sky.pro.java.diplomproject.ProjectMarketPlace.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import sky.pro.java.diplomproject.ProjectMarketPlace.dto.AdsDto;
import sky.pro.java.diplomproject.ProjectMarketPlace.dto.ResponseWrapperAdsDto;
import sky.pro.java.diplomproject.ProjectMarketPlace.mappers.AdsMapper;
import sky.pro.java.diplomproject.ProjectMarketPlace.model.Ads;
import sky.pro.java.diplomproject.ProjectMarketPlace.repositories.AdsRepository;
import sky.pro.java.diplomproject.ProjectMarketPlace.repositories.CommentRepository;
import sky.pro.java.diplomproject.ProjectMarketPlace.repositories.UserRepository;
import sky.pro.java.diplomproject.ProjectMarketPlace.service.ImageService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static sky.pro.java.diplomproject.ProjectMarketPlace.ConstantsForTests.*;

@ExtendWith(MockitoExtension.class)
class AdsServiceImplTest {
    @Mock
    private AdsRepository adsRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CommentRepository commentRepository;
    @Spy
    private Authentication authentication;
    @Spy
    private ImageService imageService;
    @Spy
    private AdsMapper adsMapper;

    @InjectMocks
    private AdsServiceImpl out;

    @Test
    void saveAds() throws IOException {
        when(adsRepository.save(any())).thenReturn(ADS_1);
        when(userRepository.findByUsername(any())).thenReturn(USER_1);
        when(adsMapper.createAdsDtoToAds(any())).thenReturn(ADS_1);
        when(adsMapper.toAdsDto(any())).thenReturn(ADS_DTO_1);
        when(authentication.getName()).thenReturn(USER_NAME_1);
        when(imageService.getImagesByAds(any())).thenReturn(IMAGES);
        out.saveAds(CREATE_ADS_DTO, any(), authentication);
        verify(adsRepository, times(2)).save(any());
        verify(userRepository, times(1)).findByUsername(any());
    }

    @Test
    void getAllAds() {
        List<Ads> adsList = new ArrayList<>(List.of(ADS_1, ADS_2));
        List<AdsDto> adsDtoList = new ArrayList<>(List.of(ADS_DTO_1, ADS_DTO_2));
        when(adsRepository.findAll()).thenReturn(adsList);
        when(adsMapper.listAdsToListAdsDto(anyList())).thenReturn(adsDtoList);
        ResponseWrapperAdsDto wrapper = out.getAllAds().getBody();
        assert wrapper != null;
        assertEquals(2, wrapper.getCount());
        assertEquals(adsDtoList, wrapper.getResults());
    }

    @Test
    void removeAdsById() {
        when(adsRepository.findById(any())).thenReturn(Optional.of(ADS_1));
        when(commentRepository.findByAds(any())).thenReturn(null);
        when(adsMapper.toAdsDto(any())).thenReturn(ADS_DTO_1);
        out.removeAdsById(1L);
        verify(adsRepository, times(1)).findById(any());
        verify(imageService, times(1)).removeImagesByAds(any());
        verify(commentRepository, times(1)).findByAds(any());
        verify(commentRepository, times(1)).deleteAll(any());
        verify(adsRepository, times(1)).deleteById(any());
        verify(adsMapper, times(1)).toAdsDto(any());
    }

    @Test
    void getAdsById() {
        when(adsRepository.findById(any())).thenReturn(Optional.of(ADS_1));
        out.getAdsById(1L);
        verify(adsRepository, times(1)).findById(any());
    }

    @Test
    void updateAds() {
        when(adsMapper.toAds(any())).thenReturn(ADS_1);
        when(adsRepository.findById(any())).thenReturn(Optional.of(ADS_2));
        when(adsRepository.save(any())).thenReturn(ADS_1);
        when(adsMapper.toAdsDto(any())).thenReturn(ADS_DTO_1);
        AdsDto adsDto = out.updateAds(2L, ADS_DTO_1).getBody();
        assert adsDto != null;
        assertEquals(ADS_1.getPrice(), adsDto.getPrice());
    }

    @Test
    void getAdsMe() {
        List<Ads> adsList = new ArrayList<>(List.of(ADS_1, ADS_2));
        List<AdsDto> adsDtoList = new ArrayList<>(List.of(ADS_DTO_1, ADS_DTO_2));
        when(userRepository.findByUsername(any())).thenReturn(USER_1);
        when(authentication.getName()).thenReturn(USER_NAME_1);
        when(adsRepository.findByUsers(USER_1)).thenReturn(adsList);
        when(adsMapper.listAdsToListAdsDto(anyList())).thenReturn(adsDtoList);
        ResponseWrapperAdsDto wrapper = out.getAdsMe(authentication).getBody();
        assert wrapper != null;
        assertEquals(2, wrapper.getCount());
        assertEquals(adsDtoList, wrapper.getResults());
    }

    @Test
    void updateAdsImage() throws IOException {
        when(adsRepository.findById(any())).thenReturn(Optional.of(ADS_1));
        when(imageService.getImagesByAds(any())).thenReturn(IMAGES);
        out.updateAdsImage(1L, any());
        verify(adsRepository, times(1)).findById(any());
        verify(adsRepository, times(1)).save(any());
    }
}