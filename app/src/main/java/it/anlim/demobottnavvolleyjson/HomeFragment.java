package it.anlim.demobottnavvolleyjson;

import static it.anlim.demobottnavvolleyjson.ServerConnection.BASIC_URL;
import static it.anlim.demobottnavvolleyjson.ServerConnection.DATA_URL;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment {
    Button btnStart;
    ProgressBar pbGetData;
    RecyclerView rclMediaList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        btnStart = view.findViewById(R.id.btnStart);
        pbGetData = view.findViewById(R.id.pbGetData);
        rclMediaList = view.findViewById(R.id.rclMediaList);

        btnStart.setOnClickListener(button -> {
            pbGetData.setVisibility(View.VISIBLE);

            ServerConnection serverConnection = new ServerConnection();
            serverConnection.sendRequest(getActivity(), BASIC_URL, DATA_URL, result -> {
                pbGetData.setVisibility(View.GONE);

                if(result == null){
                    Toast.makeText(getActivity(), "Error getting data", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    JSONArray jsonArray = new JSONObject(result).getJSONArray("content");
                    DataAdapter dataAdapter = new DataAdapter(jsonArray);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    rclMediaList.setLayoutManager(layoutManager);
                    rclMediaList.setItemAnimator(new DefaultItemAnimator());
                    rclMediaList.setAdapter(dataAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });

        });
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}