package com.skyman.billiarddata.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.skyman.billiarddata.R;
import com.skyman.billiarddata.etc.SectionManager;

public class BilliardLvExplainDialog extends DialogFragment implements SectionManager.Initializable {

    // instance variable
    private ImageView close;

    // constructor
    private BilliardLvExplainDialog() {
    }

    public static BilliardLvExplainDialog newInstance() {

        BilliardLvExplainDialog dialog = new BilliardLvExplainDialog();

        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.custom_dialog_billiard_lv_explain, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // widget : connect -> init
        connectWidget();
        initWidget();

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void initAppDbManager() {

    }

    @Override
    public void connectWidget() {

        close = (ImageView) getView().findViewById(R.id.d_billiardLvExplain_button_close);
    }

    @Override
    public void initWidget() {

        close.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                }
        );

    }
}
