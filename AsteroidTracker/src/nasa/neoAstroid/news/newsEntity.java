package nasa.neoAstroid.news;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EntityUtils;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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
	public Drawable imageDrawable;
	public byte[] imageByteArray;
	public String imageURL;
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
	
	public byte[] getDrawableImageByteArray(){
		return this.imageByteArray;
	}
	
	public void setDrawable(String imageURL){
//		try {
			this.imageURL = imageURL;
			byte[] imgData = httpParse.getHTTP.getImageData(imageURL);
//			URL url = new URL(imageURL);		
//			Object url_content = url.getContent();
//			InputStream is = (InputStream) url_content;
//			this.imageDrawable =  Drawable.createFromStream(is, "src");
			ByteArrayInputStream imageStream = new ByteArrayInputStream(imgData);
			Bitmap theImage = BitmapFactory.decodeStream(imageStream);
			Drawable image =new BitmapDrawable(theImage);
			this.imageByteArray = imgData;
			this.imageDrawable = image;
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	
	public Drawable setDrawableWithByteArray(byte[] imageContents){
//		this.imageDrawable
		return new BitmapDrawable(BitmapFactory.decodeByteArray(imageContents, 0, imageContents.length));
	}

	public void setImageByteArray(InputStream input){
		BufferedInputStream bis = new BufferedInputStream(input);
		ByteArrayBuffer baf = new ByteArrayBuffer(1000);
		int current = 0;
		  try {
			  Log.i("DB", "ADDING BYTE DATA START");
			while ((current = bis.read()) != -1) {
				  Log.i("DB", "ADDING BYTE DATA" +current);
			      baf.append((byte) current);
			     }
            this.imageByteArray = baf.toByteArray();
			} catch (IOException e) {
				  Log.i("DB", "ADDING BYTE DATA ERROR");

				e.printStackTrace();
			}
}
}
