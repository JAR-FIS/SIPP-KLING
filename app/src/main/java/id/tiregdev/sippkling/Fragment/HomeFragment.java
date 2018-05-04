package id.tiregdev.sippkling.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import id.tiregdev.sippkling.R;
import id.tiregdev.sippkling.utils.SQLiteHandler;

/**
 * Created by Muhammad63 on 2/5/2018.
 */

public class HomeFragment extends Fragment {

    private View v;
    private TextView owner;
    private SQLiteHandler db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);
        db = new SQLiteHandler(getActivity());
        owner = v.findViewById(R.id.owner);
        owner.setText("Halo, " + db.getUserDetails().get("nama"));
        popup();
        return v;
    }

    public void popup(){
        Button bt = v.findViewById(R.id.popupBtn);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LayoutInflater factory = LayoutInflater.from(getActivity());
                final View exitDialogView = factory.inflate(R.layout.popup_home, null);
                final AlertDialog exitDialog = new AlertDialog.Builder(getActivity()).create();
                exitDialog.setView(exitDialogView);
                exitDialogView.findViewById(R.id.btClose).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        exitDialog.dismiss();
                    }
                });
                exitDialogView.findViewById(R.id.rumahSehat).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ListDataFragment llf = ListDataFragment.newInstance("rumahsehat");
                        ft.replace(R.id.content_frame, llf);
                        ft.commit();
                        exitDialog.dismiss();
                    }
                });
                exitDialogView.findViewById(R.id.ttu).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        TTUFragment llf = new TTUFragment();
                        ft.replace(R.id.content_frame, llf);
                        ft.commit();
                        exitDialog.dismiss();
                    }
                });
                exitDialogView.findViewById(R.id.pkl).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ListDataFragment llf = ListDataFragment.newInstance("pkl");
                        ft.replace(R.id.content_frame, llf);
                        ft.commit();
                        exitDialog.dismiss();
                    }
                });
                exitDialogView.findViewById(R.id.tpm).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        TPMFragment llf = new TPMFragment();
                        ft.replace(R.id.content_frame, llf);
                        ft.commit();
                        exitDialog.dismiss();
                    }
                });
                exitDialog.show();
            }
        });
    }
}
