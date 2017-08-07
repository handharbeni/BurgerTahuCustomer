package illiyin.mhandharbeni.burgertahucustomer.fragment.sub.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import illiyin.mhandharbeni.burgertahucustomer.R;
import illiyin.mhandharbeni.burgertahucustomer.fragment.adapter.MenuItemAdapter;
import illiyin.mhandharbeni.burgertahucustomer.fragment.adapter.MenuItemOrderAdapter;
import illiyin.mhandharbeni.burgertahucustomer.fragment.adapter.decoration.DividerItemDecoration;
import illiyin.mhandharbeni.databasemodule.ItemOrder;
import illiyin.mhandharbeni.databasemodule.ModelOrder;
import illiyin.mhandharbeni.networklibrary.CallHttp;
import illiyin.mhandharbeni.realmlibrary.Crud;
import illiyin.mhandharbeni.sessionlibrary.Session;
import illiyin.mhandharbeni.sessionlibrary.SessionListener;
import illiyin.mhandharbeni.utilslibrary.NiceDialog;
import illiyin.mhandharbeni.utilslibrary.NiceDialogListener;
import io.realm.RealmResults;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by root on 03/08/17.
 */

public class DetailOrder extends AppCompatActivity implements SessionListener{
    Bundle params;
    Integer id_order;

    ItemOrder itemOrder;
    Crud crudItemOrder;

    ModelOrder modelOrder;
    Crud crudModelOrder;

    MenuItemOrderAdapter menuItemOrderAdapter;
    List<ItemOrder> itemOrderList;

    RecyclerView listCart;

    TextView address, distance, addnotes, totalhargaitem, deliveryfee, totalharga, totalSemua;

    Button btnDone, btnTracking;

    ImageView imgBack;

    /*customer*/
    String nama, status, totalbelanja, email_customer, alamat_customer, delivery_fee, keterangan, tanggal, jam, latitude, longitude;

    /*outlet*/
    Integer id_kurir;
    String nama_outlet, alamat_outlet, latitude_outlet, longitude_outlet;

    /*session init*/
    Session session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new Session(this, this);
        params = getIntent().getExtras();
        id_order = 0;
        if(params != null)
            id_order = params.getInt("id_order");

        setContentView(R.layout.layout_detail_order);
        initLayout();
        initDataItem(id_order);
        initDataMaster(id_order);
        setLayout(status);

    }

    public void initLayout(){
        address = (TextView) findViewById(R.id.address);
        distance = (TextView) findViewById(R.id.distance);
        addnotes = (TextView) findViewById(R.id.notesaddres);
        totalhargaitem = (TextView) findViewById(R.id.totalhargaitem);
        deliveryfee = (TextView) findViewById(R.id.deliveryfee);
        totalharga = (TextView) findViewById(R.id.totalharga);
        totalSemua = (TextView) findViewById(R.id.totalSemua);

        btnDone = (Button) findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpConfirmation();
            }
        });
        btnTracking = (Button) findViewById(R.id.btnTracking);

        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void initDataItem(Integer id_order){
        itemOrderList = new ArrayList<>();
        listCart = (RecyclerView) findViewById(R.id.listCart);

        itemOrder = new ItemOrder();
        crudItemOrder = new Crud(this, itemOrder);
        RealmResults results = crudItemOrder.read("id_order", id_order);
        if (results.size() > 0){
            for (int i=0;i<results.size();i++){
                ItemOrder items = (ItemOrder) results.get(i);
                itemOrderList.add(items);
            }
        }
        menuItemOrderAdapter = new MenuItemOrderAdapter(getApplicationContext(), itemOrderList);
        listCart.setAdapter(menuItemOrderAdapter);

        int decorPriorityIndex = 0;
        Drawable dividerDrawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.divider_menu);
        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listCart.addItemDecoration(dividerItemDecoration, decorPriorityIndex);
        listCart.setLayoutManager(llm);
    }
    public void initDataMaster(Integer id_order){
        modelOrder = new ModelOrder();
        crudModelOrder = new Crud(this, modelOrder);
        RealmResults results = crudModelOrder.read("id", id_order);
        id_kurir = 0;
        if (results.size() > 0){
            ModelOrder order = (ModelOrder) results.get(0);
            nama = order.getNama_customer();
            status = order.getStatus();
            totalbelanja = order.getTotal_belanja()!=null?order.getTotal_belanja():"0";
            email_customer = order.getEmail_customer();
            alamat_customer = order.getAlamat_customer();
            latitude = order.getLatitude_customer();
            longitude = order.getLongitude_customer();
            delivery_fee = order.getDelivery_fee()!=null?order.getDelivery_fee():"0";
            keterangan = order.getKeterangan();
            tanggal = order.getTanggal();
            jam = order.getJam();

            nama_outlet = order.getAlamat();
            alamat_outlet = order.getAlamat();
            latitude_outlet = order.getLatitude();
            longitude_outlet = order.getLongitude();

            id_kurir = order.getId_kurir();
        }
        address.setText(alamat_customer);
        distance.setText("test");
        addnotes.setText(keterangan);
        totalhargaitem.setText(totalbelanja);
        deliveryfee.setText(delivery_fee);
        totalharga.setText(String.valueOf(Integer.valueOf(totalbelanja) + Integer.valueOf(delivery_fee)));
        totalSemua.setText(String.valueOf(Integer.valueOf(totalbelanja) + Integer.valueOf(delivery_fee)));

        session.setTrackKurir(0, String.valueOf(id_kurir), "");

    }
    public void setLayout(String status){
        btnDone.setVisibility(View.GONE);
        btnTracking.setVisibility(View.GONE);
        if (status.equalsIgnoreCase("1")
                || status.equalsIgnoreCase("2")
                || status.equalsIgnoreCase("3")
                || status.equalsIgnoreCase("4")){
            btnDone.setVisibility(View.VISIBLE);
            btnTracking.setVisibility(View.VISIBLE);
        }
    }
    public void getAlamatTujuan(){

    }
    public void getAlamatOutlet(){

    }
    public void getEstimatedTime(){

    }
    public void showRoute(){

    }
    public void popUpConfirmation(){
        NiceDialog niceDialog = new NiceDialog(this, new NiceDialogListener() {
            @Override
            public void positiveClicked(int code) {
                receiveOrder();
            }

            @Override
            public void negativeClicked(int code) {

            }
        });
        niceDialog.standart("Anda sudah menerima pesanan anda?", 102);
    }
    public void receiveOrder(){
        Session session = new Session(this, new SessionListener() {
            @Override
            public void sessionChange() {

            }
        });
        String token = session.getToken();
        String method = "done";

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("token", token)
                .addFormDataPart("method", method)
                .addFormDataPart("id_order", String.valueOf(id_order))
                .build();
        CallHttp callHttp = new CallHttp(this);
        String response = callHttp.post(getString(R.string.server)+"/users/order", requestBody);
        finish();
    }

    @Override
    public void sessionChange() {

    }

    @Override
    protected void onPause() {
        session.setTrackKurir(0, "0", "");
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        session.setTrackKurir(0, "0", "");
        super.onDestroy();
    }
}
