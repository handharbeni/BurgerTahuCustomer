package illiyin.mhandharbeni.sessionlibrary;

import android.content.Context;

import com.pddstudio.preferences.encrypted.EncryptedPreferences;

/**
 * Created by root on 17/07/17.
 */

public class Session {
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
        encryptedPreferences = new EncryptedPreferences.Builder(this.context)
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
    public boolean checkSession(){
        return !(encryptedPreferences.getUtils().decryptStringValue(encryptedPreferences.getString(KEY, "0")).equalsIgnoreCase("0")
                && encryptedPreferences.getString(STATUS, "0").equalsIgnoreCase("0"));
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
