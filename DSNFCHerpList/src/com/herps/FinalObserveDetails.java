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

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

public class FinalObserveDetails extends Activity {
	
	private HerpDetails selectedOrganism;
	private TextView commonNameTv;
	private TextView groupTv;
	private TextView familyTv;
	private TextView genusTv, epithetTv;
	private TextView dateTv, GPSTv;
	private RelativeLayout myLayout;
	private Bitmap bitmap;
	private ImageView capturedImageView;
	private Camera camera;
	DatabaseHandler db ;
	
	private String latitude;
	private String longitude;
	private String species;
	private String timestamp;
	private View button;
	Uri configPath;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		db = new DatabaseHandler(this);
		myLayout = new RelativeLayout(this);
		NavigationBar nb = new NavigationBar(this);
		nb.setLeftBarButton("Back");
		//nb.setRightBarButton("Save Observation", false);
		nb.setBarTitle("Observation");
		
		Intent viewIntent = getIntent();		
   	 	configPath = viewIntent.getData();
   	 	
		nb.setId(0);
		NavigationBar.NavigationBarListener nbl = new NavigationBar.NavigationBarListener() {
		    @Override
		    public void OnNavigationButtonClick(int which) {
		    	switch(which){
		    	case 0:
		    		
		    		/*Intent intent = new Intent();
	                intent.setClass(ObserveDetails.this, OrganismDetails.class);
	                startActivity(intent);*/ 
		    		Intent intent1 = new Intent(getParent(), OrganismDetails.class);
		    		intent1.putExtra("com.herps.HerpDetails", selectedOrganism);
		    		intent1.setData(configPath);
		    		View view = CommonActivityGroup.group.getLocalActivityManager()
		              .startActivity("OrganismDetails", intent1
		              .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
		              .getDecorView();
		        	//startActivity(i);
		    	//	Toast.makeText(this, "You clicked " + selectedItem.getCommonName(), Toast.LENGTH_SHORT).show();
		        	CommonActivityGroup.group.replaceView(view);
		    		
		    	break;
		    	case 1:
		    		if(selectedOrganism.getImageName() == null || selectedOrganism.getImageName().equalsIgnoreCase("")){
		    			db.addObservation(new Observation(selectedOrganism.getCommonName(),"abcd1234.jpg",selectedOrganism.getFamily(),latitude,longitude,selectedOrganism.getGenus()+ " " + selectedOrganism.getEpithet(),timestamp));
		    		}
		    		else{
		    			
		    			db.addObservation(new Observation(selectedOrganism.getCommonName(),selectedOrganism.getImageName(),selectedOrganism.getFamily(),latitude,longitude,selectedOrganism.getGenus()+ " " + selectedOrganism.getEpithet(),timestamp));
		    		}
		    		Toast.makeText(getApplicationContext(), "Your Observation was saved!", Toast.LENGTH_SHORT).show();
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
		myLayout.addView(LayoutInflater.from(this).inflate(R.layout.finalobservedetails, null),detailsParam);
		
		setContentView(myLayout);
		Bundle b = getIntent().getExtras();
		if(b.getParcelable("com.herps.HerpDetails") != null){
		 	selectedOrganism =
	        	b.getParcelable("com.herps.HerpDetails");
		}	
		
		if(selectedOrganism != null){
//			commonNameTv = (TextView) findViewById(R.id.commonNameTextView);
//			commonNameTv.setText(selectedOrganism.getCommonName());
//			groupTv = (TextView) findViewById(R.id.groupTextView);
//			groupTv.setText(selectedOrganism.getGroup());
			View detailsView = myLayout.getChildAt(1);
			commonNameTv = (TextView) detailsView.findViewById(R.id.commonNameTextView);
			commonNameTv.setText(selectedOrganism.getCommonName());
			//groupTv = (TextView)  detailsView.findViewById(R.id.groupTextView);
			//groupTv.setText(selectedOrganism.getGroup());
			genusTv = (TextView)  detailsView.findViewById(R.id.genusTextView);
			genusTv.setText(selectedOrganism.getGenus() + " " + selectedOrganism.getEpithet());
			
			//epithetTv = (TextView)  detailsView.findViewById(R.id.epithetTextView);
			//epithetTv.setText(selectedOrganism.getEpithet());
			
			familyTv = (TextView)  detailsView.findViewById(R.id.familyTextView);
			familyTv.setText(selectedOrganism.getFamily());
			
			//Date date=new Date(); 
			dateTv = (TextView)  detailsView.findViewById(R.id.dateTextView);
			
			dateTv.setText(android.text.format.DateFormat.format("MM/dd/yy", new java.util.Date())+"," +
					android.text.format.DateFormat.format("hh:mmaa", new java.util.Date()));
			timestamp = DateFormat.format("MM/dd/yy", new java.util.Date())+"," +
			android.text.format.DateFormat.format("hh:mmaa", new java.util.Date());
			
			//gps start
			GPSTv = (TextView)  detailsView.findViewById(R.id.locationTextView);
			
			//add GPS here
			
			LocationManager mlocManager=null;  
	         LocationListener mlocListener;  
	         mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);  
	         mlocListener = new GPS();  
	        mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, mlocListener);  
	  
	        if (mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {  
	            if(GPS.latitude>0)  
	            {  
	            	//GPSTv.setText("Latitude:- " + GPS.latitude +"Longitude:- " + GPS.longitude );
	            	latitude = Double.toString(GPS.latitude);
	            	longitude = Double.toString(GPS.longitude);
	            
	            	
	            	
	             }  
	             else  
	             {  
	            	 
	            	 Toast.makeText(getApplicationContext(), "Enable GPS", Toast.LENGTH_SHORT).show();
//	                  alert.setTitle("Wait");  
//	                  alert.setMessage("GPS in progress, please wait.");  
//	                  alert.setPositiveButton("OK", null);  
//	                  alert.show();  
	              }  
	          } else {  
	        	  System.out.println("GPS is not turned on...");  
	          }  
			//gps end
	        
	     // add button onclick listerner. save in DB on click
	        button = (Button) findViewById(R.id.savebutton);
			button.setOnClickListener(new View.OnClickListener() {
		           
	            @Override
	            public void onClick(View v) {
	            	System.gc();
	                // TODO Auto-generated method stub
	               //save to database
	            	if(selectedOrganism.getImageName() == null || selectedOrganism.getImageName().equalsIgnoreCase("")){
		    			db.addObservation(new Observation(selectedOrganism.getCommonName(),"abcd1234.jpg",selectedOrganism.getFamily(),latitude,longitude,selectedOrganism.getGenus()+ " " + selectedOrganism.getEpithet(),timestamp));
		    		}
		    		else{
		    			
		    			db.addObservation(new Observation(selectedOrganism.getCommonName(),selectedOrganism.getImageName(),selectedOrganism.getFamily(),latitude,longitude,selectedOrganism.getGenus()+ " " + selectedOrganism.getEpithet(),timestamp));
		    		}
		    		Toast.makeText(getApplicationContext(), "Your Observation was saved!", Toast.LENGTH_SHORT).show();
	            	
	                
	            }
	        });
			
			capturedImageView = (ImageView)  detailsView.findViewById(R.id.imageView);
//			File file = getParent().getFileStreamPath("abcd12345.jpg");
//			if(file.exists()){
				FileInputStream in;
				try {
					if(selectedOrganism.getImageName() == null || selectedOrganism.getImageName().equalsIgnoreCase("")){
						in = openFileInput("abcd12345.jpg");
					}
					else{
						in = openFileInput(selectedOrganism.getImageName());
					}
					
					//capturedImageView.setImageBitmap(BitmapFactory.decodeStream(in));
					
					
					capturedImageView.setImageBitmap(BitmapFactory.decodeStream(in));
					
					
					
				} catch (FileNotFoundException e) {
					
					e.printStackTrace();
				}catch(Exception e)
				{
					e.printStackTrace();
					System.out.println("the problem is in FinalObserveDetails");
				}
				
				
				
		//	}
	      //  capturedImageView.setImageBitmap(bitmap);
		
		}
		
		
		//setContentView(R.layout.organismdetail);
		
	
	}
	
//	private File createImageFile() throws IOException {
//	    // Create an image file name
//	    String timeStamp = 
//	        new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//	    String imageFileName = selectedOrganism.getCommonName().replaceAll("\\s", "")+ "_" + timeStamp ;
//	    File image = File.createTempFile(
//	        imageFileName, 
//	        "jpeg", 
//	        getAlbumDir()
//	    );
//	    
//	    mCurrentPhotoPath = image.getAbsolutePath();
//	    return image;
//	}

	
//	 @Override
//	  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//	    if (requestCode == 1337 && resultCode == Activity.RESULT_OK)
//	      try {
//	        // We need to recyle unused bitmaps
//	        if (bitmap != null) {
//	          bitmap.recycle();
//	        }
//	        InputStream stream = getContentResolver().openInputStream(data.getData());
//	        bitmap = BitmapFactory.decodeStream(stream);
//	        stream.close();
//	        View detailsView = myLayout.getChildAt(1);
//	        capturedImageView = (ImageView)  detailsView.findViewById(R.id.imageView);
//	      
//	        capturedImageView.setImageBitmap(bitmap);
//	      } catch (FileNotFoundException e) {
//	        e.printStackTrace();
//	      } catch (IOException e) {
//	        e.printStackTrace();
//	      }
//	    super.onActivityResult(requestCode, resultCode, data);
//	  }
	
	
}
