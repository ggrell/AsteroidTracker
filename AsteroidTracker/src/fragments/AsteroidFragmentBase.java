package fragments;

import com.vitruviussoftware.bunifish.asteroidtracker.R;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

public class AsteroidFragmentBase extends ListFragment {

    Resources resources;
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
            if (container == null) {
                return null;
            }
            return (LinearLayout)inflater.inflate(R.layout.lists, container, false);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        resources= getResources();
        }
    
    public void setAdap(ListAdapter adapter){
        setListAdapter(adapter);
    }

}

