package com.sensor;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;
import android.widget.Toast;

public class TouchSensorActivity extends Activity implements OnTouchListener , OnGestureListener{
    /** Called when the activity is first created. */
	private int counter = 0;
	private GestureDetector mGestureDetector = new GestureDetector(this);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        TextView tv = (TextView)findViewById(R.id.textView1);
        tv.setOnTouchListener(this);
        tv.setLongClickable(true);
       // tv.setText(String.valueOf(counter));
    }

	@Override
	public boolean onTouch(View v, MotionEvent event) {
//		TextView tv = (TextView)findViewById(R.id.textView1);
//		tv.setText(String.valueOf(++counter));
		//Toast.makeText(this, event.getRawX()+" "+event.getRawY(), Toast.LENGTH_SHORT).show();
		return mGestureDetector.onTouchEvent(event);

	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		if((e1.getX()-e2.getX())>100&&Math.abs(velocityX)>50)
			Toast.makeText(this, e1.getX()+ " to the left " + e2.getX(), Toast.LENGTH_SHORT).show();
		else if((e2.getX()-e1.getX())>100&&Math.abs(velocityX)>50)
			Toast.makeText(this, e1.getX()+ " to the right " + e2.getX(), Toast.LENGTH_SHORT).show();
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		Toast.makeText(this, "Long pressed!", Toast.LENGTH_SHORT).show();
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
}