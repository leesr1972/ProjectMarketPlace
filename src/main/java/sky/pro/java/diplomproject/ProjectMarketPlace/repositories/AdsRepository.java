package sky.pro.java.diplomproject.ProjectMarketPlace.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sky.pro.java.diplomproject.ProjectMarketPlace.model.Ads;
import sky.pro.java.diplomproject.ProjectMarketPlace.model.Users;

import java.util.List;

@Repository
public interface AdsRepository extends JpaRepository<Ads, Long> {

    List<Ads> findByUsers(Users user);
    List<Ads> findByTitleContains(String partOfTitle);
    List<Ads> findByDescriptionContains(String partOfDescription);
    List<Ads> findByPrice(Integer price);
    List<Ads> findByPriceLessThanEqual(Integer maxPrice);
    List<Ads> findByPriceGreaterThanEqual(Integer minPrice);
    List<Ads> findByPriceBetween(Integer minPrice, Integer maxPrice);
    List<Ads> findByTitleContainsAndDescriptionContains(String partOfTitle, String partOfDescription);
    List<Ads> findByTitleContainsAndDescriptionContainsAndPriceLessThanEqualOrderByPrice(String partOfTitle,
                                                                                         String partOfDescription,
                                                                                         Integer maxPrice);
    List<Ads> findByTitleContainsAndDescriptionContainsAndPriceGreaterThanEqualOrderByPrice(String partOfTitle,
                                                                                            String partOfDescription,
                                                                                            Integer minPrice);
    List<Ads> findByTitleContainsAndDescriptionContainsAndPriceBetween(String partOfTitle,
                                                                       String partOfDescription,
                                                                       Integer minPrice,
                                                                       Integer maxPrice);


}
