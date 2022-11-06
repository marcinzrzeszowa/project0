package pl.projectarea.project0;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.projectarea.project0.article.Article;
import pl.projectarea.project0.article.ArticleRepository;
import pl.projectarea.project0.pricealert.PriceAlert;
import pl.projectarea.project0.pricealert.PriceAlertRepository;
import pl.projectarea.project0.user.User;
import pl.projectarea.project0.user.UserRepository;
import pl.projectarea.project0.user.UserService;

import java.math.BigDecimal;
import java.util.List;

@Component
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
        LoadUsers();
        loadArticles();
        loadPriceAlerts();

    }

    private void LoadUsers(){

        User user1 = new User("Admin",UserService.passwordEncoder().encode("123"),"ROLE_ADMIN", "marcinzbrzozowa@gmail.com");
        User user2 = new User("test", UserService.passwordEncoder().encode("test"),"ROLE_MODERATOR", "test@gmail.com");
        userRepository.save(user1);
        userRepository.save(user2);

        PriceAlert pa1 = new PriceAlert("BTC-USD", "Opis1",new BigDecimal(23000),  new BigDecimal(20000), true, user1 );
        PriceAlert pa2 = new PriceAlert("BTC-USD","Cena powyzej ceny " ,new BigDecimal(18000),true, user1 );
        PriceAlert pa3 = new PriceAlert("PLN=X","Cena poniżej ceny " ,true, new BigDecimal(5), user2 );
        PriceAlert pa4 = new PriceAlert("PLN=X","Opis 4 ",true, new BigDecimal(4), user2 );
        PriceAlert pa5 = new PriceAlert("EURPLN=X","Opis 5",new BigDecimal(7),new BigDecimal(6),true, user2 );
        PriceAlert pa6 = new PriceAlert("EURPLN=X","Opis 6",true,new BigDecimal(5), user1 );
        priceAlertRepository.saveAll(List.of(pa1,pa2,pa3,pa4,pa5,pa6));
    }

    private void loadArticles(){
        Article a1 = new Article(
                "Dlaczego powstała strona ProjectArea",
                "<div class=\"contentLeft\">Witam serdecznie. <br/>  Potrzeba przećwiczenia i utrwalenia wiedzy z zakresu programowania, stała się powodem powastania tej witryny." +
                " Szukałem tematu na ciekawy projekt, i stąd pomysł na stronę. Biorąc pod uwagę moje bardzo małe doświadczenie z zakresu programowania, projekt okazał się wymagający i czasochłonny. Strona internetowa jest funkcjonalna na zadowalającym poziome. Dlatego jestem zadowolony z osiągniętego efektu." +
                "Chciałbym jednak zaznaczyć, że prace nad treścią witryny i formą prezentacji nadal trwają.</br>" +
                "\tGłówną funkcjonalnością mojej aplikacji jest:</br>" +
                "<ul>" +
                "   <li>" +
                        "Pobieranie aktualnego kursów surowców, walut i kryptowalut z giełdy. Wykożystałem do tego dane, udostępniane na stronie <a href=\"https://finance.yahoo.com\">https://finance.yahoo.com</a>. Serwis daje dostęp do darmowego interfejs dla programistów, i nie nakłada dużych ograniczeń w wersji bezpłatnej." +
                "   </li>"+
                        "Kolejną funkcjonalnością jest przechowywanie wiadomości tekstowych użytkowników w bazie danych. Każda wiadomośc zawiera informacje o cene, konkretnego waloru z giełdy." +
                        "Wiadomości te mają na celu informować użutkownika o zmianie ceny rynkowaj w interesującym go zakresie."+
                "   <li>" +
                        "Pobieranie danych zewnętrzych z giełdy odbywa się, w regularnym odstępie czasowym ustawionym na 5 minut." +
                "   </li>"+
                "   <li>" +
                "Następnie ceny z giełdy porównywane są z zakresem cen zapisanym przez użytkowników." +
                "Jeżeli aktulnay kurs z giełdy przkracza wartość ceny maksymalnej, lub spada poniżej wartości ceny minimalnej, zostanie wysłana wiadomość." +
                "Każda wiadomość powiązana jest z kontem użytkownika i zotaje przesłana na wskazany przy rejestracji adres e-mail." +
                "Następnie wiadomość przetaje być aktywna i nie jest brana pod uwagę przy kolejnym cyklu sprawdzania ceny." +
                "Aplikacja nasłuchuje zmian w liscie wiadomości uzytkowników w celu jej aktualizacji." +
                "Tylko zarejestrowany użytkownik ma możliwość dodawania i edycji swoich wiadomości." +
                "Konto administratora umożliwia zarządzanie użytkownikami i wiadomościami wszystkich użytkowników.</br>" +
                "Każdy odwiedzający stronę, ma możliwość przeglądnięcia kursów giełdowych z dostępnej listy wraz z wskaźnikami giełdowymi oraz zapoznaniem się z nowościami na stronie startowej." +
                "Projekt powstał z wykorzystaniem języka programowania Java, oraz technologii: Spring Boot, MySQL, Thymeleaf, CSS </div>" +
                "   </li>"+
                "</ul>");
        articleRepository.saveAll(List.of(a1));
    }

    private void loadPriceAlerts(){
    }
}
