package sky.pro.java.diplomproject.ProjectMarketPlace.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sky.pro.java.diplomproject.ProjectMarketPlace.model.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
