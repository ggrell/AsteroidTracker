/*
 * @(#)BookFragment
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
package fragments;

import java.util.List;

import activities.fragment.AsteroidTabFragments;
import adapters.BooksAdapter;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.view.MenuItem;
import com.vitruviussoftware.bunifish.asteroidtracker.R;

import domains.AmazonItemListing;
import domains.baseEntity;

public class BookFragment extends AsteroidFragmentBase {

    public BooksAdapter adapterBooks;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingMessage = resources.getString(R.string.text_content_loading_books);
    }
    
    @Override
    public void onStart(){
        super.onStart();
        getLoaderManager().initLoader(4, null, this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setOnItemClickListener(listingClickListener);
    }


    @Override
    public Loader<List> onCreateLoader(int id, Bundle args) {
        super.onCreateLoader(id, args);
        AsyncTaskLoader<List> loader = new AsyncTaskLoader<List>(getActivity()) {
        @Override
        public List<AmazonItemListing> loadInBackground() {
            return downloadManager.retrieveScienceBooks(isNetworkAvailable);
            }
        };
    loader.forceLoad();
    return loader;
    }
    
    @Override
    public void onLoadFinished( Loader<List> arg0, List data ) 
    {
        super.onLoadFinished(arg0, data);
        if (adapterBooks != null) {
            if (adapterBooks.getItem(0).title.equals(baseEntity.FAILURELOADING)) {
                loadContent(data);
            } else {
                if(data.size() > 1){
                    loadContent(data);
                }
            }
        } else {
            loadContent(data);
        }
    }

    public void loadContent(List data){
        adapterBooks = new BooksAdapter(AsteroidTabFragments.cText, R.layout.view_books_fragment, data);
        setListAdapter(adapterBooks);
    }
    
    protected void restartLoading(MenuItem item) {
        reloadItem = item;
        setRefreshIcon(true, "Books");
        getLoaderManager().restartLoader(4, null, this);
    }

    public boolean onOptionsItemSelected(final MenuItem item) 
    {
      switch (item.getItemId()) {
      case R.id.reload:
          restartLoading(item);
          return super.onOptionsItemSelected(item);
    default:
        return false;
        }
    }
    
    public OnItemClickListener listingClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView parent, View view, int position, long id) { 
 
            Log.i("clickit", "click");

            if (!adapterBooks.getItem(0).title.equals(baseEntity.FAILURELOADING)) {
                Object object = getListAdapter().getItem(position);    
                AmazonItemListing asteroidEntity = (AmazonItemListing) object;
                Intent i = new Intent(Intent.ACTION_VIEW);
                try {
                    i.setData(Uri.parse(asteroidEntity.getDetailPageUri()));
                    startActivity(i);
                } catch (ActivityNotFoundException e){
                    Log.d("Books", "clicklistner", e);
                }
            }
        };
    };

}