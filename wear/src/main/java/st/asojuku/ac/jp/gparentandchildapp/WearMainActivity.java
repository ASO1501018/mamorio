//package st.asojuku.ac.jp.gparentandchildapp;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.wearable.view.WatchViewStub;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//
//
//public class WearMainActivity extends Activity {
//
//    private Button button;
//    private Intent gps;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState)  {
//        super.onCreate(savedInstanceState);
//
//        Log.d("Log", "onCreate()");
//
//        gps = new Intent(WearMainActivity.this,GPSMainActivity.class);
//
//        setContentView(R.layout.activity_wear_main);
//        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
//        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
//            @Override
//            public void onLayoutInflated(WatchViewStub stub) {
//
//                button = (Button)stub.findViewById(R.id.button);
//                button.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Log.d("Log", "setOnClickListener()");
//
//                        startActivity(gps);
//                        // UI がブロックするかもしれないので新しくThreadを立てる
//                    }
//                });
//
//            }
//        });
//    }
//}