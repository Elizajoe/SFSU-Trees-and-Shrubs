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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.herps.R;
import com.herps.data.ListItem;
import com.herps.navigation.NavigationBar;
//import android.annotation.SuppressLint;

public class ObserverAppV2 extends ListActivity {
    /** Called when the activity is first created. */
	public static ArrayList<ListItem> organismList = new ArrayList<ListItem>();
	
	Uri configPath;
	String path ;
	LayoutInflater inflater;
	//EditText inputSearch;
	HerpAdapter adapter;
	RelativeLayout myLayout;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
       
        Intent viewIntent = getIntent();		
   	 	configPath = viewIntent.getData(); 
        
        System.out.println("inside observerapp2");
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
  //      requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myLayout = new RelativeLayout(this);
		NavigationBar nb = new NavigationBar(this);
		nb.setLeftBarButton("About ");
		//nb.setRightBarButton("",false);
		nb.setBarTitle("SFSU Trees and Shrubs");
		
		
		NavigationBar.NavigationBarListener nbl = new NavigationBar.NavigationBarListener() {
		    @Override
		    public void OnNavigationButtonClick(int which) {
		    	switch(which){
		    	case 0:
		    		
		    		Intent intent = new Intent();
		    		intent.setData(configPath);
	                intent.setClass(ObserverAppV2.this, About.class);
	                startActivity(intent);
		    		
		    	break;
		    	default:
		    		break;
		    	}
		    }
		
		};
		
		nb.setNavigationBarListener(nbl);

		
		RelativeLayout.LayoutParams herpsListParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		//herpsListParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE);
		herpsListParam.addRule(RelativeLayout.ALIGN_TOP,RelativeLayout.TRUE);
		myLayout.addView(LayoutInflater.from(this).inflate(R.layout.herpslist, null),herpsListParam);
		
		RelativeLayout.LayoutParams nbParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		nbParams.addRule(RelativeLayout.ABOVE, R.layout.herpslist);
		myLayout.addView(nb,nbParams);	
        
      //  setContentView(R.layout.herpslist);
		setContentView(myLayout);
               GetReptileList getReptile = new GetReptileList();
        getReptile.applicationContext = getParent();
        getReptile.execute("");
       // getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
      //  inputSearch = (EditText) findViewById(R.id.inputSearch);
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	
    	if(!organismList.get(position).isSection()){
    		
    		System.out.println("im in select listitem");
    		
    		HerpDetails selectedItem = (HerpDetails)organismList.get(position);
    		
    	//	Intent i = new Intent(getApplicationContext(),OrganismDetails.class);
        //	i.setClassName("com.herps", "com.herps.OrganismDetails");
    		
    		   Intent i = new Intent(this, OrganismDetails.class);
        	//i.putExtra("com.herps.HerpDetails",selectedItem);
    		
        	i.putExtra("com.herps.HerpDetails", selectedItem);
        	i.setData(configPath);
        	View view = CommonActivityGroup.group.getLocalActivityManager().startActivity("OrganismDetails", i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
              .getDecorView();
        	//startActivity(i);
    	//	Toast.makeText(this, "You clicked " + selectedItem.getCommonName(), Toast.LENGTH_SHORT).show();
        	CommonActivityGroup.group.replaceView(view);
    		
    	}
    	
    	super.onListItemClick(l, v, position, id);
    }
    
    
    
    
   
    @Override
	public void onBackPressed() {
    	System.out.println("i pressed back");
    	
    	/*Intent selectionintent = new Intent(this, ConfigSelection.class);
    	selectionintent.setData(Uri.parse("/data/data/com.herps/app_SfsuTnS"));*/
    	
    	AlertDialog alertbox = new AlertDialog.Builder(getParent())
        .setMessage("Go to View Lists Menu?")
        .setNegativeButton("Yes", new DialogInterface.OnClickListener() {

            // do something when the button is clicked
            public void onClick(DialogInterface arg0, int arg1) {

                finish();
                //close();


            }
        })
        .setPositiveButton("No", new DialogInterface.OnClickListener() {

        // do something when the button is clicked
        public void onClick(DialogInterface arg0, int arg1) {
        	
        	//add code to continue activity
                       }
    })
        .show();
    	
	}

   

    
	@SuppressLint("NewApi")
	private class GetReptileList extends AsyncTask<String, Void, String>{
    	protected Context applicationContext;
    	private ProgressDialog dialog;
    	
    	

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			this.dialog.cancel();
			//populate view
			adapter = new HerpAdapter(applicationContext, organismList);
			
