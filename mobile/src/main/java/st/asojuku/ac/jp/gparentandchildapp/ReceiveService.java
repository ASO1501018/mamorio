package st.asojuku.ac.jp.gparentandchildapp;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.WearableListenerService;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReceiveService extends WearableListenerService {

    private static final String DATA_PATH = "/path";
    private DBManager DB;
    @Override
    public void onCreate() {
        super.onCreate();

        DB = new DBManager(this);
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        String childID = DB.getMember("childID");

        if(childID.equals("0")) return;

        Log.d("Log", "DonDataChanged() ");

        DataMap dataMap;
        for (DataEvent event : dataEvents) {

            // Check the data type
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                // Check the data path
                String path = event.getDataItem().getUri().getPath();
                if (path.equals(DATA_PATH)) {}
                dataMap = DataMapItem.fromDataItem(event.getDataItem()).getDataMap();
                Log.d("Log", "DataMap received on watch: " + dataMap);

                String latitude = dataMap.getString("latitude");
                String longitude = dataMap.getString("longitude");

                Date now = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                String date = dateFormat.format(now);
                String time = timeFormat.format(now);


                GPS gps = new GPS(childID,date,time,latitude,longitude);

                DB.insertGPS(gps);
            }
        }
    }

}