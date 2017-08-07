package illiyin.mhandharbeni.burgertahucustomer.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import belka.us.androidtoggleswitch.widgets.BaseToggleSwitch;
import belka.us.androidtoggleswitch.widgets.ToggleSwitch;
import illiyin.mhandharbeni.burgertahucustomer.R;
import illiyin.mhandharbeni.burgertahucustomer.fragment.adapter.MenuItemAdapter;
import illiyin.mhandharbeni.burgertahucustomer.fragment.adapter.OrderAdapter;
import illiyin.mhandharbeni.burgertahucustomer.fragment.adapter.decoration.DividerItemDecoration;
import illiyin.mhandharbeni.burgertahucustomer.fragment.adapter.utils.CartUtil;
import illiyin.mhandharbeni.burgertahucustomer.fragment.sub.activity.DetailOrder;
import illiyin.mhandharbeni.databasemodule.ModelMenu;
import illiyin.mhandharbeni.databasemodule.ModelOrder;
import illiyin.mhandharbeni.realmlibrary.Crud;
import illiyin.mhandharbeni.servicemodule.service.intentservice.MenuService;
import io.realm.RealmObject;
import io.realm.RealmResults;

import static android.content.ContentValues.TAG;

/**
 * Created by root on 19/07/17.
 */

public class FragmentOrder  extends Fragment implements CartUtil {
    ModelOrder modelOrder;
    Crud crud;
    List<ModelOrder> lstOrder;

    View v;

    ToggleSwitch kindOrder;
    RecyclerView listOrder;

    OrderAdapter orderAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        modelOrder = new ModelOrder();
        crud = new Crud(getActivity().getApplicationContext(), modelOrder);
        getActivity().registerReceiver(this.receiver, new IntentFilter("SERVICE ORDER"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_order, container, false);

        kindOrder = (ToggleSwitch) v.findViewById(R.id.kindOrder);
        listOrder = (RecyclerView) v.findViewById(R.id.listOrder);

        kindOrder.setOnToggleSwitchChangeListener(new BaseToggleSwitch.OnToggleSwitchChangeListener() {
            @Override
            public void onToggleSwitchChangeListener(int position, boolean isChecked) {
                initDataOrder(position);
            }
        });

        return v;
    }

    private void initDataOrder(int position){
        lstOrder = new ArrayList<>();
        String[] statusOrder = null;
        if (position == 0){
            statusOrder = new String[]{"1", "2", "3", "4"};
        }else{
            statusOrder = new String[]{"5", "6"};
        }
        RealmResults result = crud.read("status", statusOrder);
        if (result.size() > 0){
            for (int i=0;i<result.size();i++){
                modelOrder = (ModelOrder) result.get(i);
                lstOrder.add(modelOrder);
            }
        }
        listData();
        initLayoutManager();
    }
    public void listData(){
        orderAdapter = new OrderAdapter(getActivity().getApplicationContext(), lstOrder, this);
        listOrder.setAdapter(orderAdapter);
    }
    public void initLayoutManager(){
        LinearLayoutManager llm = new LinearLayoutManager(getActivity().getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listOrder.setLayoutManager(llm);
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().registerReceiver(this.receiver, new IntentFilter("SERVICE ORDER"));
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver((receiver),
                new IntentFilter(MenuService.ACTION_LOCATION_BROADCAST)
        );
    }

    @Override
    public void onPause() {
        crud.closeRealm();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
        super.onPause();
    }

    @Override
    public void onStop() {
        crud.closeRealm();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
        super.onStop();
    }

    @Override
    public void onDestroy() {
        crud.closeRealm();
        getActivity().unregisterReceiver(this.receiver);
        super.onDestroy();
    }
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            String mode = bundle.getString("MODE");
            switch (mode){
                case "UPDATE ORDER":
                    initDataOrder(kindOrder.getCheckedTogglePosition());
                    break;
                default:
                    break;
            }


        }
    };

    @Override
    public void updateInfo() {

    }

    @Override
    public void changeActivity(String value) {
        Intent i = new Intent(getActivity().getApplicationContext(), DetailOrder.class);
        i.putExtra("id_order", Integer.valueOf(value));
        getActivity().startActivity(i);
    }
}
