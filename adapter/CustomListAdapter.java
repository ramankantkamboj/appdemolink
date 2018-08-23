package com.example.raman.vollerylogin.adapter;

/**
 * Created by Raman on 21-12-2016.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.raman.vollerylogin.app.AppController;
import com.example.raman.vollerylogin.model.WorldsBillionaires;
import com.example.raman.vollerylogin.R;

import java.util.List;

public class CustomListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<WorldsBillionaires> billionairesItems;
    private ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomListAdapter(Activity activity, List<WorldsBillionaires> billionairesItems) {
        this.activity = activity;
        this.billionairesItems = billionairesItems;
    }

    @Override
    public int getCount() {
        return billionairesItems.size();
    }

    @Override
    public Object getItem(int location) {
        return billionairesItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.activity_list_row,null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView worth = (TextView) convertView.findViewById(R.id.worth);
        TextView source = (TextView) convertView.findViewById(R.id.source);
        TextView year = (TextView) convertView.findViewById(R.id.inYear);




        // getting billionaires data for the row
        WorldsBillionaires m = billionairesItems.get(position);

        // thumbnail image
        thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);

        // name
        name.setText("Area - " + m.getName());

        // Wealth Source
        source.setText("Date: " + String.valueOf(m.getSource()));


        worth.setText(String.valueOf(m.getWorth()));

        // release year
        year.setText(String.valueOf(m.getYear()));

        return convertView;
    }

}
