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

import java.io.IOException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class Preview extends SurfaceView implements SurfaceHolder.Callback { // <1>
  private static final String TAG = "Preview";

  SurfaceHolder mHolder;  // <2>
  public Camera camera; // <3>

  Preview(Context context) {
    super(context);

    // Install a SurfaceHolder.Callback so we get notified when the
    // underlying surface is created and destroyed.
    mHolder = getHolder();  // <4>
    mHolder.addCallback(this);  // <5>
    mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS); // <6>
  }

  // Called once the holder is ready
  @SuppressLint("NewApi")
public void surfaceCreated(SurfaceHolder holder) {  // <7>
    // The Surface has been created, acquire the camera and tell it where
    // to draw.
    camera = Camera.open(); // <8>
    camera.setDisplayOrientation(90);
    try {
      camera.setPreviewDisplay(holder);  // <9>

      camera.setPreviewCallback(new PreviewCallback() { // <10>
        // Called for each frame previewed
        public void onPreviewFrame(byte[] data, Camera camera) {  // <11>
          Log.d(TAG, "onPreviewFrame called at: " + System.currentTimeMillis());
       //   Preview.this.invalidate();  // <12>
        }
      });
    } catch (IOException e) { // <13>
      e.printStackTrace();
    }
    camera.startPreview();
  }

  // Called when the holder is destroyed
  public void surfaceDestroyed(SurfaceHolder holder) {  // <14>
    camera.stopPreview();
    //camera = null;
    camera.release();
  }

  // Called when holder has changed
  public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) { // <15>
	  
	  camera.startPreview();
	//  camera.setDisplayOrientation(Surface.ROTATION_90);
  }

}