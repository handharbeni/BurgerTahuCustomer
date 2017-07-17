package illiyin.mhandharbeni.utilslibrary;

import android.content.Context;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;

import com.zplesac.connectionbuddy.ConnectionBuddy;
import com.zplesac.connectionbuddy.ConnectionBuddyConfiguration;
import com.zplesac.connectionbuddy.interfaces.ConnectivityChangeListener;
import com.zplesac.connectionbuddy.models.ConnectivityEvent;
import com.zplesac.connectionbuddy.models.ConnectivityState;

import illiyin.mhandharbeni.sessionlibrary.Session;

/**
 * Created by root on 17/07/17.
 */

public class CheckConnection extends AppCompatActivity implements ConnectivityChangeListener{
    Context context;
    Session session;
    public void init(Context context){
        this.context = context;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ConnectionBuddyConfiguration networkInspectorConfiguration = new ConnectionBuddyConfiguration.Builder(this.context).build();
        ConnectionBuddy.getInstance().init(networkInspectorConfiguration);
    }
    @Override
    public void onConnectionChange(ConnectivityEvent event) {
        session = new Session(context);
        if(event.getState().getValue() == ConnectivityState.CONNECTED){
            session.setConnectionState("1");
        }else{
            session.setConnectionState("0");
        }
    }
}
