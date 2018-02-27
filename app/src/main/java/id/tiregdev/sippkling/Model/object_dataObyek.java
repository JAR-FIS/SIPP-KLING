package id.tiregdev.sippkling.Model;

/**
 * Created by Muhammad63 on 2/9/2018.
 */

public class object_dataObyek {
    private String nama, identitas, alamat, lastUpdate, status;

    public object_dataObyek(String nama, String identitas, String alamat, String lastUpdate, String status) {
        this.nama = nama;
        this.identitas = identitas;
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

    public String getIdentitas() {
        return identitas;
    }

    public void setIdentitas(String identitas) {
        this.identitas = identitas;
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
