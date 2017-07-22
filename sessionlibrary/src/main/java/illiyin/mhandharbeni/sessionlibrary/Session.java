package illiyin.mhandharbeni.sessionlibrary;

import android.content.Context;

import com.pddstudio.preferences.encrypted.EncryptedPreferences;

/**
 * Created by root on 17/07/17.
 */

public class Session {
    String defaultKey = "aJ5QElpvadHaiz7mcPNPVQx0P3Xxx0P3Xx";
    String NAMA = "NAMA",
            ALAMAT = "ALAMAT",
            NOTELP = "NOTELP",
            EMAIL = "EMAIL",
            KEY = "KEY",
            STATUS = "STATUS",
            CONNECTION = "CONNECTION";
    Context context;
    EncryptedPreferences encryptedPreferences;
    public Session(Context context) {
        this.context = context;
        encryptedPreferences = new EncryptedPreferences.Builder(context)
                .withEncryptionPassword(this.context.getString(R.string.password)).build();
    }

    public void setSession(String nama, String alamat, String notelp, String email, String key, String status){
        encryptedPreferences.edit()
                .putString(NAMA, nama)
                .putString(ALAMAT, alamat)
                .putString(NOTELP, notelp)
                .putString(EMAIL, email)
                .putString(KEY, encryptedPreferences.getUtils().encryptStringValue(key))
                .putString(STATUS, status)
                .apply();
    }
    public Boolean checkSession(){
        if (encryptedPreferences.getUtils().decryptStringValue(
                encryptedPreferences.getString(KEY, defaultKey)
        ).equalsIgnoreCase("NOTHING") && encryptedPreferences.getString(STATUS, "0").equalsIgnoreCase("0")){
            return false;
        }
        return true;
    }
    public void deleteSession(){
        encryptedPreferences.forceDeleteExistingPreferences();
    }

    public void setConnectionState(String state){
        encryptedPreferences.edit().putString(CONNECTION, state).apply();
    }
    public String getConnectionState(){
        return encryptedPreferences.getString(CONNECTION, "0");
    }
}
