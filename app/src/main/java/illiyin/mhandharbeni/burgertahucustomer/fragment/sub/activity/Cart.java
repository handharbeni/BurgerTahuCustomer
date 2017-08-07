package illiyin.mhandharbeni.burgertahucustomer.fragment.sub.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.schibstedspain.leku.LocationPickerActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import illiyin.mhandharbeni.burgertahucustomer.R;
import illiyin.mhandharbeni.burgertahucustomer.fragment.adapter.CartItemAdapter;
import illiyin.mhandharbeni.burgertahucustomer.fragment.adapter.decoration.DividerItemDecoration;
import illiyin.mhandharbeni.burgertahucustomer.fragment.adapter.utils.CartUtil;
import illiyin.mhandharbeni.databasemodule.AdapterModel;
import illiyin.mhandharbeni.databasemodule.ModelCart;
import illiyin.mhandharbeni.databasemodule.ModelOutlet;
import illiyin.mhandharbeni.networklibrary.CallHttp;
import illiyin.mhandharbeni.realmlibrary.Crud;
import illiyin.mhandharbeni.sessionlibrary.Session;
import illiyin.mhandharbeni.sessionlibrary.SessionListener;
import io.realm.RealmResults;

/**
 * Created by root on 25/07/17.
 */

public class Cart extends AppCompatActivity implements CartUtil, SessionListener{
    private Integer RESULT_OK = -1, RESULT_CANCEL=0;
    private static String LATDESTI = "LATDESTI";
    private static String LONGDESTI = "LONGDESTI";
    private static String ADDRESSDESTI = "ADDRESDESTI";
    private static String ZIPCODEDESTI = "ZIPCODEDESTI";
    private static String DISTANCE = "DISTANCE", DELIVERYFEE = "DELIVERYFEE", FEEDELIV = "FEEDELIV";
    private String ADDRESS = "ADDRESS";

    private static final String TAG = "CartActivity";
    private ImageView imgBack;
    private Crud crudCart;
    private ModelCart modelCart;

    private Crud crudOutlet;
    private ModelOutlet modelOutlet;

    private List<ModelCart> modelCartList;

    private RecyclerView listCart;
    private TextView changeDestination;

    private Session session;

    private List<ModelAddress> outlet;
    private List<ModelAddress> sortOutlet;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        modelCart = new ModelCart();
        crudCart = new Crud(this, modelCart);
        modelOutlet = new ModelOutlet();
        crudOutlet = new Crud(this, modelOutlet);
        session = new Session(getApplicationContext(), this);
        setContentView(R.layout.layout_cart);

        listCart = (RecyclerView) findViewById(R.id.listCart) ;

