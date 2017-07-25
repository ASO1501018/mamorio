package st.asojuku.ac.jp.gparentandchildapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button pidBtn;
    private LinearLayout linearLayout;
    private EditText pidEdtext;
    private ArrayList<Button> childBtns;
    private Intent intent;
    private DBManager db;
    //private TextView sqlText;

 //   linearLayout = (LinearLayout)findViewById(R.id.line);
@Override
    //onCreateはアプリを起動した際に最初に呼び出されるもの
    protected void onCreate(Bundle savedInstanceState) {
        //superはスーパークラス
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        new FirstConnect(this);

        db = new DBManager(this);
        if(db.getMember("childID")==null) {
            db.insertMember("childID", "0");
            db.insertMember("childname", "");
        }

        pidBtn = (Button)findViewById(R.id.debug_PID_button);
        childBtns = new ArrayList<Button>();

        pidEdtext = (EditText)findViewById(R.id.debug_PID_edtext);

        //sqlText = (TextView)findViewById(R.id.Debug_SQLtext);

        linearLayout = (LinearLayout)findViewById(R.id.line);


        pidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pid = String.valueOf(pidEdtext.getText());
                SelectChild selectChild = new SelectChild();

                selectChild.getChildIDList(pid);

                ArrayList<Child> childs = selectChild.getChilds();

                addButton(childs);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        db.updateMember("childID","0");
        permcheck();
    }

    private void addButton(ArrayList<Child> childs) {


        linearLayout.removeAllViews();
        childBtns.clear();
        for (Child child :childs) {
            Button button = new Button(this);
            //Button b = (Button) findViewById(R.id.Childname);

            // drawableフォルダに置いたbutton_background.pngを背景に設定
            button.setBackgroundResource(R.drawable.button_background);

            LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

//            LayoutParams buttonlp = button.getLayoutParams();
            MarginLayoutParams buttonmlp = (MarginLayoutParams) buttonLayoutParams;
//
            buttonmlp.setMargins(30,30,30,30);

            button.setTextColor(ContextCompat.getColor(this,R.color.white));
            button.setTypeface(Typeface.SANS_SERIF);
//            button.setLayoutParams(buttonmlp);
            //button.setLabelFor(R.id.Childname);
            button.setLayoutParams(buttonLayoutParams);

            button.setId(Integer.parseInt(child.getChildID()));
            button.setText(child.getChildName());




//            setBtnlayout(button);

            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    trandata((Button) v);
                }
            });



            childBtns.add(button);
            linearLayout.addView(button);
        }
    }

    //SubActivityに画面遷移と同時にデータを渡す
    public void trandata(Button button){
        intent = new Intent(MainActivity.this, SubActivity.class);

        db.updateMember("childID", String.valueOf(button.getId()));
        db.updateMember("childname", String.valueOf(button.getText()));
        //sqlText.setText(button.getText());

        //intent.putExtra("DATA",button.getId());
        startActivity(intent);
    }


    private void permcheck(){


        final boolean gpsEnabled
                = ((LocationManager) getSystemService(LOCATION_SERVICE)).isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!gpsEnabled) {
            // GPSを設定するように促す
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            //startActivity(settingsIntent);
            startActivity(settingsIntent);
        }


        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)!=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)!=
                        PackageManager.PERMISSION_GRANTED) {

            String[] paerm ={
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            };

            ActivityCompat.requestPermissions(this,paerm,1);

            return;
        }
    }
}
