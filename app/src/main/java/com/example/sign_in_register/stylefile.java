package com.example.sign_in_register;

public class stylefile {

    private int fontsize,fontstyleCode;
    private String ColorString;

    //constructor
    public stylefile(int fontsize,int fontstyleCode, String ColorString){
        this.fontsize = fontsize;
        this.fontstyleCode = fontstyleCode;
        this.ColorString = ColorString;
    }

    //setter
    public void setfontsize(int fontsize)
    {
        this.fontsize = fontsize;
    }

    public void setFontstyleCode(int fontstyleCode)
    {
        this.fontstyleCode = fontstyleCode;
    }

    public void setColorString(String ColorString)
    {
        this.ColorString = ColorString;
    }

    //getter

    public int getfontsize()
    {
        return fontsize;
    }

    public int getFontstyleCode()
    {
        return fontsize;
    }

    public String getColorString()
    {
        return ColorString;
    }
}
