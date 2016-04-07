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
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.herps.R;
import com.herps.data.ListItem;


public class SpeciesAdapter extends ArrayAdapter {

	private Context context;
    private ArrayList<HerpDetails> items;
    private LayoutInflater vi;
    
    public SpeciesAdapter(Context context,ArrayList<HerpDetails> items) {
        super(context,0, items);
        this.context = context;
        this.items = items;
      //get the LayoutInflater for inflating the customomView
        vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	View v = convertView;
    	 
        final ListItem i = (ListItem) items.get(position);
        if (i != null) {

                HerpDetails orgDetails = (HerpDetails)i;
                //inflate the custom layout
                v = vi.inflate(R.layout.speciesdetails, null);
                
                //set the data to be displayed
                final TextView title = (TextView)v.findViewById(R.id.textView01);
                if (title != null)
                	
                	title.setText(orgDetails.getGroup());
                   // title.setText(orgDetails.getGenus()+ " " +orgDetails.getEpithet());
                	//title.setText(orgDetails.getGenus());
                final ImageView imageView = (ImageView)v.findViewById(R.id.imageView01);
                if(imageView != null){
                	//String imageName = orgDetails.getGenus() + "_" + orgDetails.getEpithet();
                	
                	String common = orgDetails.getFamily();
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
                	//imageView.setImageResource(context.getResources().getIdentifier(imageName,"drawable",context.getPackageName()));
                	
                	//File testdirectory =new File( "/data/data/com.herps/app_SfsuTnS_Photos/"+imageName);
                	BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 4;
                	
                	//File testdirectory =new File("storage/emulated/0/SfsuTnS_Photos/" +imageName);
                    String root = Environment.getExternalStorageDirectory().toString() +"/SfsuTnS_Photos/";
                    File testdirectory =new File(root +imageName);
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
                		testdirectory =new File("storage/sdcard/SfsuTnS_Photos/" +imageName);
                		if (testdirectory.exists())
        				{
        					//String imageName = selectedOrganism.getCommonName() + "_" + selectedOrganism.getGroup();
        					//imageView.setImageDrawable(Drawable.createFromPath("/data/data/com.herps/app_SfsuTnS_Photos/"+imageName));
        					
        					//imageView.setImageDrawable(Drawable.createFromPath(testdirectory.toString()));
        					//System.out.println("Hello Im home"+imageName);
                			Bitmap myBitmap = BitmapFactory.decodeFile(testdirectory.getAbsolutePath(),options);
                    		int h = 100; // height in pixels
                   		    int w = 100; // width in pixels
                   		    Bitmap photoBitMap = Bitmap.createScaledBitmap(myBitmap,h, w, true);
                   		    imageView.setImageBitmap(photoBitMap);
                			
        					
        					//imageView.setImageResource(this.getResources().getIdentifier(imageName,"drawable",this.getPackageName()));
        					//imageView.setImageResource(this.getResources().getIdentifier(imageName,"drawable",this.getPackageName()));
        					
        				}
                	}
                	
                }

        }
      //return the view to be displayed
        return v;
    	
    	
    	
    }
}
