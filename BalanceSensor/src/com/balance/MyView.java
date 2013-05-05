package com.balance;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

public class MyView extends SurfaceView implements Runnable, SensorEventListener, android.view.SurfaceHolder.Callback, OnTouchListener {
	public static final int TIME_IN_FRAME = 10;
	private float mPosx = 200;
	private float mPosy = 0;
	private float mPosx_2 = 100;
	private float mPosy_2 = 100;
	private Bitmap ball;
	private Bitmap ball2;
	private float dist=0;
	//private Bitmap bg;
	private Paint mPaint;
	private SensorManager mSensorManage;
	private Context mContext = null;
	private Sensor mSensor;
	//private SensorEventListener mSensorEventListener;
	boolean mIsRunning = false;
    double bx = 0.;
    double by = 0.;
    double bz = 0.;
    double btime = 0;
    double x = 0;
    double y = 0;
    double z = 0;
    int millsec = 0;
    SurfaceHolder mSurfaceHolder = null;
    float mScreenWidth = 0;
    float mScreenHeight = 0;
    float mBallWidth = 0;
    float mBallHeight = 0;
    float mBallWidth_2 = 0;
    float mBallHeight_2 = 0;
    
	final TextView m_tvx = (TextView)findViewById(R.id.TextView1);
    final TextView m_tvy = (TextView)findViewById(R.id.TextView2);
    final TextView m_tvz = (TextView)findViewById(R.id.TextView3);

    Canvas mCanvas = null;
	public MyView(Context context){
		super(context);
		mContext = context;
		this.setOnTouchListener(this);
		this.setFocusableInTouchMode(true);
		this.setFocusable(true);
		mSurfaceHolder = this.getHolder();
		mSurfaceHolder.addCallback(this);
		mCanvas = new Canvas();
		mPaint = new Paint();
		mPaint.setColor(Color.BLUE);
		ball = BitmapFactory.decodeResource(this.getResources(), R.drawable.ball);
		ball2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.ball2);
		//bg = BitmapFactory.decodeResource(this.getResources(), R.drawable.bg);
	    mSensorManage = (SensorManager)mContext.getSystemService(mContext.SENSOR_SERVICE);
	    mSensor = mSensorManage.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	    mSensorManage.registerListener(this, mSensor,SensorManager.SENSOR_DELAY_GAME);
	}
	
	private void draw(){
		mCanvas.drawColor(Color.BLACK);
		//mCanvas.drawBitmap(bg, 0, 0, mPaint);
		mCanvas.drawBitmap(ball, mPosx,mPosy, mPaint);
		mCanvas.drawBitmap(ball2, mPosx_2, mPosy_2, mPaint);
		mCanvas.drawText("X = "+ x, 0, 20, mPaint);
		mCanvas.drawText("Y = "+ y, 0, 40, mPaint);
		mCanvas.drawText("dist = "+ dist, 0, 60, mPaint);
		mCanvas.drawText("PosX = "+ mPosx, 0, 80, mPaint);
		mCanvas.drawText("PosY = "+ mPosy, 0, 100, mPaint);
	}
	
	private void drawball(float x, float y){
		mCanvas.drawColor(Color.BLACK);
		mCanvas.drawBitmap(ball, x, y, mPaint);
	}
	@Override
	public void run() {
		while(mIsRunning){
			long startTime = System.currentTimeMillis();
			synchronized (mSurfaceHolder) {
			mCanvas = mSurfaceHolder.lockCanvas();
			try{
				if(ball!=null)
					draw();	
			}
			catch(Exception e){
				e.printStackTrace();
			}
			finally
			{
				if(ball!=null){
					mSurfaceHolder.unlockCanvasAndPost(mCanvas);
				}
					
			} 
			long endTime = System.currentTimeMillis();
			int diffTime = (int)(endTime-startTime);		// TODO Auto-generated method stub
			while(diffTime <=TIME_IN_FRAME){
				diffTime = (int)(System.currentTimeMillis()-startTime);
				millsec++;
				Thread.yield();
				}
			float b1x = mPosx+mBallWidth/2;
			float b1y = mPosy+mBallHeight/2;
			float b2x = mPosx_2+mBallWidth_2/2;
			float b2y = mPosy_2+mBallHeight_2/2;
			dist = (float) Math.sqrt((b2x-b1x)*(b2x-b1x) +(b2y-b1y)*(b2y-b1y));
			if(dist>(mBallWidth+mBallWidth_2)/2){
				mPosx -= (float) x;
				mPosy += (float) y;			
				mPosx_2 -= (float) x*2;
				mPosy_2 += (float) y*2;
			}
			else{
				mPosx -= (float) x;
				if(b1y<b2y&&y>0){
					mPosy -= (float) y;
					mPosy_2 += (float) y*2;
				}
				if(b1y<b2y&&y<0){
					mPosy += (float) y;
					mPosy_2-= (float) y*2;
				}
				if(b1y>b2y&&y>0){
					mPosy += (float)y;
					mPosy_2-= (float) y*2;
				}
				if(b1y>b2y&&y<0){
					mPosy -= (float)y;
					mPosy_2+= (float) y*2;
				}
				mPosx_2 -= (float) x*2;
			}
			mPosx = stayInScreen(mPosx, mScreenWidth, mBallWidth, y);
			mPosy = stayInScreen(mPosy, mScreenHeight, mBallHeight, x);
			mPosx_2 = stayInScreen(mPosx_2, mScreenWidth, mBallWidth_2, y*2);
			mPosy_2 = stayInScreen(mPosy_2, mScreenHeight, mBallHeight_2, x*2);




			}
		}
	}
	private float stayInScreen(float mPos, float mScreenSize, float mBallSize, double v){
		if(mPos < 0.)
			mPos = (float) 0.;
		if(mPos >  mScreenSize-mBallSize)
			mPos = mScreenSize-mBallSize;
		return mPos;
	}
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		mIsRunning = true;
		new Thread(this).start();
		mScreenWidth = this.getWidth();
		mScreenHeight = this.getHeight();
		mBallWidth = ball.getWidth();
		mBallHeight = ball.getHeight();
		mBallWidth_2 = ball2.getWidth();
		mBallHeight_2 = ball2.getHeight();
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mIsRunning = false;// TODO Auto-generated method stub
		
	}
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSensorChanged(SensorEvent event) {

		x = event.values[SensorManager.DATA_X];
		y = event.values[SensorManager.DATA_Y];
		z = event.values[SensorManager.DATA_Z];
//		m_tvx.setText("0");
//		m_tvy.setText("0");
//		m_tvz.setText("0");
//		mPosx -= (float) x;
//		mPosy += (float) y;
//		if(mPosx < 0.)
//			mPosx = (float) 0.;
//		if(mPosy < 0.)
//			mPosy = (float) 0.;
//		if(mPosx >  mScreenWidth-mBallWidth)
//			mPosx = mScreenWidth-mBallWidth;
//		if(mPosy > mScreenHeight-mBallHeight)
//			mPosy = mScreenHeight-mBallHeight;
/*		double speed_x = (x-bx)/(System.currentTimeMillis()-btime); 
		double speed_y = (y-by)/(System.currentTimeMillis()-btime);
		double speed_z = (z-bz)/(System.currentTimeMillis()-btime);*/
		//bx = //x;
		//by = //y;
		//bz = //z;
		//btime = System.currentTimeMillis();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		mPosx = event.getRawX();
		mPosy = event.getRawY();
		// TODO Auto-generated method stub
		return false;
	}

}
