package pl.projectarea.project0;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.projectarea.project0.article.Article;
import pl.projectarea.project0.article.ArticleRepository;
import pl.projectarea.project0.price_alert.PriceAlert;
import pl.projectarea.project0.price_alert.PriceAlertRepository;
import pl.projectarea.project0.stock_ticker.StockTicker;
import pl.projectarea.project0.stock_ticker.StockTickerRepository;
import pl.projectarea.project0.stock_ticker.TickerType;
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
    private final StockTickerRepository stockTickerRepository;

    @Autowired
    public StartupData(ArticleRepository articleRepository, PriceAlertRepository priceAlertRepository, UserRepository userRepository, StockTickerRepository stockTickerRepository) {
        this.articleRepository = articleRepository;
        this.priceAlertRepository = priceAlertRepository;
        this.userRepository = userRepository;
        this.stockTickerRepository = stockTickerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadArticles();
        Load();
    }

    private void Load(){

        StockTicker st0 = new StockTicker("PLN=X","PLN/USD", TickerType.WALUTA);
        StockTicker st1 = new StockTicker("EURPLN=X","EUR/PLN", TickerType.WALUTA);
        StockTicker st2 = new StockTicker("GBPPLN=X","GBP/PLN", TickerType.WALUTA);
        StockTicker st3 = new StockTicker("CHFPLN=X","CHF/PLN", TickerType.WALUTA);
        StockTicker st4 = new StockTicker("EUR=X","USD/EUR", TickerType.WALUTA);
        StockTicker st5 = new StockTicker("GC=F","Złoto/USD", TickerType.SUROWIEC);
        StockTicker st6 = new StockTicker("SI=F","Srebro/USD", TickerType.SUROWIEC);
        StockTicker st7 = new StockTicker("BZ=F","Ropa/USD", TickerType.SUROWIEC);
        StockTicker st8 = new StockTicker("^GSPC","S&P500", TickerType.INDEX);
        StockTicker st9 = new StockTicker("BTC-USD","BTC/USD", TickerType.KRYPTOWALUTA);
        StockTicker st10 = new StockTicker("ETH-USD","ETH/USD", TickerType.KRYPTOWALUTA);
        StockTicker st11 = new StockTicker("DOT-USD","DOT/USD", TickerType.KRYPTOWALUTA);
        stockTickerRepository.saveAll(List.of(st0,st1,st2,st3,st4,st5,st6,st7,st8,st9,st10,st11));

        User user1 = new User("Admin",UserService.passwordEncoder().encode("123"),"ROLE_ADMIN", "marcinzbrzozowa@gmail.com");
        User user2 = new User("test", UserService.passwordEncoder().encode("test"),"ROLE_MODERATOR", "test@gmail.com");
        userRepository.save(user1);
        userRepository.save(user2);

        PriceAlert pa1 = PriceAlert.newObj(st9, user1,"Cena BTC poza zakresem cenowym", new BigDecimal(23000), new BigDecimal(20000));
        PriceAlert pa2 = PriceAlert.newObjJustMinPrice(st9,user1,new BigDecimal(16000),"Cena BTC spadła !");
        PriceAlert pa3 = PriceAlert.newObjJustMaxPrice(st0,user2,"Cena USD drastycznie wzrosła ! ",new BigDecimal(5));
        PriceAlert pa4 = PriceAlert.newObj(st5,user2,"Złoto poza zakresem",new BigDecimal(1800), new BigDecimal(1700));
        PriceAlert pa5 = PriceAlert.newObjJustMinPrice(st1, user1, new BigDecimal(4),"EUR spada");
        PriceAlert pa6 = PriceAlert.newObj(st1, user1,"EUR poza zakresem",  new BigDecimal(4.5),  new BigDecimal(4));
        priceAlertRepository.saveAll(List.of(pa1,pa2,pa3,pa4,pa5,pa6));
    }

    private void loadArticles(){
        Article a1 = new Article(
                "Dlaczego powstała strona ProjectArea",
                "<div class=\"contentLeft\">Witam. <br/>"+
                "Szukałem tematu na ciekawy projekt programistyczny, i stąd pomysł na stworzenie strony. Biorąc pod uwagę moje bardzo małe doświadczenie z zakresu programowania, projekt okazał się wymagający i czasochłonny. Strona internetowa jest funkcjonalna na zadowalającym poziome. Dlatego jestem zadowolony z osiągniętego efektu." +
                " <br/> Chciałbym jednak zaznaczyć, że prace nad treścią witryny jak i formą prezentacji nadal trwają.</br>" +
                "<br/>Główną funkcjonalnością mojej aplikacji jest:</br>" +
                "<ul>" +
                "   <li>" +
                        "Pobieranie aktualnego kursów surowców, walut i kryptowalut z giełdy. Wykożystałem do tego dane, udostępniane z zewnętrznej strony. Serwis daje dostęp do darmowego interfejs dla programistów, i nie nakłada dużych ograniczeń na programistę w wersji bezpłatnej." +
                "   </li>"+
                "   <li>" +
                        "Kolejną funkcjonalnością jest przechowywanie wiadomości tekstowych użytkowników w bazie danych. Każda wiadomośc zawiera informacje o cene, konkretnego waloru z giełdy." +
                        " Wiadomości te mają na celu informować użutkownika o zmianie ceny rynkowaj w interesującym go zakresie."+
                        "<br/>Pobieranie danych zewnętrzych z giełdy odbywa się, w regularnym odstępie czasowym ustawionym na 5 minut." +
                "   </li>"+
                "   <li>" +
                "Następnie ceny z giełdy porównywane są z zakresem cen zapisanym przez użytkowników." +
                "<br/>Jeżeli aktulnay kurs z giełdy przkracza wartość ceny maksymalnej, lub spada poniżej wartości ceny minimalnej, zostanie wysłana wiadomość." +
                "<br/>Każda wiadomość powiązana jest z kontem użytkownika i zotaje przesłana na wskazany przy rejestracji adres e-mail." +
                "Następnie wiadomość przetaje być aktywna i nie jest brana pod uwagę przy kolejnym cyklu sprawdzania ceny." +
                    "<li>" +
                        "Aplikacja nasłuchuje zmian w liscie wiadomości uzytkowników w celu jej aktualizacji." +
                     "</li>"+
                        "<li>" +
                        "Tylko zarejestrowany użytkownik ma możliwość dodawania i edycji swoich wiadomości." +
                        "</li>"+
                        "<li>" +
                        "Konto administratora umożliwia zarządzanie użytkownikami i wiadomościami wszystkich użytkowników.</br>" +
                        "</li>"+
                        "<li>" +
                        "Każdy odwiedzający stronę, ma możliwość przeglądnięcia kursów giełdowych z dostępnej listy wraz z&nbsp;wskaźnikami giełdowymi oraz zapoznaniem się z nowościami na stronie startowej." +
                        "</li>"+
                        "<li>" +
                        "Projekt powstał z wykorzystaniem języka programowania Java, oraz technologii: Spring Boot, MySQL, Thymeleaf, CSS " +
                        "</li>"+
                        "" +
                        "" +
                        "</div>" +
                "   </li>"+
                "</ul>");
        articleRepository.saveAll(List.of(a1));
    }

}
