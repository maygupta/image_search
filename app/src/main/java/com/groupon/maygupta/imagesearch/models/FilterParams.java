package com.groupon.maygupta.imagesearch.models;

/**
 * Created by maygupta on 10/31/15.
 */
public class FilterParams {
    public String color;
    public String size;
    public String type;
    public String domain;

    public static int getIndex(String value, String[] stringArray) {
        int retIndex = 0;

        if (value.equals("any"))
            return retIndex;

        for ( int i = 0; i < stringArray.length; i++) {
            if (value.equals(stringArray[i])) {
                retIndex = i;
                break;
            }
        }
        return retIndex;
    }
}
