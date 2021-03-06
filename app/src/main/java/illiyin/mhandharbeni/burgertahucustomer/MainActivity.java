package illiyin.mhandharbeni.burgertahucustomer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import illiyin.mhandharbeni.burgertahucustomer.fragment_activity.LoginFragment;
import illiyin.mhandharbeni.burgertahucustomer.fragment_activity.MainFragment;
import illiyin.mhandharbeni.realmlibrary.Crud;
import illiyin.mhandharbeni.servicemodule.ServiceAdapter;
import illiyin.mhandharbeni.sessionlibrary.Session;
import illiyin.mhandharbeni.sessionlibrary.SessionListener;
import illiyin.mhandharbeni.utilslibrary.CheckConnection;
import illiyin.mhandharbeni.utilslibrary.LoadImage;
import illiyin.mhandharbeni.utilslibrary.NiceDialog;
import illiyin.mhandharbeni.utilslibrary.NiceDialogListener;
import illiyin.mhandharbeni.utilslibrary.SnackBar;

public class MainActivity extends CheckConnection implements SessionListener{
    public String TAG = getClass().getSimpleName().toString();
    Crud crud;
    NiceDialog niceDialog;
    LoadImage loadImage;
//    ServiceAdapter serviceAdapter;
    SnackBar snackBar;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init(this);

//        serviceAdapter = new ServiceAdapter(this);
        niceDialog = new NiceDialog(this, new NiceDialogListener() {
            @Override
            public void positiveClicked(int code) {

            }

            @Override
            public void negativeClicked(int code) {

            }
        });
        session = new Session(getApplicationContext(), this);
        loadImage = new LoadImage(this);
        snackBar = new SnackBar(this, this);

        startService();
        setInitView();
    }
    public void startService(){
//        serviceAdapter.startService();
    }
    public void setInitView(){
        if (session.checkSession()){
            /*main*/
            Intent intent = new Intent(this, MainFragment.class);
            startActivity(intent);
            finish();
        }else{
            /*login*/
            Intent intent = new Intent(this, LoginFragment.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void sessionChange() {
        setInitView();
    }

    @Override
    protected void onDestroy() {
        session.destroyListener();
        super.onDestroy();
    }
}
