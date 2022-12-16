package sky.pro.java.diplomproject.ProjectMarketPlace.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdsDto {
    private Integer author;
    private String image;
    private Integer pk;
    private Integer price;
    private String title;
    private String description;
}
