package com.hello;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;

public class HelloAndroidActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final ImageView iv = (ImageView)findViewById(R.id.imageView1);	
        try
        {
        	final EditText et = (EditText)findViewById(R.id.editText1);
        	final Bitmap bmap =BitmapFactory.decodeFile("/sdcard/test1.jpg");
        	final int width_org = bmap.getWidth();
        	final int height_org = bmap.getHeight();
//        	SeekBar sb = (SeekBar)findViewById(R.id.seekBar1);
//        	sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//				public void onStopTrackingTouch(SeekBar seekBar) {
//					// TODO Auto-generated method stub
//					
//				}
//				
//				public void onStartTrackingTouch(SeekBar seekBar) {
//					// TODO Auto-generated method stub
//					
//				}
//				
//				public void onProgressChanged(SeekBar seekBar, int progress,
//						boolean fromUser) {
//					et.setText(String.valueOf(seekBar.getProgress()));
//				}
//			}
//        	);
//			Matrix matrix = new Matrix();
//			float scaleWidth = (float)width_org/2;
//			float scaleHeight = (float)height_org/2;
//			matrix.postScale(scaleWidth, scaleHeight);
//			Bitmap bmap_scale = Bitmap.createBitmap(bmap,0, 0, width_org, height_org, matrix, true);
//			BitmapDrawable bd = new BitmapDrawable(bmap_scale);
//			Animation animation = new TranslateAnimation(0,0,Animation.ZORDER_NORMAL, 300);
//		    animation.setDuration(1500);
//		    iv.setAnimation(animation);
			//iv.setImageDrawable(bd);
			iv.setImageBitmap(bmap);
        }
         catch(Exception e)
         {
        	 e.printStackTrace();
         }
         
    }
}