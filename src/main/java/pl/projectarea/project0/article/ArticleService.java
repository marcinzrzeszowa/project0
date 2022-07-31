package pl.projectarea.project0.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
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
    public void deleteArticle(int id) {
    if (!articleRepository.existsById(id)){
        throw new IllegalStateException("Nie możesz usunąć nie istniejącego artykułu");
    }
    articleRepository.deleteById(id);
    }

    //@Transactional
    public void updateArticle(int id) {
        //TODO
    }
}
