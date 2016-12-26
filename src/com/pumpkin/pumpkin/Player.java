package com.pumpkin.pumpkin;

import java.io.File;
import java.util.ArrayList;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class Player extends Activity implements OnClickListener{

	static MediaPlayer mp;
	ArrayList<File> mySongs;
	SeekBar sb;
	int position;
	Button btpv,btfb,btplay,btff,btnxt;
	Uri u;
	Thread upSeekBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player);
		btpv=(Button)findViewById(R.id.btPv);
		btfb=(Button)findViewById(R.id.btFB);
		btplay=(Button)findViewById(R.id.btPlay);
		btff=(Button)findViewById(R.id.btFF);
		btnxt=(Button)findViewById(R.id.btNxt);
		btpv.setOnClickListener(this);
		btfb.setOnClickListener(this);
		btplay.setOnClickListener(this);
		btff.setOnClickListener(this);
		btnxt.setOnClickListener(this);
		if(mp!=null)
		{
			mp.stop();
			mp.release();
		}
		Intent i=getIntent();
		Bundle b = i.getExtras();
		mySongs =(ArrayList) b.getParcelableArrayList("songlist");
		position=b.getInt("pos",0);
		u=Uri.parse(mySongs.get(position).toString());
		
		sb=(SeekBar)findViewById(R.id.seekBar1);
		upSeekBar=new Thread()
		{@Override
			public void run() {
				int totalDuration=mp.getDuration();
				int currentPosition=0;
				while(currentPosition<totalDuration)
				{
					try{
						sleep(1000);
						currentPosition=mp.getCurrentPosition();
						sb.setProgress(currentPosition);
					}
					catch(Exception e)
					{
						
					}
				}
			}
		};
		mp=MediaPlayer.create(getApplicationContext(), u);
		mp.start();
		sb.setMax(mp.getDuration());
		upSeekBar.start();
		sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				mp.seekTo(sb.getProgress());
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.player, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id=v.getId();
		switch(id)
		{
		case R.id.btPlay:
			if(mp.isPlaying())
			{
				mp.pause();
				btplay.setText(">");
			}
			else 
				{mp.start();
				btplay.setText("ll");
				}
		break;
		
		case R.id.btFF:
			mp.seekTo(mp.getCurrentPosition()+5000);
			break;
			
		case R.id.btFB:
			mp.seekTo(mp.getCurrentPosition()-5000);
			break;
			
		case R.id.btNxt:
			mp.stop();
			mp.release();
			position=(position+1)%mySongs.size();
			u=Uri.parse(mySongs.get(position).toString());
			mp=MediaPlayer.create(getApplicationContext(), u);
			mp.start();
			sb.setMax(mp.getDuration());
			break;
			
		case R.id.btPv:
			mp.stop();
			mp.release();
			position=(position-1<0)?  mySongs.size()-1: position-1;
			u=Uri.parse(mySongs.get(position).toString());
			mp=MediaPlayer.create(getApplicationContext(), u);
			mp.start();
			sb.setMax(mp.getDuration());
			break;
			

		}
		
	}

}
