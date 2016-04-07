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
import java.io.IOException;
import java.io.InputStream;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.herps.R;
import com.herps.data.Observation;
import com.herps.database.DatabaseHandler;
import com.herps.navigation.NavigationBar;

public class SpeciesObserve extends Activity {
	
	private HerpDetails selectedSpecies;
	private TextView commonNameTv;
	private TextView groupTv;
	private TextView familyTv;
	private TextView genusTv, epithetTv;
	private TextView dateTv,GPSTv;
	RelativeLayout myLayout;
	private Bitmap bitmap;
	private ImageView capturedImageView;
	private Camera camera;
	private Button button;
	DatabaseHandler db ;
	private String timestamp;
	private String latitude;
	private String longitude;
	final Context context = this;
	Uri configPath;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		db = new DatabaseHandler(this);
		myLayout = new RelativeLayout(this);
		NavigationBar nb = new NavigationBar(this);
		nb.setLeftBarButton("Back ");
		nb.setRightBarButton("Menu",true);
		nb.setBarTitle("Observation");
		Intent viewIntent = getIntent();		
   	 	configPath = viewIntent.getData();
	
		
		nb.setId(0);
		NavigationBar.NavigationBarListener nbl = new NavigationBar.NavigationBarListener() {
		    @Override
		    public void OnNavigationButtonClick(int which) {
		    	switch(which){
		    	case 0:
		    		
		    		Intent intent1 = new Intent(getParent(), SpeciesDetails.class);
		    		intent1.putExtra("com.herps.HerpDetails", selectedSpecies);
		    		intent1.setData(configPath);
		    		View view = TaxActivityGroup.species.getLocalActivityManager()
		            .startActivity("SpeciesDetails", intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();
		        	TaxActivityGroup.species.replaceView(view);
		    		
		    	break;
		    	case 1:
		    		
		    		PackageManager packageManager = context.getPackageManager();
		    		if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
		    			//yes
		    			/*start camera */
			    		 Intent intent = new Intent(getParent(),SpeciesCameraActivity.class);
			    		 intent.putExtra("com.herps.HerpDetails", selectedSpecies);
			    		 intent.setData(configPath);
			    		 View newView = TaxActivityGroup.species.getLocalActivityManager()
			              .startActivity("SpeciesCameraActivity", intent
			              .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
			              .getDecorView();
			    		 TaxActivityGroup.species.replaceView(newView);
		    		}
		    		else{
		    			//no
		    			AlertDialog alertbox = new AlertDialog.Builder(getParent())
			            .setMessage("Sorry! No CAMERA ")
			            .setPositiveButton("OK", new DialogInterface.OnClickListener() {

			                // do something when the button is clicked
			                public void onClick(DialogInterface arg0, int arg1) {

			                 
			                }
			            })
			            .show();
		    		}
		    		  		
		        	
		    		break;
		    		
		    	default:
		    		break;
		    	}
		    }
		
		};
	    nb.setNavigationBarListener(nbl);
		
		RelativeLayout.LayoutParams nbParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		nbParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
		myLayout.addView(nb,nbParams);	
		
		RelativeLayout.LayoutParams detailsParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		detailsParam.addRule(RelativeLayout.ALIGN_TOP,-1);
		myLayout.addView(LayoutInflater.from(this).inflate(R.layout.observedetails, null),detailsParam);
		
		setContentView(myLayout);
		Bundle b = getIntent().getExtras();
		if(b.getParcelable("com.herps.HerpDetails") != null)
		{
		 	selectedSpecies = 	b.getParcelable("com.herps.HerpDetails");
		}	
		
		if(selectedSpecies != null){

			View detailsView = myLayout.getChildAt(1);
			commonNameTv = (TextView) detailsView.findViewById(R.id.commonNameTextView);
			commonNameTv.setText(selectedSpecies.getGenus());
			
			genusTv = (TextView)  detailsView.findViewById(R.id.genusTextView);
			genusTv.setText(selectedSpecies.getCommonName() + " " + selectedSpecies.getEpithet());
			
			familyTv = (TextView)  detailsView.findViewById(R.id.familyTextView);
			familyTv.setText(selectedSpecies.getFamily());
			
			//Date date=new Date(); 
			dateTv = (TextView)  detailsView.findViewById(R.id.dateTextView);
			
			dateTv.setText(android.text.format.DateFormat.format("MM/dd/yy", new java.util.Date())+"," +
					android.text.format.DateFormat.format("hh:mmaa", new java.util.Date()));
			timestamp = DateFormat.format("MM/dd/yy", new java.util.Date())+"," +
			android.text.format.DateFormat.format("hh:mmaa", new java.util.Date());
			
			GPSTv = (TextView)  detailsView.findViewById(R.id.locationTextView);
			
			button = (Button) findViewById(R.id.savebutton);
			
			// add button onclick listerner. save in DB on click
			button.setOnClickListener(new View.OnClickListener() {
		           
	            @Override
	            public void onClick(View v) {
	                // TODO Auto-generated method stub
	               //save to database
	            	if(selectedSpecies.getImageName() == null || selectedSpecies.getImageName().equalsIgnoreCase("")){
		    			db.addObservation(new Observation(selectedSpecies.getGenus(),"abcd1234.jpg",selectedSpecies.getFamily(),latitude,longitude,selectedSpecies.getCommonName()+ " " + selectedSpecies.getEpithet(),timestamp));
		    		}
		    		else{
		    			
		    			db.addObservation(new Observation(selectedSpecies.getGenus(),selectedSpecies.getImageName(),selectedSpecies.getFamily(),latitude,longitude,selectedSpecies.getCommonName()+ " " + selectedSpecies.getEpithet(),timestamp));
		    		}
		    		Toast.makeText(getApplicationContext(), "Your Observation was saved!", Toast.LENGTH_SHORT).show();
	            	
	                
	            }
	        });
			
			// GPS here
			
			LocationManager mlocManager=null;  
	         LocationListener mlocListener;  
	         mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);  
	         mlocListener = new GPS();  
	        mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, mlocListener);  
	  
	        if (mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {  
	            if(GPS.latitude>0)  
	            {  
	            	GPSTv.setText("Latitude:- " + GPS.latitude +"\n" +"Longitude:- " + GPS.longitude +"\n" );
	            	latitude = Double.toString(GPS.latitude);
	            	longitude = Double.toString(GPS.longitude);
	             }  
	             else  
	             {  
	            	 
	            	 Toast.makeText(getApplicationContext(), "Please enable GPS", Toast.LENGTH_SHORT).show();
	              }  
	          } 
	        else {  
	        	  System.out.println("GPS is not turned on...");  
	          }  
		}
	}
	
	 @Override
	  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == 1337 && resultCode == Activity.RESULT_OK)
	      try {
	        // We need to recyle unused bitmaps
	        if (bitmap != null) {
	          bitmap.recycle();
	        }
	        InputStream stream = getContentResolver().openInputStream(data.getData());
	        bitmap = BitmapFactory.decodeStream(stream);
	        stream.close();
	        View detailsView = myLayout.getChildAt(1);
	        capturedImageView = (ImageView)  detailsView.findViewById(R.id.imageView);
	      
	        capturedImageView.setImageBitmap(bitmap);
	      } catch (FileNotFoundException e) {
	        e.printStackTrace();
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
	    super.onActivityResult(requestCode, resultCode, data);
	  }
	
	
}
