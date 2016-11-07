package com.example.anupam.places.response;

import java.util.ArrayList;

/**
 * Created by anupam on 05-11-2016.
 */
public class Photo {
    private Integer height;
    private ArrayList<String> html_attributions;

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public ArrayList<String> getHtml_attributions() {
        return html_attributions;
    }

    public void setHtml_attributions(ArrayList<String> html_attributions) {
        this.html_attributions = html_attributions;
    }

    public String getPhoto_reference() {
        return photo_reference;
    }

    public void setPhoto_reference(String photo_reference) {
        this.photo_reference = photo_reference;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    private String photo_reference;
    private Integer width;
}
