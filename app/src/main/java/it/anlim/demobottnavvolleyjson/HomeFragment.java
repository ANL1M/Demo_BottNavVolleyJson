package it.anlim.demobottnavvolleyjson;

import static it.anlim.demobottnavvolleyjson.ServerConnection.BASIC_URL;
import static it.anlim.demobottnavvolleyjson.ServerConnection.DATA_URL;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment {
    FloatingActionButton btnStart;
    ProgressBar pbGetData;
    RecyclerView rclMediaList;
    TextView txtEmptyList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        btnStart = view.findViewById(R.id.btnStart);
        pbGetData = view.findViewById(R.id.pbGetData);
        rclMediaList = view.findViewById(R.id.rclMediaList);
        txtEmptyList = view.findViewById(R.id.txtEmptyList);

        //Clear saved parameters
        SharedPreferencesHelper.setSession(getActivity(), "MediaName", "");
        SharedPreferencesHelper.setSession(getActivity(), "MediaURL", "");

        btnStart.setOnClickListener(button -> {
            pbGetData.setVisibility(View.VISIBLE);
            txtEmptyList.setVisibility(View.GONE);

            // Connection to server
            ServerConnection serverConnection = new ServerConnection();
            serverConnection.sendRequest(getActivity(), BASIC_URL, DATA_URL, result -> {
                pbGetData.setVisibility(View.GONE);

                if(result == null){
                    txtEmptyList.setVisibility(View.VISIBLE);
                    Snackbar snackbar = Snackbar.make(rclMediaList, "Error getting data", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    return;
                }

                try {
                    //Parsing result
                    JSONArray jsonArray = new JSONObject(result).getJSONArray("content");



                    //Item -> OnClick
                    DataAdapter dataAdapter = new DataAdapter(jsonArray, position -> {
                        try {
                            JSONObject jsonObject = jsonArray.getJSONObject(position);
                            String MediaName =  jsonObject.get("mediaTitleCustom").toString();
                            String MediaURL =  jsonObject.get("mediaUrl").toString();

                            SharedPreferencesHelper.setSession(getActivity(), "MediaName", MediaName);
                            SharedPreferencesHelper.setSession(getActivity(), "MediaURL", MediaURL);

                            assert getActivity() != null;
                            BottomNavigationView navigationView = getActivity().findViewById(R.id.btmnavMain);
                            navigationView.setSelectedItemId(R.id.detailsFragment);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    });

                    // Initialization and setting adapter
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    rclMediaList.setLayoutManager(layoutManager);
                    rclMediaList.setItemAnimator(new DefaultItemAnimator());
                    rclMediaList.setAdapter(dataAdapter);

                    //Item -> OnSwipe
                    new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                        @Override
                        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                            return false;
                        }

                        @Override
                        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                            int position = viewHolder.getAdapterPosition();
                            jsonArray.remove(position);
                            dataAdapter.notifyItemRemoved(position);
                        }
                    }).attachToRecyclerView(rclMediaList);

                } catch (JSONException e) {
                    txtEmptyList.setVisibility(View.VISIBLE);
                    Snackbar snackbar = Snackbar.make(rclMediaList, "Data display error", Snackbar.LENGTH_LONG);
                    snackbar.show();
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