package qr.hiram.qreader.models;

public class Article {
    private String id, description, image, color;
    private float price;

    public Article(String id, String description, String image, String color, float price) {
        this.id = id;
        this.description = description;
        this.image = image;
        this.color = color;
        this.price = price;
    }

    public Article() {
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
