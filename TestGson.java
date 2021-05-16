package test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

class student{
    public int id =0;
    public String name ="";
}
public class TestGson {
    public static void main(String[] args) {
        Gson gson = new GsonBuilder().create();
    }
}
