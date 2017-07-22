package illiyin.mhandharbeni.databasemodule;

import android.content.Context;

import illiyin.mhandharbeni.networklibrary.CallHttp;
import illiyin.mhandharbeni.realmlibrary.Crud;
import io.realm.RealmObject;

/**
 * Created by root on 17/07/17.
 */

public class AdapterModel {
    String server = "";

    String endPointMenu = "";
    String endPointOutlet = "";
    String endPointResto = "";
    String endPointOrder = "";

    Context context;
    Crud crud;
    CallHttp callHttp;

    public AdapterModel(Context context) {
        this.context = context;
        callHttp = new CallHttp(context);
    }
    public void syncDataMenu(RealmObject realmObject){
        crud = new Crud(context, realmObject);

        /*get data from server*/
        String response = callHttp.get("");
        /*cek apakah sudah tersedia didalam database*/

        /*jika sudah maka cek sha*/

        /*jika sha tidak sama maka update data*/

        /*jika sama biarkan*/

        /*jika belum ada dalam database maka insert data baru tersebut*/
    }
    public void syncDataOutlet(RealmObject realmObject){
        crud = new Crud(context, realmObject);

        /*get data from server*/
        String response = callHttp.get("");
        /*cek apakah sudah tersedia didalam database*/

        /*jika sudah maka cek sha*/

        /*jika sha tidak sama maka update data*/

        /*jika sama biarkan*/

        /*jika belum ada dalam database maka insert data baru tersebut*/
    }
    public void syncDataResto(){
        /*get data from server*/

        /*cek apakah sudah tersedia didalam database*/

        /*jika sudah maka cek sha*/

        /*jika sha tidak sama maka update data*/

        /*jika sama biarkan*/

        /*jika belum ada dalam database maka insert data baru tersebut*/
    }
    public void syncOrder(){
        /*get data from server*/

        /*cek apakah sudah tersedia didalam database*/

        /*jika sudah maka cek sha*/

        /*jika sha tidak sama maka update data*/

        /*jika sama biarkan*/

        /*jika belum ada dalam database maka insert data baru tersebut*/
    }
}
