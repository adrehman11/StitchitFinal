package com.example.stichitv2;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
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
    DatabaseReference database = FirebaseDatabase.getInstance().getReference("NotificationTailor");
    DatabaseReference orderARs = FirebaseDatabase.getInstance().getReference("NotificationCustomer");
    Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        servicename = intent.getStringExtra("servicename");

        if(servicename.equals("sendorder"))
        {
            orderID = intent.getStringExtra("oid");

            DatabaseReference orderid = database.child(orderID);
            orderid.setValue("Unseen");
        }
        else if(servicename.equals("OrderRejected") || servicename.equals("OrderAccepted"))
        {
            orderID = intent.getStringExtra("OrderID");
            if(servicename.equals("OrderRejected"))
            {
                DatabaseReference orderAR = orderARs.child(orderID);
                orderAR.setValue("OrderRejected");
            }
            else if (servicename.equals("OrderAccepted"))
            {
                DatabaseReference orderAR = orderARs.child(orderID);
                orderAR.setValue("OrderAccepted");
            }

        }


        else if(servicename.equals("notificationCustomer")){
            String jsonArray = intent.getStringExtra("orderarray");
            try {
                JSONArray Orderarray = new JSONArray(jsonArray);
                for(int i=0;i<Orderarray.length();i++) {
                    JSONObject resdata = Orderarray.getJSONObject(i);
                    orderID=resdata.getString("orderID");
                    final DatabaseReference getnotification1 = orderARs.child(orderID);
                    getnotification1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.d("Rehman",dataSnapshot.toString());
                            String value = dataSnapshot.getValue(String.class);
                            if(value==null)
                            {

                            }
                            else if(value.equals("OrderAccepted"))
                            {
                                shownotification("Your Order is Accepted");
                                getnotification1.setValue("seen");
                            }
                            else if(value.equals("OrderRejected"))
                            {
                                shownotification("Your Order is Rejected");
                                getnotification1.setValue("seen");
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
        else if(servicename.equals("getnotificationtailor")){

            String jsonArray = intent.getStringExtra("orderarray");
            try {
                JSONArray Orderarray = new JSONArray(jsonArray);
                for(int i=0;i<Orderarray.length();i++) {
                    JSONObject resdata = Orderarray.getJSONObject(i);
                    orderID=resdata.getString("orderID");
                    final DatabaseReference getnotification1 = database.child(orderID);
                    getnotification1.addValueEventListener(new ValueEventListener() {
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
                                getnotification1.setValue("seen");
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
        PendingIntent p1 = PendingIntent.getActivities(this,0, new Intent[]{new Intent(this, TailorNewOrder.class)},0);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel("Mynotification","Mynotification",NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        if(message.equals("Your Order is Accepted"))
        {
             p1 = PendingIntent.getActivities(this,0, new Intent[]{new Intent(this, Home_Customer.class)},0);
        }
        if(message.equals("Your Order is Rejected"))
        {
            p1 = PendingIntent.getActivities(this,0, new Intent[]{new Intent(this, RejectedOrderCustomer.class)},0);
        }
        if(message.equals("You recieved new order"))
        {
            p1 = PendingIntent.getActivities(this,0, new Intent[]{new Intent(this, TailorNewOrder.class)},0);
        }

        NotificationCompat.Builder n = new NotificationCompat.Builder(this,"Mynotification")
                .setSmallIcon(R.drawable.ic_notify)
                .setContentTitle("Notification")
                .setContentText(message)
                .setContentIntent(p1)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000})
                .setSound(uri)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager =NotificationManagerCompat.from(this);
        notificationManager.notify(999,n.build());
    }


}
