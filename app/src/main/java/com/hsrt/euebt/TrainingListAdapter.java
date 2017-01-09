package com.hsrt.euebt;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


/**
 * Created by Tugrul on 19.12.2016.
 */

public class TrainingListAdapter extends BaseAdapter {
    private Context mContext;
    private List<Training> mUebungList;

    public TrainingListAdapter(Context mContext, List<Training> mUebungList) {
        this.mContext = mContext;
        this.mUebungList = mUebungList;
    }

    @Override
    public int getCount() {
        return mUebungList.size();
    }

    @Override
    public Object getItem(int position) {
        return mUebungList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.item_uebung_list, null);
        TextView tvName = (TextView)v.findViewById(R.id.tv_name);
        TextView tvBeschreibung = (TextView)v.findViewById(R.id.tv_beschreibung);
        //Set text for TextView
        tvName.setText(mUebungList.get(position).getName());
        // Changed by Johannes: Using name as tag, since trainings don't have an ID...
        v.setTag(mUebungList.get(position).getName());

        return v;
    }
}