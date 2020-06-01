package com.example.stichitv2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Messaging_service extends Service {
private String orderID,servicename;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        servicename = intent.getStringExtra("servicename");

        if(servicename.equals("sendorder"))
        {
            orderID = intent.getStringExtra("oid");
            DatabaseReference orderid = database.getReference(orderID);
            orderid.setValue("uncomplete");
        }

        else if(servicename.equals("getorder")){
            orderID = intent.getStringExtra("oid");
            DatabaseReference orderid = database.getReference(orderID);
            orderid.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    String value = dataSnapshot.getValue(String.class);

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                }
            });
        }


        return  START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

//    public void shownotification(String message){
//        PendingIntent p1 = PendingIntent.getActivities(this,0, new Intent[]{new Intent(this, testactivity.class)},0);
//        Notification n = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.drawable.ic_clock)
//                .setContentTitle("Hogya")
//                .setContentText(message)
//                .setContentIntent(p1)
//                .setAutoCancel(true)
//
//                .build();
//        NotificationManager notificationManager =(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        notificationManager.notify(0,n);
//    }


}
