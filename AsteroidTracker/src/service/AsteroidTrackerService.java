package service;

import java.io.IOException;
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

    public static String useServiceUri    = "https://raw.github.com/AsteroidTracker/AsteroidTrackerService/master/useService";
    public static String URIRecent        = "https://raw.github.com/AsteroidTracker/AsteroidTrackerService/master/neo_recent/recent";
    public static String URIUpcoming      = "https://raw.github.com/AsteroidTracker/AsteroidTrackerService/master/neo_upcoming/upcoming";
    public static String URIImpact        = "https://raw.github.com/AsteroidTracker/AsteroidTrackerService/master/neo_impact/impactRisk";
    public static String URINews          = "https://raw.github.com/AsteroidTracker/AsteroidTrackerService/master/neo_news/latestnews";
    boolean useService = false;
    public static Gson gson = new Gson();
    HttpUtil httputil = new HttpUtil();
    
    public boolean IsGitServiceAvailable(){
        Log.i("gitservice", "IsGitServiceAvailable");
        if (HttpUtil.get(useServiceUri).trim().equals("true")){
            Log.i("gitservice", "IsGitServiceAvailable: "+ true);
            return true;
        }else {
            Log.i("gitservice", "IsGitServiceAvailable: "+ false);
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
            newslist = gson.fromJson(HttpUtil.get(URINews), collectionType);
            for(int i = 0; i < newslist.size(); i++){
                newslist.get(i).updateImageURLDrawable();
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
            impactList = gson.fromJson(HttpUtil.get(URIImpact), collectionType);
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
