package com.devwoo.videoplayer.ui;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;

import com.devwoo.videoplayer.R;
import com.devwoo.videoplayer.app.MovieActivity;
import com.devwoo.videoplayer.utils.Log;

public class MoviePlayer implements MediaPlayer.OnErrorListener,
		MediaPlayer.OnCompletionListener, MediaPlayer.OnInfoListener,
		MediaPlayer.OnVideoSizeChangedListener {

	private static final String TAG = "MoviePlayer";
	private static final int BLACK_TIMEOUT = 500;
	private static final String KEY_VIDEO_POSITION = "video-position";

	private Uri mUri;
	private Context mContext;
	private MovieActivity mActivity;
	private View mRootView;
	private VideoView mVideoView;
	private int mVideoPosition = 0;
	private boolean mHasPaused = false;

	public MoviePlayer(View rootView, MovieActivity activity,
			Bundle savedInstance, boolean canReplay, Uri videoUri) {
		Log.d(TAG, "---------------MoviePlayer----------------- videoUri "
				+ videoUri);
		mContext = activity.getApplicationContext();
		mActivity = activity;
		mUri = videoUri;
		mRootView = rootView;
		mVideoView = (VideoView)rootView.findViewById(R.id.video_view);
		mVideoView.setOnErrorListener(this);
		mVideoView.setOnCompletionListener(this);
		mVideoView.setVideoURI(mUri);

		mVideoView.setOnPreparedListener(new OnPreparedListener() {

			@Override
			public void onPrepared(MediaPlayer mp) {

			}

		});

		mVideoView.postDelayed(new Runnable() {

			@Override
			public void run() {
				mVideoView.setVisibility(View.VISIBLE);
			}

		}, BLACK_TIMEOUT);

		if (savedInstance != null) {
			mVideoPosition = savedInstance.getInt(KEY_VIDEO_POSITION, 0);

			mVideoView.start();
			mVideoView.suspend();
			mHasPaused = true;

		} else {
			startVideo();
		}
	}

	private void startVideo() {
		Log.d(TAG, "--------startVideo------------");
		mVideoView.start();
	}

	private void playVideo() {
		Log.d(TAG, "--------playVideo------------");
		mVideoView.start();
	}

	private void pauseVideo() {
		Log.d(TAG, "--------pauseVideo------------");
		mVideoView.pause();
	}

	public void onResume() {
		Log.d(TAG, "--------onResume------------ haspaused " + mHasPaused
				+ " pos " + mVideoPosition);
		if (mHasPaused) {
			mVideoView.seekTo(mVideoPosition);
			mVideoView.resume();
		}
	}

	public void onPause() {
		Log.d(TAG, "--------onPause------------");
		mHasPaused = true;
		mVideoPosition = mVideoView.getCurrentPosition();
		mVideoView.suspend();
	}

	public void onDestory() {
		Log.d(TAG, "--------onDestory------------");
		mVideoView.stopPlayback();
	}

	@Override
	public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
		return false;
	}

	@Override
	public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {

	}

	@Override
	public boolean onInfo(MediaPlayer mp, int what, int extra) {
		return false;
	}

	@Override
	public void onCompletion(MediaPlayer mp) {

	}
}