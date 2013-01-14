/*
 * @(#)BooksAdapter
 *
 * Copyright 2013 by Constant Contact Inc.,
 * Waltham, MA 02451, USA
 * Phone: (781) 472-8100
 * Fax: (781) 472-8101
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Constant Contact, Inc. created for Constant Contact, Inc.
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Constant Contact, Inc.
 * 
 * History
 *
 * Date         Author      Comments
 * ====         ======      ========
 *
 * 
 **/
package adapters;

import java.util.List;

import com.vitruviussoftware.bunifish.asteroidtracker.R;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import domains.AmazonItemListing;
import domains.baseEntity;

public class BooksAdapter extends ArrayAdapter<AmazonItemListing> {

    private LayoutInflater inflater = null;

    public BooksAdapter(Context context, int textViewResourceId, List Objects) {
        super(context, textViewResourceId, Objects);
    }

    public static class ViewHolder {
        public TextView title;
        public TextView title_error;
        public TextView author;
        public TextView productGroup;
        public TextView imageUri;
        public TextView detailPageUri;
        public TextView description;
        public ImageView listingIcon;
        public ImageView listingIconAudible;
        public ImageView amazonImage;
        public TextView checkOutText;
    }

    @TargetApi(11)
    public void setData(List<AmazonItemListing> data) {
        clear();
        if (data != null) {
            //If the platform supports it, use addAll, otherwise add in loop
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                addAll(data);
            }else{
                for(AmazonItemListing item: data){
                    add(item);
                }
            }
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        AmazonItemListing entityObject = (AmazonItemListing) getItem(position);
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vi = convertView;
        ViewHolder holder;
        if (convertView == null) {
            vi = inflater.inflate(R.layout.view_books_fragment, null);
            holder = new ViewHolder();
            holder.title              =    (TextView) vi.findViewById(R.id.book_title);
            holder.title_error        =    (TextView) vi.findViewById(R.id.books_error);
            holder.author             =    (TextView) vi.findViewById(R.id.book_author);
            holder.listingIcon        =    (ImageView) vi.findViewById(R.id.book_Image);
            holder.listingIconAudible =    (ImageView) vi.findViewById(R.id.book_Image_audible);
            holder.description        =    (TextView) vi.findViewById(R.id.book_description);
            holder.productGroup       =    (TextView) vi.findViewById(R.id.book_productType);
            holder.checkOutText       =    (TextView) vi.findViewById(R.id.checkouttext);
            holder.detailPageUri      =    (TextView) vi.findViewById(R.id.book_detailsPageUri);
            holder.amazonImage        =    (ImageView) vi.findViewById(R.id.amazon);
            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();
        }
        if (entityObject.getTitle().equals(baseEntity.FAILURELOADING)) {
            holder.title_error.setText("Unable to retrieve book listings");
            holder.title.setText("");
            holder.productGroup.setText("");
            holder.checkOutText.setText("");
            holder.description.setText("");
            holder.author.setText("");
            holder.amazonImage.setVisibility(View.INVISIBLE);
            
        } else {
            if (entityObject.getProductGroup().equals("Audible")) {
                holder.listingIcon.setVisibility(View.INVISIBLE);
                holder.listingIconAudible.setVisibility(View.VISIBLE);
                holder.listingIconAudible.setImageDrawable(getContext().getResources().getDrawable(R.drawable.headphones_light_hdpi));
            } else {
                holder.listingIcon.setVisibility(View.VISIBLE);
                holder.listingIconAudible.setVisibility(View.INVISIBLE);
                holder.listingIcon.setImageDrawable(entityObject.getImage());
            }
            holder.title.setText(entityObject.getTitle());
            holder.description.setText(Html.fromHtml(entityObject.getDescription()));
            holder.author.setText(entityObject.getAuthor());
            holder.productGroup.setText("Media Type: " + entityObject.getProductGroup());
            holder.checkOutText.setText("Check this title out at");
        }
        return vi;
    }

}
