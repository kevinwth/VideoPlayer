package com.devwoo.videoplayer.app;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.devwoo.videoplayer.R;
import com.devwoo.videoplayer.data.Video;
import com.devwoo.videoplayer.data.VideoDataAdapter;
import com.devwoo.videoplayer.data.VideoDataAdapter.ViewHolder;
import com.devwoo.videoplayer.utils.Log;

public class VideoActivity extends Activity implements
		LoaderManager.LoaderCallbacks<Cursor>,
		AdapterView.OnItemClickListener{

	private static final String TAG = "VideoActivity";

	private VideoDataAdapter mAdapter;
	private ListView mListView;
	private View mEmptyView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "------------- onCreate ------------");
		setContentView(R.layout.activity_main);
		mListView = (ListView)findViewById(R.id.video_list);
		mEmptyView = findViewById(R.id.empty_view);
		mAdapter = new VideoDataAdapter(this);
		mAdapter.registerDataSetObserver(new VideoDataObserver());
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
		getLoaderManager().initLoader(0, null, this);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}



	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		Log.d(TAG,"----------------------onCreateLoader----------------");
		return new CursorLoader(this,
				MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
				Video.PROJECTION,
				null,
				null,
				null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor c) {
		Log.d(TAG,"-----------onloadfinish----------- "+c.getCount());
		mAdapter.swapCursor(c);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {

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
		Log.d(TAG,"-------------------onResume------------------");
		
		mAdapter.notifyDataSetChanged();
		
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if(view != null){
			Log.d(TAG, "--------- filePath--- "+((ViewHolder)view.getTag()).video.filePath);
			
			Intent intent = new Intent(Intent.ACTION_VIEW).setDataAndType(
					Uri.parse(((ViewHolder)view.getTag()).video.filePath), "video/*");
			intent.setClass(VideoActivity.this, MovieActivity.class);
			startActivity(intent);
		}
	}
	
	private class VideoDataObserver extends DataSetObserver{

		@Override
		public void onChanged() {
			super.onChanged();
			final int count = mAdapter.getCount();
			if(count <= 0){
				mEmptyView.setVisibility(View.VISIBLE);
			}else{
				mEmptyView.setVisibility(View.INVISIBLE);
			}
		}
		
	}
	
}
