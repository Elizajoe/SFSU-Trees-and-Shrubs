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

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.herps.data.ListItem;
import com.herps.data.SectionItem;


public class HerpAdapter extends ArrayAdapter {

	private Context context;
    private ArrayList<ListItem> items;
    private LayoutInflater vi;
    
    
    
    public HerpAdapter(Context context,ArrayList<ListItem> items) {
        super(context,0, items);
        this.context = context;
        this.items = items;
        vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    
     
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	
    	
    	View v = convertView;
    	 
        final ListItem i = (ListItem) items.get(position);
        if (i != null) {
            if(i.isSection()){
                SectionItem si = (SectionItem)i;
                v = vi.inflate(R.layout.section, null);
 
                v.setOnClickListener(null);
                v.setOnLongClickListener(null);
                v.setLongClickable(false);
 
                final TextView sectionView = (TextView) v.findViewById(R.id.list_item_section_text);
                sectionView.setText(si.getId());
            }else{
                HerpDetails orgDetails = (HerpDetails)i;
                v = vi.inflate(R.layout.herpsdetails, null);
                final TextView title = (TextView)v.findViewById(R.id.org_text);
               // final TextView subtitle = (TextView)v.findViewById(R.id.list_item_entry_summary);
 
                if (title != null)
                    title.setText(orgDetails.getCommonName());
                final ImageView imageView = (ImageView)v.findViewById(R.id.imageView1);
                if(imageView != null){
                	try{
                	//String imageName = orgDetails.getGenus() + "_" + orgDetails.getEpithet();
                	//String imageName = orgDetails.getCommonName();
                	String common = orgDetails.getCommonName();
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
                	//File testdirectory =new File( "storage/sdcard/SfsuTnS_Photos/"+imageName);
                	//File testdirectory = "/data/data/com.herps/app_SfsuTnS_Photos";
                	
                	BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 1;
                    
                    String root = Environment.getExternalStorageDirectory().toString() +"/SfsuTnS_Photos/";
                	//File testdirectory =new File("storage/emulated/0/SfsuTnS_Photos/" +imageName);
                    System.out.println("Images are stored at "+ root);
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
                		//testdirectory =new File("storage/sdcard/SfsuTnS_Photos/" +imageName);
                		if (testdirectory.exists())
        				{
        					//String imageName = selectedOrganism.getCommonName() + "_" + selectedOrganism.getGroup();
        					//imageView.setImageDrawable(Drawable.createFromPath("/data/data/com.herps/app_SfsuTnS_Photos/"+imageName));
        					
        					//imageView.setImageDrawable(Drawable.createFromPath(testdirectory.toString()));
        				//	System.out.println("Hello Im home"+imageName);
        					
        					//imageView.setImageResource(this.getResources().getIdentifier(imageName,"drawable",this.getPackageName()));
        					//imageView.setImageResource(this.getResources().getIdentifier(imageName,"drawable",this.getPackageName()));
        					Bitmap myBitmap = BitmapFactory.decodeFile(testdirectory.getAbsolutePath(),options);
                   		 	int h = 100; // height in pixels
                   		    int w = 100; // width in pixels
                   		    Bitmap photoBitMap = Bitmap.createScaledBitmap(myBitmap,h, w, true);
                   		    imageView.setImageBitmap(photoBitMap);
        					
        					
        					
        				}
                	}
                	}catch (Exception e){
                		
                		System.out.println("Error loading images " +e);
                		
                	}
                }
//                if(subtitle != null)
//                    subtitle.setText(ei.subtitle);
            }
        }
        return v;
    	
    	
    	
    }
}
