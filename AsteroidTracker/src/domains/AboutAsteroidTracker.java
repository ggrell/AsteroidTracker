package domains;

import android.graphics.drawable.Drawable;

public class AboutAsteroidTracker {

	private static final String About_Title="";
	private static final String About_WhatisNEO = "What Is A Near-Earth Object (NEO)? \nNear-Earth Objects (NEOs) are comets and asteroids that have been nudged by the gravitational attraction of nearby planets into orbits that allow them to enter the Earth\'s neighborhood. Composed mostly of water ice with embedded dust particles...";
	private static final String About_Description = "\nAndoidTracker is a science aggregation app that displays information about Near Earth Objects. ";
	private static final String About_AllDataFrom = "All the data is from the NASA Near Earth Object program (http://neo.jpl.nasa.gov).";
	private static final String About_DevelopedBy = "buniFish";
	private static Drawable About_Image;
	
	/**
	 * @return the aboutTitle
	 */
	public static String getAboutTitle() {
		return About_Title;
	}

	/**
	 * @return the aboutWhatisneo
	 */
	public static String getAboutWhatisneo() {
		return About_WhatisNEO;
	}

	/**
	 * @return the aboutDescription
	 */
	public static String getAboutDescription() {
		return About_Description;
	}

	/**
	 * @return the aboutDevelopedby
	 */
	public static String getAboutDevelopedby() {
		return About_DevelopedBy;
	}

	
	/**
	 * @return the aboutAlldatafrom
	 */
	public static String getAboutAlldatafrom() {
		return About_AllDataFrom;
	}

	public static Drawable getImage(){
		return About_Image;
	}
}