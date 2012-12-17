package adapters;

import java.util.List;


import com.vitruviussoftware.bunifish.asteroidtracker.R;

import domains.Impact;
import domains.NearEarthObject;
import domains.News;
import android.R.color;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
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

public class NewsAdapter extends ArrayAdapter<News>{

    private static LayoutInflater inflater = null;
    private int resourceId;
    List dataObject;
    
    @SuppressWarnings("unchecked")
    public NewsAdapter(Context context, int textViewResourceId, List objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    public static class ViewHolder {
        public TextView title;
        public TextView artcileUrl;
        public TextView description;
        public TextView pubDate;
        public ImageView imgURL;
        public TextView title_error;
//        public ImageView Icon;
    }

    @TargetApi(11)
    public void setData(List<News> data) {
        clear();
        if (data != null) {
            //If the platform supports it, use addAll, otherwise add in loop
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                addAll(data);
            }else{
                for(News item: data){
                    add(item);
                }
            }
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        News entityObject = (News) getItem(position);
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vi = convertView;
        ViewHolder holder;
        if (convertView == null) {
            vi = inflater.inflate(R.layout.view_news_fragment, null);        
            holder = new ViewHolder();
            holder.title        =  (TextView) vi.findViewById(R.id.jpl_news_title);
            holder.title_error  =  (TextView) vi.findViewById(R.id.news_error);
            holder.artcileUrl   =  (TextView) vi.findViewById(R.id.jpl_news_title);
            holder.description  =  (TextView) vi.findViewById(R.id.jpl_news_Description);
            holder.imgURL       =  (ImageView) vi.findViewById(R.id.jpl_articleImage);
            holder.pubDate      =  (TextView) vi.findViewById(R.id.jpl_news_Date);
            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();
        }
        if(entityObject.title.equals("Unable to retrieve Asteroid News")){
            holder.title_error.setText(entityObject.getName());
        } else {
            holder.title.setText(entityObject.title);
            holder.pubDate.setText(entityObject.pubDate);
//            holder.artcileUrl.setText(entityObject.artcileUrl);
            holder.description.setText(entityObject.description);
            holder.imgURL.setImageDrawable(entityObject.getImageURL());
        }
    return vi;
    }

}
