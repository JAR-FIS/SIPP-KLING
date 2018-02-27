package id.tiregdev.sippkling.Model;

/**
 * Created by Muhammad63 on 2/9/2018.
 */

public class object_jasaboga {
    private String nama, nomorObyek, alamat, lastUpdate, status;

    public object_jasaboga(String nama, String nomorObyek, String alamat, String lastUpdate, String status) {
        this.nama = nama;
        this.nomorObyek = nomorObyek;
        this.alamat = alamat;
        this.lastUpdate = lastUpdate;
        this.status = status;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNomorObyek() {
        return nomorObyek;
    }

    public void setNomorObyek(String nomorObyek) {
        this.nomorObyek = nomorObyek;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