			setListAdapter(adapter);
		}



		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			this.dialog = ProgressDialog.show(applicationContext, "Fetching", "Plant Common Names", true);
		}



		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			
			//String dataFileName = "C:/Local Disk D_8920122020/SFSU Computer Science/CSC 899 Masters Project/Meenal/SNFCFlora_sample_new.txt";
			organismList = new ArrayList<ListItem>();
			String strRead;
			
			try { 
	            //BufferedReader br = new BufferedReader( new FileReader(dataFileName) );
				//ContextWrapper context = null;
				
				
				//AssetManager am = applicationContext.getAssets();
					
				//InputStream input = am.open("SNFC_Herps_Tab_Delimited.txt");
				
				System.out.println("inside doinBackground");
				path = configPath.toString();
				System.out.println("the value of path is :" + path);
				File fileIn  = new File(path);
				FileInputStream input= new FileInputStream(fileIn);
				
				
				//InputStream input = am.open("ShrubsTest.txt");
				
				InputStreamReader inputStreamReader = new InputStreamReader(input);
				BufferedReader br = new BufferedReader( new BufferedReader(inputStreamReader) );
	            String initialGroup = "";
	            while ((strRead=br.readLine())!=null){
	            	String splitarray[] = strRead.split("\t");
	            	  /*  if(!initialGroup.equalsIgnoreCase(splitarray[2])){
	            	    	
	            	    	SectionItem sectItem = new SectionItem();
	        				sectItem.setId(splitarray[2]);
	        				organismList.add(sectItem);
	        				initialGroup = splitarray[2];
	            	    }*/
	            	//for(int i=0;i<splitarray.length;i++){
	            		HerpDetails orgdetails = new HerpDetails();
	            		orgdetails.setGroup(splitarray[0]);
	            		orgdetails.setFamily(splitarray[2]);
	            		orgdetails.setCommonName(splitarray[1]);
	            		orgdetails.setGenus(splitarray[3]);
	            		orgdetails.setEpithet(splitarray[4]);
	            		orgdetails.setCoordinate(splitarray[6]);
	            		
	            		String common = orgdetails.getCommonName();
	            		
	            		String splitarray1[] = common.split(" ");
	            		String common_name="";
	    				for (int i=0;i<splitarray1.length;i++)
	    				{
	    					 common_name=common_name+splitarray1[i];
	    					
	    					if(i<splitarray1.length-1)
	    					{
	    						common_name=common_name+"_";
	    					}
	    					
	    					
	    				}
	    				
	    				
	    				
	            		
	            		String imageName=common_name+".jpeg";
	            		orgdetails.setImageName(imageName);
	            		
	            		System.out.println("image name in ObserverApp2 is" + orgdetails.getImageName());
	            		
	            		System.out.println(orgdetails.getCoordinate());
	            		
	            		
	            		//orgdetails.setAlternate(splitarray[3]);
	            		//orgdetails.setGenus(splitarray[4]);
	            		//orgdetails.setFamily(splitarray[5]);
	            		
	            		
	            		
	            		
	            		
	            		/*if(splitarray.length == 6){
	            			orgdetails.setAlternate(splitarray[5]);
	            		}*/
	            	//	orgdetails.setColor(splitarray[6]);
	            		
	            		organismList.add(orgdetails);
	            	          	
	            }
	             
	      } catch(Exception e) { 
	            System.err.println("Parse Error: " + e.getMessage()); 
	            System.out.println("catch"); 
	      } 
			return "";
		}
    	
    }
}