package com.example.raman.vollerylogin.adapter;

/**
 * Created by Raman on 25-01-2017.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.raman.vollerylogin.KidsImageView;
import com.example.raman.vollerylogin.KidsItem;
import com.example.raman.vollerylogin.R;
import com.example.raman.vollerylogin.app.AppController;

import java.util.List;

import static com.facebook.login.widget.ProfilePictureView.TAG;


public class KidsListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<KidsItem> feedItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public KidsListAdapter(Activity activity, List<KidsItem> feedItems) {
        this.activity = activity;
        this.feedItems = feedItems;
    }

    @Override
    public int getCount() {
        Log.v(TAG,"position size " +feedItems.size());
        return feedItems.size();
    }

    @Override
    public Object getItem(int location) {
        return feedItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.v(TAG,"position " +position+ " convertView "+ convertView + " parent "+ parent);
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
           convertView = inflater.inflate(R.layout.kids_item, parent, false);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        TextView kidid = (TextView) convertView.findViewById(R.id.et_kidid);
        TextView names = (TextView) convertView.findViewById(R.id.names);
        TextView timestamp = (TextView) convertView.findViewById(R.id.timestamp);
        TextView statusMsg = (TextView) convertView.findViewById(R.id.txtStatusMsg);
        ImageView profilePic = (ImageView) convertView.findViewById(R.id.profilePic);
        KidsImageView feedImageView = (KidsImageView) convertView.findViewById(R.id.feedImage1);

        KidsItem item = feedItems.get(position);

        kidid.setText(item.getId());
        Log.v(TAG," set id " + item.getId());
        names.setText(item.getName());

        timestamp.setText(item.getTimeStamp());
        // Chcek for empty status message
        if (!TextUtils.isEmpty(item.getStatus())) {
            statusMsg.setText(item.getStatus());
            statusMsg.setVisibility(View.VISIBLE);
        } else {
            // status is empty, remove from view
            statusMsg.setVisibility(View.GONE);
        }

        // user profile pic
        //profilePic.setImageUrl(item.getProfilePic(), imageLoader);
        //profilePic.setImageUrl(item.getProfilePic(), imageLoader);
        String picurl = item.getProfilePic();
        String pathname = "/mnt/sdcard/Download/";
        String fullpathimage = pathname+picurl;
        profilePic.setImageDrawable(Drawable.createFromPath(fullpathimage));

        // Feed image
        if (item.getImges() != null) {
            feedImageView.setImageUrl(item.getImges(), imageLoader);
            feedImageView.setVisibility(View.VISIBLE);
            feedImageView
                    .setResponseObserver(new KidsImageView.ResponseObserver() {
                        @Override
                        public void onError() {
                        }

                        @Override
                        public void onSuccess() {
                        }
                    });
        } else {
            feedImageView.setVisibility(View.GONE);
        }

        return convertView;
    }

}