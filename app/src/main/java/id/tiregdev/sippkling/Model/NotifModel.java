package id.tiregdev.sippkling.Model;

/**
 * Created by Muhammad63 on 2/7/2018.
 */

public class NotifModel {

    private String judul, perihal, waktu;

    public NotifModel(String judul, String perihal, String waktu) {
        this.judul = judul;
        this.perihal = perihal;
        this.waktu = waktu;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPerihal() {
        return perihal;
    }

    public void setPerihal(String perihal) {
        this.perihal = perihal;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }
}
