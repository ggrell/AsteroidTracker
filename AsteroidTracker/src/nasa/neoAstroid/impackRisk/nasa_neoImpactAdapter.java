package nasa.neoAstroid.impackRisk;

import java.util.List;
import com.vitruviussoftware.bunifish.asteroidtracker.R;
import android.R.color;
import android.content.Context;
import android.content.res.ColorStateList;
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
public class nasa_neoImpactAdapter extends ArrayAdapter {
	private static LayoutInflater inflater = null;
	private int resourceId;
	List dataObject;
	private Integer[] imgid = {R.drawable.androidmarker, R.drawable.blue_pin, R.drawable.asteroid};
	
	@SuppressWarnings("unchecked")
	public nasa_neoImpactAdapter(Context context, int textViewResourceId, List objects) {
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
	}

	public static class ViewHolder {
		public TextView title;
		public TextView title_error;
		public TextView yearRange;
		public TextView potentialImpacts;
		public TextView impactProbabilities;
		public TextView Vinfinity;
		public TextView H_AbsoluteMagnitude;
		public TextView estimatedDiameter;
		public TextView PalermoScaleAve;
		public TextView PalermoScaleMax;
		public TextView TorinoScale;
		public TextView HazardScale;
		public ImageView Icon;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		nasa_neoImpactEntity entityObject = (nasa_neoImpactEntity) getItem(position);
		inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View vi = convertView;
		ViewHolder holder;
		if (convertView == null) {
			vi = inflater.inflate(R.layout.nasa_neo_impact_listview, null);		
			holder = new ViewHolder();
			holder.title 				= 	(TextView) vi.findViewById(R.id.Impact_Name);
			holder.title_error 			= 	(TextView) vi.findViewById(R.id.nasaNeoImpact_error);
			holder.yearRange 			= 	(TextView) vi.findViewById(R.id.Impact_yearRange);
			holder.potentialImpacts 	= 	(TextView) vi.findViewById(R.id.Impact_potentialImpacts);
			holder.impactProbabilities 	= 	(TextView) vi.findViewById(R.id.Impact_ImpactProbability);
			holder.Vinfinity 			= 	(TextView) vi.findViewById(R.id.Impact_VInfinity);
			holder.H_AbsoluteMagnitude 	= 	(TextView) vi.findViewById(R.id.Impact_H_AbsoluteMagnitude);
			holder.estimatedDiameter 	= 	(TextView) vi.findViewById(R.id.Impact_EstimatedDiameter);
			holder.PalermoScaleAve 		=	(TextView) vi.findViewById(R.id.Impact_PelermoScale_ave);
			holder.PalermoScaleMax 		=	(TextView) vi.findViewById(R.id.Impact_PelermoScale_max);
			holder.TorinoScale 			= 	(TextView) vi.findViewById(R.id.Impact_TorinoScale);
			holder.HazardScale 			= 	(TextView) vi.findViewById(R.id.Impact_Hazard);
			holder.Icon 				= 	(ImageView)vi.findViewById(R.id.Impact_Image);
			vi.setTag(holder);
		} else {
			holder = (ViewHolder) vi.getTag();
		}
		if (entityObject.getName().equals("Unable to retrieve Asteroid feed")){
			holder.title_error.setText("Unable to retrieve NASA NEO feed");
		}else{
			int torinoScale = Integer.parseInt(entityObject.getTorinoScale());
			holder = getHazardLevel(torinoScale, holder);
			if (entityObject.getName().equals("Unable to retrieve Asteroid feed")){
				holder.title.setText("Name: "+entityObject.getName());
			}else{
				holder.title.setText("Name: "+entityObject.getName());
				holder.yearRange.setText("Year-Range: "+entityObject.getYearRange());
				holder.Icon.setImageDrawable(entityObject.getIcon());
			}
		}
		return vi;
	}
	
	public ViewHolder getHazardLevel(int torinoScale, ViewHolder holder){
		//TODO Move string to values
		String hazardText = "Impact Hazard Level";
		if(torinoScale == 0){
			holder.HazardScale.setTextColor(Color.WHITE);
			holder.HazardScale.setText(hazardText+": No Hazard");
		}else if(torinoScale == 1){
			holder.HazardScale.setTextColor(Color.GREEN);
			holder.HazardScale.setText(hazardText+": Normal");
		}else if(torinoScale >= 2 || torinoScale <= 4){
			holder.HazardScale.setTextColor(Color.YELLOW);
			holder.HazardScale.setText(hazardText+": Merits Attention");
		}else if(torinoScale >= 5 || torinoScale <= 7){
			holder.HazardScale.setTextColor(Color.rgb(255, 165, 0));
			holder.HazardScale.setText(hazardText+": Threatening");
		}else if(torinoScale >= 8|| torinoScale <= 10){
			holder.HazardScale.setTextColor(Color.RED);
			holder.HazardScale.setText(hazardText+": Certain Collisions");
		}
		return holder;
	}

}
