package com.example.hoangquocphu.demojsonwebservices;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //region Khai báo biến toan cục
    private TextView tvMaNV, tvTenNV;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addEvents();
        formLoad();
    }

    //region Ánh xạ đối tượng
    public void addEvents() {
        tvMaNV = (TextView) findViewById(R.id.tvMaNV);
        tvTenNV = (TextView) findViewById(R.id.tvTenNV);
    }
    //endregion

    //region Load form
    public void formLoad() {
        Intent intent = getIntent();
        tvMaNV.setText("Mã nhân viên: " + intent.getStringExtra("MaNV"));
        tvTenNV.setText("Tên nhân viên: " + intent.getStringExtra("TenNV"));
    }

    // Load Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_item, menu);
        return true;
    }

    // Select item menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.itemChangePass) {

        }
        return super.onOptionsItemSelected(item);
    }
    //endregion
}
