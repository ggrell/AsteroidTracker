package service;

import java.lang.reflect.Type;
import java.util.ArrayList;

import android.util.Log;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import domains.AmazonItemListing;
import domains.Impact;
import domains.NearEarthObject;
import domains.News;
import domains.baseEntity;


public class AsteroidTrackerService extends BaseService {

    public static String URI_USESERVICE    = "https://raw.github.com/AsteroidTracker/AsteroidTrackerService/master/useService";
    public static String URI_RECENT        = "https://raw.github.com/AsteroidTracker/AsteroidTrackerService/master/neo_recent/recent";
    public static String URI_UPCOMING      = "https://raw.github.com/AsteroidTracker/AsteroidTrackerService/master/neo_upcoming/upcoming";
    public static String URI_IMPACT        = "https://raw.github.com/AsteroidTracker/AsteroidTrackerService/master/neo_impact/impactRisk";
    public static String URI_NEWS          = "https://raw.github.com/AsteroidTracker/AsteroidTrackerService/master/neo_news/latestnews";
    public static String URI_BOOKS         = "https://raw.github.com/AsteroidTracker/AsteroidTrackerService/master/neo_books/books";


    boolean useService = false;
    boolean IsMocking = false;

    public boolean isGitServiceAvailable(){
        if (httputil.get(URI_USESERVICE).trim().equals("true")) {
            return true;
        }else {
            return false;
        }
    }

    public ArrayList<NearEarthObject> getNEOList(String URI) 
    {
            ArrayList<NearEarthObject> responseData = new ArrayList<NearEarthObject>();
            try {
                Type collectionType = new TypeToken<ArrayList<NearEarthObject>>(){}.getType();
                responseData = gson.fromJson(httputil.get(URI), collectionType);
            } catch (JsonSyntaxException e) {
                responseData = getNeoErrorEntity(responseData);
                Log.e("AsteroidTrackerService", "Error on getList" +e);
            }
            catch (Exception e) {
                responseData = getNeoErrorEntity(responseData);
                Log.e("AsteroidTrackerService", "Error on getList" +e);
            }
            return responseData;
    }

    public ArrayList sanitizeList(ArrayList list) {
        if(list.size() > 0) {
            list.clear();
        }
        return list;
    }

    public ArrayList<NearEarthObject> getNeoErrorEntity(ArrayList<NearEarthObject> list) {
        list = sanitizeList(list);
        NearEarthObject error = new NearEarthObject();
        error.setName(baseEntity.FAILURELOADING);
        list.add(error);
        return list;
    }

    public ArrayList<News> getNewsErrorEntity(ArrayList<News> list) {
        list = sanitizeList(list);
        News error = new News();
        error.setTitle(baseEntity.FAILURELOADING);
        list.add(error); 
        return list;
    }
    public ArrayList<Impact> getImpactErrorEntity(ArrayList<Impact> list) {
        list = sanitizeList(list);
        Impact error = new Impact();
        error.setName(baseEntity.FAILURELOADING);
        list.add(error);
        return list;
    }
    public ArrayList<AmazonItemListing> getBookErrorEntity(ArrayList<AmazonItemListing> list) {
        list = sanitizeList(list);
        AmazonItemListing error = new AmazonItemListing();
        error.setTitle(baseEntity.FAILURELOADING);
        list.add(error);
        return list;
    }

    public ArrayList<News> getLatestNews(){
        ArrayList<News> newslist = new ArrayList<News>();
        try {
            Type collectionType = new TypeToken<ArrayList<News>>(){}.getType();
            newslist = gson.fromJson(httputil.get(URI_NEWS), collectionType);
            for(int i = 0; i < newslist.size(); i++) {
                try {
                    newslist.get(i).updateImageURLDrawable();
                } catch(NullPointerException e) {
                    newslist.get(i).setDefaultIcon();
                }
            }
        } catch (JsonSyntaxException e) {
            newslist = getNewsErrorEntity(newslist);
            Log.e("AsteroidTrackerService", "Error on getList" +e);
        } catch (Exception e) {
            newslist = getNewsErrorEntity(newslist);
            Log.e("AsteroidTrackerService", "Error on getList" +e);
        }
        return newslist;
    }
    
    public ArrayList<Impact> getImpactData(){
        ArrayList<Impact> impactList = new ArrayList<Impact>();
        try {
            Type collectionType = new TypeToken<ArrayList<Impact>>(){}.getType();
            impactList = gson.fromJson(httputil.get(URI_IMPACT), collectionType);
        } catch (JsonSyntaxException e) {
            impactList = getImpactErrorEntity(impactList);
            Log.e("AsteroidTrackerService", "Error on getList" +e);
        } catch (Exception e) {
            impactList = getImpactErrorEntity(impactList);
            Log.e("AsteroidTrackerService", "Error on getList" +e);
        }
        return impactList;
    }

    public ArrayList<AmazonItemListing> getAmazonBookData() {
        ArrayList<AmazonItemListing> amazonList = new ArrayList<AmazonItemListing>();
        try {
            Type collectionType = new TypeToken<ArrayList<AmazonItemListing>>(){}.getType();
            amazonList = gson.fromJson(httputil.get(URI_BOOKS), collectionType);
            for(int i = 0; i < amazonList.size(); i++) {
                amazonList.get(i).updateImageURLDrawable();
            }
        } catch (JsonSyntaxException e) {
            amazonList = getBookErrorEntity(amazonList);
            Log.e("AsteroidTrackerService", "Error on getList" +e);
        } catch (Exception e) {
            amazonList = getBookErrorEntity(amazonList);
            Log.e("AsteroidTrackerService", "Error on getList" +e);
        }
        return amazonList;
    }


}