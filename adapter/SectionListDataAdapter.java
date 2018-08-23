package com.example.raman.vollerylogin.adapter;

/**
 * Created by Raman on 16-01-2017.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.example.raman.vollerylogin.MapsActivity;
import com.example.raman.vollerylogin.R;
import com.example.raman.vollerylogin.app.AppController;
import com.example.raman.vollerylogin.model.SingleItemModel;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class SectionListDataAdapter extends RecyclerView.Adapter<SectionListDataAdapter.SingleItemRowHolder> {

    private ArrayList<SingleItemModel> itemsList;
    private Context mContext;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public SectionListDataAdapter(Context context, ArrayList<SingleItemModel> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_single_card, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {

        SingleItemModel singleItem = itemsList.get(i);
        String getkidid = singleItem.getKidid();
        String kidname = singleItem.getName();
        String picurl = singleItem.getUrl();
        String pathname = "/mnt/sdcard/Download/";
        String fullpathimage = pathname+picurl;
        String imagePath = Environment.getExternalStorageDirectory().toString() + fullpathimage;
        holder.tvTitle.setText(kidname);
        holder.tvid.setText(getkidid);


        //File imageFile = new File(fullpathimage);
       // Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
      //  holder.itemImage.setImageBitmap(getCircleBitmap(bitmap));             //error this app
       // holder.itemImage.setImageURI(Uri.parse(fullpathimage));
      //  holder.itemImage.setImageDrawable(Drawable.createFromPath(fullpathimage));
        Bitmap bmp = BitmapFactory.decodeFile(fullpathimage);
        ImageView img;
        holder.itemImage.setImageBitmap(bmp);
        Log.v(TAG," single item :" +singleItem.getName()+ " imageurl : "+fullpathimage);
       // holder.itemImage.setImageResource(R.drawable.ic_08_kid_connector);

    }

    private Bitmap getCircleBitmap(Bitmap bitmap) {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        bitmap.recycle();

        return output;
    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView tvTitle,tvid;

        protected ImageView itemImage;


        public SingleItemRowHolder(View view) {
            super(view);
            this.tvid = (TextView) view.findViewById(R.id.tvid);
            this.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            this.itemImage = (ImageView) view.findViewById(R.id.itemImage);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   // itemImage.setImageResource(R.drawable.circle);
                    Intent intent = new Intent(v.getContext(), MapsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    //Get the value of the item you clicked
                    String itemClicked = tvid.getText().toString();
                   // Toast.makeText(HomeMultiKids.this, itemClicked, Toast.LENGTH_LONG).show();
                    intent.putExtra("selectkidid", itemClicked);
                    mContext.startActivity(intent);
                    Toast.makeText(v.getContext(), tvid.getText(), Toast.LENGTH_SHORT).show();
                  //  itemImage = (ImageView) findViewById(R.id.imgProgramatically);

                }
            });


        }

    }

}