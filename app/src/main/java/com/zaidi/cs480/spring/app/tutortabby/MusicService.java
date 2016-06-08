package com.zaidi.cs480.spring.app.tutortabby;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by MAGarcia on 6/7/2016.
 */
public class MusicService extends Service implements MediaPlayer.OnPreparedListener {
  private static final String ACTION_PLAY = "com.example.action.PLAY";
  MediaPlayer mMediaPlayer = null;

  public int onStartCommand(Intent intent, int flags, int startId) {
    super.onStartCommand(intent, flags, startId);
    if (intent.getAction().equals(ACTION_PLAY)) {
      try {
        mMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.worlds);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.prepareAsync();
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }

    return 0;
  }

  public void requestPause() {
    mMediaPlayer.pause();
  }

  public void requestResume() {
    mMediaPlayer.reset();
  }

  public void requestStop() {
    mMediaPlayer.stop();
  }

  @Override
  public void onPrepared(MediaPlayer mp) {
    mp.start();
  }

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override
  public void onDestroy() {
    mMediaPlayer.release();
  }
}
