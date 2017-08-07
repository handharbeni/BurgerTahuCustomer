package illiyin.mhandharbeni.burgertahucustomer.fragment_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import illiyin.mhandharbeni.burgertahucustomer.R;
import illiyin.mhandharbeni.networklibrary.CallHttp;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by root on 19/07/17.
 */

public class SignupFragment extends FragmentActivity{
    TextView link_login;
    EditText input_name, input_email, input_mobile, input_password, input_reEnterPassword;
    Button btn_signup;
    CallHttp callHttp;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        callHttp = new CallHttp(getApplicationContext());

        setContentView(R.layout.layout_signup);
        link_login = (TextView) findViewById(R.id.link_login);

        btn_signup = (Button) findViewById(R.id.btn_signup);

        input_name = (EditText) findViewById(R.id.input_name);
        input_email = (EditText) findViewById(R.id.input_email);
        input_mobile = (EditText) findViewById(R.id.input_mobile);
        input_password = (EditText) findViewById(R.id.input_password);
        input_reEnterPassword = (EditText) findViewById(R.id.input_reEnterPassword);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input_password.getText().toString().equalsIgnoreCase(input_reEnterPassword.getText().toString())){
                    /*password sama, lajutkan*/

                    String endUri = getString(R.string.server)+"/users/daftar";
                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("nama", input_name.getText().toString())
                            .addFormDataPart("email", input_email.getText().toString())
                            .addFormDataPart("password", input_password.getText().toString())
                            .build();
                    String response = callHttp.post(endUri, requestBody);
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    /*password tidak sama*/
                    input_password.setError("Password Tidak Sama");
                    input_reEnterPassword.setError("Password Tidak Sama");
                }

            }
        });

        link_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), LoginFragment.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
