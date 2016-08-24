package winterday.winterssptracker;

import android.app.Activity;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener {

    Button button_CH_1_KontaDied;
    Button button_CH_2_KontaDied;
    Button button_CH_3_KontaDied;

    Button button_CH_1_Update;


    Button button;
    TextView textview;
    CountDownTimer countdowntimer;



    String spinnerCH1_selected;



    TextView tvCH1Status, tvCH1Timer;
    TextView tvCH2Status, tvCH2Timer;
    TextView tvCH3Status, tvCH3Timer;

    EditText edCH1Update;


    CountDownTimer countdown1Min;
    CountDownTimer countDownTimer_CH_1;
    CountDownTimer countDownTimer_CH_2;
    CountDownTimer countDownTimer_CH_3;

    String channel_1_Status = "Start";
    String channel_2_Status = "Start";
    String channel_3_Status = "Start";

    // ToDo: Switch the phrases timing to constant .....


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_try);

        button_CH_1_KontaDied = (Button)findViewById(R.id.bt_CH_1_KontaDied);
        button_CH_2_KontaDied = (Button)findViewById(R.id.bt_CH_2_KontaDied);
        button_CH_3_KontaDied = (Button)findViewById(R.id.bt_CH_3_KontaDied);

        button_CH_1_Update = (Button)findViewById(R.id.bt_CH_1_update);

        edCH1Update = (EditText)findViewById(R.id.ed_CH_1);

        tvCH1Status = (TextView)findViewById(R.id.tw_CH_1_Status);
        tvCH1Timer = (TextView)findViewById(R.id.tw_CH_1_Time);

        tvCH2Status = (TextView)findViewById(R.id.tw_CH_2_Status);
        tvCH2Timer = (TextView)findViewById(R.id.tw_CH_2_Time);

        tvCH3Status = (TextView)findViewById(R.id.tw_CH_3_Status);
        tvCH3Timer = (TextView)findViewById(R.id.tw_CH_3_Time);

        final Spinner spin_CH_1 = (Spinner) findViewById(R.id.spinner_CH_1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.array_SSP_Phrase, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_CH_1.setAdapter(adapter);
        spin_CH_1.setOnItemSelectedListener(this);

        button_CH_1_KontaDied.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                channel_1_Status = "Before Mining";

                if (countDownTimer_CH_1 != null){
                    countDownTimer_CH_1.cancel();
                }

                countDownTimer_CH_1 = new countdown_CH_1(60000, 1000);      // 1 Min
                countDownTimer_CH_1.start();
            }
        });

        button_CH_2_KontaDied.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                channel_2_Status = "Before Mining";

                if (countDownTimer_CH_2 != null){
                    countDownTimer_CH_2.cancel();
                }

                countDownTimer_CH_2 = new countdown_CH_2(60000, 1000);      // 1 Min
                countDownTimer_CH_2.start();
            }
        });

        button_CH_3_KontaDied.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                channel_3_Status = "Before Mining";

                if (countDownTimer_CH_3 != null){
                    countDownTimer_CH_3.cancel();
                }

                countDownTimer_CH_3 = new countdown_CH_3(60000, 1000);      // 1 Min
                countDownTimer_CH_3.start();
            }
        });

        button_CH_1_Update.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String getMins = "";

                String userInputCH1Timing = edCH1Update.getText().toString();

                String getSeconds = userInputCH1Timing.substring(Math.max(userInputCH1Timing.length() - 2, 0));

                //Todo: Error if second is empty
                if (userInputCH1Timing.length() == 4){

                    getMins = userInputCH1Timing.substring(0,2);

                } else if (userInputCH1Timing.length() == 3) {

                    getMins = userInputCH1Timing.substring(0,1);

                }

                int timeInSeconds = ( Integer.parseInt(getMins) * 60 ) + Integer.parseInt(getSeconds);
                int timeinMS = timeInSeconds * 1000;

                channel_1_Status = spinnerCH1_selected;

                if (countDownTimer_CH_1 != null){
                    countDownTimer_CH_1.cancel();
                }

                countDownTimer_CH_1 = new countdown_CH_1(timeinMS, 1000);      // 1 Min
                countDownTimer_CH_1.start();
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        spinnerCH1_selected = parent.getItemAtPosition(pos).toString();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }


    public class countdown_CH_1 extends CountDownTimer {

        public countdown_CH_1(long millisInFuture, long countDownInterval) {

            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            int progress = (int) (millisUntilFinished / 1000);

            int seconds = progress % 60;
            int totalMinutes = progress / 60;
            int minutes = totalMinutes % 60;

            String fakeZero;

            if (seconds < 10){
                fakeZero = "0";
            } else {
                fakeZero = "";
            }

            tvCH1Status.setText(channel_1_Status);
            tvCH1Timer.setText(Integer.toString(minutes) + " : " +  fakeZero + Integer.toString(seconds));
        }

        @Override
        public void onFinish() {

            if (channel_1_Status == "Battle Phase"){

                // Before Capture is: 10mins
                channel_1_Status = "Before Capturing";
                countDownTimer_CH_1 = new countdown_CH_1(660000, 1000);      // 11 Min
                countDownTimer_CH_1.start();

            } else if (channel_1_Status == "Before Capturing") {

                // Need Capture is: 12min
                channel_1_Status = "Capturing";
                countDownTimer_CH_1 = new countdown_CH_1(120000, 1000);      // 10 Min
                countDownTimer_CH_1.start();

            } else if (channel_1_Status == "Capturing") {

                // Before Mining is: 1min
                countDownTimer_CH_1 = new countdown_CH_1(60000, 1000);       // 1 Min
                channel_1_Status = "Before Mining";
                countDownTimer_CH_1.start();

            } else if (channel_1_Status == "Before Mining") {

                // Mining is: 32min
                channel_1_Status = "Mining Phase";
                countDownTimer_CH_1 = new countdown_CH_1(1980000, 1000);      // 33 Min
                countDownTimer_CH_1.start();

            } else if (channel_1_Status == "Mining Phase") {

                // Battle Phrase 17mins
                channel_1_Status = "Battle Phase";

                countDownTimer_CH_1 = new countdown_CH_1(1020000, 1000);      // 1 Min
                countDownTimer_CH_1.start();

            } else {

                channel_1_Status = "Battle Phase";
                countDownTimer_CH_1.start();
            }
        }
    }

    public class countdown_CH_2 extends CountDownTimer {

        public countdown_CH_2(long millisInFuture, long countDownInterval) {

            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            int progress = (int) (millisUntilFinished / 1000);

            int seconds = progress % 60;
            int totalMinutes = progress / 60;
            int minutes = totalMinutes % 60;

            String fakeZero;

            if (seconds < 10){
                fakeZero = "0";
            } else {
                fakeZero = "";
            }

            tvCH2Status.setText(channel_2_Status);
            tvCH2Timer.setText(Integer.toString(minutes) + " : " +  fakeZero + Integer.toString(seconds));
        }

        @Override
        public void onFinish() {

            if (channel_2_Status == "Battle Phase"){

                // Before Capture is: 10mins
                channel_2_Status = "Before Capturing";
                countDownTimer_CH_2 = new countdown_CH_2(660000, 1000);      // 11 Min
                countDownTimer_CH_2.start();

            } else if (channel_2_Status == "Before Capturing") {

                // Need Capture is: 12min
                channel_2_Status = "Capturing";
                countDownTimer_CH_2 = new countdown_CH_2(600000, 1000);      // 10 Min
                countDownTimer_CH_2.start();

            } else if (channel_2_Status == "Capturing") {

                // Before Mining is: 1min
                countDownTimer_CH_2 = new countdown_CH_2(60000, 1000);       // 1 Min
                channel_2_Status = "Before Mining";
                countDownTimer_CH_2.start();

            } else if (channel_2_Status == "Before Mining") {

                // Mining is: 32min
                channel_2_Status = "Mining Phase";
                countDownTimer_CH_2 = new countdown_CH_2(1980000, 1000);      // 33 Min
                countDownTimer_CH_2.start();

            } else if (channel_2_Status == "Mining Phase") {

                // Battle Phrase 17mins
                channel_2_Status = "Battle Phase";

                countDownTimer_CH_2 = new countdown_CH_2(1020000, 1000);      // 17 Min
                countDownTimer_CH_2.start();

            } else {

                channel_2_Status = "Battle Phase";
                countDownTimer_CH_2.start();
            }
        }
    }

    public class countdown_CH_3 extends CountDownTimer {

        public countdown_CH_3(long millisInFuture, long countDownInterval) {

            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            int progress = (int) (millisUntilFinished / 1000);

            int seconds = progress % 60;
            int totalMinutes = progress / 60;
            int minutes = totalMinutes % 60;

            String fakeZero;

            if (seconds < 10){
                fakeZero = "0";
            } else {
                fakeZero = "";
            }

            tvCH3Status.setText(channel_3_Status);
            tvCH3Timer.setText(Integer.toString(minutes) + " : " +  fakeZero + Integer.toString(seconds));
        }

        @Override
        public void onFinish() {

            if (channel_3_Status == "Battle Phase"){

                // Before Capture is: 10mins
                channel_3_Status = "Before Capturing";
                countDownTimer_CH_3 = new countdown_CH_3(660000, 1000);      // 11 Min
                countDownTimer_CH_3.start();

            } else if (channel_3_Status == "Before Capturing") {

                // Need Capture is: 12min
                channel_3_Status = "Capturing";
                countDownTimer_CH_3 = new countdown_CH_3(600000, 1000);      // 10 Min
                countDownTimer_CH_3.start();

            } else if (channel_3_Status == "Capturing") {

                // Before Mining is: 1min
                countDownTimer_CH_3 = new countdown_CH_3(60000, 1000);       // 1 Min
                channel_3_Status = "Before Mining";
                countDownTimer_CH_3.start();

            } else if (channel_3_Status == "Before Mining") {

                // Mining is: 32min
                channel_3_Status = "Mining Phase";
                countDownTimer_CH_3 = new countdown_CH_3(1980000, 1000);      // 33 Min
                countDownTimer_CH_3.start();

            } else if (channel_3_Status == "Mining Phase") {

                // Battle Phrase 17mins
                channel_3_Status = "Battle Phase";

                countDownTimer_CH_3 = new countdown_CH_3(1020000, 1000);      // 17 Min
                countDownTimer_CH_3.start();

            } else {

                channel_3_Status = "Battle Phase";
                countDownTimer_CH_3.start();
            }
        }
    }
}