package com.example.raman.vollerylogin;

/**
 * Created by Raman on 25-01-2017.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.raman.vollerylogin.adapter.KidsListAdapter;
import com.example.raman.vollerylogin.app.AppController;
import com.example.raman.vollerylogin.app.SessionManager;
import com.example.raman.vollerylogin.helper.SQLiteHandler;
import com.example.raman.vollerylogin.volley.CustomRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HomeMultiKids extends ActionBarActivity implements  NavigationDrawerFragment.NavigationDrawerCallbacks{


    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    private DrawerLayout mDrawerLayout;
    private static final String TAG = HomeMultiKids.class.getSimpleName();
    private ListView listView;
    private KidsListAdapter listAdapter;
    private List<KidsItem> kidsItems;
    private String logintype;
    private String loginuid;
    private String editstatus;
    private String otpstatus;
    private SQLiteHandler db;
    private SessionManager session;
    private Context mContext;
    TextView fetchkidid;
    String getkidid;
    List<String> listkidid=new ArrayList<>();
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kids);

        listView = (ListView) findViewById(R.id.list);

        // Set up the drawer.

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.main_layout));

        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_layout);
        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();

        logintype = user.get("logintype");
        loginuid = user.get("uid");
        editstatus = user.get("editstatus");
        otpstatus = user.get("otpstatus");

      //  findmultikids(loginuid);
       kidsItems = new ArrayList<KidsItem>();

        listAdapter = new KidsListAdapter(this, kidsItems);

        listAdapter.notifyDataSetChanged();
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // String cities = String.valueOf(parent.getItemAtPosition(position));
                        Intent intent = new Intent(HomeMultiKids.this, KidCurrentHistory.class);
                        //Get the value of the item you clicked
                        String itemClicked = listkidid.get(position);
                        Toast.makeText(HomeMultiKids.this, itemClicked, Toast.LENGTH_LONG).show();
                        intent.putExtra("getkidid", itemClicked);
                        startActivity(intent);
                    }
                }
        );

        // Fetching user details from sqlite
        ArrayList<HashMap<String, String>> userkids = db.getkidListDetails(loginuid);
        int getkidsize = userkids.size();
        Log.v(TAG,"count userkids data: " + getkidsize );
        String[] userkidid = new String[getkidsize];
        String[] userbarcodeid= new String[getkidsize];
        String[] userkid_name = new String[getkidsize];
        String[] userkid_image= new String[getkidsize];
        String[] userkid_address= new String[getkidsize];

        for(int i = 0; i<=getkidsize-1; i++)
        {
            KidsItem item = new KidsItem();
            item.setId(userkids.get(i).get("kidid"));
            item.setName(userkids.get(i).get("kid_name"));
            item.setImges("kids_mapimage");
            item.setStatus(userkids.get(i).get("kid_address"));
            item.setProfilePic(userkids.get(i).get("kid_image"));
            item.setTimeStamp("12:58 AM");
            kidsItems.add(item);
//            userkidid[i] = userkids.get(i).get("kidid");
//            userkid_name[i] = userkids.get(i).get("kid_name");
//            userkid_image[i] = userkids.get(i).get("kid_image");
//            userkid_address[i] = userkids.get(i).get("kid_address");
            Log.v(TAG,"value kids list accoding user" + userkids.get(i));
            listkidid.add(userkids.get(i).get("kidid"));
        }
        listAdapter.notifyDataSetChanged();




    }


    public void OpenKidsHistorydetails(View view)
    {

       // Toast.makeText(getApplicationContext(), fetchkidid.getText().toString(), Toast.LENGTH_LONG).show();
        //Intent intent=new Intent(this,MapsActivity.class);
       // intent.putExtra("getkidid",getkidid);
      //  startActivity(intent);
      //  startActivity(new Intent(this,MapsActivity.class));
    }


    private void findmultikids(String userid) {
        String tag_json_obj = "findmultikids";
        String mulitplekids_url = "http://app.evotron.in/home_multiplekid.php";

      //  final ProgressDialog pDialog = new ProgressDialog(mContext);
      //  pDialog.setMessage(getResources().getString(R.string.loading));
      //  pDialog.setCancelable(false);
      //  pDialog.show();

        Map<String, String> params = new HashMap<String, String>();
        params.put("userid", userid);


        CustomRequest request = new CustomRequest(Request.Method.POST, mulitplekids_url, params, new Response.Listener<JSONObject>() {
            /**
             * Called when a response is received.
             *
             * @param response
             */
               // parseJsonFeed
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray feedArray = response.getJSONArray("feed");

                    for (int i = 0; i < feedArray.length(); i++) {
                        JSONObject feedObj = (JSONObject) feedArray.get(i);

                        KidsItem item = new KidsItem();
                        item.setId(feedObj.getString("id"));
                        item.setName(feedObj.getString("name"));

                        // Image might be null sometimes
                        String image = feedObj.isNull("image") ? null : feedObj.getString("image");
                        Log.v(TAG,"image data : "+image);
                        item.setImges(image);
                        item.setStatus(feedObj.getString("address"));
                        item.setProfilePic(feedObj.getString("profilePic"));
                        item.setTimeStamp(feedObj.getString("timeStamp"));
                        kidsItems.add(item);
                    }

                    // notify data changes to list adapater

                    listAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
              //  pDialog.dismiss();
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }

        });
        DefaultRetryPolicy dfR = new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(dfR);
        AppController.getInstance().addToRequestQueue(request, tag_json_obj);
    }



    public void add_device(View view)
    {
        startActivity(new Intent(this,ScanBarcode.class));
    }

    // click view profile button (navigation header bar)
    public void ProfileShow(View view)
    {
        startActivity(new Intent(this,ProfileActivity.class));
    }

    /**
     * Parsing json reponse and passing the data to feed view list adapter
     * */


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){
            case R.id.menu_item_fence:   //this item has your app icon
                Intent intent = new Intent(this, CreateFence.class);
                startActivity(intent);
                return true;

             case R.id.menu_item_alert:  //other menu items if you have any
           // add any action here
                 Intent intentalert = new Intent(this, ShowAlerts.class);
                 startActivity(intentalert);
               return true;

            case R.id.menu_item_dnd:  //other menu items if you have any
                // add any action here
                Intent intendnd = new Intent(this, KidsAlerts.class);
                startActivity(intendnd);
                return true;
            //  MenuItem favoriteItem = menu.findItem(R.id.action_favorite);

            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        // for fence
        menu.findItem(R.id.menu_item_fence).setEnabled(true);
        MenuItem favoriteItemfence = menu.findItem(R.id.menu_item_fence);
        Drawable newIcon = (Drawable)favoriteItemfence.getIcon();
        newIcon.mutate().setColorFilter(Color.argb(255, 200, 200, 200), PorterDuff.Mode.SRC_IN);
        favoriteItemfence.setIcon(newIcon);

// alert
        menu.findItem(R.id.menu_item_alert).setEnabled(true);
        MenuItem favoriteItemalert = menu.findItem(R.id.menu_item_alert);
        Drawable newIconalert = (Drawable)favoriteItemalert.getIcon();
        newIconalert.mutate().setColorFilter(Color.argb(255, 200, 200, 200), PorterDuff.Mode.SRC_IN);
        favoriteItemalert.setIcon(newIconalert);
// for dnd
        menu.findItem(R.id.menu_item_dnd).setEnabled(true);
        MenuItem favoriteItemdnd = menu.findItem(R.id.menu_item_dnd);
        Drawable newIcondnd = (Drawable)favoriteItemdnd.getIcon();
        newIcondnd.mutate().setColorFilter(Color.argb(255, 200, 200, 200), PorterDuff.Mode.SRC_IN);
        favoriteItemdnd.setIcon(newIcondnd);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

    }
}