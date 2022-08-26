package pl.projectarea.project0.notification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceAlertRepository extends JpaRepository<PriceAlert, Integer> {
}