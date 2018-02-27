package id.tiregdev.sippkling.Model;

/**
 * Created by Muhammad63 on 2/8/2018.
 */

public class object_reward {
    private String nmrPeringkat, nama, kec, kel, jumlahData;

    public object_reward(String nmrPeringkat, String nama, String kec, String kel, String jumlahData) {
        this.nmrPeringkat = nmrPeringkat;
        this.nama = nama;
        this.kec = kec;
        this.kel = kel;
        this.jumlahData = jumlahData;
    }

    public String getNmrPeringkat() {
        return nmrPeringkat;
    }

    public void setNmrPeringkat(String nmrPeringkat) {
        this.nmrPeringkat = nmrPeringkat;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKec() {
        return kec;
    }

    public void setKec(String kec) {
        this.kec = kec;
    }

    public String getKel() {
        return kel;
    }

    public void setKel(String kel) {
        this.kel = kel;
    }

    public String getJumlahData() {
        return jumlahData;
    }

    public void setJumlahData(String jumlahData) {
        this.jumlahData = jumlahData;
    }
}
