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
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.herps.R;
import com.herps.data.Observation;
import com.herps.navigation.NavigationBar;

public class DatabaseItemDetails extends Activity {
	
	RelativeLayout myLayout;
	private Observation selectedObservation;
	private TextView commonNameTv;
	private ImageView savedImageView;
	private TextView familyTv;
	private TextView speciesTv,locationTv;
	private TextView dateTv,GPSTv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myLayout = new RelativeLayout(this);
		NavigationBar nb = new NavigationBar(this);
		nb.setLeftBarButton("Back ");
		nb.setBarTitle("Observation");
		nb.setRightBarButton("Share ",false);
		
		nb.setId(0);
		NavigationBar.NavigationBarListener nbl = new NavigationBar.NavigationBarListener() {
		    @Override
		    public void OnNavigationButtonClick(int which) {
		    	switch(which){
		    	case 0:
		    		Intent i = new Intent(getParent(), ObservationListActivity.class);
		        	
		        	
		        	View view = ObservationActivityGroup.group.getLocalActivityManager()
		              .startActivity("ObservationListActivity", i
		              .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
		              .getDecorView();
		        	ObservationActivityGroup.group.replaceView(view);
		    	
		    		
		    	break;
		    	case 1:
		    		Intent i1=new Intent(android.content.Intent.ACTION_SEND);
		    		i1.setType("image/png");
		    		i1.putExtra(android.content.Intent.EXTRA_SUBJECT,"Observation from SFSU Trees and Shrubs App");
		    		i1.putExtra(android.content.Intent.EXTRA_TEXT, "Name: " + selectedObservation.getCommonName()+"\n" +"Latitude: " + selectedObservation.getLatitude()+"\n" + "Longitude: " + selectedObservation.getLongitude() + "\n" + "Timestamp: " + selectedObservation.getTimeStamp() + "\n");
		    		File file = getFileStreamPath(selectedObservation.getImageName());
		    		Uri uri = Uri.fromFile(file);
		    		i1.putExtra(android.content.Intent.EXTRA_STREAM, uri);
		    		
		    		startActivity(Intent.createChooser(i1,"Share via"));
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
		myLayout.addView(LayoutInflater.from(this).inflate(R.layout.databaseitemdetails, null),detailsParam);
		
		setContentView(myLayout);
		
		Bundle b = getIntent().getExtras();
		if(b.getParcelable("com.herps.Observation") != null){
			selectedObservation =	b.getParcelable("com.herps.Observation");
		}	
		
		if(selectedObservation != null){
			View detailsView = myLayout.getChildAt(1);
			commonNameTv = (TextView) detailsView.findViewById(R.id.commonNameTextView);
			commonNameTv.setText(selectedObservation.getCommonName());
			
			//start
			familyTv = (TextView)  detailsView.findViewById(R.id.familyTextView);
			familyTv.setText(selectedObservation.getFamily());
			
			speciesTv = (TextView)  detailsView.findViewById(R.id.speciesTextView);
			speciesTv.setText(selectedObservation.getSpecies());
			
			
			
			//Date date=new Date(); 
			dateTv = (TextView)  detailsView.findViewById(R.id.dateTextView);			
			dateTv.setText(selectedObservation.getTimeStamp());
			
			locationTv = (TextView)  detailsView.findViewById(R.id.locationTextView);
			
			locationTv.setText(selectedObservation.getLatitude()+" " + selectedObservation.getLongitude());
			//end
			
			savedImageView = (ImageView)  detailsView.findViewById(R.id.imageView);
			FileInputStream in;
			try {
				if(selectedObservation.getImageName() == null || selectedObservation.getImageName().equalsIgnoreCase("")){
					in = openFileInput("abcd12345.jpg");
				}
				else{
					in = openFileInput(selectedObservation.getImageName());
				}
				
				savedImageView.setImageBitmap(BitmapFactory.decodeStream(in));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
