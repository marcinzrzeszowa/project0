package pl.projectarea.project0.article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository  extends JpaRepository<Article,Long> {

    List<Article> findAll();

    Optional<Article> findById(Long id);

    Article save(Article entity);

    boolean existsById(Long id);

    void deleteById(Long id);
}
