package com.vitruviussoftware.bunifish.asteroidtracker;

import java.util.List;
import com.vitruviussoftware.bunifish.asteroidtracker.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressWarnings("rawtypes")
public class aboutAdapter extends ArrayAdapter {
	private static LayoutInflater inflater = null;
	private int resourceId;
	List dataObject;
	private Integer[] imgid = {R.drawable.androidmarker, R.drawable.blue_pin, R.drawable.asteroid};
	
	@SuppressWarnings("unchecked")
	public aboutAdapter(Context context, int textViewResourceId, List objects) {
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
	}

	public aboutAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		resourceId = textViewResourceId;
	}
	
	public static class ViewHolder {
		public TextView aboutTitle;
		public TextView aboutDescription;
		public TextView aboutDevelopedBy;
		public TextView aboutNEO;
		public TextView aboutAllData;
		public ImageView aboutPIC;
		public TextView about_Link_ImpactRisk;
		public TextView about_Link_ImpactRisk_faq;
		public TextView about_Link_CloseApproach;
		public TextView about_Link_TorinoScale;
		public TextView about_Link_JPLNews;
		public ImageView Icon;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View vi = convertView;
		ViewHolder holder;
		if (convertView == null) {
			vi = inflater.inflate(R.layout.about_main, null);		
			holder = new ViewHolder();
			holder.aboutDescription    = 	(TextView) vi.findViewById(R.id.asteroiddescription);
			holder.aboutNEO 	 	   =  	(TextView) vi.findViewById(R.id.about_whatisneo);
			holder.aboutDevelopedBy    =	(TextView) vi.findViewById(R.id.about_developedBY);
			holder.aboutAllData 	   = 	(TextView) vi.findViewById(R.id.about_allDatafromNASA);
			holder.Icon 			   =	(ImageView) vi.findViewById(R.id.picview1);
			holder.about_Link_ImpactRisk = 	(TextView) vi.findViewById(R.id.about_Link_NASANEO_ImpactRisk);
			holder.about_Link_JPLNews = 	(TextView) vi.findViewById(R.id.about_Link_JPLNews);
			holder.about_Link_ImpactRisk_faq = (TextView) vi.findViewById(R.id.about_Link_NASANEO_ImpactRisk_faq);
			holder.about_Link_CloseApproach = (TextView) vi.findViewById(R.id.about_Link_NASANEO_CloseApproaches);
			holder.about_Link_TorinoScale =	(TextView) vi.findViewById(R.id.about_Link_NASANEO_TorinoScale);
			vi.setTag(holder);
		} else {
			holder = (ViewHolder) vi.getTag();
		}
			Linkify.addLinks(holder.aboutAllData , Linkify.ALL);
			Linkify.addLinks(holder.about_Link_ImpactRisk , Linkify.ALL);
			Linkify.addLinks(holder.about_Link_ImpactRisk_faq , Linkify.ALL);
			Linkify.addLinks(holder.about_Link_CloseApproach , Linkify.ALL);
			Linkify.addLinks(holder.about_Link_TorinoScale , Linkify.ALL);
			Linkify.addLinks(holder.about_Link_JPLNews , Linkify.ALL);
			return vi;
	}

}
