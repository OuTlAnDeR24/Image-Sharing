package com.example.ashish.imagesharing;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.util.List;

/**
 * Created by ashish on 20/07/15.
 */
@ParseClassName("ImageObject")
public class ImageObject extends ParseObject {

//    private String author;
//    private List<Comments> comments;
//    private String title;
//    private double rate;
//    private String authorNumber;
//    private List<String> customerNumbers;
//    private List<ParseFile> photoFiles;

    public ImageObject() {
        // Default Constructor
    }

    public String getAuthor() {
        return getString("author");
    }

    public String getTitle() {
        return getString("title");
    }

    public List<Comments> getComments() {
        return getList("comments");
    }

    public String getAuthorNumber() {
        return getString("authorNumber");
    }

    public List<String> getCustomerNumbers() {
        return getList("customerNumbers");
    }

    public List<ParseFile> getPhotoFiles() {
        return getList("photoFiles");
    }

    public double getRate() {
        return getDouble("rate");
    }

    public void setAuthor(String author) {
        put("author" , author);
    }

    public void setTitle(String title) {
        put("title" , title);
    }

    public void setComments(List<Comments> comments) {
        put("comments" , comments);
    }

    public void setRate(double rate) {
        put("rate" , rate);
    }

    public void setAuthorNumber(String authorNumber) {
        put("authorNumber" , authorNumber);
    }

    public void setCustomerNumbers(List<String> customerNumbers) {
        put("customerNumbers" , customerNumbers);
    }

    public void setPhotoFiles(List<ParseFile> photoFiles) {
        put("photoFiles" , photoFiles);
    }

    public void addComment(Comments comment) {
        List<Comments> comments = getList("comments");
        comments.add(comment);
        put("comments", comments);
    }
}
