package pl.projectarea.project0.article;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String shortDescription;
    private String Description;
    private LocalDateTime localDate;
    private String imageSource;

    public Article() {
    }

    public Article(String shortDescription, String description, LocalDateTime localDate, String imageSource) {
        this.shortDescription = shortDescription;
        this.Description = description;
        this.localDate = localDate;
        this.imageSource = imageSource;
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
                "id=" + id +
                ", shortDescription='" + shortDescription + '\'' +
                ", Description='" + Description + '\'' +
                ", imageSource='" + imageSource + '\'' +
                '}';
    }
}
