package com.example.ashish.imagesharing;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by ashish on 20/07/15.
 */
@ParseClassName("Comments")
public class Comments extends ParseObject {

    public Comments() {
        // Default Constructor
    }

    public String getUserName() {
        return getString("userName");
    }

    public String getPhoneNumber() {
        return getString("phoneNumber");
    }

    public void setUserName(String userName) {
        put("userName", userName);
    }

    public void setPhoneNumber(String phoneNumber) {
        put("phoneNumber", phoneNumber);
    }



}
