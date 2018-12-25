package com.example.hoangquocphu.demojsonwebservices.Login;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hoangquocphu.demojsonwebservices.MainActivity;
import com.example.hoangquocphu.demojsonwebservices.Products.Products_Activity;
import com.example.hoangquocphu.demojsonwebservices.R;
import com.example.hoangquocphu.demojsonwebservices.SignIn.SignIn_Activity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Login_Activity extends AppCompatActivity {
    //region Khai báo biến toàn cục
    private EditText edUser, edPassword;
    private Button btnLogion, btnCancel;

    public String MaNV = "";
    public String TenNV = "";

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        addEvents();
    }


    //region ÁNH XẠ ĐỐI TƯỢNG
    public void addEvents() {
        edUser = (EditText) findViewById(R.id.edUsername);
        edPassword = (EditText) findViewById(R.id.edPassword);
        btnLogion = (Button) findViewById(R.id.btnLogin);
        btnCancel = (Button) findViewById(R.id.btnCancel);
    }
    //endregion

    //region LOAD MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.logion_menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.itemSignIn) {
            Intent intent = new Intent(this, SignIn_Activity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //endregion

    //region XỬ LÝ EVENTS

    // Xử lý đa tiến trình
    public class MyJsonTask extends AsyncTask<String, JSONObject, Void> {

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
        protected void onProgressUpdate(JSONObject... values) {
            super.onProgressUpdate(values);

            // Get dữ liệu
            JSONObject jsonObject = values[0];
            try {
                // Kiểm tra có tồn tại từ khoá "result" trong chuỗi Json hay ko
                //jsonObject.has("result") &&
                if (jsonObject.getString("message").equals("Wrong username, password")) {
                    Toast.makeText(getApplicationContext(), "Wrong Username or Password", Toast.LENGTH_SHORT).show();
                } else {

                    // Khai báo mới 1 JSONObject để lưu mảng "result"
                    JSONObject objectAccount = jsonObject.getJSONObject("result");

                    // Tìm các thông tin
                    if (objectAccount.has("MaNV")) {
                        MaNV = objectAccount.getString("MaNV");
                    }
                    if (objectAccount.has("TenNV")) {
                        TenNV = objectAccount.getString("TenNV");
                    }

                    // Gọi form main và gán giá trị
                    Intent intent = new Intent(getApplicationContext(), Products_Activity.class);
                    intent.putExtra("MaNV", MaNV);
                    intent.putExtra("TenNV", TenNV);
                    startActivity(intent);
                }

            } catch (JSONException e) {
                Toast.makeText(Login_Activity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Xử lý button
    public void btnLoginAccount(View view) {
        try {
            readUrlLink();
        } catch (Exception ex) {
            Toast.makeText(this, "" + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // Xử lý button cancel
    public void btnCancel(View view) {
        System.exit(0);
    }

    // Đọc url
    public void readUrlLink() {
        String url = "192.168.200.232/crm/api/user/login?userid=" + edUser.getText() + "&password=" + edPassword.getText();
        new MyJsonTask().execute(url);
    }
    //endregion
}