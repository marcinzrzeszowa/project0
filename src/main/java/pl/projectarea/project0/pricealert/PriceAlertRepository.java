package pl.projectarea.project0.pricealert;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.projectarea.project0.pricealert.PriceAlert;

import java.util.List;

@Repository
public interface PriceAlertRepository extends JpaRepository<PriceAlert, Integer> {

    @Query(nativeQuery = true, value = "SELECT FROM price_alert WHERE ticker=?1")
    List<PriceAlert> findAllByTicker(String ticker);

    @Query(nativeQuery = true, value = "SELECT FROM price_alert WHERE user_id=?1")
    List<PriceAlert> findAllByUserId(Integer id);

}
