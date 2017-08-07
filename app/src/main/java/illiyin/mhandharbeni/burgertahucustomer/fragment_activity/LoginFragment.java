package illiyin.mhandharbeni.burgertahucustomer.fragment_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import illiyin.mhandharbeni.burgertahucustomer.R;
import illiyin.mhandharbeni.networklibrary.CallHttp;
import illiyin.mhandharbeni.sessionlibrary.Session;
import illiyin.mhandharbeni.sessionlibrary.SessionListener;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by root on 19/07/17.
 */

public class LoginFragment extends FragmentActivity implements SessionListener{
    TextView link_signup;
    TextView input_email, input_password;

    Button btn_login;
    CallHttp callHttp;
    Session session;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new Session(getApplicationContext(), this);
        callHttp = new CallHttp(getApplicationContext());
        setContentView(R.layout.layout_login);
        input_email = (TextView) findViewById(R.id.input_email);
        input_password = (TextView) findViewById(R.id.input_password);
        btn_login = (Button) findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_login.setEnabled(false);
                String endUri = getString(R.string.server)+"/users/login";
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("email", input_email.getText().toString())
                        .addFormDataPart("password", input_password.getText().toString())
                        .build();
                String response = callHttp.post(endUri, requestBody);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean returns = jsonObject.getBoolean("return");
                    if (returns){
                        btn_login.setEnabled(true);
                        /*login berhasil*/
                        String nama = jsonObject.getString("nama");
                        String email = jsonObject.getString("email");
                        String token = jsonObject.getString("access_token");

                        session.setSession(nama, "", "", email, token, "1");
                    }
                } catch (JSONException e) {
                    btn_login.setEnabled(true);
                    e.printStackTrace();
                }
            }
        });

        link_signup = (TextView) findViewById(R.id.link_signup);
        link_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), SignupFragment.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    @Override
    public void sessionChange() {
        if (session.checkSession()){
            Intent intent = new Intent(this, MainFragment.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        session.destroyListener();
        super.onDestroy();
    }

    
}
