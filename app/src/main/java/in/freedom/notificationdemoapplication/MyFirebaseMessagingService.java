package in.freedom.notificationdemoapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

 //   private NotificationUtils notificationUtils;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.i(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null)
            return;

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
          //  handleNotification(remoteMessage.getNotification().getBody());
            sendMyBroadCast(remoteMessage.getNotification().getBody());
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
             //   handleDataMessage(json);
                sendMyBroadCast(remoteMessage.getData().toString());
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.i(TAG, "FCM Token: " + s);
        sendMyBroadCast(s);
    }

    /**
     * This method is responsible to send broadCast to specific Action
     * */
    private void sendMyBroadCast(String s)
    {
        try
        {
            Intent broadCastIntent = new Intent();
            broadCastIntent.setAction(MainActivity.BROADCAST_ACTION);

            // uncomment this line if you want to send data
            broadCastIntent.putExtra("data", s);

            sendBroadcast(broadCastIntent);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}