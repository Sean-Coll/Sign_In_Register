package com.example.sign_in_register;

public class Item {

    private String contentText;               // To show the item name such as font-size, background color etc...
    private String extraParameter;            // extra parameter to use for different situation


    // constracutor
    public Item(String contentText,String extraParameter)
    {
        this.contentText = contentText;
        this.extraParameter = extraParameter;
    }

    // setter
    public void setContentText(String contentText)
    {
        this.contentText = contentText;
    }

    public void setextraParameter(String extraParameter)
    {
        this.extraParameter = extraParameter;
    }

    // getter
    public String getContentText()
    {
        return contentText;
    }

    public String getextraParameter()
    {
        return extraParameter;
    }
}
