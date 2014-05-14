package com.devwoo.videoplayer.data;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.devwoo.videoplayer.R;
import com.devwoo.videoplayer.utils.VideoUtils;

public class VideoDataAdapter extends CursorAdapter {

	private static final String TAG = "adapter";

	private Context mContext;
	private static final int[] mBgColor = new int[] { R.color.list_bg_1,
			R.color.list_bg_2, R.color.list_bg_3, R.color.list_bg_4,
			R.color.list_bg_5, R.color.list_bg_6, R.color.list_bg_7,
			R.color.list_bg_8, R.color.list_bg_9, R.color.list_bg_10,
			R.color.list_bg_11 };

	public VideoDataAdapter(Context context) {

		super(context, null, false);
		Log.d(TAG, "-------------------- adapter init----------");
		mContext = context;

	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		final Video video = new Video(cursor);
		final ViewHolder holder = (ViewHolder) view.getTag();
		holder.video = video;
		view.setBackgroundColor(context.getResources().getColor(
				mBgColor[(int) (Math.random() * 10 + 1)]));
		view.getBackground().setAlpha(150);
		Log.d(TAG, "----------random ---- " + (int) (Math.random() * 10 + 1));
		Log.d(TAG, "------------bindview---------- name " + video.displayName);
		if (video.thumbnail != null) {
			holder.thumbnail.setBackground(new BitmapDrawable(context
					.getResources(), video.thumbnail));
		} else {
			// holder.thumbnail.setBackgroundResource(resid);
		}
		holder.name.setText(video.displayName);
		holder.duration.setText(VideoUtils.durationFormat(video.duration));

	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		Log.d(TAG, "--------------- new View ------------");
		final View view = LayoutInflater.from(context).inflate(
				R.layout.video_list_item, parent, false);
		final ViewHolder holder = new ViewHolder();

		holder.thumbnail = (ImageView) view.findViewById(R.id.video_thumbnail);
		holder.name = (TextView) view.findViewById(R.id.video_name);
		holder.duration = (TextView) view.findViewById(R.id.video_duration);

		view.setTag(holder);

		return view;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (!getCursor().moveToPosition(position)) {
			Log.d(TAG, "couldn't move cursor to position " + position);
		}

		View v;
		if (convertView == null) {
			Log.d(TAG, "-------------- get view  1-------------");
			v = newView(mContext, getCursor(), parent);
		} else {
			Log.d(TAG, "-------------- get view  2-------------");
			v = convertView;
		}
		bindView(v, mContext, getCursor());

		return v;
	}

	@Override
	public Object getItem(int position) {

		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void asyncDeleteVideo(final Video video) {
		final AsyncTask<Video, Void, Void> deleteTask = new AsyncTask<Video, Void, Void>() {

			@Override
			protected Void doInBackground(Video... v) {
				Video.deleteVideo(mContext, video.id);
				return null;
			}
		};
		deleteTask.execute(video);
	}

	public void asyncDeleteVideos(final Integer[] videoIds) {
		final AsyncTask<Integer, Void, Void> deleteTask = new AsyncTask<Integer, Void, Void>() {

			@Override
			protected Void doInBackground(Integer... params) {
				for (Integer id : videoIds) {
					Video.deleteVideo(mContext, id);
				}
				return null;
			}
		};
		deleteTask.execute(videoIds);
	}

	public void asyncDeleteVideos(final Video video) {
		final AsyncTask<Video, Void, Void> deleteTask = new AsyncTask<Video, Void, Void>() {

			@Override
			protected Void doInBackground(Video... videos) {
				for (Video v : videos) {
					Video.deleteVideo(mContext, v.id);
				}
				return null;
			}
		};

		deleteTask.execute(video);
	}

	public class ViewHolder {
		ImageView thumbnail;
		TextView name;
		TextView duration;
		public Video video;
	}

}