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

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.herps.R;
import com.herps.navigation.NavigationBar;

public class SpeciesDetails extends Activity {
	
	private HerpDetails selectedOrganism;
	private TextView commonNameTv;
	private TextView groupTv;
	private TextView familyTv;
	private TextView genusTv, epithetTv;
	RelativeLayout myLayout;
	private ImageView imageView;
	private Button button;
	Uri configPath;
	String locationString="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		myLayout = new RelativeLayout(this);
		NavigationBar nb = new NavigationBar(this);
		nb.setLeftBarButton("Scientific Names List");
		nb.setRightBarButton("Observe", false);
		nb.setBarTitle("");
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
		    		
		    		
		    		
		    		Intent intent1 = new Intent(getParent(), Species.class);
		    		intent1.putExtra("com.herps.HerpDetails", selectedOrganism);
		    		intent1.setData(configPath);
		    		View view = TaxActivityGroup.species.getLocalActivityManager()
		              .startActivity("Species", intent1
		              .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
		              .getDecorView();
		        	//startActivity(i);
		    	//	Toast.makeText(this, "You clicked " + selectedItem.getCommonName(), Toast.LENGTH_SHORT).show();
		    		TaxActivityGroup.species.replaceView(view);
		    		
		    	break;
		    	case 1:
		    		//Toast.makeText(getApplicationContext(), "msg msg", Toast.LENGTH_SHORT).show();
		    		Intent intent2 = new Intent(getParent(), SpeciesObserve.class);
		    		intent2.putExtra("com.herps.HerpDetails", selectedOrganism);
		    		intent2.setData(configPath);
		    		View newview = TaxActivityGroup.species.getLocalActivityManager()
		              .startActivity("SpeciesObserve", intent2
		              .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
		              .getDecorView();
		        	//startActivity(i);
		    	//	Toast.makeText(this, "You clicked " + selectedItem.getCommonName(), Toast.LENGTH_SHORT).show();
		    		TaxActivityGroup.species.replaceView(newview);
	              //  intent1.setClass(OrganismDetails.this, ObserveDetails.class);
	             //   startActivity(intent1);
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
		myLayout.addView(LayoutInflater.from(this).inflate(R.layout.organismdetail, null),detailsParam);
		
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
			//commonNameTv.setText(selectedOrganism.getCommonName());
			commonNameTv.setText(selectedOrganism.getGenus());
			//groupTv = (TextView)  detailsView.findViewById(R.id.groupTextView);
			//groupTv.setText(selectedOrganism.getGroup());
			genusTv = (TextView)  detailsView.findViewById(R.id.genusTextView);
			genusTv.setText(selectedOrganism.getCommonName() + " " + selectedOrganism.getEpithet());
			
			//epithetTv = (TextView)  detailsView.findViewById(R.id.epithetTextView);
			//epithetTv.setText(selectedOrganism.getEpithet());
			
			familyTv = (TextView)  detailsView.findViewById(R.id.familyTextView);
			familyTv.setText(selectedOrganism.getFamily());
			
			imageView = (ImageView) detailsView.findViewById(R.id.imageView1);
			String common = selectedOrganism.getFamily();
			
			String splitarray1[] = common.split(" ");
    		String common_name="";
			for (int j=0;j<splitarray1.length;j++)
			{
				common_name=common_name+splitarray1[j];
				
				if(j<splitarray1.length-1)
				{
					common_name=common_name+"_";
				}
				
				
			}
			
			
			String imageName=common_name+".jpeg";
        	
        	imageName = imageName.toLowerCase();
			
        	System.out.println("image name is:" +imageName );
        	
        	
        	//File testdirectory =new File( "/data/data/com.herps/app_SfsuTnS_Photos/"+imageName);
        	
        	//File testdirectory =new File( ExternalMemory.mEx.getExtMemPath()+imageName);
        	BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;

            String root = Environment.getExternalStorageDirectory().toString() +"/SfsuTnS_Photos/";
        	//File testdirectory =new File("storage/emulated/0/SfsuTnS_Photos/" +imageName);
            File testdirectory =new File(root +imageName);
        	//imageView.setImageResource(this.getResources().getIdentifier(imageName,"drawable",this.getPackageName()));
        	
        	if((testdirectory.exists()) )
        	{
        		//imageView.setImageURI(Uri.parse(testdirectory+"/"+imageName));
        		//imageView.setImageDrawable(Drawable.createFromPath("/data/data/com.herps/app_SfsuTnS_Photos/"+imageName));
        		
        		//imageView.setImageDrawable(Drawable.createFromPath(testdirectory.toString()));
        		
        		Bitmap myBitmap = BitmapFactory.decodeFile(testdirectory.getAbsolutePath(),options);
       		 	int h = 100; // height in pixels
       		    int w = 100; // width in pixels
       		    Bitmap photoBitMap = Bitmap.createScaledBitmap(myBitmap,h, w, true);
       		    imageView.setImageBitmap(photoBitMap);
        	}
        	
        	else{
        		//testdirectory =new File("storage/sdcard/SfsuTnS_Photos/" +imageName);
        		
        		
        		if (testdirectory.exists())
				{
					//String imageName = selectedOrganism.getCommonName() + "_" + selectedOrganism.getGroup();
					//imageView.setImageDrawable(Drawable.createFromPath("/data/data/com.herps/app_SfsuTnS_Photos/"+imageName));
					
					//imageView.setImageDrawable(Drawable.createFromPath(testdirectory.toString()));
					
					Bitmap myBitmap = BitmapFactory.decodeFile(testdirectory.getAbsolutePath(),options);
	       		 	int h = 100; // height in pixels
	       		    int w = 100; // width in pixels
	       		    Bitmap photoBitMap = Bitmap.createScaledBitmap(myBitmap,h, w, true);
	       		    imageView.setImageBitmap(photoBitMap);
					
					
					
					System.out.println("Hello Im home"+imageName);
					
					//imageView.setImageResource(this.getResources().getIdentifier(imageName,"drawable",this.getPackageName()));
					//imageView.setImageResource(this.getResources().getIdentifier(imageName,"drawable",this.getPackageName()));
					
				}
        		
        		
        		
        		
        	}
        	
        	
        	
        	button = (Button) findViewById(R.id.button1);
        	button.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					try{
					System.out.println("find button clicked");
					String coordinate = selectedOrganism.getCoordinate();
					
					String splitarray[]= coordinate.split(",");
					int length = splitarray.length;
					
					
					
					int i=0;
					String locString="";
					if(length==2)
					{
						
						
						locString=splitarray[0]+","+splitarray[1];
						
						System.out.println("location is "+locString);
							
							String uri = "http://maps.google.com/maps/api/directions/json?&daddr="+locString;
				            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
				            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
				            startActivity(intent);
							
							
							
							
					}
					else
					{
						
						String startString="";
						  String endString="";
						  
							startString+= splitarray[0];
							startString+=",";
							startString+=splitarray[1];
							endString+=splitarray[2];
							endString+=",";
							endString+=splitarray[3];
							
							final String loc1=startString;
							final String loc2 =endString;
							
							AlertDialog.Builder builder = new AlertDialog.Builder(getParent());
						 	builder.setTitle(R.string.locations);
							builder.setMessage(R.string.coordinates);
							
							builder.setPositiveButton(R.string.loc1, new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									
									locationString =loc1;
									System.out.println(locationString);
									
									String uri = "http://maps.google.com/maps/api/directions/json?&daddr="+locationString;
						            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
						            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
						            startActivity(intent);
									
									
									
								}
							});
						    
						    builder.setNegativeButton(R.string.loc2, new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									
									locationString =loc2;
									System.out.println(locationString);
									String uri = "http://maps.google.com/maps/api/directions/json?&daddr="+locationString;
						            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
						            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
						            startActivity(intent);
									
									
									
								}
							});
					   	 	
						    
						  
							    AlertDialog dialog =builder.create();
							
							    System.out.println("after creating builder");
							    
							    
							    
							    dialog.show();
							
							
						
						
						
						
						
					}
					}
					catch(Exception e){
						System.out.println("in exception");
						 e.printStackTrace();
					}
					
					
					/*Intent mapIntent = new Intent(v.getContext(),Gmap.class);
					//mapIntent.setData(Uri.parse(blogUrl));
					startActivity(mapIntent);*/
				}
			});
        	
        	
        	
			
		}
		
		
		//setContentView(R.layout.organismdetail);
		
	
	}

	
	
}
