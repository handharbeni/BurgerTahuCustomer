package illiyin.mhandharbeni.servicemodule.service.intentservice;

import android.app.IntentService;
import android.content.Intent;

import illiyin.mhandharbeni.databasemodule.AdapterModel;
import illiyin.mhandharbeni.databasemodule.ModelMenu;

/**
 * Created by root on 17/07/17.
 */

public class MenuService extends IntentService {
    ModelMenu modelMenu;
    AdapterModel adapterModel;
    public MenuService() {
        super("Menu Service");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        modelMenu = new ModelMenu();
        adapterModel = new AdapterModel(getBaseContext(), modelMenu);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        adapterModel.syncDataMenu();
    }
}
