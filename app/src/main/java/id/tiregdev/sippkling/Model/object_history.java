package id.tiregdev.sippkling.Model;

/**
 * Created by Muhammad63 on 2/7/2018.
 */

public class object_history {
    private String aktivitas, obyek, idSubmit, time;

    public object_history(String aktivitas, String obyek, String idSubmit, String time) {
        this.aktivitas = aktivitas;
        this.obyek = obyek;
        this.idSubmit = idSubmit;
        this.time = time;
    }

    public String getAktivitas() {
        return aktivitas;
    }

    public void setAktivitas(String aktivitas) {
        this.aktivitas = aktivitas;
    }

    public String getObyek() {
        return obyek;
    }

    public void setObyek(String obyek) {
        this.obyek = obyek;
    }

    public String getIdSubmit() {
        return idSubmit;
    }

    public void setIdSubmit(String idSubmit) {
        this.idSubmit = idSubmit;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
