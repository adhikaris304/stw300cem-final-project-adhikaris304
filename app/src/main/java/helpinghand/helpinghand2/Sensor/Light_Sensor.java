package helpinghand.helpinghand2.Sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import helpinghand.helpinghand2.R;

public class Light_Sensor extends AppCompatActivity {
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light__sensor);
        setTitle("Light Sensor");

        lightInstance();
    }

    private void lightInstance() {
        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor=sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        SensorEventListener lightlistener=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if(event.sensor.getType()== Sensor.TYPE_LIGHT){
                    Toast.makeText(Light_Sensor.this, "on Sensor Change:"+ event.values[0], Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        sensorManager.registerListener(lightlistener,sensor,sensorManager.SENSOR_DELAY_NORMAL);
    }
}
