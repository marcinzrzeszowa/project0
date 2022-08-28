package pl.projectarea.project0;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import pl.projectarea.project0.article.Article;
import pl.projectarea.project0.article.ArticleRepository;
import pl.projectarea.project0.pricealert.PriceAlert;
import pl.projectarea.project0.pricealert.PriceAlertRepository;
import pl.projectarea.project0.user.User;
import pl.projectarea.project0.user.UserRepository;

import java.math.BigDecimal;
import java.util.List;

@Configuration
class StartupData implements CommandLineRunner {

    private final ArticleRepository articleRepository;
    private final PriceAlertRepository priceAlertRepository;
    private final UserRepository userRepository;

    @Autowired
    public StartupData(ArticleRepository articleRepository, PriceAlertRepository priceAlertRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.priceAlertRepository = priceAlertRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadArticles();
        loadPriceAlerts();

    }

    private void loadArticles(){
        Article a1 = new Article("Witryna www.projectarea.pl", "Moja piewrsza aplikacja internetowa. Powstała w celu przećwiczenia wiedzę z zakresu tworzenia aplikacji w języku Java. \nJest to strona do umieszczania informacji o moich projekatach programistycznych.\n Powstała z wykorzystaniem m.in.technologii jak: Spring Boot, MySQL, Thymeleaf, CSS", "bol.jpg");
        Article a2 = new Article("Walutomat... ","Jestem w trakcie tworzenia równoległego projektu kalkulatora wymiany walut", "alejaja.jpg");
        articleRepository.saveAll(List.of(a1,a2));
    }


    private void loadPriceAlerts(){

        User testUser = new User();
        BigDecimal maxPrice = new BigDecimal(23000);
        BigDecimal minPrice = new BigDecimal(20000);


        PriceAlert pa1 = new PriceAlert("BTC-USD","Cena poza zakresem "+ maxPrice +" - " + minPrice,maxPrice,minPrice,true, null );
        PriceAlert pa2 = new PriceAlert("BTC-USD","Cena powyzej ceny "+ maxPrice,maxPrice,true, null );
        PriceAlert pa3 = new PriceAlert("BTC-USD","Cena poniżej ceny "+ minPrice,true,minPrice, null );
        priceAlertRepository.saveAll(List.of(pa1,pa2,pa3));
    }


}
