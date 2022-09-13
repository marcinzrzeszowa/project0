package pl.projectarea.project0.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Article getArticle(Long id){
        return articleRepository.findById(id).orElseThrow(()->new RuntimeException("Artykuł nie istnieje"));
    }

    public Article saveArticle(Article article) {
        if(!article.getDescription().isBlank() && !article.getShortDescription().isBlank()){
            articleRepository.save(article);
        }
        return article;
    }
    public void deleteArticle(Long id) {
        //Optional<Article> article = articleRepository.findById(id).
    if (!articleRepository.existsById(id)){
        throw new IllegalStateException("Nie możesz usunąć nie istniejącego artykułu");
    }
    articleRepository.deleteById(id);
    }

    //@Transactional
    public Article updateOrSaveArticle(Long id, Article newArticle) {
            Article article = articleRepository.findById(id)
                    .map(element ->{
                        element.setShortDescription(newArticle.getShortDescription());
                        element.setDescription(newArticle.getDescription());
                        element.setLocalDate(newArticle.getLocalDate());
                        return  articleRepository.save(element);
                    }).orElseGet(()->{
                        return articleRepository.save(newArticle);
                    });
            return article;

            //ifPresentOrElse(System.out::println, ()-> System.out.println() "Nie może znaleść artykułu")
    }

    public Article findById(Long articleId) {
        return articleRepository.findById(articleId).stream().findFirst().orElse(null);
    }
}
