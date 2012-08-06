package domains;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class Impact {

	private String name = "";
	private String YearRange = "";
	private String potentialImpacts = "";
	private String impactProbabilites = "";
	private String VInfinity = "";
	private String H_AbsoluteMag = "";
	private String EstimagesDiameter = "";
	private String PalermoScaleAve = "";
	private String PalermoScaleMax = "";
	private String TorinoScale = "";
	public Drawable IconD = activities.AsteroidTrackerActivity.drawable;
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setYearRange(String yearRange) {
		YearRange = yearRange;
	}
	public String getYearRange() {
		return YearRange;
	}
	public void setImpactProbabilites(String impactProbabilites) {
		Log.d("floatIssue", impactProbabilites);
		float impactPercentage = Float.parseFloat(impactProbabilites);
		DecimalFormat ourForm = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		NumberFormat percent = NumberFormat.getPercentInstance();
		percent = new DecimalFormat("0.00000#%");	
		this.impactProbabilites  = percent.format(impactPercentage);
//		this.impactProbabilites = impactProbabilites;
	}
	
	public String getImpactProbabilites() {
//		Log.i("floatIssue", this.impactProbabilites);
//		float impactPercentage = Float.parseFloat(this.impactProbabilites);
//		DecimalFormat ourForm = (DecimalFormat) NumberFormat.getInstance(Locale.US);
//		NumberFormat percent = NumberFormat.getPercentInstance();
//		percent = new DecimalFormat("0.00000#%");	
//		String st = percent.format(impactPercentage);
		return this.impactProbabilites;
	}
	public void setVInfinity(String vInfinity) {
		VInfinity = vInfinity;
	}
	public String getVInfinity() {
		return VInfinity;
	}
	public void setH_AbsoluteMag(String h_AbsoluteMag) {
		H_AbsoluteMag = h_AbsoluteMag;
	}
	public String getH_AbsoluteMag() {
		return H_AbsoluteMag;
	}
	public void setEstimagesDiameter(String estimagesDiameter) {
		EstimagesDiameter = estimagesDiameter;
	}
	public String getEstimagesDiameter() {
		return EstimagesDiameter;
	}
	public void setPalermoScaleAve(String palermoScaleAve) {
		PalermoScaleAve = palermoScaleAve;
	}
	public String getPalermoScaleAve() {
		return PalermoScaleAve;
	}
	public void setPalermoScaleMax(String palermoScaleMax) {
		PalermoScaleMax = palermoScaleMax;
	}
	public String getPalermoScaleMax() {
		return PalermoScaleMax;
	}
	public void setTorinoScale(String torinoScale) {
		TorinoScale = torinoScale;
	}
	public String getTorinoScale() {
		return TorinoScale;
	}
	public Drawable getIcon(){
		return this.IconD;
	}
	public void setIcon(Drawable Icon){
		this.IconD = Icon;
	}
	public void setPotentialImpacts(String potentialImpacts) {
		this.potentialImpacts = potentialImpacts;
	}
	public String getPotentialImpacts() {
		return potentialImpacts;
	}
}
