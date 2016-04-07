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

package com.herps.observation;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.herps.R;
import com.herps.data.Observation;
import com.herps.database.DatabaseHandler;


public class ObservationAdapter extends ArrayAdapter {

	private Context context;
    private ArrayList<Observation> items;
    private LayoutInflater vi;
   
    
    public ObservationAdapter(Context context,ArrayList<Observation> items) {
        super(context,0, items);
        this.context = context;
        this.items = items;
        vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	View v = convertView;
    	 
        final Observation i = (Observation) items.get(position);
        if (i != null) {
           
                Observation observation = (Observation)i;
                v = vi.inflate(R.layout.observationdetails, null);
                final TextView title = (TextView)v.findViewById(R.id.common_name);

 
                if (title != null)
                    title.setText(observation.getCommonName());
                
                final TextView subTitle = (TextView)v.findViewById(R.id.sub_text);
                if(subTitle != null){
                	subTitle.setText(observation.getTimeStamp());
                }
        }
        
        ImageView deleteBtn = (ImageView) v.findViewById(R.id.deleteBtn);
        
        deleteBtn.setOnClickListener(new OnClickListener() {		
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				// add alert dialog box
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
				alertDialogBuilder.setTitle("Do you want to delete this observation?");
				
				alertDialogBuilder
				
				.setCancelable(false)
				.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, close
						// current activity
						//MainActivity.this.finish();
						DatabaseHandler db = new DatabaseHandler(context);
						db.deleteContact(i);
						items.remove(i);
						notifyDataSetChanged();
					}
				  })
				.setNegativeButton("No",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, just close
						// the dialog box and do nothing
						dialog.cancel();
					}
				});
 
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
 
				// show it
				alertDialog.show();
				//end alert box
				
				
				
				
			}
		});
        
        return v;

        }
    	
    	
    }

