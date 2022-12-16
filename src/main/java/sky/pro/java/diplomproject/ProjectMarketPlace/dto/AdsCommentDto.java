package sky.pro.java.diplomproject.ProjectMarketPlace.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdsCommentDto {
    private Integer author;
    private LocalDateTime createdAt;
    private Integer pk;
    private String text;
}
