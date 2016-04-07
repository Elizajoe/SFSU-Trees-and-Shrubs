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
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;

public class HerpsCameraActivity extends Activity implements OnClickListener, PictureCallback {

	private CameraSurfaceView cameraSurfaceView;
	private Button shutterButton;
	private HerpDetails selectedOrganism;
	private FileOutputStream fos;
	Uri configPath;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent viewIntent = getIntent();		
   	 	configPath = viewIntent.getData();
		Bundle b = getIntent().getExtras();
		if(b.getParcelable("com.herps.HerpDetails") != null){
		 	selectedOrganism =
	        	b.getParcelable("com.herps.HerpDetails");
		}	
		setContentView(R.layout.activity_camera);

		// set up our preview surface
		FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
		cameraSurfaceView = new CameraSurfaceView(this);
		preview.addView(cameraSurfaceView);

		// grab out shutter button so we can reference it later
		shutterButton = (Button) findViewById(R.id.shutter_button);
		shutterButton.setOnClickListener(this);
	}

	

	@Override
	public void onClick(View v) {
		takePicture();
	}

	private void takePicture() {
		shutterButton.setEnabled(false);
		
		cameraSurfaceView.takePicture(this);
	}

	@Override
	public void onPictureTaken(byte[] data, Camera camera) {
		
		FileOutputStream outStream = null;
		try {

			selectedOrganism.setImageName(selectedOrganism.getCommonName() + System.currentTimeMillis()+".jpg");
			
			if(selectedOrganism.getImageName() == null || selectedOrganism.getImageName().equalsIgnoreCase("")){
				fos = openFileOutput("abcd1234.jpg", getParent().MODE_PRIVATE);
			}
			else{
				 fos = openFileOutput(selectedOrganism.getImageName(), getParent().MODE_WORLD_READABLE);
			}
			fos.write(data);
			fos.close();
			//preview.camera.release();
			Intent intent = new Intent(getParent(),FinalObserveDetails.class);
			intent.putExtra("com.herps.HerpDetails", selectedOrganism);
			intent.setData(configPath);
			
			
    		 View newView = CommonActivityGroup.group.getLocalActivityManager().startActivity("FinalObserveDetails", intent
              .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
              .getDecorView();
    		 
    		 CommonActivityGroup.group.replaceView(newView);
		//	Log.d(TAG, "onPictureTaken - wrote bytes: " + data.length);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("this is the exception dam it" + e);
		} 
		
		
		finally {
		}
	//	camera.startPreview();
		shutterButton.setEnabled(true);
	}
}