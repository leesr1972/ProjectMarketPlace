package sky.pro.java.diplomproject.ProjectMarketPlace.service;

import org.springframework.http.ResponseEntity;
import sky.pro.java.diplomproject.ProjectMarketPlace.dto.AdsCommentDto;
import sky.pro.java.diplomproject.ProjectMarketPlace.dto.ResponseWrapperAdsCommentDto;
import sky.pro.java.diplomproject.ProjectMarketPlace.model.Comment;

import java.util.List;

public interface CommentService {
    ResponseEntity<AdsCommentDto> addAdsComment(AdsCommentDto adsCommentDto, Long adsId);
    ResponseEntity<ResponseWrapperAdsCommentDto> getAllCommentsOfAds(Long adsId);
    ResponseEntity<AdsCommentDto> getAdsComment(Long commentId);
    List<Comment> getCommentsByAdsId(Long adsId);
    ResponseEntity<AdsCommentDto> updateAdsComment(Long commentId, AdsCommentDto adsCommentDto);
    void deleteAdsComment(Long commentId);
}
