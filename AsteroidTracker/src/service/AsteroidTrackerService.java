package service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import utils.HttpUtil;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import domains.NearEarthObject;
import domains.Impact;
import domains.News;


public class AsteroidTrackerService {

    public static String URI_USESERVICE    = "https://raw.github.com/AsteroidTracker/AsteroidTrackerService/master/useService";
    public static String URI_RECENT        = "https://raw.github.com/AsteroidTracker/AsteroidTrackerService/master/neo_recent/recent";
    public static String URI_UPCOMING      = "https://raw.github.com/AsteroidTracker/AsteroidTrackerService/master/neo_upcoming/upcoming";
    public static String URI_IMPACT        = "https://raw.github.com/AsteroidTracker/AsteroidTrackerService/master/neo_impact/impactRisk";
    public static String URI_NEWS          = "https://raw.github.com/AsteroidTracker/AsteroidTrackerService/master/neo_news/latestnews";
    boolean useService = false;
    public static Gson gson = new Gson();
    HttpUtil httputil = new HttpUtil();
    
    public boolean isGitServiceAvailable(){
        if (HttpUtil.get(URI_USESERVICE).trim().equals("true")){
            Log.d("gitservice", "IsGitServiceAvailable: "+ true);
            return true;
        }else {
            Log.d("gitservice", "IsGitServiceAvailable: "+ false);
            return false;
        }
    }
    
    public ArrayList<NearEarthObject> getNEOList(String URI){
        ArrayList<NearEarthObject> responseData = new ArrayList<NearEarthObject>();
        try {
            Type collectionType = new TypeToken<ArrayList<NearEarthObject>>(){}.getType();
            responseData = gson.fromJson(HttpUtil.get(URI), collectionType);
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
    
    public ArrayList<News> getLatestNews(){
        ArrayList<News> newslist = new ArrayList<News>();
        try {
            Type collectionType = new TypeToken<ArrayList<News>>(){}.getType();
            newslist = gson.fromJson(HttpUtil.get(URI_NEWS), collectionType);
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
            impactList = gson.fromJson(HttpUtil.get(URI_IMPACT), collectionType);
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
