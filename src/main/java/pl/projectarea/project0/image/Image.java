package pl.projectarea.project0.image;

public class Image {
    private Integer id;
    private String name;

    public Image() {
    }

    public Image(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
