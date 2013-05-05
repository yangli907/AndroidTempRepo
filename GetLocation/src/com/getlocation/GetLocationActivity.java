package com.getlocation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.OverlayItem;

public class GetLocationActivity extends MapActivity {
    /** Called when the activity is first created. */
	LocationManager lm = null;
	TextView altitude = null;
	TextView latitude = null;
	TextView longtitude = null;
	Button btn = null;
	MapController mapcont = null;
	MapView mapview = null;
	MapController mc = null;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        altitude = (TextView)findViewById(R.id.textView2);
        latitude = (TextView)findViewById(R.id.textView3);
        longtitude = (TextView)findViewById(R.id.textView4);
        mapview = (MapView)findViewById(R.id.map_view);
        mapview.setBuiltInZoomControls(true);
        mc = mapview.getController();
        
        final MyLocationOverlay mlo = new MyLocationOverlay(this, mapview);
		
        btn = (Button)findViewById(R.id.button1);
        btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		    	if(location!=null){
					
					latitude.setText(String.valueOf(location.getLatitude()));
					longtitude.setText(String.valueOf(location.getLongitude()));
					float[] dist = new float[3];
					GeoPoint gp = new GeoPoint((int)(location.getLatitude()*1000000.), (int)(location.getLongitude()*1000000.));
					DatabaseHelper dbhelper = null;
					try{
						dbhelper.addRow(String.valueOf(gp.getLatitudeE6()), String.valueOf(gp.getLongitudeE6()));
					}
					catch (Exception e) {
						e.printStackTrace();// TODO: handle exception
					}
					final Resources resource = getResources();
					addItemOverlay overlay = new addItemOverlay(resource.getDrawable(R.drawable.icon));
					overlay.addOverlay(new OverlayItem(gp, "Yang", "Ann Arbor"));
					mlo.enableMyLocation();
					mlo.runOnFirstFix(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							
						}
					});
					mapview.getOverlays().add(mlo);
					mc.setCenter(gp);
					mc.animateTo(gp);
					mc.setZoom(15);

					}
			}
		});
        lm = (LocationManager)this.getSystemService(LOCATION_SERVICE);
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {

        	new AlertDialog.Builder(this).setTitle("地圖工具").setMessage("您尚未開啟定位服務，要前往設定頁面啟動定位服務嗎？")
        			.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener()
        			{

        				public void onClick(DialogInterface dialog, int which)
        				{
        					startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        				}
        			}).setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        			{
        				public void onClick(DialogInterface dialog, int which)
        				{
        					//Toast.makeText(this, "未開啟定位服務，無法使用本工具!!", Toast.LENGTH_SHORT).show();
        				}
        			}).show();

        }
        LocationListener ll = new LocationListener() {
			
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				
			}
			
			@Override
			public void onProviderEnabled(String provider) {
				
			}
			
			@Override
			public void onProviderDisabled(String provider) {
				
			}
			
			@Override
			public void onLocationChanged(Location location) {

			}
		};
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, ll);

    }
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
    
	
}