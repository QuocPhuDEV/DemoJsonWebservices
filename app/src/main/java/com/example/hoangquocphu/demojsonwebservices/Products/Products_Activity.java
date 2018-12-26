package com.example.hoangquocphu.demojsonwebservices.Products;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hoangquocphu.demojsonwebservices.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Products_Activity extends AppCompatActivity {
    //region Khai báo biến toan cục
    private TextView edPartNo, edSerial;

    public String PartNo, Serial;
    public static String URL = "http://192.168.1.102/api/carton/savelabel?partno=8989&serial=8989";
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_);

        addEvents();
    }

    //region ÁNH XẠ ĐỐI TƯỢNG
    public void addEvents() {
        edPartNo = (EditText) findViewById(R.id.edPartNo);
        edSerial = (EditText) findViewById(R.id.edSerial);
    }
    //endregion

    //region XỬ LÝ LOAD FORM
    public void formLoad() {
        Intent intent = getIntent();
    }

    // Load Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.products_menu_item, menu);
        return true;
    }
    //endregion

    //region SEND DATA TO WEBSERVICES
    // Select item menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.itemSendData) {
            sendDataToService();
        }
        return super.onOptionsItemSelected(item);
    }
    //endregion

    // region XỬ LÝ EVENTS
    public void sendDataToService() {
        new AsyncTaskProducts().execute();
    }

    public class AsyncTaskProducts extends AsyncTask<String, Void, Void> {
        private final HttpClient client = new DefaultHttpClient();
        private String Content;
        private String Error = null;
        private final String TAG = null;

        private ProgressDialog dialog = new ProgressDialog(Products_Activity.this);

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Wait...");
            dialog.show();
            Log.e(TAG, "------------------------------------- log here");
        }

        @Override
        protected Void doInBackground(String... urls) {
            try {
                HttpPost httpPost = new HttpPost(URL);


                // Gán giá trị
                PartNo = edPartNo.getText().toString();
                Serial = edSerial.getText().toString();

                // Đưa giá trị vào Object
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("partNo", PartNo);
                jsonObject.put("serial", Serial);

                MultipartEntity entityBuilder = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                entityBuilder.addPart("result", new StringBody(jsonObject.toString()));
                httpPost.setEntity(entityBuilder);

                HttpResponse response = client.execute(httpPost);

                this.Content = EntityUtils.toString(response.getEntity());
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialog.dismiss();
        }
    }
    // endregion
}
