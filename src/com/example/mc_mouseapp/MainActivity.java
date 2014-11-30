package com.example.mc_mouseapp;



import android.support.v7.app.ActionBarActivity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity implements SensorEventListener {
	private TextView tv;
	private SensorManager sensorManager;	
	private Sensor gyroscope;
	TextView xValueText,yValueText,zValueText;
	float[] values = new float[3];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); 
        sensorManager.registerListener(this, gyroscope ,SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override  
    protected void onResume()  
    {  
        super.onResume();  
        
        
    }  
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    
    
	@Override
	public void onSensorChanged(SensorEvent event) {
		new GyroData().execute(event);	
		
//		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
//    	{
//        	  		
//        		
//        			values[0]=event.values[2];
//        		
//        			values[1]=event.values[1];
//        		
//        			values[2]=event.values[0];
//        
//        			xValueText = (TextView) findViewById(R.id.xVal);
//        	        yValueText =(TextView) findViewById(R.id.yVal);
//        	        zValueText =(TextView) findViewById(R.id.zVal);
//        	        xValueText.setText(Float.toString(values[0]));
//        	        yValueText.setText(Float.toString(values[1]));
//        	        zValueText.setText(Float.toString(values[2]));
//        	    
//        	
//    	}
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
//			xValueText = (TextView) findViewById(R.id.xVal);
//	        yValueText =(TextView) findViewById(R.id.yVal);
//	        zValueText =(TextView) findViewById(R.id.zVal);
//	        xValueText.setText(Float.toString(result[0]));
//	        yValueText.setText(Float.toString(result[1]));
//	        zValueText.setText(Float.toString(result[2]));
			MainActivity.this.setGyroResult(result);
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
    	
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
}
