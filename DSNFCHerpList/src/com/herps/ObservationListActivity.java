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


import java.util.ArrayList;
import java.util.Collections;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.herps.R;
import com.herps.data.Observation;
import com.herps.data.ObservationComparator;
import com.herps.database.DatabaseHandler;
import com.herps.navigation.NavigationBar;
import com.herps.observation.ObservationAdapter;


public class ObservationListActivity extends ListActivity {
    /** Called when the activity is first created. */
	public static ArrayList<Observation> observationList = new ArrayList<Observation>();
	
	LayoutInflater inflater;
	EditText inputSearch;
	ObservationAdapter adapter;
	RelativeLayout myLayout;
	private String NewText ;

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myLayout = new RelativeLayout(this);
		NavigationBar nb = new NavigationBar(this);
		nb.setBarTitle("Observation List");
		nb.setRightBarButton("Bulk Upload  ",false);
		
		
		
		
		NavigationBar.NavigationBarListener nbl = new NavigationBar.NavigationBarListener() {
		    @Override
		    public void OnNavigationButtonClick(int which) {
		    	switch(which){
//		    	case 0:
//		    		Intent intent1 = new Intent();
//	                intent1.setClass(ObservationListActivity.this, About.class);
//	                startActivity(intent1);
//		    		
//		    	break;
		    	case 1:
		    		String EmailText="Name"+"\t"+"Status"+"\t"+"LifeForm"+"\t"+"TimeStamp"+"\t"+"Latitude"+"\t"+"Longitude";
		    		for(int i= 0;i<observationList.size();i++)
		    		{
		    			NewText = observationList.get(i).getCommonName()+"\t"+ observationList.get(i).getSpecies()+"\t"
		    		    		+"\t"+ observationList.get(i).getTimeStamp()+"\t"+ observationList.get(i).getLatitude()+"\t"+ observationList.get(i).getLongitude();
		    			EmailText =  EmailText+"\n"+"\n"+ NewText+"\n";
		    		}
		    		
		    		 
		    		
		    		
		    		Intent intent=new Intent(android.content.Intent.ACTION_SEND);
		    		intent.setType("plain/text");
		    		intent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Your Observations");
		    		intent.putExtra(android.content.Intent.EXTRA_TEXT, EmailText);
		    		startActivity(Intent.createChooser(intent,"Share via"));
		    		
		    	break;
		    	default:
		    		break;
		    	}
		    }
		
		};
		
		nb.setNavigationBarListener(nbl);

		
		RelativeLayout.LayoutParams herpsListParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		herpsListParam.addRule(RelativeLayout.ALIGN_TOP,RelativeLayout.TRUE);
		myLayout.addView(LayoutInflater.from(this).inflate(R.layout.herpslist, null),herpsListParam);
		
		RelativeLayout.LayoutParams nbParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		nbParams.addRule(RelativeLayout.ABOVE, R.layout.observationlist);
		myLayout.addView(nb,nbParams);	

		setContentView(myLayout);
	
		
        GetObservations getObservations = new GetObservations();        
        getObservations.applicationContext = getParent();
        getObservations.execute("");
       
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
  	
		
    		Observation selectedItem = (Observation)observationList.get(position);  		
    		Intent i = new Intent(this, DatabaseItemDetails.class);        	
        	i.putExtra("com.herps.Observation", selectedItem);
        	View view = ObservationActivityGroup.group.getLocalActivityManager()
              .startActivity("DatabaseItemDetails", i
              .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
              .getDecorView();
        	ObservationActivityGroup.group.replaceView(view);
    		super.onListItemClick(l, v, position, id);
    }
  
    @Override
	public void onBackPressed() {
		
    	
    	
    	
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
	private class GetObservations extends AsyncTask<String, Void, String>{
    	protected Context applicationContext;
    	private ProgressDialog dialog;     	

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			this.dialog.cancel();
			
			 adapter = new ObservationAdapter(applicationContext, observationList);
			
			setListAdapter(adapter);		
			
		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			this.dialog = ProgressDialog.show(applicationContext, "Fetching", "Observation List", true);
		}
		@Override
		protected String doInBackground(String... params) {
			
			
			try { 
					DatabaseHandler db = new DatabaseHandler(applicationContext);
	           		observationList = (ArrayList<Observation>) db.getAllObservations();
	           		Collections.sort(observationList,new ObservationComparator());
	            	          	
	            }
	             
	       catch(Exception e) { 
	            System.err.println("Parse Error: " + e.getMessage()); 
	            System.out.println("catch"); 
	      } 
			return "";
		}
    	
    }
}