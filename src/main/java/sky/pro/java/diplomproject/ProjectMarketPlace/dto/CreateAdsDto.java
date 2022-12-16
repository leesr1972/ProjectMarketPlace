package sky.pro.java.diplomproject.ProjectMarketPlace.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateAdsDto {
    private String description;
    private String image;
    private Integer pk;
    private Integer price;
    private String title;
}
