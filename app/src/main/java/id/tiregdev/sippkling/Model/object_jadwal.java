package id.tiregdev.sippkling.Model;

/**
 * Created by Muhammad63 on 2/8/2018.
 */

public class object_jadwal {
    private String titleJadwal, isiJadwal;

    public object_jadwal(String titleJadwal, String isiJadwal) {
        this.titleJadwal = titleJadwal;
        this.isiJadwal = isiJadwal;
    }

    public String getTitleJadwal() {
        return titleJadwal;
    }

    public void setTitleJadwal(String titleJadwal) {
        this.titleJadwal = titleJadwal;
    }

    public String getIsiJadwal() {
        return isiJadwal;
    }

    public void setIsiJadwal(String isiJadwal) {
        this.isiJadwal = isiJadwal;
    }
}
