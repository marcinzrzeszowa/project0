package pl.projectarea.project0.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import static org.apache.coyote.http11.Constants.a;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> getArticles( ){
        return articleRepository.findAll();
    }

    public void addArticle(Article article) {
        if(!article.getDescription().isBlank() && !article.getShortDescription().isBlank() && !article.getImageSource().isBlank()){

            articleRepository.save(article);
        }
    }
}
