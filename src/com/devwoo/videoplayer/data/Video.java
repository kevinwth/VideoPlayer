package com.devwoo.videoplayer.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Video.Thumbnails;
import android.provider.MediaStore.Video.VideoColumns;

import com.devwoo.videoplayer.utils.Log;

public class Video {

	private static final String TAG = "video";
	private static final int INVALID_ID = -1;
	
	private static final int INDEX_ID = 0;
	private static final int INDEX_MIMETYPE = 1;
	private static final int INDEX_DATA = 2;
	private static final int INDEX_TITLE = 3;
	private static final int INDEX_DISPLAY_NAME = 4;
	private static final int INDEX_DATE_MODIFY = 5;
	private static final int INDEX_DURATION = 6;
	private static final int INDEX_SIZE = 7;

	public static final String[] PROJECTION = new String[] { VideoColumns._ID,
			VideoColumns.MIME_TYPE, VideoColumns.DATA, VideoColumns.TITLE,
			VideoColumns.DISPLAY_NAME, VideoColumns.DATE_MODIFIED,
			VideoColumns.DURATION, VideoColumns.SIZE };
	public static final String DEFAULT_ORDER = VideoColumns.DATE_MODIFIED
			+ " DESC ";
	
	public int id;
	public String mimeType;
	public String filePath;
	public String caption;
	public String displayName;
	public long dateModify;
	public long duration;
	public long fileSize;
	public Bitmap thumbnail;

	
	public Video(Cursor c ){
		Log.d(TAG,"------------------video init "+c.getCount());
		loadFromCursor(c);
	}


	public void loadFromCursor(Cursor cursor) {
		Log.d(TAG, "----------------- load from cursor------ count "+cursor.getCount());
		id = cursor.getInt(INDEX_ID);
		mimeType = cursor.getString(INDEX_MIMETYPE);
		filePath = cursor.getString(INDEX_DATA);
		caption = cursor.getString(INDEX_TITLE);
		displayName = cursor.getString(INDEX_DISPLAY_NAME);
		dateModify = cursor.getLong(INDEX_DATE_MODIFY);
		duration = cursor.getLong(INDEX_DURATION) ;
		fileSize = cursor.getLong(INDEX_SIZE);
		Log.d(TAG, "----------------- load from cursor------ filePath "+filePath);
		thumbnail = ThumbnailUtils.createVideoThumbnail(filePath, Thumbnails.MINI_KIND);
	}

	public static void deleteVideo(Context context,int videoId){
		if(videoId == INVALID_ID) return ;
		ContentResolver cr = context.getContentResolver();
		Uri uri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, videoId);
		
		cr.delete(uri, "", null);
		
	}
	
	public Uri getContentUri() {
		Uri baseUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
		return baseUri.buildUpon().appendPath(String.valueOf(id)).build();
	}

	public Uri getPlayUri() {
		return getContentUri();
	}

	public String getFilePath() {
		return filePath;
	}
}