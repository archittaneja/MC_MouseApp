package com.example.mc_mouseapp;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;


import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity implements SensorEventListener {
	private TextView tv;
	private SensorManager sensorManager;	
	private Sensor gyroscope;
	TextView xValueText,yValueText,zValueText;
	Boolean BLUETOOTH_ENABLED = false;
	BluetoothAdapter adapter; // The default adapter.
	TextView view = null; // For errors.
	final int REQUEST_ENABLE_BT_SUCCESS = 1; // Successful Bluetooth enable.
	private final UUID mouseDroidUUID = UUID.fromString("3DD7E793-C461-4FAE-B715-12E8940A0975");
	float[] values = new float[3];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); 
        sensorManager.registerListener(this, gyroscope ,SensorManager.SENSOR_DELAY_FASTEST);
        //adapter = BluetoothAdapter.getDefaultAdapter();
        view = (TextView)findViewById(R.id.textForErrors);
//        if (!adapter.isEnabled()) {
//			view.setText("Bluetooth is not enabled. Enabling ...");
//			Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//			startActivityForResult(enableBluetooth, REQUEST_ENABLE_BT_SUCCESS);
//		}
		
		// Get the Paired Devices.
        
		
		
		// Select the correct paired device. 
		// TODO :: Provide user with a list of devices and choose to connect to one.
		//
        
    }
    
    /**
	 * Inner class to connect to a device off of the main UI thread.
	 * @author sukrit
	 */
	

	    @Override  
    protected void onResume()  
    {  
        super.onResume();  
    }  

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
	@Override
	public void onSensorChanged(SensorEvent event) {
		new GyroData().executeOnExecutor(GyroData.THREAD_POOL_EXECUTOR,event);	
		
		
	}
	
	class GyroData extends AsyncTask<SensorEvent, Void, float[]>{
	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();       
	    }
	    protected float[] doInBackground(SensorEvent... event) {
			if (event[0].sensor.getType() == Sensor.TYPE_ACCELEROMETER)
	    	{
	        		String s;
	        		float temp;
	        		s= String.format("%.1f", event[0].values[2]);
	        		temp= Float.parseFloat(s);
	        		if(Math.abs(temp - values[2]) > 0.5)
	        		{
	        			values[2] = temp;
	        		}
	        		s= String.format("%.1f", event[0].values[1]);
	        		temp= Float.parseFloat(s);
	        		if(Math.abs(temp - values[1]) > 0.5)
	        		{
	        			values[1] = temp;
	        		}
	        		s= String.format("%.1f", event[0].values[1]);
	        		temp= Float.parseFloat(s);
	        		if(Math.abs(temp - values[0]) > 0.5)
	        		{
	        			values[0] = temp;
	        		}
	        
	    	}
			return values;
			}
	    protected void onPostExecute(float[] result) {        
			MainActivity.this.setGyroResult(result);
			
	    }
	};
	
	
	
	
	class Bluetooth extends AsyncTask<Void, Void, Void>{
	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();       
	    }
	   @SuppressLint("NewApi") @Override
		protected Void doInBackground(Void... params) {
		   System.out.println("BLUETOOTH PLS");
			adapter = BluetoothAdapter.getDefaultAdapter();
	    	Set<BluetoothDevice> pairedDevices = adapter.getBondedDevices();
			while (pairedDevices.size() == 0) {
				pairedDevices = adapter.getBondedDevices();
			}
			OutputStream outputStream = null;
			BluetoothDevice pairedDevice = null;
			if (pairedDevices.size() > 0) {
				pairedDevice = (BluetoothDevice) pairedDevices.toArray()[0];
				ParcelUuid[] uuids = pairedDevice.getUuids();
                BluetoothSocket socket = null;
				try {
					final UUID mouseDroidUUID = UUID.fromString("3DD7E793-C461-4FAE-B715-12E8940A0975");
					socket = pairedDevice.createRfcommSocketToServiceRecord(mouseDroidUUID);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                try {
					socket.connect();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                try {
					outputStream = socket.getOutputStream();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				System.out.println("LOHCHUBH PLS");
				outputStream.write("WTF".getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return null;
		}
	};
	
	class SendBluetoothData extends AsyncTask<Void, Void, Void>{
	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();       
	    }
	   @SuppressLint("NewApi") @Override
		protected Void doInBackground(Void... params) {
		   		
			return null;
		}
	};
	
	
	public void setGyroResult(float[] result)
	{
		System.out.println("LOHCHUBH");
		xValueText = (TextView) findViewById(R.id.xVal);
        yValueText =(TextView) findViewById(R.id.yVal);
        zValueText =(TextView) findViewById(R.id.zVal);
        
        xValueText.setText(Float.toString(result[0]) );
        yValueText.setText(Float.toString(result[1]));
        zValueText.setText(Float.toString(result[2]));
        if(!BLUETOOTH_ENABLED)
		{	System.out.println("BLUETOOTH");
			BLUETOOTH_ENABLED = true;
			new Bluetooth().executeOnExecutor(Bluetooth.THREAD_POOL_EXECUTOR);
		
		}
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
}
