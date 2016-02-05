package  com.example.simone.myapplication;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.content.Context;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor accelerometer;

//variables to hold the absolute value of event change in three axes
    private float absX = 0;
    private float absY = 0;
    private float absZ = 0;

    private float vibrateThreshold = 0;

    private TextView  currentX, currentY, currentZ;

    public Vibrator v;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initializeViews();
        // access the Sensor service
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            // There is an accelerometer

            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            vibrateThreshold = accelerometer.getMaximumRange() / 2;
        } else {
            // There is no accelerometer
        }

        // accessing the vibrator service
       /* if(v.hasVibrator()) {
            v = (Vibrator)this.getSystemService(Context.VIBRATOR_SERVICE);
        }
        else
        {
          // There is no vibrator
        }*/

    }
// initializing the views with id attribute in layout xml
    public void initializeViews() {
        currentX = (TextView)findViewById(R.id.currentX);
        currentY = (TextView)findViewById(R.id.currentY);
        currentZ = (TextView)findViewById(R.id.currentZ);
    }

    //Registering the accelerometer with onsResume to listen to the events
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        Button btnBack = (Button) findViewById(R.id.buttonBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(getApplicationContext(), Main2Activity.class);
                startActivity(intent);
            }
        });

    }

    //Stopping the accelerometer from listening to events
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        // clean current values
        displayCleanValues();
        // display the current x,y,z accelerometer values
        displayCurrentValues();

        // get the change of the x,y,z values of the accelerometer
        absX = Math.abs(event.values[0]);
        absY = Math.abs(event.values[1]);
        absZ = Math.abs(event.values[2]);
    }

    public void displayCleanValues() {
        currentX.setText("0.0");
        currentY.setText("0.0");
        currentZ.setText("0.0");
    }

    // Method to display the values of the accelerometer
    public void displayCurrentValues() {
        currentX.setText(Float.toString(absX));
        currentY.setText(Float.toString(absY));
        currentZ.setText(Float.toString(absZ));
    }

}

