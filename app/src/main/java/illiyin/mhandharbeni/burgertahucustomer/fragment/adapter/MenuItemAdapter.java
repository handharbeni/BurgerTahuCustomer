package illiyin.mhandharbeni.burgertahucustomer.fragment.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ramotion.foldingcell.FoldingCell;
import com.squareup.picasso.Picasso;

import java.util.List;

import illiyin.mhandharbeni.burgertahucustomer.R;
import illiyin.mhandharbeni.databasemodule.ModelCart;
import illiyin.mhandharbeni.databasemodule.ModelMenu;
import illiyin.mhandharbeni.realmlibrary.Crud;
import io.realm.RealmResults;

/**
 * Created by root on 22/07/17.
 */

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.MyViewHolder> {
    private String TAG = "MenuAdapter";
    private List<ModelMenu> menuList;
    private Context mContext;
    private ModelMenu modelMenu;
    private ModelCart modelCart;
    private Crud crudMenu;
    private Crud crudCart;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitleMenu, txtHargaMenu;
        public Button btnAddMenu;
        public ImageView imageMenu;
        public FoldingCell foldingCell;

        public MyViewHolder(View view) {
            super(view);
            txtTitleMenu = (TextView) view.findViewById(R.id.txtTitleMenu);
            txtHargaMenu = (TextView) view.findViewById(R.id.txtHargaMenu);
            btnAddMenu = (Button) view.findViewById(R.id.btnAddMenu);
            imageMenu = (ImageView) view.findViewById(R.id.imageMenu);
            foldingCell = (FoldingCell) view.findViewById(R.id.folding_cell);
        }
    }

    public MenuItemAdapter(Context mContext, List<ModelMenu> menuList) {
        this.mContext = mContext;
        this.menuList = menuList;
        modelCart = new ModelCart();
        modelMenu = new ModelMenu();
        this.crudMenu = new Crud(mContext, modelMenu);
        this.crudCart = new Crud(mContext, modelCart);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final ModelMenu m = menuList.get(position);
        holder.txtTitleMenu.setText(m.getNama());
        holder.txtHargaMenu.setText(m.getHarga());
        Picasso.with(mContext).load(m.getGambar()).placeholder(R.mipmap.ic_launcher).into(holder.imageMenu);
        holder.foldingCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                holder.foldingCell.toggle(false);
            }
        });
        holder.btnAddMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean duplicate = crudCart.checkDuplicate("id", m.getId());
                if (!duplicate){
                    /*insert new*/
                    ModelCart newCart = new ModelCart();
                    newCart.setId(m.getId());
                    newCart.setNama(m.getNama());
                    newCart.setGambar(m.getGambar());
                    newCart.setHarga(m.getHarga());
                    newCart.setKategori(m.getKategori());
                    newCart.setSha(m.getSha());
                    crudCart.create(newCart);
                }else{
                    /*update qty + harga total*/
                    /*get last qty*/
                    int lastQty = 1;
                    RealmResults results = crudCart.read("id", m.getId());
                    ModelCart lastModelCart = (ModelCart) results.get(0);
                    lastQty += lastModelCart.getJumlah();
                    /*update to table*/
                    crudCart.openObject();
                    ModelCart updateObject = (ModelCart) crudCart.getRealmObject("id", m.getId());
                    updateObject.setSha(m.getSha());
                    updateObject.setKategori(m.getKategori());
                    updateObject.setHarga(m.getHarga());
                    updateObject.setGambar(m.getGambar());
                    updateObject.setJumlah(lastQty);
                    updateObject.setNama(m.getNama());
                    crudCart.update(updateObject);
                    crudCart.commitObject();
                    //.updateJumlah(m.getId(), lastQty, Integer.valueOf(m.getHarga())*lastQty);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_menu ,parent, false);
        return new MyViewHolder(v);
    }
    public void swap(List<ModelMenu> datas){
        menuList.clear();
        menuList.addAll(datas);
        notifyDataSetChanged();
    }
}