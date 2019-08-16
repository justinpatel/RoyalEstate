package com.justin.perfect.royalestate.adapter;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.justin.perfect.royalestate.R;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.HorizontalViewHolder>{

    public interface RecyclerViewClickListener {
        void onClick(View view,int position);
    }

    private String[] items;
    private int globalPosition;
    private RecyclerViewClickListener mListener;

    public HorizontalAdapter(String[] items,RecyclerViewClickListener listener) {
        this.items = items;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public HorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.property_types,parent,false);
        return new HorizontalViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final HorizontalViewHolder holder, int position) {
        holder.tv.setText(items[position]);
        holder.bind(position,mListener);

        if (position == globalPosition){
            holder.tv.setBackgroundColor(Color.parseColor("#BDBDBD"));
            holder.tv.setTypeface(null, Typeface.BOLD);

        }else {
            holder.tv.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.tv.setTypeface(null, Typeface.NORMAL);
        }
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv;
        RecyclerViewClickListener recyclerViewClickListener;

        public HorizontalViewHolder(View itemView, RecyclerViewClickListener recyclerViewClickListener) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.types_tv);
            this.recyclerViewClickListener = recyclerViewClickListener;

            itemView.setOnClickListener(this);

            /*tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    globalPosition = getAdapterPosition();
                    notifyDataSetChanged();
                }
            }); */
        }

        @Override
        public void onClick(View view) {
            recyclerViewClickListener.onClick(view,getAdapterPosition());
        }

        public void bind(final int position, final RecyclerViewClickListener mListener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onClick(view,position);
                    //Snackbar.make(view,position+"",Snackbar.LENGTH_SHORT).show();
                    globalPosition = getAdapterPosition();
                    notifyDataSetChanged();
                }
            });
        }
    }
}
