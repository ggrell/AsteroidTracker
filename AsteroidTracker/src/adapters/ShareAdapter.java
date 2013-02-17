package adapters;

import java.util.List;

import com.vitruviussoftware.bunifish.asteroidtracker.R;

import android.app.Activity;
import android.content.pm.ResolveInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ShareAdapter extends ArrayAdapter
{
    Activity context;
    List items;
    int layoutId;

    public ShareAdapter(Activity context, int layoutId, List items) {
        super(context, layoutId, items);
 
        this.context = context;
        this.items = items;
        this.layoutId = layoutId;
    }
    
    
    public View getView(int pos, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater=context.getLayoutInflater();
        View row = inflater.inflate(layoutId, null);
        
        TextView label = (TextView) row.findViewById(R.id.shareText);
        label.setText(((ResolveInfo)items.get(pos)).activityInfo.applicationInfo.loadLabel(context.getPackageManager()).toString());

        ImageView image = (ImageView) row.findViewById(R.id.shareLogo);
        image.setImageDrawable(((ResolveInfo)items.get(pos)).activityInfo.applicationInfo.loadIcon(context.getPackageManager()));
        
        return(row);
    }
}
