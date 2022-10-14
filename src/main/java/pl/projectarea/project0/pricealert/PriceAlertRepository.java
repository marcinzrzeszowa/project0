package pl.projectarea.project0.pricealert;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.projectarea.project0.article.Article;
import pl.projectarea.project0.pricealert.PriceAlert;

import java.util.List;
import java.util.Optional;

@Repository
public interface PriceAlertRepository extends JpaRepository<PriceAlert, Long> {

    List<PriceAlert> findByIsActive(boolean isActive);

    List<PriceAlert> findAll();

    Optional<PriceAlert> findById(Long id);

    PriceAlert save(PriceAlert entity);

    boolean existsById(Long id);

    void deleteById(Long id);


  /*

  SELECT price_alert.id, price_alert.description, user.username FROM `price_alert` LEFT JOIN `user` ON price_alert.user = user.id
ORDER BY `user`.`username` ASC;

 @Query("select p from PriceAlert p inner join p.user ")
    */

}
