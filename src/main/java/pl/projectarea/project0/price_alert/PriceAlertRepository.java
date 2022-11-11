package pl.projectarea.project0.price_alert;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PriceAlertRepository extends JpaRepository<PriceAlert, Long> {

    List<PriceAlert> findByIsActive(boolean isActive);

    @Query(nativeQuery = true, value ="select * from price_alert p left join user u ON p.user_id = u.id Where u.id = :id")
    List<PriceAlert> findByUserId(@Param("id") Long id);

   /* @Query(nativeQuery = true, value ="select * from price_alert p left join user u ON p.user_id = u.id Where u.id = :id And p.is_active = true")
    List<PriceAlert> findByUserIdAndIsActive(@Param("id") Long id);
*/
    List<PriceAlert> findAll();

    Optional<PriceAlert> findById(Long id);

    PriceAlert save(PriceAlert entity);

    boolean existsById(Long id);

    void deleteById(Long id);




  /*
  SELECT * FROM `project0`.`user` WHERE `id` = 1

"FROM price_alert LEFT JOIN user ON price_alert.user = user.id WHERE  user_id=:id"

  select g, p from Participation p
    right outer join p.group g
    where p.user.id=:userId

    from Participation p left join p.user u where u.id =:userid

    from Participation p join fetch p.group where p.user.id=:userId

  @Query("from TaskGroup g join fetch g.tasks")
    List<TaskGroup> findAll();

      @Query(nativeQuery = true, value = "select count(*) > 0 from tasks where id=:id")
    boolean existsById(@Param("id") Integer id);

  SELECT price_alert.id, price_alert.description, user.username FROM `price_alert` LEFT JOIN `user` ON price_alert.user = user.id
ORDER BY `user`.`username` ASC;

SELECT price_alert.id, price_alert.description, user.username FROM `price_alert` LEFT JOIN `user` ON price_alert.user = user.id
WHERE  user.id=1

 @Query("select p from PriceAlert p inner join p.user ")
    */

}
