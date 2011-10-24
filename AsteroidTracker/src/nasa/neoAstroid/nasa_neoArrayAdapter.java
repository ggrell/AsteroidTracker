package nasa.neoAstroid;

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

	public static class ViewHolder {
		public TextView title;
		public TextView title_error;
		public TextView description;
		public TextView relativeVelocity;
		public TextView estimatedDiameter;
		public TextView date;
		public ImageView Icon;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		nasa_neo entityObject = (nasa_neo) getItem(position);
		inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View vi = convertView;
		ViewHolder holder;
		if (convertView == null) {
			vi = inflater.inflate(R.layout.nasa_neolistview, null);		
			holder = new ViewHolder();
			holder.title 				= 	(TextView) vi.findViewById(R.id.asteroidName);
			holder.title_error 			= 	(TextView) vi.findViewById(R.id.nasaNeo_error);
			holder.description 			= 	(TextView) vi.findViewById(R.id.middletext1);
			holder.date 				= 	(TextView) vi.findViewById(R.id.nasaNeo_date);
			holder.relativeVelocity		=	(TextView) vi.findViewById(R.id.nasaNeo_relV);
			holder.estimatedDiameter	= 	(TextView) vi.findViewById(R.id.nasaNeo_estimatedDiameter);
			holder.Icon = (ImageView) vi.findViewById(R.id.picview1);
			vi.setTag(holder);
		} else {
			holder = (ViewHolder) vi.getTag();
		}
		
		if (entityObject.getName().equals("Unable to retrieve Asteroid feed")){
			holder.title_error.setText("Unable to retrieve NASA NEO feed");
		}else{
			holder.title.setText("Name: "+entityObject.getName());
			holder.Icon.setImageDrawable(entityObject.getIcon());
			holder.description.setText("Miss-Distance: "+entityObject.getMissDistance_AU() + " (km)");
			holder.relativeVelocity.setText("Relative Velocity: "+ entityObject.getRelativeVelocity() + " (km/s)");
			holder.estimatedDiameter.setText("Est Diameter: "+ entityObject.getEstimatedDiameter() + " (m)");
			holder.date.setText("Closest Approach Date: "+entityObject.getDateStr());	
		}
		return vi;
	}

}
