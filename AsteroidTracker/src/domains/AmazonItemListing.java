package domains;
import java.net.URL;

import utils.HttpUtil;

import android.graphics.drawable.Drawable;


public class AmazonItemListing {

    public String asinItemID;
    public String title;
    public String productGroup;
    public String author;
    public String imageUri;
    public String detailPageUri;
    public String description;
    public Drawable image;

    public String getAsinItemID() {
        return asinItemID;
    }
    public void setAsinItemID(String asinItemID) {
        this.asinItemID = asinItemID;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getProductGroup() {
        return productGroup;
    }
    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getImageUri() {
        return imageUri;
    }
    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
    public String getDetailPageUri() {
        return detailPageUri;
    }
    public void setDetailPageUri(String detailPageUri) {
        this.detailPageUri = detailPageUri;
    }

    public void updateImageURLDrawable() {
        if (this.imageUri.length() > 0) {
            this.image = HttpUtil.getImageData(this.imageUri);
        } else {
            // TODO Set a default book image
        }
    }

    public Drawable getImage() {
        return this.image;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setImage(Drawable image) {
        this.image = image;
    }
    public void printme(){
        System.out.println("ASIN "+getAsinItemID());
        System.out.println("Title "+getTitle());
        System.out.println("productGroup "+getProductGroup());
        System.out.println("author "+getAuthor());
        System.out.println("imageUri "+getImageUri());
        System.out.println("detailPageUri "+getDetailPageUri());
    }

}
