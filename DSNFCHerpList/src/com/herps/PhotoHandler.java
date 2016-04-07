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

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Environment;
import android.widget.Toast;

@SuppressLint("NewApi")
public class PhotoHandler implements PictureCallback {

  private final Context context;

  public PhotoHandler(Context context) {
    this.context = context;
  }

  @Override
  public void onPictureTaken(byte[] data, Camera camera) {

    File pictureFileDir = getDir();

    if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {

     // Log.d(Constants.DEBUG_TAG, "Can't create directory to save image.");
      Toast.makeText(context, "Can't create directory to save image.",
          Toast.LENGTH_LONG).show();
      return;

    }

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
    String date = dateFormat.format(new Date());
    String photoFile = "Picture_" + date + ".jpg";

    String filename = pictureFileDir.getPath() + File.separator + photoFile;

    File pictureFile = new File(filename);

    try {
      FileOutputStream fos = new FileOutputStream(pictureFile);
      fos.write(data);
      fos.close();
      Toast.makeText(context, "New Image saved:" + photoFile,
          Toast.LENGTH_LONG).show();
    } catch (Exception error) {
  //    Log.d(Constants.DEBUG_TAG, "File" + filename + "not saved: "
  //        + error.getMessage());
      Toast.makeText(context, "Image could not be saved.",
          Toast.LENGTH_LONG).show();
    }
  }

  @SuppressLint("NewApi")
private File getDir() {
    File sdDir = Environment
      .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
    return new File(sdDir, "CameraAPIDemo");
  }
} 