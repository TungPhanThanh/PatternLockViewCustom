package com.tungpt.PatternLockView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private ArrayList<Model> mModels;

    public Adapter(Context context, ArrayList<Model> models) {
        mLayoutInflater = LayoutInflater.from(context);
        this.mModels = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mLayoutInflater.inflate(R.layout.item_list, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindData(mModels.get(i));
    }

    @Override
    public int getItemCount() {
        return mModels != null ? mModels.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextViewTitle;
        private TextView mTextViewWebsite;
        private TextView mTextViewId;
        private TextView mTextViewPassword;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewTitle = itemView.findViewById(R.id.text_name);
        }

        public void bindData(Model model) {
            mTextViewTitle.setText(model.getTitle());
        }
    }
}
