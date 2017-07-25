package st.asojuku.ac.jp.gparentandchildapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class SubActivity extends AppCompatActivity {

    private TextView Name;
    private DBManager db;
    private String childName;
    //private String textstr;
    private Intent backGPS,backSend;
    private boolean gpsKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.gps_window);

        db = new DBManager(this);

        Name = (TextView)findViewById(R.id.Childname);

        Intent intent = getIntent();
        //int id = intent.getIntExtra("DATA",0);

        childName = db.getMember("childname");

        //viewにidの中身を表示
        Name.setText(String.valueOf(childName));

        //System.out.println(id);
        //setContentView(id);
        //text.setText(intid);

        gpsKey = false;

        backGPS = new Intent(getApplication(), BackGroundGPS.class);
        backSend = new Intent(getApplication(),SendGPS.class);

        ImageButton btn = (ImageButton)findViewById(R.id.switch1);
        // 背景画像を変更する
        //btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_togglebutton));
        //btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_togglebutton));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gpsKey){
                    //GPS取得終了
                    ((ImageView)view).setImageResource(R.drawable.gps_off);
                    //stopService(backGPS);
                    stopService(backSend);

                    Log.v("btn","stop");
                }else{

                    //GPS取得開始
                    ((ImageView)view).setImageResource(R.drawable.gps_on);
                    //startService(backGPS);
                    startService(backSend);

                    Log.v("btn","start");
                }
                gpsKey = !gpsKey;
            }
        });
//
//        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                if(isChecked){
//                    startService(backGPS);
//                    startService(backSend);
//
//                    Log.v("btn","start");
//
//                }else{
//                    stopService(backGPS);
//                    stopService(backSend);
//
//                    Log.v("btn","stop");
//                }
//
//            }
//        });
    }

}

