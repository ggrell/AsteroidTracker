package adapters;

import java.util.List;


import com.vitruviussoftware.bunifish.asteroidtracker.R;

import domains.nasa_neoImpactEntity;
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

public class impactRisk_DetailAdapter extends ArrayAdapter{

	private static LayoutInflater inflater = null;
	private int resourceId;
	List dataObject;
//	private Integer[] imgid = {R.drawable.androidmarker, R.drawable.blue_pin, R.drawable.asteroid};
	
	@SuppressWarnings("unchecked")
	public impactRisk_DetailAdapter(Context context, int textViewResourceId, List objects) {
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
	}

	public static class ViewHolder {
		public TextView title;
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
			vi = inflater.inflate(R.layout.impactrisk_detail_view, null);		
			holder = new ViewHolder();
			holder.title = 		 			(TextView) vi.findViewById(R.id.impactRisk_DetailView_NAME);
			holder.yearRange = 				(TextView) vi.findViewById(R.id.impactRisk_DetailView_YEARRANGE);
			holder.potentialImpacts = 		(TextView) vi.findViewById(R.id.impactRisk_DetailView_POTENTIALIMPACT);
			holder.impactProbabilities = 	(TextView) vi.findViewById(R.id.impactRisk_DetailView_IMPACTPROB);
			holder.Vinfinity = 		 		(TextView) vi.findViewById(R.id.impactRisk_DetailView_VINFINITY);
			holder.H_AbsoluteMagnitude = 	(TextView) vi.findViewById(R.id.impactRisk_DetailView_ABSOLUTEMAG);
			holder.estimatedDiameter = 		(TextView) vi.findViewById(R.id.impactRisk_DetailView_ESTIMATED_DIAMATER);
			holder.PalermoScaleMax =		(TextView) vi.findViewById(R.id.impactRisk_DetailView_PALERMOSCALE);
			holder.TorinoScale = 			(TextView) vi.findViewById(R.id.impactRisk_DetailView_TORINOSCALE);
			vi.setTag(holder);
		} else {
			holder = (ViewHolder) vi.getTag();
		}
			holder.title.setText(entityObject.getName());
			holder.yearRange.setText(entityObject.getYearRange());
			holder.potentialImpacts.setText(entityObject.getPotentialImpacts());
			holder.impactProbabilities.setText(entityObject.getImpactProbabilites());
			holder.Vinfinity.setText(entityObject.getVInfinity());
			holder.H_AbsoluteMagnitude.setText(entityObject.getH_AbsoluteMag());
			holder.estimatedDiameter.setText(entityObject.getEstimagesDiameter());
			holder.PalermoScaleMax.setText(entityObject.getPalermoScaleMax());
			holder.TorinoScale.setText(entityObject.getTorinoScale());
//		}
		return vi;
	}

}
