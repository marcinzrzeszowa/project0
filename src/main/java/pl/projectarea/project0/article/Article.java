package pl.projectarea.project0.article;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Entity
@Table
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Podaj opis")
    private String shortDescription;
    @NotBlank(message = "Napisz treść")
    private String Description;
    @NotBlank(message = "Podaj lokalizację pliku")
    private String imageSource;
    private LocalDateTime localDate;

    public Article() {
        this.localDate = LocalDateTime.now();
    }
    public Article(String shortDescription, String description, String imageSource) {
        this.shortDescription = shortDescription;
        this.Description = description;
        this.imageSource = imageSource;
        this.localDate = LocalDateTime.now();
    }

    public int getId() {
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

    public LocalDateTime getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDateTime localDate) {

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
}
