package fragments;

import com.vitruviussoftware.bunifish.asteroidtracker.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class NewsFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
            if (container == null) {
                return null;
            }
            return (RelativeLayout)inflater.inflate(R.layout.jpl_asteroid_news, container, false);
    }
}
