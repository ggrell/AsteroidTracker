package nasa.neoAstroid.neo;

import java.util.List;


import com.vitruviussoftware.bunifish.asteroidtracker.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressWarnings("rawtypes")
public class nasa_neoArrayAdapter extends ArrayAdapter {
	private static LayoutInflater inflater = null;
	private int resourceId;
	List dataObject;
	private Integer[] imgid = {R.drawable.androidmarker, R.drawable.blue_pin, R.drawable.asteroid};
	
	@SuppressWarnings("unchecked")
	public nasa_neoArrayAdapter(Context context, int textViewResourceId, List objects) {
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
	}

	static class ViewHolder {
		public TextView title;
		public TextView title_error;
		public TextView description;
		public TextView relativeVelocity;
		public TextView estimatedDiameter;
		public TextView AlertMessage;
		public TextView date;
		public ImageView Icon;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		nasa_neo entityObject = (nasa_neo) getItem(position);
		ViewHolder holder;
		if (convertView == null){
			inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.nasa_neolistview, parent, false);
			holder = new ViewHolder();
			holder.title 				= 	(TextView) convertView.findViewById(R.id.asteroidName);
			holder.title_error 			= 	(TextView) convertView.findViewById(R.id.nasaNeo_error);
			holder.description 			= 	(TextView) convertView.findViewById(R.id.middletext1);
			holder.date 				= 	(TextView) convertView.findViewById(R.id.nasaNeo_date);
			holder.relativeVelocity		=	(TextView) convertView.findViewById(R.id.nasaNeo_relV);
			holder.estimatedDiameter	= 	(TextView) convertView.findViewById(R.id.nasaNeo_estimatedDiameter);
			holder.AlertMessage			=	(TextView) convertView.findViewById(R.id.nasaNeo_AlertMessage);
			holder.Icon = (ImageView) convertView.findViewById(R.id.picview1);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();					
		}
//		View rowView = inflater.inflate(R.layout.nasa_neolistview, parent, false);
//		View vi = convertView;
//			vi = inflater.inflate(R.layout.nasa_neolistview, null);		
//			vi.setTag(holder);
//		} else {
//			holder = (ViewHolder) vi.getTag();
//		}
		if (entityObject.getName().equals("Unable to retrieve Asteroid feed")){
			holder.title_error.setText("Unable to retrieve NASA NEO feed");
			holder.Icon.setImageDrawable(entityObject.getIcon());
		}else{
			holder.title.setText("Name: "+entityObject.getName());
			holder.Icon.setImageDrawable(entityObject.getIcon());
			holder.description.setText("Miss-Distance: "+entityObject.getMissDistance_AU_Kilometers() + " (km)");
			holder.relativeVelocity.setText("Relative Velocity: "+ entityObject.getRelativeVelocity() + " (km/s)");
			holder.estimatedDiameter.setText("Est Diameter: "+ entityObject.getEstimatedDiameter() + " (m)");
			holder.date.setText("Closest Approach Date: "+entityObject.getDateStr());	
			holder.AlertMessage.setText("");	
			holder.title.setTextColor(Color.WHITE);
			if (Double.parseDouble(entityObject.getMissDistance_AU_Kilometers().replace(",","")) < 400000){
//			String missDistance = holder.description.getText().toString().replace(",","").trim();
//			if(missDistance.contains("Miss")){
//				int bidx = missDistance.indexOf(":")+1;
//				int eidx = missDistance.indexOf("(km)");
//				String missDist_parsed = missDistance.substring(bidx, eidx).trim();
//				Log.i("yellow", "parsing yellow: "+missDist_parsed);
//				missDistance = missDist_parsed;
//			}
//			if (Double.parseDouble(missDistance) < 400000){
				holder.AlertMessage.setTextColor(Color.YELLOW);
				holder.AlertMessage.setText("It's Passing closer than the Moon!");			
			}
		}
		return convertView;
	}

}
