package illiyin.mhandharbeni.burgertahucustomer.fragment.sub;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import illiyin.mhandharbeni.burgertahucustomer.R;
import illiyin.mhandharbeni.burgertahucustomer.fragment.adapter.MenuItemAdapter;
import illiyin.mhandharbeni.burgertahucustomer.fragment.adapter.decoration.DividerItemDecoration;
import illiyin.mhandharbeni.databasemodule.DatabaseListener;
import illiyin.mhandharbeni.databasemodule.ModelMenu;
import illiyin.mhandharbeni.realmlibrary.Crud;
import illiyin.mhandharbeni.servicemodule.service.intentservice.MenuService;
import io.realm.RealmResults;

/**
 * Created by root on 20/07/17.
 */

public class DetailMenu extends Fragment implements DatabaseListener{
    ModelMenu modelMenu;
    Crud crud;

    List<ModelMenu> listMenu;
    String kategori;

    MenuItemAdapter menuItemAdapter;
    RecyclerView rvMenu;
    TextView txtKategori;
    View v;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().registerReceiver(this.receiver, new IntentFilter("SERVICE MENU"));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.list_menu, container, false);
        kategori=getArguments().getString("kategori");
        modelMenu = new ModelMenu();
        crud = new Crud(getActivity().getApplicationContext(), modelMenu);
        txtKategori = (TextView) v.findViewById(R.id.txtKategori);
        txtKategori.setText(kategori);
        initData();
        listData();
        initLayoutManager();
        return v;
    }

    public void initData(){
        listMenu = new ArrayList<>();
        RealmResults resultMenu = crud.read("kategori", kategori);
        if (resultMenu.size() > 0){
            for (int i=0;i<resultMenu.size();i++){
                modelMenu = (ModelMenu) resultMenu.get(i);
                listMenu.add(new ModelMenu(modelMenu.getId(),
                            modelMenu.getNama(),
                            modelMenu.getGambar(),
                            modelMenu.getHarga(),
                            modelMenu.getKategori(),
                            modelMenu.getSha()
                        ));
            }
        }
    }

    public void initDataAdapter(){
        listMenu = new ArrayList<>();
        RealmResults resultMenu = crud.read("kategori", kategori);
        if (resultMenu.size() > 0){
            for (int i=0;i<resultMenu.size();i++){
                modelMenu = (ModelMenu) resultMenu.get(i);
                listMenu.add(new ModelMenu(modelMenu.getId(),
                        modelMenu.getNama(),
                        modelMenu.getGambar(),
                        modelMenu.getHarga(),
                        modelMenu.getKategori(),
                        modelMenu.getSha()
                ));
            }
        }
//        menuItemAdapter.notifyDataSetChanged();
    }
    public void listData(){
        rvMenu = (RecyclerView) v.findViewById(R.id.listMenu);
        menuItemAdapter = new MenuItemAdapter(getActivity().getApplicationContext(), listMenu);
        rvMenu.setAdapter(menuItemAdapter);
    }
    public void initLayoutManager(){
        int decorPriorityIndex = 0;
        Drawable dividerDrawable = ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.divider_menu);
        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity().getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvMenu.addItemDecoration(dividerItemDecoration, decorPriorityIndex);
        rvMenu.setLayoutManager(llm);
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().registerReceiver(this.receiver, new IntentFilter("SERVICE MENU"));
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
                case "UPDATE MENU":
                    initDataAdapter();
                    menuItemAdapter.swap(listMenu);
                    break;
                case "UPDATE LIST":

                    break;
                default:

                    break;
            }


        }
    };

    @Override
    public void onMenuChange() {
//        Toast.makeText(getActivity().getApplicationContext(), "Changed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOrderChange() {

    }

    @Override
    public void onOutletChange() {

    }
}
