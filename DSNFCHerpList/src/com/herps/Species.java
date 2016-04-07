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
import java.util.Collections;

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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.herps.R;
import com.herps.data.SpeciesComparator;

public class Species extends ListActivity {
    /** Called when the activity is first created. */
	// the ArrayList that will hold the data to be displayed in the ListView 
	public static ArrayList<HerpDetails> speciesList = new ArrayList<HerpDetails>();
	ArrayList<HerpDetails>searchResults;
	
	Uri configPath;
	String path ;
	
	
	
	EditText editText;
	LayoutInflater inflater;
	SpeciesAdapter adapter;
	RelativeLayout myLayout;


	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        
        Intent viewIntent = getIntent();		
   	 	configPath = viewIntent.getData();
        
        
       
       setContentView(R.layout.search);		
        GetReptileList getReptile = new GetReptileList();
        getReptile.applicationContext = getParent();
        getReptile.execute("");       
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	
            		
    			HerpDetails selectedItem = (HerpDetails)searchResults.get(position); 
    		    Intent i = new Intent(this, SpeciesDetails.class);
             	i.putExtra("com.herps.HerpDetails", selectedItem);
             	i.setData(configPath);
             	View view = TaxActivityGroup.species.getLocalActivityManager()
               .startActivity("SpeciesDetails", i
               .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
               .getDecorView();

        	TaxActivityGroup.species.replaceView(view);
    	
    	
    	super.onListItemClick(l, v, position, id);
    	}
        
    
   
    @Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
    	
    	
    	
    	AlertDialog alertbox = new AlertDialog.Builder(getParent())
        .setMessage("Go to View Lists Menu?")
        .setNegativeButton("Yes", new DialogInterface.OnClickListener() {

            // do something when the button is clicked
            public void onClick(DialogInterface arg0, int arg1) {

                finish();
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
			
			System.out.println("in post execute");
			 this.dialog.cancel();
			 
			 final EditText searchBox=(EditText) findViewById(R.id.searchBox);		 
		     final ListView speciesListView=(ListView) findViewById(android.R.id.list);
		     
		     speciesListView.setFocusableInTouchMode(true);
		     speciesListView.requestFocus();
		     
			 searchResults=new ArrayList<HerpDetails>(speciesList);
			 final SpeciesAdapter adapter = new SpeciesAdapter(applicationContext, searchResults);
			 speciesListView.setAdapter(adapter);
			 
			 speciesListView.setTextFilterEnabled(true);
			 searchBox.addTextChangedListener(new TextWatcher() {
			 @Override
     			public void onTextChanged(CharSequence s, int start, int before, int count) {
			   //get the text in the EditText
			   String searchString=searchBox.getText().toString();
			   int textLength=searchString.length();
			   searchResults.clear();  //clear the initial data set
			 
			   for(int i=0;i<speciesList.size();i++)
			   {
			  String speciesName=speciesList.get(i).getGenus();
			  System.out.println(speciesName);
			 if(textLength<=speciesName.length()){
			  //compare the String in EditText with Names in the ArrayList
			    if(searchString.equalsIgnoreCase(speciesName.substring(0, textLength)))
				  searchResults.add(speciesList.get(i));
			  }
			   }
			 
			   adapter.notifyDataSetChanged();
			   
			
			 }
			 @Override
			 public void beforeTextChanged(CharSequence s, int start, int count,
			     int after) {
			 
			 
			   }
			 @Override
			   public void afterTextChanged(Editable s) {
			 
			 
			   }
			  });
			 }
		
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			this.dialog = ProgressDialog.show(applicationContext, "Fetching", "Taxonomy List", true);
		}

		@SuppressWarnings("unchecked")
		@Override
		protected String doInBackground(String... params)
		{
			// TODO Auto-generated method stub		
			
			speciesList = new ArrayList<HerpDetails>();
			
			String strRead;
			
			try { 
				
				//AssetManager am = applicationContext.getAssets();
				//InputStream input = am.open("SNFC_Herps_Tab_Delimited.txt");
				
				System.out.println("inside doinBackground");
				path = configPath.toString();
				System.out.println("the value of path is :" + path);
				File fileIn  = new File(path);
				FileInputStream input= new FileInputStream(fileIn);
				
				
				
				InputStreamReader inputStreamReader = new InputStreamReader(input);
				BufferedReader br = new BufferedReader( new BufferedReader(inputStreamReader) );
	          
	            while ((strRead=br.readLine())!=null)
	            {
	            		String splitarray[] = strRead.split("\t");
	            		HerpDetails orgdetails = new HerpDetails();
	            		
	            		orgdetails.setGroup(splitarray[0]);
	            		orgdetails.setFamily(splitarray[1]);
	            		orgdetails.setCommonName(splitarray[2]);	
	            		orgdetails.setGenus(splitarray[0]);
	            		orgdetails.setEpithet(splitarray[3]);
	            		orgdetails.setCoordinate(splitarray[6]);
	            		
	            		String common = orgdetails.getFamily();
	            		
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
	            		
	            		System.out.println("image name in Species is" + orgdetails.getImageName());
	            		
	            		
	            		
	            		System.out.println(orgdetails.getCoordinate());
	            		
	            		
	            		System.out.println(orgdetails.getGenus());
	            	
	            		
	            			            		
	            		if(splitarray.length == 6)
	            		{
	            			orgdetails.setAlternate(splitarray[5]);
	            		}

	            		
	            		speciesList.add(orgdetails);
	            		
	            		Collections.sort(speciesList,new SpeciesComparator());
	            			
	            }
	             
	      } catch(Exception e) { 
	            System.err.println("Parse Error: " + e.getMessage()); 
	            System.out.println("catch"); 
	      } 
			return "";
			
		}
    	
	}
}