package com.herps;

import java.io.File;

import com.herps.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ConfigSelection extends Activity {
	String path;
	private ListView showConfigs;
	ActionMode mActionMode;
	String configName;
	String[] configList;
	ArrayAdapter<String> adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config_selection);
		

		
		Intent selectionIntent = getIntent();
		Uri configPath = selectionIntent.getData();
		
		
		
		 path = configPath.toString();
		 Log.d("Files", "Path: " + path);
			File f = new File(path);
			File file[] = f.listFiles();
			Log.d("Files", "Size: "+ file.length);
			
			
			
			
			
		 configList =new String[file.length];
			
			for (int i=0; i < file.length; i++)
			{
			    Log.d("Files", "FileName:" + file[i].getName());
			    configList[i] = file[i].getName();		
			    
			    
			}
			
			showConfigs = (ListView) findViewById(R.id.listView1);
			 registerForContextMenu(showConfigs);
			
			 adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,configList);
			showConfigs.setAdapter(adapter);
			
			
			
			 
			
			
			showConfigs.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View v,int position, long id) {
					
					configName = path+"/"+configList[position];
					System.out.println(" hey heyy Selecting config name at :" + configName);
					
					
					//Intent selectionIntent = new Intent(v.getContext(),Trees.class);
					Intent selectionIntent = new Intent(v.getContext(),TnsTabList.class);
					selectionIntent.setData(Uri.parse(configName));
					startActivity(selectionIntent);
					
					System.out.println("this is my selection now what??");
					
					
					
					
				}
			});
			
			
		
			
			
			
			
			
			
			
			
			
		
		
	}
	
	
	
	private void deleteAll() {
	
		File testdirectory = getDir("SfsuTnS", Context.MODE_PRIVATE);
		path = testdirectory.getAbsolutePath();
		System.out.println(path);
		if (testdirectory.exists()){
			//testdirectory.delete();
			if (testdirectory.isDirectory()) {
		        String[] children = testdirectory.list();
		        for (int i = 0; i < children.length; i++) {
		            new File(testdirectory, children[i]).delete();
		        }
		    }
			
			
 		}
		File testdirectory1 = getDir("SfsuTnS_Photos", Context.MODE_PRIVATE);
		path =testdirectory1.getAbsolutePath();
		if (testdirectory1.exists()){
			//testdirectory.delete();
			if (testdirectory1.isDirectory()) {
		        String[] children = testdirectory1.list();
		        for (int i = 0; i < children.length; i++) {
		            new File(testdirectory1, children[i]).delete();
		        }
		    }
			
			
 		}
		
		
		
		onResume();
		
		
	}



	@Override
	protected void onResume() {
		super.onResume();
		 this.onCreate(null);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	                                ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.config_selection, menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.config_selection, menu);
		return true;
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	    AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	    switch (item.getItemId()) {
	        
	        case R.id.action_delete:
	            deleteConfig(info.id);
	            return true;
	        default:
	            return super.onContextItemSelected(item);
	    }
	}
	


	private void deleteConfig(long id) {
		
		configName = path+"/"+configList[(int) id];
		File f = new File(configName);
		 f.delete();
		 onResume();
		 
		
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId =item.getItemId();
		
		if(itemId == R.id.action_delete){
			deleteAll();
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	
	@Override
    public void onBackPressed() {
		Intent intent = new Intent(this, MainActivity.class);
		
		startActivity(intent);
        return;
    }


	
	
	

}