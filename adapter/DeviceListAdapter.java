package com.example.raman.vollerylogin.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.example.raman.vollerylogin.R;
import com.example.raman.vollerylogin.app.AppController;
import com.example.raman.vollerylogin.model.CheckBoxData;
import com.example.raman.vollerylogin.model.Device;

import java.util.ArrayList;

import static com.facebook.login.widget.ProfilePictureView.TAG;

/**
 * Created by Raman on 16-02-2017.
 */

public class DeviceListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<Device> feedAlerts;
    String value;
    int val;

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    public DeviceListAdapter(ArrayList<Device> feedAlerts, Context context) {
        this.feedAlerts = feedAlerts;
        this.context = context;
        Log.v(TAG," device adapter list : "+feedAlerts);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kids_item_alerts, parent, false);
        DeviceListAdapter.ItemHolder holder = new DeviceListAdapter.ItemHolder(view);
        return holder;

    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        DeviceListAdapter.ItemHolder itemHolder = (DeviceListAdapter.ItemHolder) holder;
        loadView(itemHolder, position);
    }

    private void loadView(DeviceListAdapter.ItemHolder itemHolder, int position) {

        if (feedAlerts.get(position) != null) {

            if (feedAlerts.get(position).getName() != null) {
                itemHolder.tv_member_name.setText(feedAlerts.get(position).getName());
            }

            if (imageLoader == null)
                imageLoader = AppController.getInstance().getImageLoader();
            if (feedAlerts.get(position).getProfilePic() != null) {
                // itemHolder.profilePic_name.setImageDrawable(Drawable.createFromPath(feedAlerts.get(position).getProfilePic()));
                String path ="/mnt/sdcard/Download/";
                String picname = feedAlerts.get(position).getProfilePic();
                String imageurl = path+picname;
                Log.v(TAG," image url : "+imageurl);
               // itemHolder.profilePic_name.setImageUrl(imageurl, imageLoader);
                 itemHolder.profilePic_name.setImageDrawable(Drawable.createFromPath(imageurl));

            }


            if (feedAlerts.get(position).getCheckbox_name() != null) {
                addCheckBoxView(itemHolder, feedAlerts.get(position));
            }




        }
    }

    private void addCheckBoxView(DeviceListAdapter.ItemHolder parent, Device checkBoxData) {
        // View addedView = getCheckBoxView(p, context,parent);
       /* final android.view.ViewParent parent2 = addedView.getParent();

        if (parent2 instanceof android.view.ViewManager) {
            final android.view.ViewManager viewManager = (android.view.ViewManager) parent2;
            viewManager.removeView(addedView);
        }
        try {
            int count = parent.chekBoxContainer.getChildCount();
            if (count > 1)
                parent.chekBoxContainer.removeViewAt(1);
            parent.chekBoxContainer.addView(addedView);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        Log.v(TAG,"checkbox data list : "+checkBoxData);
        if (parent.chekBoxContainer.getChildCount() > 0) {
            parent.chekBoxContainer.removeAllViews();
        }
        TextView tvName;

        AppCompatCheckBox checkBox;
        ArrayList<CheckBoxData> list = null;
        if (checkBoxData.getCheckbox_name() != null) {
            list = checkBoxData.getCheckbox_name();

            if (list.size() > 0) for (int i = 0; i < checkBoxData.getCheckbox_name().size(); i++) {
                final int pos = i;
                final View view = LayoutInflater.from(context)
                        .inflate(R.layout.checkbox_item, null, false);
                tvName = (TextView) view.findViewById(R.id.tvName);

                checkBox = (AppCompatCheckBox) view.findViewById(R.id.checkbox);
                //  Log.v(TAG,"checkbox get value : "+checkBox);
                tvName.setText(list.get(pos).getAlert_name());
                checkBox.setId(Integer.parseInt(list.get(pos).getAlertid()));
                // checkBox.setText(list.get(pos).getAlertid());
                Log.v(TAG, "checkbox get alertid : " + list.get(pos).getAlertid());
                Log.v(TAG, "checkbox get alertname : " + list.get(pos).getAlert_name());
                parent.chekBoxContainer.addView(view);

                final AppCompatCheckBox finalCheckBox = checkBox;

                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (finalCheckBox.isChecked()) {
                            // set cheek mark drawable and set checked property to true
                            value = "Checked";
                            // finalCheckBox.setCheckMarkDrawable(0);
                            val =finalCheckBox.getId();

                            finalCheckBox.setChecked(true);

                        } else {
                            // set cheek mark drawable and set un-checked property to false
                            value = "Un-Checked";
                            val =finalCheckBox.getId();
                            //  checkBox.setCheckMarkDrawable(R.drawable.checked);
                            finalCheckBox.setChecked(false);
                        }

                        Toast.makeText(context,value +": "+val , Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

 /*   private View getCheckBoxView(final FeedAlert checkBoxData, Context ctx,ItemHolder parent) {
        TextView tvName;
        AppCompatCheckBox checkBox;
        ArrayList<CheckBoxData> list = null;
        if (checkBoxData.getCheckbox_name() != null) {
            list = checkBoxData.getCheckbox_name();
            if (list.size() > 0) {
                for (int i = 0; i < checkBoxData.getCheckbox_name().size(); i++) {
                    final int pos = i;
                    final View view = LayoutInflater.from(ctx)
                            .inflate(R.layout.checkbox_item, null, false);
                    tvName = (TextView) view.findViewById(R.id.tvName);
                    checkBox = (AppCompatCheckBox) view.findViewById(R.id.checkbox);

                    tvName.setText(list.get(pos).getAlert_name());
                }

            }
        }


        return view;
    }*/

    @Override
    public int getItemCount() {
        return feedAlerts.size();
    }


    class ItemHolder extends RecyclerView.ViewHolder {


        LinearLayout chekBoxContainer;
        TextView tv_member_name;
        ImageView profilePic_name;

        public ItemHolder(View itemView) {
            super(itemView);
            tv_member_name = (TextView) itemView.findViewById(R.id.name);
            profilePic_name = (ImageView) itemView.findViewById(R.id.profilePic);
            chekBoxContainer = (LinearLayout) itemView.findViewById(R.id.checkbox_container);

        }


    }

}