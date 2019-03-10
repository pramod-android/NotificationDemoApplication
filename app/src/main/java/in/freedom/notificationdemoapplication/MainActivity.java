package in.freedom.notificationdemoapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends AppCompatActivity {


    private final String TAG = "MainActivity";
    public static final String BROADCAST_ACTION = "com.ojastec.broadcastreceiverdemo";

    Button startServiceBt;
    TextView receiverTv;
    MyBroadCastReceiver myBroadCastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeMembers();

//        setListener();

        registerMyReceiver();


//        FirebaseInstanceId.getInstance().getInstanceId()
//                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
//                        if (!task.isSuccessful()) {
//                            Log.w(TAG, "getInstanceId failed", task.getException());
//                            return;
//                        }
//
//                        // Get new Instance ID token
//                        String token = task.getResult().getToken();
//
//                        // Log and toast
//                        // String msg = getString(R.string.msg_token_fmt, token);
//                        Log.d(TAG, token);
//                        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
//
//                    }
//                });

    }

    private void initializeMembers() {
        // startServiceBt = (Button) findViewById(R.id.start_service_button);
        receiverTv = (TextView) findViewById(R.id.receiver_textview);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        // String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, token);
                        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                        receiverTv.setText(token);
                    }
                });

        myBroadCastReceiver = new MyBroadCastReceiver();
    }


    /**
     * This method is responsible to register an action to BroadCastReceiver
     */
    private void registerMyReceiver() {

        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(BROADCAST_ACTION);
            registerReceiver(myBroadCastReceiver, intentFilter);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * MyBroadCastReceiver is responsible to receive broadCast from register action
     */
    class MyBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            try {
                Log.d(TAG, "onReceive() called");

                if (receiverTv != null) {
                    receiverTv.setText("Broadcast Received");
                }

                // uncomment this line if you had sent some data
                String data = intent.getStringExtra("data"); // data is a key specified to intent while sending broadcast
                Log.e(TAG, "data==" + data);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * This method called when this Activity finished
     * Override this method to unregister MyBroadCastReceiver
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        // make sure to unregister your receiver after finishing of this activity
        unregisterReceiver(myBroadCastReceiver);
    }
}
