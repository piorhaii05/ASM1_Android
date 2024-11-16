package com.example.shopping_online.Layout;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping_online.Adapter.RecyclerVIew_Adapter;
import com.example.shopping_online.ClothesModel;
import com.example.shopping_online.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ASM_GD1 extends AppCompatActivity {

    private RecyclerView recyclerView_clothes;
    private ArrayList<ClothesModel> list;
    private RecyclerVIew_Adapter adapter;
    private APIService apiService;
    private Retrofit retrofit;

    private Button btn_add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_asm_gd1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView_clothes = findViewById(R.id.recyclerView_clothes);
        btn_add = findViewById(R.id.btn_Add);

        retrofit = new Retrofit.Builder()
                .baseUrl(APIService.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        load_item_goto_Server();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_item_goto_Server();
            }
        });

    }

    private void load_item_goto_Server() {
        apiService = retrofit.create(APIService.class);
        Call<ArrayList<ClothesModel>> listCall = apiService.getClothes();

        listCall.enqueue(new Callback<ArrayList<ClothesModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ClothesModel>> call, Response<ArrayList<ClothesModel>> response) {
                if(response.isSuccessful()){
                    list = response.body();

                    recyclerView_clothes.setLayoutManager(new LinearLayoutManager(ASM_GD1.this));
                    adapter = new RecyclerVIew_Adapter(ASM_GD1.this, list);
                    recyclerView_clothes.setAdapter(adapter);

                }
                else {
                    Toast.makeText(ASM_GD1.this, "Load lỗi", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ArrayList<ClothesModel>> call, Throwable t) {

            }
        });
    }

    private void add_item_goto_Server() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ASM_GD1.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_item_submit, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        EditText edt_name_new = view.findViewById(R.id.edt_name_new);
        EditText edt_date_new = view.findViewById(R.id.edt_date_new);
        EditText edt_brand_new = view.findViewById(R.id.edt_brand_new);
        EditText edt_price_new = view.findViewById(R.id.edt_price_new);
        Button btn_submit = view.findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name_new = edt_name_new.getText().toString().trim();
                String date_new = edt_date_new.getText().toString().trim();
                String brand_new = edt_brand_new.getText().toString().trim();
                String price_new = edt_price_new.getText().toString().trim();

                if (name_new.isEmpty() || date_new.isEmpty() || brand_new.isEmpty() || price_new.isEmpty()) {
                    Toast.makeText(ASM_GD1.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                ClothesModel clothesModel_new = new ClothesModel();
                clothesModel_new.set_id(null);
                clothesModel_new.setName(name_new);
                clothesModel_new.setDate(date_new);
                clothesModel_new.setBrand(brand_new);
                clothesModel_new.setPrice(Integer.parseInt(price_new));

                Call<ArrayList<ClothesModel>> call = apiService.addClothes(clothesModel_new);
                call.enqueue(new Callback<ArrayList<ClothesModel>>() {
                    @Override
                    public void onResponse(Call<ArrayList<ClothesModel>> call, Response<ArrayList<ClothesModel>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            list.clear();
                            list.addAll(response.body());
                            load_item_goto_Server();
                            dialog.dismiss();
                            Toast.makeText(ASM_GD1.this, "Thêm thành công!", Toast.LENGTH_LONG).show();
                        } else {
                            int statusCode = response.code(); // Mã trạng thái HTTP
                            Toast.makeText(ASM_GD1.this, "Lỗi: " + statusCode + " - " + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<ClothesModel>> call, Throwable t) {
                        Toast.makeText(ASM_GD1.this, "Add thất bại: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }
}