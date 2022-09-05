package pl.projectarea.project0.pricealert;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.projectarea.project0.user.User;

import java.util.List;

@Repository
public interface PriceAlertRepository extends JpaRepository<PriceAlert, Integer> {
    //boolean existsByIsActiveIsFalseAndUser_Id(Integer groupId);
   // List<User> findAllByUser_Id(Integer integer);
}
