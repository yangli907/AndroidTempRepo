package com.yangli907.takePic;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.speech.RecognizerIntent;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.Toast;

public class TakePicActivity extends Activity {
	private static final int REQUEST_CODE = 123;
	private static final int REQUERY_CODE_GALLERY = 321;
	private int zoom = 0;
	/** Called when the activity is first created. */
	private Camera camera;
	private Preview preview;
	private Vibrator myVib;
	private PopupWindow pw;
	private ImageButton speechButton;
	private int height;
	private int width;
	//private PopupWindow help;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		height = displaymetrics.heightPixels;
		width = displaymetrics.widthPixels;

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		preview = new Preview(this);
		myVib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
		speechButton = (ImageButton) findViewById(R.id.micBtn);
		((FrameLayout) findViewById(R.id.preview)).addView(preview);
	}

	public void startVoice(View view) {
		myVib.vibrate(20);
		startTalking();
	}

	public void takePicture(View view) {
		myVib.vibrate(20);
		takePicture();
	}

	public void takePicture() {
		preview.camera.autoFocus(myAutoFocusCallback);
		preview.camera.takePicture(shutterCallback, rawCallback, jpegCallback);
		sendBroadcast(new Intent(
				Intent.ACTION_MEDIA_MOUNTED,
				Uri.parse("file://" + Environment.getExternalStorageDirectory())
				));
	}

	ShutterCallback shutterCallback = new ShutterCallback() {
		public void onShutter() {
		}
	};

	public void zoomIn() {
		Camera.Parameters params = preview.camera.getParameters();
		int maxZoom = params.getMaxZoom();
		if (zoom < maxZoom) {
			params.setZoom(++zoom);
		}
		preview.camera.setParameters(params);
	}

	public void zoomIn(View view) {
		myVib.vibrate(20);
		zoomIn();
	}

	public void zoomOut() {
		myVib.vibrate(20);
		Camera.Parameters params = preview.camera.getParameters();
		if (zoom > 0) {
			params.setZoom(--zoom);
		}
		preview.camera.setParameters(params);
	}

	public void zoomOut(View view) {
		zoomOut();
	}

	public void switchFlash(View v) {
		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		pw = new PopupWindow(inflater.inflate(R.layout.flash, null, true),
				findViewById(R.id.flash).getWidth() * 2, findViewById(
						R.id.flash).getHeight(), true);
		pw.showAtLocation(findViewById(R.id.flash), Gravity.RIGHT, speechButton.getRight(),speechButton.getBottom()/2-2*speechButton.getHeight());
		pw.setTouchInterceptor(new View.OnTouchListener() {
	        @Override
	        public boolean onTouch(View v, MotionEvent event) {
	            // TODO Auto-generated method stub
	            if (event.getAction() == MotionEvent.ACTION_DOWN) {
	                pw.dismiss();
	            }
	            return true;
	        }
	    });

	}

	public void openFlash(View v) {
		Camera.Parameters params = preview.camera.getParameters();
		params.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
		preview.camera.setParameters(params);
		ImageButton fb = (ImageButton) findViewById(R.id.flash);
		fb.setImageResource(R.drawable.lighton);
		pw.dismiss();
		Toast.makeText(this, "flash: on", Toast.LENGTH_SHORT).show();
		// findViewById(R.layout.flash).setVisibility(0);
	}

	public void closeFlash(View v) {
		Camera.Parameters params = preview.camera.getParameters();
		params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
		preview.camera.setParameters(params);
		ImageButton fb = (ImageButton) findViewById(R.id.flash);
		fb.setImageResource(R.drawable.lightoff);
		pw.dismiss();
		Toast.makeText(this, "flash: off", Toast.LENGTH_SHORT).show();
		// findViewById(R.layout.flash).setVisibility(0);
	}

	public void openGallery(View view) throws InterruptedException {
		/*
		 * preview.camera.wait(); Intent intentBrowseFiles = new
		 * Intent(Intent.ACTION_GET_CONTENT);
		 * intentBrowseFiles.setType("image/*");
		 * intentBrowseFiles.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		 * startActivity(intentBrowseFiles);
		 */
		/*
		 * LayoutInflater inflater = (LayoutInflater) this
		 * .getSystemService(Context.LAYOUT_INFLATER_SERVICE); pw = new
		 * PopupWindow(inflater.inflate(R.layout.gallery, null, false),
		 * findViewById(R.id.flash).getWidth() * 2, findViewById(
		 * R.id.flash).getHeight(), true);
		 * pw.showAtLocation(findViewById(R.id.flash), Gravity.RIGHT, 65, 95);
		 */
		
		Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
		photoPickerIntent.setType("image/*");
		startActivityForResult(photoPickerIntent, REQUERY_CODE_GALLERY);
		//startActivity(photoPickerIntent);
	}

	public void openHelp(View view){
		/*LayoutInflater inflater = (LayoutInflater) this
		.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		help = new PopupWindow(inflater.inflate(R.layout.help, null, false),
		640, 480, true);
		help.showAtLocation(findViewById(R.id.help), Gravity.CENTER, 0, 0);*/
		 new AlertDialog.Builder(this)
		  .setTitle(R.string.shutterDesc).setMessage(R.string.shutterHelp)
		  .setNegativeButton(R.string.closeHelp,
		   new DialogInterface.OnClickListener() {
		    
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		     // TODO Auto-generated method stub
		     
		    }
		   }
		    ).setPositiveButton(R.string.email, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
					String[] recipients = new String[]{"yangli907@163.com", "",};
					emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, recipients);
					emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Android App Feedback");
					emailIntent.setType("text/plain");
					startActivity(Intent.createChooser(emailIntent, "Send mail..."));
					finish();
				}
			})
		  .show();
	}
	/** Handles data for raw picture */
	PictureCallback rawCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
		}
	};

	/** Handles data for jpeg picture */
	PictureCallback jpegCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			FileOutputStream outStream = null;
			try {
				// write to local sandbox file system
				// outStream =
				// CameraDemo.this.openFileOutput(String.format("%d.jpg",
				// System.currentTimeMillis()), 0);
				// Or write to sdcard
				outStream = new FileOutputStream(String.format( 
						"/sdcard/dcim/Camera/%d.jpg",
						System.currentTimeMillis()));
				outStream.write(data);
				outStream.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				camera.startPreview();
			}
		}
	};

	public void onTap(View view) {
		startTalking();
	}

	public void startTalking() {
		PackageManager pm = getPackageManager();
		List<ResolveInfo> activities = pm.queryIntentActivities(
		        new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
		if (activities.size() == 0) 
		{
			new AlertDialog.Builder(this)
			  .setTitle(R.string.speechWarning).setMessage(R.string.speechNotFound)
			  .setNegativeButton(R.string.closeHelp,
			   new DialogInterface.OnClickListener() {
			    @Override
			    public void onClick(DialogInterface dialog, int which) {
			    }
			   }
			    ).setPositiveButton(R.string.installSpeech, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent browserIntent = new Intent(Intent.ACTION_VIEW,   Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.voicesearch"));
						startActivity(browserIntent);
					}
				})
			  .show();
		} 
		else
		{
			Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
			intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
					RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
			intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
					"Take Picture: 'take a picture','cheese';\n Zoom In: 'bigger'; Zoom out: 'smaller'");
			startActivityForResult(intent, REQUEST_CODE);
		}
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
			ArrayList<String> matches = data
					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			if (matches.contains("shoot") || matches.contains("take a picture")
					|| matches.contains("cheese"))
				takePicture();
			if (matches.contains("bigger")) {
				zoomIn();
				Toast.makeText(this, "Zoom In", Toast.LENGTH_SHORT).show();
			}
			if (matches.contains("smaller")) {
				zoomOut();
				Toast.makeText(this, "Zoom Out", Toast.LENGTH_SHORT).show();
			}
		}
		
		if (requestCode == REQUERY_CODE_GALLERY && resultCode == RESULT_OK)
		{
			Intent intent = new Intent(Intent.ACTION_VIEW);
			Uri selectedImageUri = data.getData();
			intent.setData(selectedImageUri);
			startActivity(intent);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	 AutoFocusCallback myAutoFocusCallback = new AutoFocusCallback(){

		  @Override
		  public void onAutoFocus(boolean arg0, Camera arg1) {
		   // TODO Auto-generated method stub
		  }};
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

	    if(event.getAction() == KeyEvent.ACTION_DOWN)
	    {
	        switch(keyCode)
	        {
	        case KeyEvent.KEYCODE_CAMERA:
	           takePicture();
	           break;
	        case KeyEvent.KEYCODE_BACK:
	        	if(pw!=null)
	        	{
	        		if(pw.isShowing()){
		        		pw.dismiss();
		        		break;
		        	}
	        	}
	        	
	        }
	    }

	    return super.onKeyDown(keyCode, event);
	}
	
}