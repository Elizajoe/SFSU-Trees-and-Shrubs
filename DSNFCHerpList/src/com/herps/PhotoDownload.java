package com.herps;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.herps.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class PhotoDownload extends Activity {
	String pathway;
	String[] photoURLS;
	protected ProgressBar mProgressBar;
	static File testdirectory;
	File configuration;
	// button to show progress dialog
	Button btnShowProgress;
	String path;
	int i;
	int count;
	String TAG ="PhotoDownload";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_download);
	
	Intent photodownloadIntent = getIntent();
	
	Bundle extras= photodownloadIntent.getExtras();
	pathway = extras.getString("PATHWAY");
	photoURLS =extras.getStringArray("PHOTOURL");
	mProgressBar = (ProgressBar)findViewById(R.id.progressBar1);
	mProgressBar.setVisibility(View.VISIBLE);
	
	System.out.println(pathway);
	System.out.println(photoURLS[0]);
	System.out.println("inside photodownload here");
	
	boolean mExternalStorageAvailable = false;
	boolean mExternalStorageWriteable = false;
	String state = Environment.getExternalStorageState();
	
	
	System.out.println("status of externalstorage is" +state);
	
			if (Environment.MEDIA_MOUNTED.equals(state)) {
		        // Can read and write the media
		        mExternalStorageAvailable = mExternalStorageWriteable = true;
		        String root = Environment.getExternalStorageDirectory().toString();
		        
		        
		        
		        System.out.println("root is "+root);
		        
		      //  System.out.println("root1 is "+root);
		        
		        //String root1 ="storage/extSdCard";
		        
		        
		        
		        testdirectory = new File(root + "/SfsuTnS_Photos");    
		        //testdirectory = new File("storage/sdcard/SfsuTnS_Photos/");    
		        // starting new Async Task
				count =photoURLS.length;
		        	
				System.out.println("the path of external storage is "+ testdirectory.getAbsolutePath());
			
				for(int j=0;j<photoURLS.length;j++){
					i=j;
					DownloadFileFromURL task = new DownloadFileFromURL();
		       	 task.execute(photoURLS[j]);
		       	
		       	
				}
				
				
				
				System.out.println("************************Inside PhotoDownload");
		        
		        
		        
		        
				} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
		        // Can only read the media
		        mExternalStorageAvailable = true;
		        mExternalStorageWriteable = false;
		        
		        AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle(getString(R.string.ext_storage_title));
				builder.setMessage(getString(R.string.ext_storage_message));
				System.out.println("cannot download");
				handlePhotoDownloadResponse();
		        
		        
				} else {
		        // Can't read or write
		        mExternalStorageAvailable = mExternalStorageWriteable = false;
		        AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle(getString(R.string.ext_storage_title));
				builder.setMessage(getString(R.string.ext_storage_message));
				System.out.println("cannot download");
				
				handlePhotoDownloadResponse();
		        
				}
		   
		Log.i(TAG,"External Media: readable=" + mExternalStorageAvailable
		            + " writable=" + mExternalStorageWriteable);
		
		
	
	
	
		
        
		
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.photo_download, menu);
		return true;
	}
	
	
	private class DownloadFileFromURL extends AsyncTask<String, Void, String>{
		private Activity context;
		int noOfURLs;
		String blogFeedURL;
		int responseCode =-1;
		
		/*public DownloadFileFromURL(Activity context) {
            this.context = context;
        }*/
				

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            
        }

		

		@Override
		protected String doInBackground(String... params) {
			noOfURLs = params.length;
			 int count;
			
			
			//for (int i =0;i<noOfURLs;i++) {
				
			 blogFeedURL =	params[0];
				String url =blogFeedURL.toString();
				
				System.out.println("downloading link :" +url);
                
                String splitarray[] = url.split("/");
                int length =splitarray.length;
                String filename =splitarray[length-1];
                System.out.println("The photo name is "+ filename);
                //testdirectory = getDir("SfsuTnS_Photos", Context.MODE_PRIVATE);
                //testdirectory = getExternalFilesDir("SfsuTnS_Photos");
                
                
               
                
                try {
					URL link = new URL(url);
					 HttpURLConnection connection = (HttpURLConnection) link.openConnection();
					 connection.connect();
					 
					 responseCode = connection.getResponseCode();
					// this will be useful so that you can show a typical 0-100% progress bar
					 
					 if (responseCode == HttpURLConnection.HTTP_OK)
					 {
						 InputStream inputstream = connection.getInputStream();
						 int contentLength = connection.getContentLength();
						 
						 
						 
						 
					 
						// testdirectory = getDir("SfsuTnS_Photos", Context.MODE_PRIVATE);
						 //testdirectory = getExternalFilesDir("SfsuTnS_Photos");
						 
						 if (!testdirectory.exists()){
								testdirectory.mkdir();
					 		}
						 
						 File configuration = new File(testdirectory,filename);
						 FileOutputStream fos = new FileOutputStream( configuration );
						 
						 
						 System.out.println("Path : " +  testdirectory.getAbsolutePath());
				 		 path = testdirectory.getAbsolutePath();
				 		 
				 		 
				 		byte data[] = new byte[1024];
				 		long total = 0;
				 		int progress = 0;
				 		while ((count = inputstream.read(data)) != -1){
				 			total += count;
				 			int progress_temp = (int) total * 100 / contentLength;
				 			fos.write(data, 0, count);
				 		}
				 		inputstream.close();
					 	fos.close();
					 	System.out.println("File DownLoaded");
					 }
						 
						
					
				} catch (MalformedURLException e1) {					
					 Log.e("PhotoDownload","Exception caught" +e1);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e){
					System.out.println("In here photodownload erro dum dum" +e);
				}
                
          //	}
                return path;  
		}
		
		
		protected void onPostExecute(String result){
			//pathway = result;
			System.out.println("I am NJ in photodownload  Post Execute");
			System.out.println("value of i is" +i);
			if (i==count-1)
			{
				
				handlePhotoDownloadResponse();
			}
			
			
		}
	
		
		
	}
	
	
	private void handlePhotoDownloadResponse() {
		
		Intent selectionIntent = new Intent(this, ConfigSelection.class);
		selectionIntent.setData(Uri.parse(pathway));
		mProgressBar.setVisibility(View.INVISIBLE);
		startActivity(selectionIntent);
	}


		
}	
		
		
	
	
	


