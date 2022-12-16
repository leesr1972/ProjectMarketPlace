package sky.pro.java.diplomproject.ProjectMarketPlace.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sky.pro.java.diplomproject.ProjectMarketPlace.model.Ads;
import sky.pro.java.diplomproject.ProjectMarketPlace.model.Images;

@Repository
public interface ImageRepository extends JpaRepository<Images, Long> {
    Images findByAds(Ads ads);
    void deleteByAds(Ads ads);
}
