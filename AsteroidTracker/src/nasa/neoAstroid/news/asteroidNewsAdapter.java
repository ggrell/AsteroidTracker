package nasa.neoAstroid.news;

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

public class asteroidNewsAdapter extends ArrayAdapter{

	private static LayoutInflater inflater = null;
	private int resourceId;
	List dataObject;
	
	@SuppressWarnings("unchecked")
	public asteroidNewsAdapter(Context context, int textViewResourceId, List objects) {
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
		Log.i("news", "news adapter");
	}

	public static class ViewHolder {
		public TextView title;
		public TextView artcileUrl;
		public TextView description;
		public TextView pubDate;
		public ImageView imgURL;
//		public ImageView Icon;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		newsEntity entityObject = (newsEntity) getItem(position);
		inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View vi = convertView;
		ViewHolder holder;
		if (convertView == null) {
			vi = inflater.inflate(R.layout.jpl_asteroid_news, null);		
			holder = new ViewHolder();
			holder.title 		=	(TextView) vi.findViewById(R.id.jpl_news_title);
			holder.artcileUrl 	=	(TextView) vi.findViewById(R.id.jpl_news_title);
			holder.description 	= 	(TextView) vi.findViewById(R.id.jpl_news_Description);
			holder.imgURL 		=	(ImageView) vi.findViewById(R.id.jpl_articleImage);
			holder.pubDate		=	(TextView) vi.findViewById(R.id.jpl_news_Date);
			vi.setTag(holder);
		} else {
			holder = (ViewHolder) vi.getTag();
		}
// 		Log.i("news", "setting title"+entityObject.title);
			holder.title.setText(entityObject.title);
			holder.pubDate.setText(entityObject.pubDate);
//			holder.artcileUrl.setText(entityObject.artcileUrl);
			holder.description.setText(entityObject.description);
			holder.imgURL.setImageDrawable(entityObject.imgURL);
		return vi;
	}

}
