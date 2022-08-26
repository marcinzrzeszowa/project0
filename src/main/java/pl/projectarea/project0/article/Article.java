package pl.projectarea.project0.article;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
@Table(name = "article")
public class Article {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "short_description", length = 200)
    @NotBlank(message = "Podaj opis")
    private String shortDescription;

    @Column(name = "description", length = 1000)
    @NotBlank(message = "Napisz treść")
    private String Description;

    @Column(name = "image_source ")
    private String imageSource;

    @Column(name = "local_date")
    private LocalDate localDate;

    public Article() {

        this.localDate = LocalDate.now();
    }
    public Article(String shortDescription, String description, String imageSource) {
        this.shortDescription = shortDescription;
        this.Description = description;
        this.imageSource = imageSource;
        this.localDate = LocalDate.now();
    }

    public Integer getId() {
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

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", Description='" + Description + '\'' +
                ", localDate=" + localDate + '\'' +
                ", imageSource='" + imageSource + '\'' +
                '}';
    }

   /* @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;

        return id == category.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }*/

}
