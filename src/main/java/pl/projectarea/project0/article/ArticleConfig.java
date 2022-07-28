package pl.projectarea.project0.article;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class ArticleConfig {

    @Bean
    CommandLineRunner initArticles(ArticleRepository repository){
        return args -> {
            Article a1 = new Article("artykuł1","Początki bywają bolesne", LocalDateTime.now(), "bol.jpg");
            Article a2 = new Article("artykuł2","Artukół drugi", LocalDateTime.now(), "alejaja.jpg");
            Article a3 = new Article("artykuł3","Temat trzeci", LocalDateTime.now(), "temat.jpg");
            repository.saveAll(List.of(a1,a2,a3));
        };
    }
}
