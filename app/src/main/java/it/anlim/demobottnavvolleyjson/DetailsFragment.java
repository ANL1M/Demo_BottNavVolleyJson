package it.anlim.demobottnavvolleyjson;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

public class DetailsFragment extends Fragment {
    WebView wvMediaPreview;
    TextView txtFileName, txtEmptyLink;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        txtFileName = view.findViewById(R.id.txtFileName);
        txtEmptyLink = view.findViewById(R.id.txtEmptyLink);
        wvMediaPreview = view.findViewById(R.id.wvMediaPreview);

        // Getting Media file parameters
        String MediaName = SharedPreferencesHelper.getSession(getActivity(), "MediaName");
        String MediaURL = SharedPreferencesHelper.getSession(getActivity(), "MediaURL");

        if (MediaName.equals("") || MediaURL.equals("")) {
            txtEmptyLink.setVisibility(View.VISIBLE);
            wvMediaPreview.setVisibility(View.GONE);
            return view;
        }

        // Set Media file Name
        txtFileName.setText(MediaName);

        // Set content to Web View
        wvMediaPreview.getSettings().setJavaScriptEnabled(true);
        wvMediaPreview.getSettings().setLoadWithOverviewMode(true);
        wvMediaPreview.getSettings().setUseWideViewPort(true);
        wvMediaPreview.loadUrl("http://docs.google.com/gview?embedded=true&url=" + MediaURL);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);}
}