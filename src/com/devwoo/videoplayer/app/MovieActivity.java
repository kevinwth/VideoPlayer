package com.devwoo.videoplayer.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.devwoo.videoplayer.R;
import com.devwoo.videoplayer.ui.MoviePlayer;

public class MovieActivity extends Activity{

	private static final String TAG = "MovieActivity";
	
	private MoviePlayer mPlayer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movie_view);
		
		View rootView = findViewById(R.id.movie_root_view);
		
		Intent intent = getIntent();
		
		mPlayer = new MoviePlayer(rootView,this,savedInstanceState,false,intent.getData());
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
	
}