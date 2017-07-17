package illiyin.mhandharbeni.burgertahucustomer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import illiyin.mhandharbeni.realmlibrary.Crud;
import illiyin.mhandharbeni.servicemodule.service.MainService;
import illiyin.mhandharbeni.sessionlibrary.Session;
import illiyin.mhandharbeni.utilslibrary.CheckConnection;
import illiyin.mhandharbeni.utilslibrary.LoadImage;
import illiyin.mhandharbeni.utilslibrary.NiceDialog;
import illiyin.mhandharbeni.utilslibrary.SnackBar;

public class MainActivity extends CheckConnection{
    Crud crud;
    NiceDialog niceDialog;
    LoadImage loadImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String urlImage = "http://s3.amazonaws.com/37assets/svn/765-default-avatar.png";

        niceDialog = new NiceDialog(this);
        Session session = new Session(this);

        init(this);

        setContentView(R.layout.activity_main);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        loadImage = new LoadImage(this);
        loadImage.setImage(urlImage, imageView);

        SnackBar snackBar = new SnackBar(this, this);
        snackBar.show(session.getConnectionState());
//        niceDialog.standart("TEST INFO");
//        niceDialog.info("TEST INFO", "Burger Tahu Suhat 2 Malang");
        startService();

    }
    public void startService(){
        if (!MainService.serviceRunning){
            startService(new Intent(getApplicationContext(), MainService.class));
        }
    }
}
