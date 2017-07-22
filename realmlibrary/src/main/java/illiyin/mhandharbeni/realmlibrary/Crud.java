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
    public RealmObject getRealmObject(String key, String value, RealmObject realmObject){
        RealmObject objectUpdate = crudRealm.setObjectUpdate(key, value, realmObject);
        return objectUpdate;
    }
    public void update(RealmObject realmObject){
        crudRealm.update(realmObject);
    }
    public void delete(String key, String value){
        crudRealm.delete(key, value);
    }
    public Boolean checkDuplicate(String key, String value){
        RealmResults realmResults = crudRealm.read(key, value);
        if (realmResults.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
