package pl.projectarea.project0.article;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "short_description", length = 200)
    @NotBlank(message = "Podaj opis")
    private String shortDescription;

    @Column(name = "description", length = 3000)
    @NotBlank(message = "Napisz treść")

    private String Description;

    @Column(name = "local_date")
    @NotNull
    private LocalDate localDate;

    public Article() {

        this.localDate = LocalDate.now();
    }
    public Article(String shortDescription, String description) {
        this.shortDescription = shortDescription;
        this.Description = description;
        this.localDate = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return Description;
    }

    public LocalDate getLocalDate() {

        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String text = localDate.format(formatters);
        LocalDate parsedDate = LocalDate.parse(text, formatters);
        return parsedDate;
    }

    public void setLocalDate(LocalDate localDate) {

        this.localDate = localDate;
    }
    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article)) return false;
        Article article = (Article) o;
        return id.equals(article.id);
    }
    @Override
    public int hashCode() {
        return id.hashCode() + 21;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", Description='" + Description + '\'' +
                ", localDate=" + localDate + '\'' +
                '}';
    }
}
