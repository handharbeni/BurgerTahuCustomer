package illiyin.mhandharbeni.realmlibrary;

import android.content.Context;

import illiyin.mhandharbeni.crudrealmmodul.CRUDRealm;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by root on 17/07/17.
 */

public class Crud {
    Context context;
    CRUDRealm crudRealm;
    RealmObject realmObject;
    public Crud(Context context, RealmObject realmObject) {
        this.context = context;
        this.realmObject = realmObject;
        crudRealm = new CRUDRealm(this.context, this.realmObject);
    }

    public void create(RealmObject realmObject){
        crudRealm.create(realmObject);
    }
    public RealmResults read(){
        return crudRealm.read();
    }
    public RealmResults read(String key, String value){
        return crudRealm.read(key, value);
    }
    public void update(String key, String value, RealmObject realmObject){
        crudRealm.update(key, value, realmObject);
    }
    public void delete(String key, String value){
        crudRealm.delete(key, value);
    }
}
