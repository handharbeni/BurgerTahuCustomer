package illiyin.mhandharbeni.servicemodule.service.intentservice;

import android.app.IntentService;
import android.content.Intent;

import illiyin.mhandharbeni.databasemodule.AdapterModel;
import illiyin.mhandharbeni.databasemodule.ModelOutlet;

/**
 * Created by root on 22/07/17.
 */

public class OrderService extends IntentService {
    ModelOutlet modelOutlet;
    AdapterModel adapterModel;
    public OrderService() {
        super("Outlet Service");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        modelOutlet = new ModelOutlet();
        adapterModel = new AdapterModel(getBaseContext());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        adapterModel.syncDataOutlet(modelOutlet);
    }
}