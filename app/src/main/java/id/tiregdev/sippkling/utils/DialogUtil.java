package id.tiregdev.sippkling.utils;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import id.tiregdev.sippkling.R;

public class DialogUtil {

    public void showDialog(final Activity context, String status){
        LayoutInflater factory = LayoutInflater.from(context);
        View exitDialogView = factory.inflate(R.layout.popup_sukses, null);
        final AlertDialog exitDialogs = new AlertDialog.Builder(context).create();
        exitDialogs.setView(exitDialogView);
        TextView stats = exitDialogView.findViewById(R.id.status);
        stats.setText(status);
        exitDialogView.findViewById(R.id.sukses).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitDialogs.dismiss();
                context.finish();
            }
        });
        exitDialogs.show();
    }
}
