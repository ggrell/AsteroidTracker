package domains;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.graphics.drawable.Drawable;
import android.content.res.Resources;
import android.util.Log;
import android.widget.ImageView;
import android.content.res.Resources;

public class NearEarthObject extends baseEntity {

    private Date closeApproachDate;
    private String closeApproachDate_Str;
    private String missDistance_AU="";
    private String missDistance_LD="";
    private String EstimatedDiameter="";
    private String Hmagnitude="";
    private String relativeVelocity="";
    private URL url;
    public Drawable IconD = activities.fragment.AsteroidTabFragments.drawable;
//    private int averageAU = 149598000;
    private int averageAU = 149597870;
    private String AlertMessage="";
    
    public enum TYPE {
        PAST, FUTURE, TODAY
    }
        
    public Drawable getIcon(){
        return this.IconD;
    }
    
    public void setIcon(Drawable Icon){
        this.IconD = Icon;
    }
    
    public void setURL(String AstroidURL) throws MalformedURLException{
        this.url = new URL(AstroidURL);
    }
    
    public URL getURL(){
        return this.url;
    }

    public void setDate(String dateString){
            DateFormat formatter = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US);
            dateString = dateString.substring(1);
            formatter =   new SimpleDateFormat("yyyy-MMM-dd");
            Date d = new Date(dateString);
            this.closeApproachDate = d;
     }
    
    public void setDateStr(String dateString){
            this.closeApproachDate_Str = dateString;
         }

    public Date getDate(){
            return this.closeApproachDate;
        }

    public String getDateStr(){
        return this.closeApproachDate_Str;
    }
    
    public void SetMissDistance_AU(String missDist_AU){
        this.missDistance_AU = missDist_AU;
    }
    
    public String getMissDistance_AU(){
        float missDist = Float.parseFloat(this.missDistance_AU);
        float missInKilo = missDist * averageAU;
        DecimalFormat ourForm = (DecimalFormat) NumberFormat.getInstance(Locale.US);
//        Log.i("dec", "NEO "+ourForm.format(missInKilo).toString());
        return ourForm.format(missInKilo).toString();
        
    }
    
    public void setMissDistance_LD(String missDist_LD){
        this.missDistance_LD = missDist_LD;
    }
    
    public String getMissDistance_LD(){
        return this.missDistance_LD;
    }

    public void setEstimatedDiameter(String astroid_EstimatedDiameter){
        if(astroid_EstimatedDiameter.contains("k")){
            astroid_EstimatedDiameter = astroid_EstimatedDiameter.replace("k", " ");
        }
        astroid_EstimatedDiameter = astroid_EstimatedDiameter.replace("m", " ");
        this.EstimatedDiameter = astroid_EstimatedDiameter;
    }
    
    public String getEstimatedDiameter(){
        return this.EstimatedDiameter;
    }
    
    public void setHmagnitude(String hMag){
        this.Hmagnitude = hMag;
    }
    
    public String getHmagnitude(){
        return this.Hmagnitude;
    }
    
    public void setRelativeVelocity(String astroid_RelativeVelocity){
        this.relativeVelocity = astroid_RelativeVelocity;
    }
    
    public String getRelativeVelocity(){
        return this.relativeVelocity;
    }

    public String getMissDistance_AU_Kilometers(){
        float missDist = Float.parseFloat(this.missDistance_AU);
        float missInKilo = missDist * averageAU;
        DecimalFormat ourForm = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        return ourForm.format(missInKilo).toString();

    }

    public String getAlertMSG(){
        return this.AlertMessage;
    }
    
    public void setAlertMSG(String alertmessage){
        this.AlertMessage = alertmessage;
    }
}
