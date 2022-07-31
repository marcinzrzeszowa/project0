package pl.projectarea.project0.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/articles")
    public List<Article> showArticles( ){
        return articleService.getArticles();
    }

    @PostMapping(value = "/articles", consumes = {"application/json"})
    public void addArticle(@RequestBody Article article){
        articleService.addArticle(article);
    }
}
