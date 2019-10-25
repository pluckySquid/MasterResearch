package com.example.indoor1;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;


import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Random;

import android.net.wifi.WifiManager;


public class MainActivity extends Activity implements SensorEventListener {

    private TextView xText, yText, zText, aText, xOri, yOri, zOri;
    private Sensor mySensor;
    private SensorManager SM;
    private Socket clientSocket = null;
    private EditText el;
    private PrintWriter writer;
    private String IPAddress;
    Accelerations accelerations;
    Orientation orientations;
    Boolean send = false;
    String databaseName = "";

    FirebaseDatabase database;
    DatabaseReference ref;

    java.sql.Timestamp timestamp;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);


        databaseName = "indoor";



        SM = (SensorManager) getSystemService(SENSOR_SERVICE);

        //acceleration Sensor
        mySensor = SM.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        SM.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_NORMAL );
        SM.registerListener(this, SM.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_NORMAL);//mSensorManager.unregisterListener(this);

            //SM.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_NORMAL );


        //assign TestView
        xText = (TextView)findViewById(R.id.xText );
        yText = (TextView)findViewById(R.id.yText);
        zText = (TextView)findViewById(R.id.zText);
        xOri = (TextView)findViewById(R.id.xOri );
        yOri = (TextView)findViewById(R.id.yOri);
        zOri= (TextView)findViewById(R.id.zOri);
        aText = (TextView)findViewById(R.id.aText);



        database = FirebaseDatabase.getInstance();
        ref = database.getReference(databaseName);
        accelerations = new Accelerations();
        orientations = new Orientation();

        WifiManager mainWifiObj;
        mainWifiObj = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

    }

    private void getAcc()
    {

        TextView xA = (TextView)findViewById(R.id.xText);
        TextView yA = (TextView)findViewById(R.id.yText);
        TextView zA = (TextView)findViewById(R.id.zText);
        accelerations.setxAcc( xA.getText().toString() );
        accelerations.setyAcc( yA.getText().toString());
        accelerations.setzAcc( zA.getText().toString());
        //accelerations.setTimestamp((TextView)findViewById(R.id.aText));

    }
    private void getOri()
    {

        TextView xO = (TextView)findViewById(R.id.xOri);
        TextView yO = (TextView)findViewById(R.id.yOri);
        TextView zO = (TextView)findViewById(R.id.zOri);
        orientations.setxOri( xO.getText().toString() );
        orientations.setyOri( yO.getText().toString());
        orientations.setzOri( zO.getText().toString());
        //accelerations.setTimestamp((TextView)findViewById(R.id.aText));

    }

    public void stopSending(View view) {
        Toast.makeText(MainActivity.this, "stop sending data to " + databaseName,Toast.LENGTH_LONG).show();
        send = false;
    }
    public void startSending(View view) {
        Toast.makeText(MainActivity.this, "start sending data to " + databaseName,Toast.LENGTH_LONG).show();
        send = true;
    }
    public void changeTableName(View v){
        el = (EditText)findViewById( R.id.ipText );
        databaseName = el.getText().toString();
        ref = database.getReference(databaseName);
        Toast.makeText(MainActivity.this, "Table's name change to " + databaseName,Toast.LENGTH_LONG).show();

    }
    public void deleteTable(View v){
        ref.removeValue();
        Toast.makeText(MainActivity.this, databaseName +" table has been deleted ",Toast.LENGTH_LONG).show();

    }

    //public void sendToFirebase(){

    //}

    @Override
    public void onSensorChanged(SensorEvent event){

        Sensor sensor = event.sensor;
        if(sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {

            xText.setText("XAcc     " + event.values[0]);
            yText.setText("YAcc     " + event.values[1]);
            zText.setText("ZAcc     " + event.values[2]);
            timestamp = new java.sql.Timestamp(System.currentTimeMillis());
            aText.setText("time: " + timestamp.getTime());
            //BackgroundTask b = new BackgroundTask();
            //String alldatas = String.valueOf(event.values[0]) + ", " + String.valueOf(event.values[1]) + ", " + String.valueOf(event.values[2]) + ", " + timestamp.toString();
            if (send == true) {
                getAcc();
                ref.child(Long.toString(timestamp.getTime())).setValue(accelerations);
            }
            //else
            //Toast.makeText(MainActivity.this, "not sennding data to " + databaseName,Toast.LENGTH_LONG).show();


            // String yT = String.valueOf(event.values[1]);
            // String zT = String.valueOf(event.values[2]);
            //String time = timestamp.toString();
            // b.execute(alldatas);
            // b.execute( yT );
            // b.execute( zT );
            //b.execute( time );
            //int o = writeToServer(event.values[0], event.values[1],event.values[2]);
            //aText.setText("a     " + o);
            //System.out.println("sdfdsdsfsdfd");
        }
        if(sensor.getType() == Sensor.TYPE_ORIENTATION){
            xOri.setText("XOri    " + event.values[0]);
            yOri.setText("YOri    " + event.values[1]);
            zOri.setText("ZOri    " + event.values[2]);
            timestamp = new java.sql.Timestamp(System.currentTimeMillis());
            aText.setText("time: " + timestamp.getTime());
            //BackgroundTask b = new BackgroundTask();
            //String alldatas = String.valueOf(event.values[0]) + ", " + String.valueOf(event.values[1]) + ", " + String.valueOf(event.values[2]) + ", " + timestamp.toString();
            if (send == true) {
                getOri();
                ref.child(Long.toString(timestamp.getTime())).setValue(orientations);
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //not in used
    }



}