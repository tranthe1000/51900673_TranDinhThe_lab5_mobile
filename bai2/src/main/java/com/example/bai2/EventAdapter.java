package com.example.bai2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder>  {
    private ArrayList<Event> events;
    private Context context;

    public interface LongTouchListener {
        void onLongTouchListener(int index);
    }

    LongTouchListener mListener;

    public EventAdapter(ArrayList<Event> events, Context context) {
        this.events = events;
        this.context = context;

        mListener = (LongTouchListener) context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvRoom;
        TextView tvDateTime;
        Switch swOnOff;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvRoom = itemView.findViewById(R.id.tvRoom);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            swOnOff = itemView.findViewById(R.id.swOnOff);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View itemView = inflater.inflate(R.layout.row_item_event, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Event event = this.events.get(position);
        holder.tvTitle.setText(event.getTitle());
        holder.tvRoom.setText(event.getRoom());
        String datetime = event.getDate() + " " + event.getTime();
        holder.tvDateTime.setText(datetime);

        holder.swOnOff.setChecked(event.isEnable());
        holder.swOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                event.setEnable(holder.swOnOff.isChecked());
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mListener.onLongTouchListener(position);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
