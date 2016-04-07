package com.herps;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.json.JSONObject;

import com.herps.R;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.webkit.WebView;
import android.widget.Toast;

public class ConfigDownloader extends Activity {
	protected String mUrl;
	static File testdirectory;
	String pathway;
	String photosetUrl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config_downloader);
		Intent downloadIntent = getIntent();
		Uri blogUri = downloadIntent.getData();
		mUrl =blogUri.toString();
		Bundle extras= downloadIntent.getExtras();
		photosetUrl = extras.getString("PHOTOSET");
		System.out.println("boom boom boom  photos are"+photosetUrl);
		
		WebView webView = (WebView) findViewById(R.id.webView1);
		webView.loadUrl(mUrl);
		
		URL photoSetURL;
		try {
			photoSetURL= new URL(photosetUrl);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		
		try {
			URL blogFeedURL = new URL(mUrl);
		FileDownload f1 = new FileDownload();
		f1.execute(blogFeedURL);
		
		
		
		Toast toast =Toast.makeText(this,"Category Data downloaded successfully", Toast.LENGTH_LONG);
		toast.setGravity(Gravity.TOP, 10, 10);
		toast.show();
		
		} catch (Exception e) {
			
			Log.e("ConfigDownLoader","Exception caught" +e);
		}
		
		
		
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.config_downloader, menu);
		return true;
	}
	
	
	public void handleDownloadResponse() {
		System.out.println("Files are stored at " + pathway);
		
		final Intent selectionIntent = new Intent(this, ConfigSelection.class);
		selectionIntent.setData(Uri.parse(pathway));
		
		try{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(getString(R.string.photo_alert));
			builder.setMessage(getString(R.string.photo_alert_message));
			
			
			final Intent photosetIntent = new Intent(this,PhotoSet.class);
			photosetIntent.setData(Uri.parse(photosetUrl));
			photosetIntent.putExtra("PATHWAY", pathway);
			
			
			 
			 
			 
			
			
			builder.setNegativeButton(R.string.photo_yes, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					startActivity(photosetIntent);
				}
			});
			
			builder.setPositiveButton(R.string.photo_no, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					startActivity(selectionIntent);
					
				}
			});
			
			
			
			
			AlertDialog dialog = builder.create();
			dialog.show();
		}
		catch(Exception e){
			
			updateDisplayForError();
			startActivity(selectionIntent);
		}
		
		
	}

	
	private void updateDisplayForError() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.error_title));
		builder.setMessage(getString(R.string.error_message));
		builder.setPositiveButton(android.R.string.ok, null);
		AlertDialog dialog = builder.create();
		dialog.show();
	}



	class FileDownload extends AsyncTask<URL, Void, String>{
		String path;
		int responseCode =-1;
		int count;
		URL blogFeedURL;
		java.util.Date d =  new java.util.Date();		
		Timestamp t = new Timestamp(d.getTime());
		//SimpleDateFormat sdf = new SimpleDateFormat("MM_dd_yyyy_h_mm_ss_a");
		//SimpleDateFormat sdf = new SimpleDateFormat("hmmssa_MMdd");
		SimpleDateFormat sdf = new SimpleDateFormat("hhmmssa_MMdd");
		String ts = sdf.format(d);
		
		

		@Override
		protected String doInBackground(URL... params) {
			
			try {
				blogFeedURL =	params[0];
				String list_path =blogFeedURL.toString();
				System.out.println(blogFeedURL.toString());
				HttpURLConnection connection =  (HttpURLConnection) blogFeedURL.openConnection();
				connection.connect();
				responseCode = connection.getResponseCode();
				
				String splitarray[] = list_path.split("/");
				int length =splitarray.length;
				String list_name =splitarray[length-1];
				System.out.println("list_name is "+ list_name);
				
				String splitname[] =list_name.split(".txt");
				length =splitname.length;
				String lname=splitname[0];
				
				
				
				
				
				//String filename= "List_at_"+ts;
				String filename= lname+"_at_"+ts;
				
				if (responseCode == HttpURLConnection.HTTP_OK)
			 	{
			 		InputStream inputstream = connection.getInputStream();
			 		int contentLength = connection.getContentLength();
			 		//testdirectory = new File(getFilesDir(), "CTNS");
			 		
			 		testdirectory = getDir("SfsuTnS", Context.MODE_PRIVATE);
			 		
			 		
			 		if (!testdirectory.exists()){
						testdirectory.mkdir();
			 		}
			 		
			 		
			 		File configuration = new File(testdirectory,filename);
				 
			 		// FileOutputStream fos = new FileOutputStream(testdirectory + "ShrubsTest.txt");
			 		FileOutputStream fos = new FileOutputStream( configuration );
			 		//FileOutputStream fos = openFileOutput(  ,Context.MODE_PRIVATE );
			 		//System.out.println("the value of testdirectory is in async " + testdirectory);
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
			 
			 	else{
			 		Log.i("DownloadConfigAsync", "Code: " + responseCode);
			 	}
			   } catch (MalformedURLException e) {
				   Log.e("ConfigDownLoaderAync","Exception caught" +e);
			   }catch (Exception e) {
				   Log.e("ConfigDownLoaderAsync","Exception caught" +e);
			   }
			return path;
		}
		
		@Override
		protected void onPostExecute(String result){
			pathway = result;
			System.out.println("I am NJ in Post Execute");
			handleDownloadResponse();
		
		}

		

	
		
	}
	
	
	
		
		

		
	
	
	
	


}
