package com.zaidi.cs480.spring.app.tutortabby;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by MAGarcia on 6/5/2016.
 */
public class MultiDexApplication extends Application {
  public MultiDexApplication() {

  }

  @Override
  protected  void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    MultiDex.install(this);
  }
}
