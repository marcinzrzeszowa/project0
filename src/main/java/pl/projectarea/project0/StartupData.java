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
import java.util.Arrays;
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
        StockTicker st12 = new StockTicker("TSLA","Tesla, Inc.", TickerType.AKCJA);

        stockTickerRepository.saveAll(Arrays.asList(st0,st1,st2,st3,st4,st5,st6,st7,st8,st9,st10,st11,st12));

        User user1 = new User("Admin",UserService.passwordEncoder().encode("123"),"ROLE_ADMIN", "marcinzbrzozowa@gmail.com");
        User user2 = new User("test", UserService.passwordEncoder().encode("test"),"ROLE_MODERATOR", "test@gmail.com");
        userRepository.save(user1);
        userRepository.save(user2);

        PriceAlert pa1 = PriceAlert.newObj(st9, user1,"Cena BTC poza zakresem cenowym", new BigDecimal(23000), new BigDecimal(16000));
        PriceAlert pa2 = PriceAlert.newObjJustMinPrice(st9,user1,new BigDecimal(16000),"Cena BTC spadła !");
        PriceAlert pa3 = PriceAlert.newObjJustMaxPrice(st0,user2,"Cena USD drastycznie wzrosła ! ",new BigDecimal(5.43));
        PriceAlert pa4 = PriceAlert.newObj(st5,user2,"Złoto poza zakresem",new BigDecimal(1800), new BigDecimal(1700));
        PriceAlert pa5 = PriceAlert.newObjJustMinPrice(st1, user1, new BigDecimal(4.63),"EUR spada");
        PriceAlert pa6 = PriceAlert.newObj(st1, user1,"EUR poza zakresem",  new BigDecimal(4.5),  new BigDecimal(4));

        priceAlertRepository.saveAll(Arrays.asList(pa1,pa2,pa3,pa4,pa5,pa6));
    }
    //&nbsp;
    private void loadArticles(){
        Article a1 = new Article(
                "Witam.<br/>Zakładka \"Notowania giełdowe\" zawiera odnośnik do aktualnych cen z giełdy. Proszę o cierpliwość przy ładowaniu ponieważ dane pochodzą z darmowego źródła. " +
                        "Treść strony zostanie porzeżona o&nbsp;inną tematykę i nowe funkcjonalności. Mam zamiar zaprezentować na niej inne projekty programistyczne w&nbsp;przyszłości. ",
                "Szukałem tematu na ciekawy projekt programistyczny, i stąd pomysł na stworzenie strony z&nbsp;kursami giełdowymi. " +
                        " <br/> Projekt powstał z wykorzystaniem języka programowania Java, oraz technologii: Spring Boot, MySQL, Thymeleaf, CSS, HTML. <br/> " +
                "<br/>Główną funkcjonalnością mojej aplikacji jest:</br>" +
                "<ul>" +
                "   <li>" +
                        "Pobieranie aktualnego kursów surowców, indeksów, akcji, walut i kryptowalut z giełdy. Wykożystałem do tego dane, udostępniane z zewnętrznej strony. " +
                        "Serwis daje dostęp do darmowego interfejs dla programistów, i nie nakłada dużych ograniczeń na programistę w&nbsp;wersji bezpłatnej." +
                        "<br/>Aktualizacja danych zewnętrzych z giełdy odbywa się, w regularnym odstępie czasowym ustawionym na pięć minut. " +
                        "   </li>"+
                "   <li>" +
                        "Kolejną funkcjonalnością jest rejestracja użytkowników. Przechowywanie komunikatów tekstowych od użytkowników w bazie danych. Każdy komunikat odnosi się waloru z&nbsp;giełdy. " +
                        "Komunikaty mają na celu informować użutkownika o zmianie ceny rynkowaj w&nbsp;interesującym go zakresie. "+

                "   </li>"+
                "   <li>" +
                "Ceny z giełdy porównywane są z zakresem cen zapisanym przez użytkowników." +
                "<br/>Jeżeli aktulnay kurs z giełdy przkracza wartość ceny maksymalnej, lub spada poniżej wartości ceny minimalnej, zostanie wysłana wiadomość. " +
                "Każda wiadomość powiązana jest z kontem użytkownika i zotaje przesłana na wskazany przy rejestracji adres e-mail. " +
                "Następnie wiadomość przetaje być aktywna i nie jest brana pod uwagę przy kolejnym cyklu sprawdzania ceny. " +
                    "<li>" +
                        "Aplikacja nasłuchuje zmian w liscie wiadomości uzytkowników w celu jej aktualizacji. " +
                     "</li>"+
                        "<li>" +
                        "Tylko zarejestrowany użytkownik ma możliwość dodawania i edycji wiadomości z&nbsp;powiadomieniami. " +
                        "</li>"+
                        "<li>" +
                        "Konto administratora umożliwia zarządzanie użytkownikami i wiadomościami wszystkich użytkowników. " +
                        "</li>"+
                        "<li>" +
                        "Każdy odwiedzający stronę, ma możliwość przeglądnięcia kursów giełdowych z dostępnej listy wraz z&nbsp;wskaźnikami giełdowymi oraz zapoznaniem się z nowościami na stronie startowej. " +
                        "</li>"+
                        "" +
                        "<li>" +
                        "<a href=\"https://github.com/marcinzrzeszowa/project0\">Kod źródłowy</a> dostępny na github.com" +
                        "</li>"+
                        "" +
                        "</div>" +
                "   </li>"+
                "</ul>");
        articleRepository.saveAll(Arrays.asList(a1));
    }
}
