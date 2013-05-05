package com.yangli907.imageviewer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends Activity {
	private Button displayBtn;
	private EditText keywords;
	private static final String endpoint = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="; 
	private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private int index = 0;
    private int max_index = 0;
	private ImageView imageView;
	ArrayList<String> urls;
	private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayBtn = (Button)findViewById(R.id.button1);
        keywords = (EditText)findViewById(R.id.editText1);
        imageView = (ImageView)findViewById(R.id.imageView1);
        urls = new ArrayList<String>();
     // Gesture detection
        gestureDetector = new GestureDetector(new MyGestureDetector());
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };
        imageView.setOnTouchListener(gestureListener);
        imageView.setLongClickable(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void cleanDefault(View view){
    	if(keywords.getText().equals("Keywords"))
    		keywords.setText("");
    }
    
    public void clean(View view){
    	keywords.setText("");
    	imageView.setImageBitmap(null);
    }
    
    public void fetchImg(View view){
    	InputMethodManager inputManager = (InputMethodManager)
        getSystemService(Context.INPUT_METHOD_SERVICE); 
    	inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
        InputMethodManager.HIDE_NOT_ALWAYS);

    	new DownloadImageTask().execute(keywords.getText().toString());
    }
	
    public Bitmap getBitmap(String url)
    {
        //from web
        try {
            Bitmap bitmap=null;
            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)imageUrl.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setInstanceFollowRedirects(true);
            InputStream is=conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            return bitmap;
        } catch (Throwable ex){
           ex.printStackTrace();
           return null;
        }
    }
    
    private class DownloadImageTask extends AsyncTask<String, Integer, ArrayList<String>>{
    	@Override
    	protected ArrayList<String> doInBackground(String... params) {
    		JSONObject json = null;
			try {
				json = loadSearchResult(params[0]);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JSONArray imageUrls = null;
			try {
				imageUrls = json.getJSONObject("responseData").getJSONArray("results");
				max_index = imageUrls.length();
				urls.clear();
				for(int i = 0; i<imageUrls.length(); i++){
					urls.add(imageUrls.getJSONObject(i).getString("url").toString());
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return urls;
		}

    	@Override
		protected void onPostExecute(ArrayList<String> result) {
			// TODO Auto-generated method stub
			imageView.setImageBitmap(getBitmap(result.get(0)));
		}
    	
		private JSONObject loadSearchResult(String string) throws IOException, JSONException {
			URL url = new URL(endpoint+URLEncoder.encode(keywords.getText().toString(),"utf-8")+"&rsz=8");
			String line;
			StringBuilder builder = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
			while((line = reader.readLine()) != null) {
			 builder.append(line);
			}
			JSONObject json = new JSONObject(builder.toString());//.getJSONObject("responseData").getJSONObject("results");
			return json;
		}
    }
    
    class MyGestureDetector extends SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                    return false;
                // right to left swipe
                if(e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                	if(index>0){
                		index--;
                		imageView.setImageBitmap(getBitmap(urls.get(index)));
                	}
                }  
                else if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                	if(index<max_index){
                		index++;
                		imageView.setImageBitmap(getBitmap(urls.get(index)));
                	}
                }
            } catch (Exception e) {
                // nothing
            }
            return false;
        }

    }
}

