package sky.pro.java.diplomproject.ProjectMarketPlace.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sky.pro.java.diplomproject.ProjectMarketPlace.dto.AdsCommentDto;
import sky.pro.java.diplomproject.ProjectMarketPlace.dto.ResponseWrapperAdsCommentDto;
import sky.pro.java.diplomproject.ProjectMarketPlace.mappers.CommentMapper;
import sky.pro.java.diplomproject.ProjectMarketPlace.model.Ads;
import sky.pro.java.diplomproject.ProjectMarketPlace.model.Comment;
import sky.pro.java.diplomproject.ProjectMarketPlace.model.Users;
import sky.pro.java.diplomproject.ProjectMarketPlace.repositories.AdsRepository;
import sky.pro.java.diplomproject.ProjectMarketPlace.repositories.CommentRepository;
import sky.pro.java.diplomproject.ProjectMarketPlace.repositories.UserRepository;
import sky.pro.java.diplomproject.ProjectMarketPlace.service.CommentService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdsServiceImpl.class);
    private final CommentRepository commentRepository;
    private final AdsRepository adsRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    public CommentServiceImpl(CommentRepository commentRepository,
                              AdsRepository adsRepository,
                              UserRepository userRepository,
                              CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.adsRepository = adsRepository;
        this.userRepository = userRepository;
        this.commentMapper = commentMapper;
    }

    /**
     * Добавление нового комментария к объявлению.
     * @param adsCommentDto - текст, дата и время нового комментария.
     * @param adsId - идентификатор комментируемого объявления.
     * @return - комментарий к объвлению.
     */
    @Override
    public ResponseEntity<AdsCommentDto> addAdsComment(AdsCommentDto adsCommentDto, Long adsId) {
        LOGGER.info("Was invoked method for add comment for Ads.");
        adsCommentDto.setCreatedAt(LocalDateTime.now());
        Comment newComment = commentMapper.toComment(adsCommentDto);
        Ads commentedAds = adsRepository.findById(adsId).orElseThrow();
        newComment.setAds(commentedAds);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users author = userRepository.findByUsername(authentication.getName());
        newComment.setUsers(author);
        Comment savedComment = commentRepository.save(newComment);
        List<Comment> commentsOfAds = commentedAds.getComments();
        commentsOfAds.add(savedComment);
        commentedAds.setComments(commentsOfAds);
        AdsCommentDto returnedAdsCommentDto = commentMapper.toAdsCommentDto(savedComment);
        returnedAdsCommentDto.setAuthor(Math.toIntExact(author.getId()));
        return ResponseEntity.ok(returnedAdsCommentDto);
    }

    /**
     * Получение всех комментариев к объявлению.
     * @param adsId - идентификатор объявления.
     * @return - количество и список комментариев к объявлению.
     */
    @Override
    public ResponseEntity<ResponseWrapperAdsCommentDto> getAllCommentsOfAds(Long adsId) {
        LOGGER.info("Was invoked method for get all comments of Ads.");
        Ads ads = adsRepository.findById(adsId).orElseThrow();
        List<Comment> commentsOfAds = commentRepository.findByAds(ads);
        List<AdsCommentDto> adsCommentDtoList = commentMapper.toListAdsCommentDto(commentsOfAds);
        for (int i = 0; i < adsCommentDtoList.size(); i++) {
            adsCommentDtoList.get(i).setAuthor(Math.toIntExact(commentsOfAds.get(i).getUsers().getId()));
        }
        ResponseWrapperAdsCommentDto wrapperCommentDto = new ResponseWrapperAdsCommentDto();
        wrapperCommentDto.setCount(adsCommentDtoList.size());
        wrapperCommentDto.setResults(adsCommentDtoList);
        return ResponseEntity.ok(wrapperCommentDto);
    }

    /**
     * Получение комментария по его идентификатору.
     * @param commentId - идентификатор комментария.
     * @return - найденный комментарий.
     */
    @Override
    public ResponseEntity<AdsCommentDto> getAdsComment(Long commentId) {
        LOGGER.info("Was invoked method for get comment by id.");
        Comment requiredComment = commentRepository.findById(commentId).orElseThrow();
        AdsCommentDto adsCommentDto = commentMapper.toAdsCommentDto(requiredComment);
        adsCommentDto.setAuthor(Math.toIntExact(requiredComment.getUsers().getId()));
        return ResponseEntity.ok(adsCommentDto);
    }

    /**
     * Получение комментариев по идентификатору объявления.
     * @param adsId - идентификатор объявления.
     * @return - найденные комментарии.
     */
    @Override
    public List<Comment> getCommentsByAdsId(Long adsId) {
        LOGGER.info("Was invoked method for get comments by id of Ads.");
        Ads ads = adsRepository.findById(adsId).orElseThrow();
        return commentRepository.findByAds(ads);
    }

    /**
     * Редактирование комментария.
     * @param commentId - идентификатор комментария.
     * @param adsCommentDto - изменения в комментарии.
     * @return - обновленный комментарий.
     */
    @Override
    public ResponseEntity<AdsCommentDto> updateAdsComment(Long commentId,
                                                          AdsCommentDto adsCommentDto) {
        LOGGER.info("Was invoked method for update comment.");
        Comment commentFromClient = commentMapper.toComment(adsCommentDto);
        Comment commentFromDataBase = commentRepository.findById(commentId).orElseThrow();
        commentFromDataBase.setCreatedAt(commentFromClient.getCreatedAt());
        if (commentFromClient.getText() != null) {
            commentFromDataBase.setText(commentFromClient.getText());
        }
        commentRepository.save(commentFromDataBase);
        AdsCommentDto returnedAdsCommentDto = commentMapper.toAdsCommentDto(commentFromDataBase);
        returnedAdsCommentDto.setAuthor(Math.toIntExact(commentFromDataBase.getUsers().getId()));
        return ResponseEntity.ok(returnedAdsCommentDto);
    }

    /**
     * Удаление комментария по его идентификатору.
     * @param commentId - идентификатор объявления.
     */
    @Override
    public void deleteAdsComment(Long commentId) {
        LOGGER.info("Was invoked method for update comment.");
        commentRepository.deleteById(commentId);
    }
}
