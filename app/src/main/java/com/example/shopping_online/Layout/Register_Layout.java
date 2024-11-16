package com.example.shopping_online.Layout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.shopping_online.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register_Layout extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    private TextInputLayout layout_Password_Confirm_Register, layout_Password_Register, layout_Username_Register;
    private TextInputEditText edt_Username_Register, edt_Password_Register, edt_Password_Confirm_Register;
    private CheckBox checkbox_rule;
    private Button btn_register;
    private TextView txt_goto_Login;

    private boolean checkEmpty;
    private boolean validate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_layout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading ...!");

        layout_Username_Register = findViewById(R.id.layout_Username_Register);
        layout_Password_Register = findViewById(R.id.layout_Password_Register);
        layout_Password_Confirm_Register = findViewById(R.id.layout_Password_Confirm_Register);
        edt_Username_Register = findViewById(R.id.edt_Username_Register);
        edt_Password_Register = findViewById(R.id.edt_Password_Register);
        edt_Password_Confirm_Register = findViewById(R.id.edt_Password_Confirm_Register);
        checkbox_rule = findViewById(R.id.checkbox_rule);
        btn_register = findViewById(R.id.btn_register);
        txt_goto_Login = findViewById(R.id.txt_goto_Login);

        txt_goto_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register_Layout.this, Login_Layout.class));
            }
        });

        validateUserPass();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerAccount();
            }
        });

    }

    private void registerAccount() {

        String user = edt_Username_Register.getText().toString().trim();
        String password = edt_Password_Register.getText().toString().trim();
        String passwordConfirm = edt_Password_Confirm_Register.getText().toString().trim();

        validateEmpty();
        validateText();
        progressDialog.show();

        if (!checkbox_rule.isChecked()) {
            Toast.makeText(Register_Layout.this, "Vui lòng chấp nhận mọi điều khoản mà chúng tôi đưa ra!", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        } else {
            firebaseAuth.createUserWithEmailAndPassword(user, password).addOnCompleteListener(Register_Layout.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        Toast.makeText(Register_Layout.this, "Đăng ký thành công", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Register_Layout.this, Login_Layout.class));
                        progressDialog.dismiss();
                        finish();
                    } else {
                        Toast.makeText(Register_Layout.this, "Đăng ký thất bại!", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }
            });
        }
    }

    private void validateText() {
        String user = edt_Username_Register.getText().toString().trim();
        String password = edt_Password_Register.getText().toString().trim();
        String passwordConfirm = edt_Password_Confirm_Register.getText().toString().trim();

        validateEmpty();
        progressDialog.show();

        Pattern pattern = Pattern.compile("^[a-zA-Z0-9._-]+@gmail\\.com$");
        Matcher matcher = pattern.matcher(user);
        boolean userMatch = matcher.find();

        if (checkEmpty) {
            if (!userMatch) {
                layout_Username_Register.setError("Tài khoản của bạn không đúng định dạng \"@gmail.com\" ");
                progressDialog.dismiss();
            } else {
                if (password.length() < 8) {
                    layout_Password_Register.setError("Mật khẩu của bạn phải lớn hơn hoặc bằng 8 ký tự");
                    progressDialog.dismiss();

                } else {
                    Pattern patternPass = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$");
                    Matcher matcherPass = patternPass.matcher(password);
                    boolean userMatchPass = matcherPass.find();
                    if (!userMatchPass) {
                        layout_Password_Register.setError("Mật khẩu của bạn phải 1 chữ Hoa, 1 chữ thường, 1 số và 1 ký tự đặc biệt");
                        progressDialog.dismiss();

                    } else {
                        if (!passwordConfirm.equals(password)) {
                            layout_Password_Confirm_Register.setError("Nhập lại mật khẩu không chính xác");
                            progressDialog.dismiss();

                        } else {
                            validate = true;
                        }
                    }
                }

            }
        }
    }



    private void validateEmpty() {
        String user = edt_Username_Register.getText().toString().trim();
        String password = edt_Password_Register.getText().toString().trim();
        String passwordConfirm = edt_Password_Confirm_Register.getText().toString().trim();

        if (user.isEmpty()) {
            layout_Username_Register.setError("Vui lòng không để trống tài khoản");
            checkEmpty = false;
        }
        if (password.isEmpty()) {
            layout_Password_Register.setError("Vui lòng không để trống tài khoản");
            checkEmpty = false;
        }
        if (passwordConfirm.isEmpty()) {
            layout_Password_Confirm_Register.setError("Vui lòng không để trống tài khoản");
            checkEmpty = false;
        } else {
            checkEmpty = true;
        }

    }

    private void validateUserPass() {
        //Set điều kiện cho username giống bên Login_Layout
        edt_Username_Register.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String userInput = s.toString();
                Pattern pattern = Pattern.compile("^[a-zA-Z0-9._-]+@gmail\\.com$");
                Matcher matcher = pattern.matcher(userInput);
                boolean userMatch = matcher.find();
                if (userMatch) {
                    layout_Username_Register.setHelperText("");
                    layout_Username_Register.setError("");
                } else {
                    layout_Username_Register.setError("Tài khoản của bạn không đúng định dạng \"@gmail.com\" ");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edt_Password_Register.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String passInput = s.toString();
                if (passInput.length() >= 8) {
                    // Giống bên Login_Layout
                    Pattern pattern = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$");
                    Matcher matcher = pattern.matcher(passInput);
                    boolean passMatch = matcher.find();
                    if (passMatch) {
                        // Điều kiện khi đúng
                        layout_Password_Register.setHelperText("");
                        layout_Password_Register.setError("");
                    } else {
                        layout_Password_Register.setError("Mật khẩu của bạn phải 1 chữ Hoa, 1 chữ thường, 1 số và 1 ký tự đặc biệt");
                    }
                } else {
                    layout_Password_Register.setError("Mật khẩu của bạn phải lớn hơn hoặc bằng 8 ký tự");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edt_Password_Confirm_Register.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String passwordInput = edt_Password_Register.getText().toString().trim();
                String passwordConfirmInput = edt_Password_Confirm_Register.getText().toString().trim();
                if (!passwordInput.equals(passwordConfirmInput)) {
                    layout_Password_Confirm_Register.setError("Nhập lại mật khẩu không chính xác");
                } else {
                    layout_Password_Confirm_Register.setError("");
                    layout_Password_Confirm_Register.setHelperText("");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}