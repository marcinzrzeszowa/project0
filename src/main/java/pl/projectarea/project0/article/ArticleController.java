package pl.projectarea.project0.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public List<Article> showArticles() {
        return articleService.getArticles();
    }

    @PostMapping( consumes = {"application/json"})
    public void addArticle(@RequestBody Article article) {
        articleService.addArticle(article);
    }

    @DeleteMapping(path = "{id}")
    public void deleteArticle(@PathVariable("id") int id){
        articleService.deleteArticle(id);
    }

    @PutMapping(path = "{id}")
    public void updateArticle(@PathVariable("id") int id){
        articleService.updateArticle(id);
    }
}
