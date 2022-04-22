package it.anlim.demobottnavvolleyjson;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {
    private final JSONArray jsonArray;
    OnClickItem onClickItem;

    // On Item Click interface
    public interface OnClickItem {
        void OnItemClick(int position);
    }

    // Adapter Initialization
    public DataAdapter(JSONArray jsonArray, OnClickItem onClickItem){
        this.jsonArray = jsonArray;
        this.onClickItem = onClickItem;

    }

    // Initialization item view (what xml file to use)
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_media, parent, false);
        return new MyViewHolder(view, onClickItem);
    }

    // Initialization elements in item view
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtMediaName;
        private final TextView txtMediaURL;
        private final TextView txtMediaID;

        public MyViewHolder(@NonNull View itemView, OnClickItem onClickItem) {
            super(itemView);

            txtMediaName = itemView.findViewById(R.id.txtMediaName);
            txtMediaURL = itemView.findViewById(R.id.txtMediaURL);
            txtMediaID = itemView.findViewById(R.id.txtMediaID);

            itemView.setOnClickListener(item -> {
                if(onClickItem != null) {
                    int pos = getAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION)
                        onClickItem.OnItemClick(pos);
                }
            });

        }
    }

    // Setting data values to xml elements
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try {
            holder.txtMediaName.setText(jsonArray.getJSONObject(position).get("mediaTitleCustom").toString());
            holder.txtMediaURL.setText(jsonArray.getJSONObject(position).get("mediaUrl").toString());
            holder.txtMediaID.setText(jsonArray.getJSONObject(position).get("mediaId").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // List size
    @Override
    public int getItemCount() {
        return jsonArray.length();
    }
}
