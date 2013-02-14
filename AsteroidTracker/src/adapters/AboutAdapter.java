package adapters;

import java.util.List;
import com.vitruviussoftware.bunifish.asteroidtracker.R;
import com.vitruviussoftware.bunifish.asteroidtracker.R.drawable;
import com.vitruviussoftware.bunifish.asteroidtracker.R.id;
import com.vitruviussoftware.bunifish.asteroidtracker.R.layout;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressWarnings("rawtypes")
public class AboutAdapter extends ArrayAdapter {
    private static LayoutInflater inflater = null;
    private int resourceId;
    List dataObject;
    private Integer[] imgid = {R.drawable.asteroid};
    private Resources res;

    @SuppressWarnings("unchecked")
    public AboutAdapter(Context context, int textViewResourceId, List objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
        res = context.getResources();
    }

    public AboutAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        resourceId = textViewResourceId;
        res = context.getResources();

    }
    
    public static class ViewHolder {
        public TextView aboutTitle;
        public TextView aboutPage;
        public TextView aboutDevelopedBy;
        public TextView aboutNEO;
        public ImageView aboutPIC;
        public TextView aboutLinkSpaceTracks;
        public TextView about_Link_ImpactRisk;
        public TextView aboutLinkTitle;
        public TextView about_Link_ImpactRisk_faq;
        public TextView about_Link_CloseApproach;
        public TextView about_Link_TorinoScale;
        public TextView about_Link_JPLNews;
        public ImageView Icon;
        public ImageView aboutImage;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vi = convertView;
        ViewHolder holder;
        if (convertView == null) {
            vi = inflater.inflate(R.layout.about_main, null);
            holder = new ViewHolder();
            holder.aboutImage                   =    (ImageView) vi.findViewById(R.id.aboutImage);
            holder.aboutPage                    =    (TextView) vi.findViewById(R.id.about_page);
            holder.aboutDevelopedBy             =    (TextView) vi.findViewById(R.id.about_developedBY);
            holder.aboutLinkSpaceTracks         =    (TextView) vi.findViewById(R.id.about_space_track_link);
            holder.aboutLinkTitle               =    (TextView) vi.findViewById(R.id.aboutHelpLinks);
            holder.Icon                         =    (ImageView) vi.findViewById(R.id.picview1);
            holder.about_Link_ImpactRisk        =    (TextView) vi.findViewById(R.id.about_link_impact_risk);
            holder.about_Link_JPLNews           =    (TextView) vi.findViewById(R.id.about_JPLNews);
            holder.about_Link_ImpactRisk_faq    =    (TextView) vi.findViewById(R.id.about_impactRiskFaq);
            holder.about_Link_CloseApproach     =    (TextView) vi.findViewById(R.id.about_link_closeApproaches);
            holder.about_Link_TorinoScale       =    (TextView) vi.findViewById(R.id.about_link_torino_scale);
            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();
        }
        holder.aboutPage.setText(Html.fromHtml(res.getString(R.string.aboutPage)));
        holder.aboutDevelopedBy.setText(Html.fromHtml(res.getString(R.string.developedby)));
        holder.aboutLinkTitle.setText(Html.fromHtml(res.getString(R.string.helpful_links)));

//        holder.aboutLinkSpaceTracks.setText(Html.fromHtml(res.getString(R.string.about_space_track_link2)));

        Linkify.addLinks(holder.aboutDevelopedBy , Linkify.ALL);
        Linkify.addLinks(holder.aboutLinkSpaceTracks , Linkify.ALL);
        Linkify.addLinks(holder.about_Link_ImpactRisk , Linkify.ALL);
        Linkify.addLinks(holder.about_Link_ImpactRisk_faq , Linkify.ALL);
        Linkify.addLinks(holder.about_Link_CloseApproach , Linkify.ALL);
        Linkify.addLinks(holder.about_Link_TorinoScale , Linkify.ALL);
        Linkify.addLinks(holder.about_Link_JPLNews , Linkify.ALL);
        Linkify.addLinks(holder.aboutPage , Linkify.ALL);
        return vi;
    }

}
