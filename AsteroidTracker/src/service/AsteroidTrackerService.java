package service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import utils.HttpUtil;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import domains.NearEarthObject;
import domains.Impact;
import domains.News;
import domains.baseEntity;


public class AsteroidTrackerService {

    public static String URI_USESERVICE    = "https://raw.github.com/AsteroidTracker/AsteroidTrackerService/master/useService";
    public static String URI_RECENT        = "https://raw.github.com/AsteroidTracker/AsteroidTrackerService/master/neo_recent/recent";
    public static String URI_UPCOMING      = "https://raw.github.com/AsteroidTracker/AsteroidTrackerService/master/neo_upcoming/upcoming";
    public static String URI_IMPACT        = "https://raw.github.com/AsteroidTracker/AsteroidTrackerService/master/neo_impact/impactRisk";
    public static String URI_NEWS          = "https://raw.github.com/AsteroidTracker/AsteroidTrackerService/master/neo_news/latestnews";

    boolean useService = false;
    public Gson gson = new Gson();
    HttpUtil httputil = new HttpUtil();
    boolean IsMocking = false;

    public boolean isGitServiceAvailable(){
        if (httputil.get(URI_USESERVICE).trim().equals("true")) {
            return true;
        }else {
            return false;
        }
    }

//    public ArrayList getMockedList(Object dataType, int setCount){
//        ArrayList responseData = null;
//        if (dataType instanceof NearEarthObject) {
//            responseData = new ArrayList<NearEarthObject>();
//            for (int i=0; i < setCount; i++){
//                NearEarthObject mock = new NearEarthObject();
//                mock.setName("TestName "+setCount);
//                mock.setEstimatedDiameter("");
//                mock.setHmagnitude("test");
//                mock.setDate("test");
//                mock.SetMissDistance_AU("test");
//                mock.setMissDistance_LD("test");
//                mock.setRelativeVelocity("test");
//                responseData.add(mock);
//            }
//        }
//        if (dataType instanceof News) {
//            responseData = new ArrayList<News>();
//            for (int i=0; i < setCount; i++){
//                News mock = new News();
//                mock.description = "This is mock data";
//                mock.title = "Asteroid news title";
//                mock.artcileUrl = "http://asteroidmock.com";
//                responseData.add(mock);
//            }
//        }
//        if (dataType instanceof Impact) {
//            responseData = new ArrayList<Impact>();
//            for (int i=0; i < setCount; i++){}
//        }
//        return responseData;
//    }


    public ArrayList<NearEarthObject> getNEOList(String URI) 
    {
            ArrayList<NearEarthObject> responseData = new ArrayList<NearEarthObject>();
            try {
                Type collectionType = new TypeToken<ArrayList<NearEarthObject>>(){}.getType();
                responseData = gson.fromJson(httputil.get(URI), collectionType);
            } catch (JsonSyntaxException e) {
                NearEarthObject neoerror = new NearEarthObject();
                neoerror.setName("Unable to retrieve Asteroid Data");
                if(responseData.size() > 0){
                    responseData.clear();
                }
                responseData.add(neoerror);
                Log.e("AsteroidTrackerService", "Error on getList" +e);
            }
            return responseData;
    }

    //TODO refactor this!!!
    public ArrayList<NearEarthObject> getNeoErrorList(){
        ArrayList<NearEarthObject> errorData = new ArrayList<NearEarthObject>();
        NearEarthObject neoerror = new NearEarthObject();
        neoerror.name = "Unable to retrieve Asteroid Data";
        errorData.add(neoerror);
        return errorData;
    }

    public ArrayList<News> getNewsErrorList(){
        ArrayList<News> errorData = new ArrayList<News>();
        News error = new News();
        error.title = "Unable to retrieve Asteroid Data";
        errorData.add(error);
        return errorData;
    }

    public ArrayList<Impact> getImpactErrorList(){
        ArrayList<Impact> errorData = new ArrayList<Impact>();
        Impact error = new Impact();
        error.name ="Unable to retrieve Asteroid Data"; 
        errorData.add(error);
        return errorData;
    }

    public ArrayList<News> getLatestNews(){
        ArrayList<News> newslist = new ArrayList<News>();
        try {
            Type collectionType = new TypeToken<ArrayList<News>>(){}.getType();
            newslist = gson.fromJson(httputil.get(URI_NEWS), collectionType);
            for(int i = 0; i < newslist.size(); i++){
                try {
                    newslist.get(i).updateImageURLDrawable();
                } catch(NullPointerException e) {
                    newslist.get(i).setDefaultIcon();
                }
            }
        } catch (JsonSyntaxException e) {
            News newsError = new News();
            newsError.title = "Unable to retrieve Asteroid News";
            if(newslist.size() > 0){
                newslist.clear();
            }
            newslist.add(newsError);
        }
        return newslist;
    }
    
    public ArrayList<Impact> getImpactData(){
        ArrayList<Impact> impactList = new ArrayList<Impact>();
        try {
            Type collectionType = new TypeToken<ArrayList<Impact>>(){}.getType();
            impactList = gson.fromJson(httputil.get(URI_IMPACT), collectionType);
        } catch (JsonSyntaxException e) {
            Impact impactError = new Impact();
            impactError.setName("Unable to retrieve Asteroid Data");
            if(impactList.size() > 0){
                impactList.clear();
            }
            impactList.add(impactError);
        }
        return impactList;
    }
}
