package domains;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * This is a basic pojo for the JPL Asteroid watch RSS feed objects.
 * http://www.jpl.nasa.gov/asteroidwatch/
 * @author spaceMonkey
 *
 */
public class newsEntity {

	//XPath for Rss Feed (putting these in pojo class for Android optimization
	public static String XPath_News_title = "//item/title/text()";
	public static String XPath_News_artcileUrl = "//item/link/text()";
	public static String XPath_News_description = "//item/description/text()";
	public static String XPath_News_pubDate = "//item/pubDate/text()";
	
	public String title = "";
	public String artcileUrl = "";
	public String description = "";
	public String pubDate;
	public Drawable imgURL;
	//Note, not doing setters and getters here per Android optimization techniques specified by Android dev team. 
	//atleast i am trying it out here.

	public void setPubDate(String dateString){
//		Log.i("date", "dateString: " +dateString);
		DateFormat formatter = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US);
		formatter = new SimpleDateFormat("yyyy-MMM-dd");
		Date date = new Date(dateString);
//		formatter.format(date);
		this.pubDate = formatter.format(date);
	}
	
	public String getPubDate(){
		return this.pubDate;
	}

}
