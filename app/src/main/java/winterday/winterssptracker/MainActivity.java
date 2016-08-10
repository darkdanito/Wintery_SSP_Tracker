package winterday.winterssptracker;

import android.app.Activity;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    Button button;
    TextView textview;
    TextView tvCH1Status, tvCH1Timer;
    CountDownTimer countdowntimer;

    CountDownTimer countdown1Min;

    String channel_1_Status = "Start";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_try);

        button = (Button)findViewById(R.id.button1);
        tvCH1Status = (TextView)findViewById(R.id.tw_CH_1_Status);
        tvCH1Timer = (TextView)findViewById(R.id.tw_CH_1_Time);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                countdowntimer = new CountDownTimerClass(3000, 1000);
                countdowntimer.start();

            }
        });
    }

    public class CountDownTimerClass extends CountDownTimer {

        public CountDownTimerClass(long millisInFuture, long countDownInterval) {

            super(millisInFuture, countDownInterval);

        }

        @Override
        public void onTick(long millisUntilFinished) {

            int progress = (int) (millisUntilFinished/1000);

            tvCH1Status.setText(channel_1_Status);
            tvCH1Timer.setText(Integer.toString(progress));

        }

        @Override
        public void onFinish() {

            if (channel_1_Status == "Battle Phase"){

                // Before Capture is 10mins
                channel_1_Status = "Before Capturing";
                countdowntimer.start();

            } else if (channel_1_Status == "Before Capturing") {

                // Need Capture timing
                channel_1_Status = "Capturing";
                countdowntimer.start();

            } else if (channel_1_Status == "Capturing") {

                // Before Mining is: 1min
                countdown1Min = new CountDownTimerClass(5000, 1000);
                channel_1_Status = "Before Mining";
                countdown1Min.start();

            } else if (channel_1_Status == "Before Mining") {

                // Mining is: 32min
                channel_1_Status = "Mining Phase";
                countdowntimer.start();

            } else if (channel_1_Status == "Mining Phase") {

                // Battle Phrase 17mins
                channel_1_Status = "Battle Phase";
                countdowntimer.start();


            } else {

                channel_1_Status = "Battle Phase";
                countdowntimer.start();
            }
        }
    }
}