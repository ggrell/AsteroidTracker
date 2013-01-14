package domains;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import utils.HttpUtil;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * This is a basic pojo for the JPL Asteroid watch RSS feed objects.
 * http://www.jpl.nasa.gov/asteroidwatch/
 * @author spaceMonkey
 *
 */
public class News extends baseEntity {

	//XPath for Rss Feed (putting these in pojo class for Android optimization
	public static String XPath_News_title = "//item/title/text()";
	public static String XPath_News_artcileUrl = "//item/link/text()";
	public static String XPath_News_description = "//item/description/text()";
	public static String XPath_News_pubDate = "//item/pubDate/text()";
	public String title = "";
	public String artcileUrl = "";
	public String description = "";
	public String source = "";
	public String pubDate;	
	public Drawable imageURL;
	public String imgURL;
	

	public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgURL() {
		return imgURL;
	}

	public void setImgURL(String ImgURL) {
		Log.i("news", "news ImageOperations"+ImgURL);
		this.imageURL = activities.AsteroidTrackerActivity.newsDrawable; //ImageOperations(ImgURL);
		this.imgURL = ImgURL;
	}

	public Drawable getImageURL() {
		return this.imageURL;
	}
	
	public void updateImageURLDrawable() {
		this.imageURL = HttpUtil.getImageData(this.imgURL);
	}
	
	//	private String imgURL;
	public void setPubDate(String dateString){
		DateFormat formatter = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US);
		formatter = new SimpleDateFormat("yyyy-MMM-dd");
		Date date = new Date(dateString);
		this.pubDate = formatter.format(date);
	}
	
	public String getPubDate(){
		return this.pubDate;
	}

	public void setDefaultIcon(){
        this.imageURL = activities.AsteroidTrackerActivity.newsDrawable;
        
	}
}
