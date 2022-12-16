package sky.pro.java.diplomproject.ProjectMarketPlace.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sky.pro.java.diplomproject.ProjectMarketPlace.dto.AdsCommentDto;
import sky.pro.java.diplomproject.ProjectMarketPlace.model.Comment;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "pk", source = "id")
    AdsCommentDto toAdsCommentDto(Comment comment);

    @Mapping(target = "id",source = "pk")
    Comment toComment(AdsCommentDto adsCommentDto);

    List<AdsCommentDto> toListAdsCommentDto(List<Comment> commentsList);
}
