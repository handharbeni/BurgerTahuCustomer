package illiyin.mhandharbeni.databasemodule;

import io.realm.RealmObject;

/**
 * Created by root on 22/07/17.
 */

public class ModelOrder extends RealmObject {
    int id, id_user, id_kurir;
    String alamat, latitude, longitude, tanggal_waktu, status, keterangan, delivery_fee;
    String sha;

    public ModelOrder(int id, int id_user, int id_kurir, String alamat, String latitude, String longitude, String tanggal_waktu, String status, String keterangan, String delivery_fee, String sha) {
        this.id = id;
        this.id_user = id_user;
        this.id_kurir = id_kurir;
        this.alamat = alamat;
        this.latitude = latitude;
        this.longitude = longitude;
        this.tanggal_waktu = tanggal_waktu;
        this.status = status;
        this.keterangan = keterangan;
        this.delivery_fee = delivery_fee;
        this.sha = sha;
    }

    public ModelOrder() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_kurir() {
        return id_kurir;
    }

    public void setId_kurir(int id_kurir) {
        this.id_kurir = id_kurir;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTanggal_waktu() {
        return tanggal_waktu;
    }

    public void setTanggal_waktu(String tanggal_waktu) {
        this.tanggal_waktu = tanggal_waktu;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getDelivery_fee() {
        return delivery_fee;
    }

    public void setDelivery_fee(String delivery_fee) {
        this.delivery_fee = delivery_fee;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }
}
