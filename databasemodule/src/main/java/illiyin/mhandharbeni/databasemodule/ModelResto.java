package illiyin.mhandharbeni.databasemodule;

import io.realm.RealmObject;

/**
 * Created by root on 17/07/17.
 */

public class ModelResto extends RealmObject {
    String id, resto;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResto() {
        return resto;
    }

    public void setResto(String resto) {
        this.resto = resto;
    }
}
