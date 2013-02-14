package service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import utils.HttpUtil;

import com.google.gson.Gson;

public class BaseService {

    HttpUtil httputil = new HttpUtil();
    Gson gson = new Gson();
 
    public String convertToJson(List objectToConvert) {
        Gson gson = new Gson();
        return gson.toJson(objectToConvert);
    }
    
    public ArrayList<Object> convertFromJson(String jsonData, Type collectionDataType) {
        ArrayList list = new ArrayList();
        Gson gson = new Gson();
        list = gson.fromJson(jsonData, collectionDataType);
        return list;
    }
}
