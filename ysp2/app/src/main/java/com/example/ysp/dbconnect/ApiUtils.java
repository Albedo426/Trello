package com.example.ysp.dbconnect;

public class ApiUtils {
    public static final String BASE_URL="http://192.168.1.35/mobil/ysP/";
    public static dbInterface getdbInterface(){
        return RetrofitClient.getClient(BASE_URL).create(dbInterface.class);
    }
 
}
