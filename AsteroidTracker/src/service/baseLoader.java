package service;

import java.util.List;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class baseLoader extends AsyncTaskLoader<List>{

    public baseLoader(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    public List loadInBackground() {
        return null;
    }

}