        changeDestination = (TextView) findViewById(R.id.changeDestination);
        changeDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickLocation();
            }
        });

        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        AdapterModel adapterModel = new AdapterModel(this);
        adapterModel.getFeeDeliv();

        initOutlet();
        initData();
        initAdapter();
        initLayoutManager();
        setInfoOutlet();
        updateInfo();
    }
    public void initOutlet(){
        outlet = new ArrayList<>();
        RealmResults results = crudOutlet.read();

        if (results.size()>0){
            for (int i=0;i<results.size();i++){
                ModelOutlet mo = (ModelOutlet) results.get(i);
                Integer random = new Random().nextInt(10) + 1;
                String alamat = mo.getAlamat();
                String id_oulet = String.valueOf(mo.getId());
                Double latitude = Double.valueOf(mo.getLatitude());
                Double longitude = Double.valueOf(mo.getLongitude());
                outlet.add(new ModelAddress(random, alamat, id_oulet, latitude, longitude));
            }
        }
//        testStringOutlet();
    }
    public void testStringOutlet(){
        Collections.sort(outlet, new Comparator<ModelAddress>() {
            @Override
            public int compare(ModelAddress o1, ModelAddress o2) {
                return o1.getDistance().compareTo(o2.getDistance());
            }
        });
        for (int i=0;i<outlet.size();i++){
            Log.d(TAG, "testStringOutlet: "+outlet.get(i).getDistance()+"/"+outlet.get(i).getAlamat());
        }
    }
    public void initData(){
        modelCartList = new ArrayList<>();
        RealmResults result = crudCart.read();
        if (result.size() > 0){
            for (int i=0;i<result.size();i++){
                ModelCart model = (ModelCart) result.get(i);
                modelCartList.add(model);
            }
        }
    }

    public void initAdapter(){
        listCart = (RecyclerView) findViewById(R.id.listCart);
        CartItemAdapter ma = new CartItemAdapter(getApplicationContext(), modelCartList, this);
        listCart.setAdapter(ma);
        listCart.setNestedScrollingEnabled(false);
    }
    public void initLayoutManager() {
        int decorPriorityIndex = 0;
        Drawable dividerDrawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.divider_menu);
        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listCart.addItemDecoration(dividerItemDecoration, decorPriorityIndex);
        listCart.setLayoutManager(llm);
    }

    @Override
    public void updateInfo() {
        RealmResults results = crudCart.read();
        int total_harga_item = 0;
        int biaya_kirim = (session.getCustomParams(DISTANCE, 0)/1000) * session.getCustomParams(FEEDELIV, 0);
        session.setCustomParams(DELIVERYFEE, biaya_kirim);
        int total_harga = 0;
        if (results.size() > 0){
            for (int i=0;i<results.size();i++){
                ModelCart modelCart = (ModelCart) results.get(i);
                int total = 0;
                total = modelCart.getJumlah() * Integer.valueOf(modelCart.getHarga());
                total_harga_item += total;
            }
        }

        total_harga = total_harga_item + biaya_kirim;

        TextView txtTotalHargaItem = (TextView) findViewById(R.id.totalhargaitem);
        TextView txtDeliveryFee = (TextView) findViewById(R.id.deliveryfee);
        TextView txtTotalHarga = (TextView) findViewById(R.id.totalharga);

        txtTotalHargaItem.setText(illiyin.mhandharbeni.utilslibrary.NumberFormat.format(Double.valueOf(String.valueOf(total_harga_item))));
        txtDeliveryFee.setText(illiyin.mhandharbeni.utilslibrary.NumberFormat.format(Double.valueOf(String.valueOf(biaya_kirim))));
        txtTotalHarga.setText(illiyin.mhandharbeni.utilslibrary.NumberFormat.format(Double.valueOf(String.valueOf(total_harga))));

        TextView totalSemua = (TextView) findViewById(R.id.totalSemua);
        totalSemua.setText(illiyin.mhandharbeni.utilslibrary.NumberFormat.format(Double.valueOf(String.valueOf(total_harga))));
    }

    @Override
    public void changeActivity(String value) {

    }


    @Override
    public void sessionChange() {
        updateInfo();
    }
    public void pickLocation(){

        AdapterModel adapterModel = new AdapterModel(this);
        adapterModel.getFeeDeliv();

        Intent intent = new LocationPickerActivity.Builder()
                .withLocation(Double.valueOf(session.getCustomParams(LATDESTI, "-7.9826195")), Double.valueOf(session.getCustomParams(LONGDESTI, "112.6287")))
                .withGeolocApiKey(getString(R.string.google_maps_key))
                .withSearchZone("id_ID")
                .shouldReturnOkOnBackPressed()
                .build(getApplicationContext());
        startActivityForResult(intent, 1);
    }
    public void calculateDistance(){
        sortOutlet = new ArrayList<>();
        illiyin.mhandharbeni.utilslibrary.Address address = new illiyin.mhandharbeni.utilslibrary.Address(this);
        for (int i=0;i<outlet.size();i++){
            Double latOutlet = outlet.get(i).getLatitude();
            Double longOutlet = outlet.get(i).getLongitude();
            String addres1 = address.getCurrentAddress(latOutlet, longOutlet);

            Double latdesti = Double.valueOf(session.getCustomParams(LATDESTI, "-7.9826195"));
            Double longdesti = Double.valueOf(session.getCustomParams(LONGDESTI, "112.6287"));
            String addres2 = address.getCurrentAddress(latdesti, longdesti);

            String id_outlet = outlet.get(i).getId_outlet();
            Integer distance = address.getDistance(addres1, addres2);

            sortOutlet.add(new ModelAddress(distance, outlet.get(i).getAlamat(), id_outlet, latOutlet, longOutlet));
        }
        sortOutlets();
        setInfoOutlet();
        updateInfo();
    }
    public void sortOutlets(){
        Collections.sort(sortOutlet, new Comparator<ModelAddress>() {
            @Override
            public int compare(ModelAddress o1, ModelAddress o2) {
                return o1.getDistance().compareTo(o2.getDistance());
            }
        });
        session.setCustomParams(DISTANCE, sortOutlet.get(0).getDistance());
        session.setCustomParams(ADDRESS, sortOutlet.get(0).getAlamat());
    }
    public void setInfoOutlet(){
        TextView detailOutlet, address, distance;
        detailOutlet = (TextView) findViewById(R.id.detailOutlet);
        address = (TextView) findViewById(R.id.address);
        distance = (TextView) findViewById(R.id.distance);

        detailOutlet.setText(session.getCustomParams(ADDRESS, "Burger Tahu Malang Suhat 2"));
        address.setText(session.getCustomParams(ADDRESSDESTI, "Malang"));
        distance.setText(String.valueOf(session.getCustomParams(DISTANCE, 0)/1000)+" Km");
    }
    public void testsortoutlet(){
        Collections.sort(sortOutlet, new Comparator<ModelAddress>() {
            @Override
            public int compare(ModelAddress o1, ModelAddress o2) {
                return o1.getDistance().compareTo(o2.getDistance());
            }
        });
        for (int i=0;i<sortOutlet.size();i++){
            Log.d(TAG, "testStringOutlet: "+sortOutlet.get(i).getDistance()+"/"+sortOutlet.get(i).getAlamat()+"/"+sortOutlet.get(i).getId_outlet());
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (resultCode == RESULT_OK){
                double latitude = data.getDoubleExtra(LocationPickerActivity.LATITUDE, 0);
                double longitude = data.getDoubleExtra(LocationPickerActivity.LONGITUDE, 0);
                String address = data.getStringExtra(LocationPickerActivity.LOCATION_ADDRESS);
                String postalcode = data.getStringExtra(LocationPickerActivity.ZIPCODE);

                session.setCustomParams(LATDESTI, String.valueOf(latitude));
                session.setCustomParams(LONGDESTI, String.valueOf(longitude));
                session.setCustomParams(ADDRESSDESTI, address);
                session.setCustomParams(ZIPCODEDESTI, postalcode);

                calculateDistance();

                Address fullAddress = data.getParcelableExtra(LocationPickerActivity.ADDRESS);
                if(fullAddress != null)
                    Log.d("FULL ADDRESS****", fullAddress.toString());
            }else if (resultCode == RESULT_CANCEL){

            }
        }
    }
}
