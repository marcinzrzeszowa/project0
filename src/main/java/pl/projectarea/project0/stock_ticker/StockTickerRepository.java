package pl.projectarea.project0.stock_ticker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.projectarea.project0.user.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockTickerRepository extends JpaRepository<StockTicker,Long> {

    StockTicker findByName(String name);

    List<StockTicker> findByType(TickerType tickerType);

    List<StockTicker> findAll();

    Optional<StockTicker> findById(Long id);

    StockTicker save(StockTicker entity);

    boolean existsById(Long id);

    void deleteById(Long id);

}
