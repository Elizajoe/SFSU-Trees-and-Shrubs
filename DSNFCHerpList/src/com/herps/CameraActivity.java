/*  Copyright (C) 2013   Divya Muthyala & Meenal Nitin Honap.

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>. */

package com.herps;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.herps.R;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;

public class CameraActivity extends Activity {
	private static final String TAG = "CameraDemo";
	Camera camera;
	Preview preview;
	Button buttonClick;
	private HerpDetails selectedOrganism;
	private FileOutputStream fos;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.camera);
		
		Bundle b = getIntent().getExtras();
		if(b.getParcelable("com.herps.HerpDetails") != null){
		 	selectedOrganism =
	        	b.getParcelable("com.herps.HerpDetails");
		}	

		preview = new Preview(this);
		((FrameLayout) findViewById(R.id.preview)).addView(preview);

		buttonClick = (Button) findViewById(R.id.buttonClick);
		buttonClick.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				System.gc();
				preview.camera.takePicture(shutterCallback, rawCallback,
						jpegCallback);
			}
		});

		Log.d(TAG, "onCreate'd");
	}

	ShutterCallback shutterCallback = new ShutterCallback() {
		public void onShutter() {
			Log.d(TAG, "onShutter'd");
		}
	};

	/** Handles data for raw picture */
	PictureCallback rawCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			Log.d(TAG, "onPictureTaken - raw");
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
//				outStream = new FileOutputStream(String.format(
//						"abcd12345.jpg", System.currentTimeMillis()));
//				outStream.write(data);
//				outStream.close();
				selectedOrganism.setImageName(selectedOrganism.getCommonName() + System.currentTimeMillis()+".jpg");
				if(selectedOrganism.getImageName() == null || selectedOrganism.getImageName().equalsIgnoreCase("")){
					fos = openFileOutput("abcd1234.jpg", getParent().MODE_PRIVATE);
				}
				else{
					 fos = openFileOutput(selectedOrganism.getImageName(), getParent().MODE_PRIVATE);
				}
				fos.write(data);
				fos.close();
				//preview.camera.release();
				Intent intent = new Intent(getParent(),FinalObserveDetails.class);
				intent.putExtra("com.herps.HerpDetails", selectedOrganism);
	    		 View newView = CommonActivityGroup.group.getLocalActivityManager()
	              .startActivity("FinalObserveDetails", intent
	              .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
	              .getDecorView();
	    		 CommonActivityGroup.group.replaceView(newView);
			//	Log.d(TAG, "onPictureTaken - wrote bytes: " + data.length);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
			}
			Log.d(TAG, "onPictureTaken - jpeg");
		}
	};

}