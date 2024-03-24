package com.example.duolingoapp.taikhoan;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.pack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duolingoapp.MainActivity;
import com.example.duolingoapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private static final int PROGRESS_INTERVAL = 200; // milliseconds
    private static final int MAX_PROGRESS = 100;

    // Khai báo biến kiểm tra kết nối internet (Bạn có thể thay đổi cách kiểm tra này tùy theo yêu cầu)
    private boolean isConnectedToInternet = true; // Mặc định là true

    private ProgressBar progressBar;
    private View signInLayout;

    private TextView signUp, forgetPassword;

    private EditText edEmail, edPassword;

    private Button btnSignIn;

    private ImageButton btnShowHidePassword;

    DatabaseAccess DB;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_screen);

        isConnectedToInternet = isInternetConnected();

        FirebaseApp.initializeApp(this);

        progressBar = findViewById(R.id.progressBar_LaunchScreen);
        signInLayout = findViewById(R.id.signInLayout);
        signUp = findViewById(R.id.txtSignUp);
        forgetPassword = findViewById(R.id.txtForgetPassword);
        edEmail = findViewById(R.id.edEmail_SignIn);
        edPassword = findViewById(R.id.edPassword_SignIn);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnShowHidePassword = findViewById(R.id.btnShowHidePassword_SignIn);

        changeBtnColor();

        DB = DatabaseAccess.getInstance(getApplicationContext());
        mAuth = FirebaseAuth.getInstance();

        // Nhận extra từ Intent
        boolean hideLaunchScreen = getIntent().getBooleanExtra("hideLaunchScreen", false);

        // Ẩn layout launch_screen.xml nếu được yêu cầu từ SignUp Activity
        if (hideLaunchScreen) {
            progressBar.setVisibility(View.GONE);
            signInLayout.setVisibility(View.VISIBLE);
        } else {
            // Ẩn layout sign_in.xml ban đầu
            signInLayout.setVisibility(View.GONE);
            // Nếu không được yêu cầu, tiến hành mô phỏng tiến trình
            simulateProgress();
        }

        signUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                isConnectedToInternet = isInternetConnected();
                String email = edEmail.getText().toString().trim();
                String matkhau = edPassword.getText().toString().trim();
                // Kiểm tra kết nối internet
                if (!isConnectedToInternet) {
                    // Nếu không có kết nối internet, kiểm tra đăng nhập bằng SQLite
                    if (DB.checkLoginSQLite(email, matkhau)) {
                        Toast.makeText(getApplicationContext(),
                                        "Đăng nhập thành công!!",
                                        Toast.LENGTH_LONG)
                                .show();
                        // Nếu đăng nhập thành công từ SQLite, chuyển đến MainActivity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        return;
                    } else {
                        // Nếu không đăng nhập thành công từ SQLite, thông báo cho người dùng
                        Toast.makeText(getApplicationContext(),
                                        "Sai Email hoặc mật khẩu!!",
                                        Toast.LENGTH_LONG)
                                .show();
                        return;
                    }
                }

                // Nếu có kết nối internet, tiến hành đăng nhập bằng Firebase

                // validations for input email and password // check th trong
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(),
                                    "Hãy nhập Email của bạn!!",
                                    Toast.LENGTH_LONG)
                            .show();
                    return;
                }

                if (TextUtils.isEmpty(matkhau)) {
                    Toast.makeText(getApplicationContext(),
                                    "Hãy nhập mật khẩu của bạn!!",
                                    Toast.LENGTH_LONG)
                            .show();
                    return;
                }

                // signin existing user

                mAuth.signInWithEmailAndPassword(email, matkhau)
                        .addOnCompleteListener(
                                new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(
                                            @NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getApplicationContext(),
                                                            "Đăng nhập thành công!!",
                                                            Toast.LENGTH_LONG)
                                                    .show();

                                            DB.iduser = mAuth.getCurrentUser().getUid();
                                            DB.CapNhatUser(mAuth.getCurrentUser().getUid());
                                            // hide the progress bar
                                            // if sign-in is successful
                                            // intent to home activity
                                            Intent intent = new Intent(LoginActivity.this,
                                                    MainActivity.class);
                                            startActivity(intent);
                                        } else {
                                            // sign-in failed
                                            Toast.makeText(getApplicationContext(),
                                                            "Sai Email hoặc mật khẩu!!",
                                                            Toast.LENGTH_LONG)
                                                    .show();
                                        }
                                    }
                                });
            }
        });

        // Bắt sự kiện nhấn nút để chuyển đổi giữa hiển thị và ẩn mật khẩu
        btnShowHidePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    // Nếu mật khẩu đang hiển thị, chuyển đổi về ẩn mật khẩu
                    edPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    btnShowHidePassword.setImageResource(R.drawable.icon_visible_off); // Đặt icon là mắt đóng
                } else {
                    // Nếu mật khẩu đang ẩn, chuyển đổi về hiển thị mật khẩu
                    edPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    btnShowHidePassword.setImageResource(R.drawable.icon_visible); // Đặt icon là mắt mở
                }

                // Đặt con trỏ về cuối chuỗi để không làm mất vị trí hiện tại của người dùng
                edPassword.setSelection(edPassword.getText().length());
            }
        });

    }

    private void simulateProgress() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            int progress = 0;

            @Override
            public void run() {
                progress += 5; // Tăng giá trị tiến trình
                progressBar.setProgress(progress);

                if (progress < MAX_PROGRESS) {
                    // Nếu tiến trình chưa đạt 100%, tiếp tục cập nhật
                    handler.postDelayed(this, PROGRESS_INTERVAL);
                } else {
                    // Nếu tiến trình đạt 100%, ẩn progressBar và hiển thị layout sign_in.xml
                    progressBar.setVisibility(View.GONE);
                    signInLayout.setVisibility(View.VISIBLE);
                }
            }
        }, PROGRESS_INTERVAL);
    }

    private void changeBtnColor() {
        // Khai báo một TextWatcher để theo dõi sự thay đổi trên các EditText
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No need to implement anything here
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Kiểm tra xem tất cả các EditText đã nhập đầy đủ thông tin chưa
                String email = edEmail.getText().toString().trim();
                String matkhau = edPassword.getText().toString().trim();

                // Nếu tất cả các EditText đều đã được nhập, thay đổi màu nền và màu chữ của button
                if (!email.isEmpty() && !matkhau.isEmpty()) {
                    // Lấy màu từ tài nguyên màu trong tệp colors.xml
                    int rectangle6Color = ContextCompat.getColor(LoginActivity.this, R.color.rectangle_6_color);
                    btnSignIn.setBackgroundTintList(ColorStateList.valueOf(rectangle6Color));
                    btnSignIn.setTextColor(Color.WHITE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No need to implement anything here
            }
        };

        // Áp dụng TextWatcher cho từng EditText
        edEmail.addTextChangedListener(textWatcher);
        edPassword.addTextChangedListener(textWatcher);
    }

    // Phương thức kiểm tra kết nối internet
    private boolean isInternetConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
                return true;
            }
        }
        return false;
    }


}