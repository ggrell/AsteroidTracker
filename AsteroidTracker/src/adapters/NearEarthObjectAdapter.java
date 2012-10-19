package adapters;

import java.util.Collection;
import java.util.List;


import com.vitruviussoftware.bunifish.asteroidtracker.R;

import domains.NearEarthObject;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressWarnings("rawtypes")
public class NearEarthObjectAdapter extends ArrayAdapter<NearEarthObject> {
    private LayoutInflater inflater = null;
    private int resourceId;
    List dataObject;
    private Integer[] imgid = {R.drawable.asteroid};
    
    @SuppressWarnings("unchecked")
    public NearEarthObjectAdapter(Context context, int textViewResourceId, List objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @SuppressWarnings("unchecked")
    public NearEarthObjectAdapter( Context context, int textViewResourceId ) {
        super(context, textViewResourceId);
        resourceId = textViewResourceId;
    }

    
    public static class ViewHolder {
        public TextView title;
        public TextView title_error;
        public TextView missDistance;
        public TextView relativeVelocity;
        public TextView estimatedDiameter;
        public TextView date;
        public ImageView Icon;
        public TextView AlertMessage;
    }
    
    @TargetApi(11)
    public void setData(List<NearEarthObject> data) {
        clear();
        if (data != null) {
            //If the platform supports it, use addAll, otherwise add in loop
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                addAll(data);
            }else{
                for(NearEarthObject item: data){
                    add(item);
                }
            }
        }
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        NearEarthObject entityObject = (NearEarthObject) getItem(position);
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vi = convertView;
        ViewHolder holder;
        if (convertView == null) {
            vi = inflater.inflate(R.layout.nasa_neolistview, null);        
            holder = new ViewHolder();
            holder.title            =     (TextView) vi.findViewById(R.id.asteroidName);
            holder.title_error      =     (TextView) vi.findViewById(R.id.nasaNeo_error);
            holder.missDistance     =     (TextView) vi.findViewById(R.id.missDistance);
            holder.date             =     (TextView) vi.findViewById(R.id.nasaNeo_date);
            holder.relativeVelocity =     (TextView) vi.findViewById(R.id.nasaNeo_relV);
            holder.estimatedDiameter=     (TextView) vi.findViewById(R.id.nasaNeo_estimatedDiameter);
            holder.AlertMessage     =    (TextView) vi.findViewById(R.id.nasaNeo_AlertMessage);
            holder.Icon = (ImageView) vi.findViewById(R.id.picview1);
            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();
        }
        
        if (entityObject.getName().equals("Unable to retrieve Asteroid Data")){
            holder.title_error.setText("Unable to retrieve Asteroid Data");
        }else{
            holder.title.setText(NearEarthObjectAdapter.this.getContext().getString(R.string.neoName)+" "+entityObject.getName());
            holder.Icon.setImageDrawable(entityObject.IconD);
            holder.missDistance.setText(NearEarthObjectAdapter.this.getContext().getString(R.string.neoMissDistance)+" "+entityObject.getMissDistance_AU() + " (km)");
            holder.relativeVelocity.setText(NearEarthObjectAdapter.this.getContext().getString(R.string.neoRelVelocity)+" "+ entityObject.getRelativeVelocity() + " (km/s)");
            holder.estimatedDiameter.setText(NearEarthObjectAdapter.this.getContext().getString(R.string.neoEstDiameter)+" "+ entityObject.getEstimatedDiameter() + " (m)");
            holder.date.setText(NearEarthObjectAdapter.this.getContext().getString(R.string.neoClosAppDate)+" "+entityObject.getDateStr());
            holder.AlertMessage.setText("");
            entityObject.setAlertMSG("");
            if (Double.parseDouble(entityObject.getMissDistance_AU_Kilometers().replace(",","")) < 400000){
                holder.AlertMessage.setTextColor(Color.YELLOW);
                holder.AlertMessage.setText("It's Passing closer than the Moon!");
                entityObject.setAlertMSG(NearEarthObjectAdapter.this.getContext().getString(R.string.neoAlertClosePass));
            }
        }
        return vi;
    }

}
