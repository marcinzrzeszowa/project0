package pl.projectarea.project0.about;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.projectarea.project0.article.Article;
import pl.projectarea.project0.article.ArticleService;

@Controller
public class AboutController {

    public AboutController(){
    }

    @GetMapping(value = {"/about"})
    public String showAbout(){
        return "about";
    }

}
