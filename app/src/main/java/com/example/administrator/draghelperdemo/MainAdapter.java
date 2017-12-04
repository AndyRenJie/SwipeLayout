package com.example.administrator.draghelperdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Andy.Ren on 2017/12/4.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder>{

    private Context mCotnext;
    private ArrayList<String> mList;
    private LayoutInflater mInflater;

    public MainAdapter(Context context,ArrayList<String> list){
        this.mCotnext = context;
        this.mList = list;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item,parent,false);
        MainViewHolder viewHolder = new MainViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        String itemStr = mList.get(position);
        holder.tvItem.setText(itemStr);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MainViewHolder extends RecyclerView.ViewHolder{

        TextView tvDel,tvTop,tvItem;

        public MainViewHolder(View itemView) {
            super(itemView);
            tvDel = (TextView) itemView.findViewById(R.id.tv_del);
            tvTop = (TextView) itemView.findViewById(R.id.tv_top);
            tvItem = (TextView) itemView.findViewById(R.id.tv_item);
        }
    }
}
