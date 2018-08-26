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
import android.util.Log;

import com.app.wi_fi_direct.services.MyBroadcastReciever;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TransferNameDevice extends AsyncTask<Void, Void, Void> {
  private InetAddress serverAddress;


  public TransferNameDevice(InetAddress serverAddress) {
    this.serverAddress = serverAddress;
  }

  private void sendData() {

    Socket socket = new Socket();

    try {
      socket.bind(null);
      Log.d("Client Address", socket.getLocalSocketAddress().toString());

      socket.connect(new InetSocketAddress(serverAddress, 8887));
      Log.d("Client", "Client Connected 8887");

      OutputStream outputStream = socket.getOutputStream();
      ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

      objectOutputStream.writeObject(MyBroadcastReciever.thisDeviceName);
      objectOutputStream.close();
      socket.close();

    } catch (Exception e) {
      Log.d("Data Transfer", e.toString());
      e.printStackTrace();
    } finally {
      if (socket.isConnected()) {
        try {
          socket.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }

  }

  @Override
  protected Void doInBackground(Void... params) {
    sendData();
    return null;
  }

  @Override
  protected void onPostExecute(Void aVoid) {
    super.onPostExecute(aVoid);
    Log.d("Sender", "Finished!");
  }

  @Override
  protected void onCancelled() {
    super.onCancelled();
    Log.d("Sender", "Cancelled!");
  }
}
