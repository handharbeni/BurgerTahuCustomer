package illiyin.mhandharbeni.burgertahucustomer.fragment.sub;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import illiyin.mhandharbeni.burgertahucustomer.R;
import illiyin.mhandharbeni.databasemodule.ModelMenu;
import illiyin.mhandharbeni.realmlibrary.Crud;
import io.realm.RealmResults;

/**
 * Created by root on 20/07/17.
 */

public class DetailMenu extends Fragment {
    ModelMenu modelMenu;
    Crud crud;

    List<ModelMenu> listMenu;
    String kategori;

    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);
        kategori=getArguments().getString("kategori");
        crud = new Crud(getActivity().getApplicationContext(), modelMenu);
        initData();
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
    public void listData(){
        
    }
}
