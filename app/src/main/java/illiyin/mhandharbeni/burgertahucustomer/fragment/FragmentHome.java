package illiyin.mhandharbeni.burgertahucustomer.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.HashMap;

import illiyin.mhandharbeni.burgertahucustomer.R;
import illiyin.mhandharbeni.burgertahucustomer.fragment.sub.DetailMenu;

/**
 * Created by root on 19/07/17.
 */

public class FragmentHome  extends Fragment {
    View v;
    private SliderLayout mDemoSlider;
    private ImageView kategoriMakanan, kategoriMinuman;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);
        initBanner();
        initDataKategori();
        return v;
    }
    public void initDataKategori(){
        kategoriMakanan = (ImageView) v.findViewById(R.id.kategoriMakanan);
        kategoriMinuman = (ImageView) v.findViewById(R.id.kategoriMinuman);

        kategoriMakanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ((MainActivity)getActivity()).changeTitle("Makanan");
//                showListMenu("Makanan");
            }
        });
        kategoriMinuman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ((MainActivity)getActivity()).changeTitle("Minuman");
//                showListMenu("Minuman");
            }
        });
    }
    public void showListMenu(String kategori){
        Bundle bundle = new Bundle();
        bundle.putString("kategori", kategori);

        Fragment fragment = new DetailMenu();
        fragment.setArguments(bundle);

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_container, fragment);
        ft.commit();
    }

    public void initBanner(){
        mDemoSlider = (SliderLayout)v.findViewById(R.id.slider);

        HashMap<String,String> url_maps = new HashMap<>();
        url_maps.put("Order Online Burger Tahu Suhat 2 Malang", "https://uangteman.com/blog/wp-content/uploads/2016/05/Ojek-Online.png");
        url_maps.put("Sehatnya Burger Tahu", "https://media.foody.id/res/g6/50948/prof/s/foody-mobile-burger-tahu-ananas-m-258-635978236168440082.jpg");
//        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
//        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");

        for(String name : url_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(getActivity().getApplicationContext());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {

                        }
                    });
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}
