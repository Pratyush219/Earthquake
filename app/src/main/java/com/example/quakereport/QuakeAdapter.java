package com.example.quakereport;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

public class QuakeAdapter extends RecyclerView.Adapter<QuakeAdapter.ViewHolder> {
    private List<Earthquake> earthquakes;
    private final Context mContext;

    public QuakeAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setEarthquakes(List<Earthquake> earthquakes) {
        this.earthquakes = earthquakes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuakeAdapter.ViewHolder holder, int position) {
        if(earthquakes != null){
            Earthquake earthquake = earthquakes.get(position);
            holder.magnitude.setText(new DecimalFormat("0.0").format(earthquake.getMagnitude()));
            String[] parts = earthquake.getLocation().split("of ");
            String off = (parts.length == 1) ? "Near" : parts[0] + "of";
            String loc = parts[Math.min(1, parts.length - 1)];
            holder.offset.setText(off);
            holder.place.setText(loc);
            holder.date.setText(earthquake.getDate());
            holder.time.setText(earthquake.getTime());
            GradientDrawable shapeDrawable = (GradientDrawable) holder.magnitude.getBackground().mutate();
            int mag = (int) earthquake.getMagnitude();
            switch (mag) {
                case 0:
                case 1:
                    shapeDrawable.setColor(ContextCompat.getColor(mContext, R.color.magnitude1));
                    break;
                case 2:
                    shapeDrawable.setColor(ContextCompat.getColor(mContext, R.color.magnitude2));
                    break;
                case 3:
                    shapeDrawable.setColor(ContextCompat.getColor(mContext, R.color.magnitude3));
                    break;
                case 4:
                    shapeDrawable.setColor(ContextCompat.getColor(mContext, R.color.magnitude4));
                    break;
                case 5:
                    shapeDrawable.setColor(ContextCompat.getColor(mContext, R.color.magnitude5));
                    break;
                case 6:
                    shapeDrawable.setColor(ContextCompat.getColor(mContext, R.color.magnitude6));
                    break;
                case 7:
                    shapeDrawable.setColor(ContextCompat.getColor(mContext, R.color.magnitude7));
                    break;
                case 8:
                    shapeDrawable.setColor(ContextCompat.getColor(mContext, R.color.magnitude8));
                    break;
                case 9:
                    shapeDrawable.setColor(ContextCompat.getColor(mContext, R.color.magnitude9));
                    break;
                default:
                    shapeDrawable.setColor(ContextCompat.getColor(mContext, R.color.magnitude10plus));
            }
            holder.details.setOnClickListener(v -> {
                Uri uri = Uri.parse(earthquake.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                if (intent.resolveActivity(mContext.getPackageManager()) != null) {
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(earthquakes == null) return 0;
        return earthquakes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView magnitude,place,date, time,offset;
        private final LinearLayout details;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            magnitude = itemView.findViewById(R.id.mag);
            place = itemView.findViewById(R.id.place);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            offset = itemView.findViewById(R.id.offset);
            details = itemView.findViewById(R.id.details);
        }
    }
}
