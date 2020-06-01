package com.example.stichitv2;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Messaging_service extends Service {
private String orderID,servicename;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference("SendOrders");



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        servicename = intent.getStringExtra("servicename");

        if(servicename.equals("sendorder"))
        {
            orderID = intent.getStringExtra("oid");

            DatabaseReference orderid = database.child(orderID);
            orderid.setValue("Unseen");
        }


        else if(servicename.equals("getorder")){

            String jsonArray = intent.getStringExtra("orderarray");
            try {
                JSONArray Orderarray = new JSONArray(jsonArray);
                for(int i=0;i<Orderarray.length();i++) {
                    JSONObject resdata = Orderarray.getJSONObject(i);
                    orderID=resdata.getString("orderID");
                    final DatabaseReference getorder = database.child(orderID);
                    getorder.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.d("Rehman",dataSnapshot.toString());
                            String value = dataSnapshot.getValue(String.class);
                           if(value==null)
                           {

                           }
                            else if(value.equals("Unseen"))
                            {
                                shownotification("You recieved new order");
                                getorder.setValue("seen");
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {

                        }
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

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

    public void shownotification(String message){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel("Mynotification","Mynotification",NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        PendingIntent p1 = PendingIntent.getActivities(this,0, new Intent[]{new Intent(this, TailorNewOrder.class)},0);
        NotificationCompat.Builder n = new NotificationCompat.Builder(this,"Mynotification")
                .setSmallIcon(R.drawable.ic_clock)
                .setContentTitle("Notification")
                .setContentText(message)
                .setContentIntent(p1)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager =NotificationManagerCompat.from(this);
        notificationManager.notify(999,n.build());
    }


}
