package com.pumpkin.pumpkin;

import java.io.File;
import java.util.ArrayList;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	ListView lv;
	String[] items;
	int i=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lv=(ListView)findViewById(R.id.lvPlaylist);
		final ArrayList<File> mySongs=findSongs(Environment.getExternalStorageDirectory());
		items = new String[mySongs.size()];
		for(i=0;i<mySongs.size();i++)
		{
			//Toast.makeText(getApplicationContext(), mySongs.get(i).getName().toString(), Toast.LENGTH_SHORT).show();
			items[i]=mySongs.get(i).getName().toString();
		}
		ArrayAdapter<String> adp= new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,items);
		lv.setAdapter(adp);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),Player.class).putExtra("pos", position).putExtra("songlist", mySongs));
				
			}
		});
	}
	
	public ArrayList<File> findSongs(File root){
		ArrayList<File> al=new ArrayList<File>();
		File[] files=root.listFiles();
		for(File singleFile : files)
		{
			
			if(singleFile.isDirectory() && !singleFile.isHidden())
			{
				al.addAll(findSongs(singleFile));
			}
			else
			{
				if(singleFile.getName().endsWith(".mp3") || singleFile.getName().endsWith(".wav"))
				{
					al.add(singleFile);
				}
			}
		}
		return al;
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
