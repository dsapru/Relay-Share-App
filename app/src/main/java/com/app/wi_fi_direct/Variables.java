// References:
// https://sourceforge.net/p/wifi-direct-file-transfer-app/code/ci/feature/change-start-transfer-data/~/tree/
// https://github.com/YaphetS1/WiFi-Direct-File-Transfer-App
// https://developer.android.com/guide/topics/connectivity/wifip2p
// https://developer.android.com/training/connect-devices-wirelessly/nsd-wifi-direct
// http://www.drjukka.com/blog/wordpress/?p=95
// http://www.drjukka.com/blog/wordpress/?p=81
// https://medium.com/@436910463q/sending-files-between-devices-has-never-been-easer-wifi-direct-open-source-8337a54e8497

package com.app.wi_fi_direct;

import android.app.Application;
import android.content.Context;

public class Variables extends Application {
  public static final String APP_TYPE = "com.app.wi_fi_direct";

  private static Variables instance;

  @Override
  public void onCreate() {
    instance = this;
    super.onCreate();
  }

  public static Variables getInstance() {
    return instance;
  }

  public static Context getContext() {
    return instance;
    // or return instance.getApplicationContext();
  }
}
