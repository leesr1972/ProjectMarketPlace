package sky.pro.java.diplomproject.ProjectMarketPlace.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sky.pro.java.diplomproject.ProjectMarketPlace.dto.AdsDto;
import sky.pro.java.diplomproject.ProjectMarketPlace.dto.CreateAdsDto;
import sky.pro.java.diplomproject.ProjectMarketPlace.dto.FullAdsDto;
import sky.pro.java.diplomproject.ProjectMarketPlace.model.Ads;
import sky.pro.java.diplomproject.ProjectMarketPlace.model.Users;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdsMapper {

    @Mapping(target = "pk", source = "id")
    AdsDto toAdsDto(Ads ads);

    @Mapping(target = "id", source = "pk")
    Ads toAds(AdsDto adsDto);

    @Mapping(target = "id", source = "pk")
    Ads createAdsDtoToAds(CreateAdsDto createAdsDto);

    @Mapping(target = "pk", source = "ads.id")
    @Mapping(target = "authorFirstName", source = "user.firstName")
    @Mapping(target = "authorLastName", source = "user.lastName")
    @Mapping(target = "email", source = "user.username")
    @Mapping(target = "image", source = "ads.image")
    FullAdsDto toFullAdsDto(Ads ads, Users user);

    List<AdsDto> listAdsToListAdsDto(List<Ads> adsList);
}
