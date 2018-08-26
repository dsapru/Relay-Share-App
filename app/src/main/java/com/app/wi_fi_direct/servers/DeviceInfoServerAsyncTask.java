// References:
// https://sourceforge.net/p/wifi-direct-file-transfer-app/code/ci/feature/change-start-transfer-data/~/tree/
// https://github.com/YaphetS1/WiFi-Direct-File-Transfer-App
// https://developer.android.com/guide/topics/connectivity/wifip2p
// https://developer.android.com/training/connect-devices-wirelessly/nsd-wifi-direct
// http://www.drjukka.com/blog/wordpress/?p=95
// http://www.drjukka.com/blog/wordpress/?p=81
// https://medium.com/@436910463q/sending-files-between-devices-has-never-been-easer-wifi-direct-open-source-8337a54e8497

package com.app.wi_fi_direct.servers;

import android.os.AsyncTask;
import android.view.View;

import com.app.wi_fi_direct.R;
import com.app.wi_fi_direct.adapters.PeersAdapter;
import com.app.wi_fi_direct.adapters.PeersViewHolder;
import com.app.wi_fi_direct.helpers.callbacks.Callback;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class DeviceInfoServerAsyncTask extends AsyncTask<Void, Void, Void> {

  private ServerSocket serverSocket;
  private Socket client;
  private PeersAdapter peersAdapter;
  private Callback referenceCallback;

  private String ourDeviceName = "";

  public DeviceInfoServerAsyncTask(ServerSocket reference,
                                   PeersAdapter peersAdapterAdapterWeakReference,
                                   Callback callback) {
    this.serverSocket = reference;
    this.peersAdapter = peersAdapterAdapterWeakReference;
    this.referenceCallback = callback;
  }

  private void recieveData() {

    try {
      client = serverSocket.accept();
      if (isCancelled()) return;

      InputStream inputStream = client.getInputStream();
      ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

      ourDeviceName = (String) objectInputStream.readObject();
      inputStream.close();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  protected Void doInBackground(Void... params) {
    recieveData();
    return null;
  }

  @Override
  protected void onPostExecute(Void aVoid) {
    super.onPostExecute(aVoid);
    for (int i = 0; i < this.peersAdapter.peersViewHolders.size(); i++) {
      PeersViewHolder temp = this.peersAdapter.peersViewHolders.get(i);
      if (temp.device.deviceName.equals(ourDeviceName)) {
        temp.statePeer.setImageResource(R.drawable.d_icon_done);
        temp.statePeer.setVisibility(View.VISIBLE);
      }
    }
    try {
      client.close();
      referenceCallback.call();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  @Override
  protected void onCancelled() {
    super.onCancelled();
    try {
      client.close();
      referenceCallback.call();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

