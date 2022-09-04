package pl.projectarea.project0.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class ArticleController {

    private final ArticleService articleService;
    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public String showIndex(Model model){
        model.addAttribute("articles", articleService.getArticles());
        return "home";
    }

    @GetMapping(value = {"/articles"})  //ok
    public List<Article> showArticles() {
        return articleService.getArticles();
    }
    @PostMapping(value = {"/articles"}, consumes = {"application/json"})
    public void addArticle(@RequestBody Article article) {
        articleService.addArticle(article);
    }

    @DeleteMapping(path = "/articles/{id}")
    public void deleteArticle(@PathVariable("id") Integer id){
        articleService.deleteArticle(id);
    }

    @PutMapping(path = "/articles/{id}")
    public Article updateArticle(@RequestBody Article newArticle, @PathVariable("id") Integer id)
    {
        return articleService.updateOrSaveArticle(id, newArticle);
    }

}
