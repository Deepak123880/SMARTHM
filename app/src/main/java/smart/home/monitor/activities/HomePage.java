package smart.home.monitor.activities;

import androidx.appcompat.app.AppCompatActivity;
import smart.home.monitor.R;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class HomePage extends AppCompatActivity {
    private SensorManager mgr;
    private Sensor temp;
    private TextView text;
    private StringBuilder msg = new StringBuilder(2048);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        mgr = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        temp = mgr.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        text = (TextView) findViewById(R.id.text);
    }

    @Override
    protected void onResume() {
        mgr.registerListener(this, temp, SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }

    @Override
    protected void onPause() {
        mgr.unregisterListener(this, temp);
        super.onPause();
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onSensorChanged(SensorEvent event) {

        float fahrenheit = event.values[0] * 9 / 5 + 32;
        msg.insert(0, "Got a sensor event: " + event.values[0] + "Celsius (" + fahrenheit + " F)\n");
        text.setText(msg);
        text.invalidate();
    }
}
