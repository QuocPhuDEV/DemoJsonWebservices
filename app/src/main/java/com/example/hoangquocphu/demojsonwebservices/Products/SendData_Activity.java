package com.example.hoangquocphu.demojsonwebservices.Products;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoangquocphu.demojsonwebservices.Login.JsonReader;
import com.example.hoangquocphu.demojsonwebservices.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SendData_Activity extends AppCompatActivity {

    //region KHAI BÁO BIẾN TOÀN CỤC
    public List<String> listData = new ArrayList<>();
    public ArrayAdapter<String> arrayAdapter;

    public TextView edPartNo, edSerial;
    public ListView listView;

    public String PartNo, Serial;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_data_);

        addEvents();
    }

    //region ÁNH XẠ ĐỐI TƯỢNG
    public void addEvents() {
        edPartNo = (EditText) findViewById(R.id.edPartNo);
        edSerial = (EditText) findViewById(R.id.edSerial);
        listView = (ListView) findViewById(R.id.lvProducts);
    }
    //endregion

    //region LOAD MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.products_menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.itemSendData) {
//            Intent intent = new Intent(this, SignIn_Activity.class);
//            startActivity(intent);
            sendData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //endregion

    //region XỬ LÝ EVENTS

    // Xử lý đa tiến trình
    public class MyJsonTask extends AsyncTask<String, JSONObject, Void> {

        public ProgressDialog dialog = new ProgressDialog(SendData_Activity.this);

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Sending....");
            dialog.show();
        }

        @Override
        protected Void doInBackground(String... strings) {
            // Lấy URL truyền vào
            String url = strings[0];
            JSONObject jsonObject;
            try {
                // Đọc Json và chuyển về Object
                jsonObject = JsonReader.readFileJsonFromUrl(url);
                publishProgress(jsonObject);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialog.dismiss();
            Toast.makeText(SendData_Activity.this, "Send data successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    // Đọc url
    public void readUrlLink(String _part, String _serial) {
        String url = "http://192.168.1.102/api/carton/savelabel?partno=" + _part + "&serial=" + _serial + "";
        new MyJsonTask().execute(url);
    }

    // Send nhiều dữ liệu
    public void sendData() {
        if (listData.size() < 1) {
            Toast.makeText(this, "No have data to send to server!", Toast.LENGTH_SHORT).show();
        } else {
            for (int i = 0; i < listData.size(); i++) {

                List<String> list = new ArrayList<>(Arrays.asList(listData.get(i).toString().split("\t")));
                PartNo = list.get(1).toString();
                Serial = list.get(5).toString();

                readUrlLink(PartNo, Serial);

            }
        }

    }

    // Add data từ textbox xuống listview
    public void btnAddData(View view) {
        listData.add(new String("Part No:\t" + edPartNo.getText().toString() + "\t" + "\t" + "\t"
                + "Serial:\t" + edSerial.getText().toString()));

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(arrayAdapter);

        edPartNo.setText(null);
        edSerial.setText(null);
    }

    //endregion
}
