package com.example.itijavaproject.ui.homescreen.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.itijavaproject.R;
import com.example.itijavaproject.pojo.model.Medicine;

public class HomeDialog {

    private final Context context;
    private final Dialog dialog;

    @SuppressLint("ResourceType")
    public HomeDialog(Context context, HomeDialogCommunicator communicator) {
        this.context = context;
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.home_dialog);
        dialog.findViewById(R.id.btnClose).setOnClickListener(v -> communicator.close());
        dialog.findViewById(R.id.btnTake).setOnClickListener(v -> communicator.takeMed());
        dialog.findViewById(R.id.btnSnooze).setOnClickListener(v -> communicator.snooze());
        dialog.findViewById(R.id.imgEdit).setOnClickListener(v -> communicator.editMed());
        dialog.findViewById(R.id.imgDelete).setOnClickListener(v -> communicator.deleteMed());
        dialog.findViewById(R.id.imgInfo).setOnClickListener(v -> communicator.showInfo());
    }

    public void show(Medicine medicine) {
        ((TextView) dialog.findViewById(R.id.txtMedName)).setText(medicine.getName());
        ((TextView) dialog.findViewById(R.id.txtSchaduler)).setText(medicine.getName());
        ((TextView) dialog.findViewById(R.id.txtInfo)).setText(medicine.getInstructions());
        ((ImageView) dialog.findViewById(R.id.imgMedIcon))
                .setImageDrawable(getDrawbaleImage(medicine.getIconType()));

        dialog.show();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private Drawable getDrawbaleImage(String img) {
        if (img.equals(context.getString(R.string.pill))) {

            return context.getResources().getDrawable(R.drawable.ic_pill);
        } else if (img.equals(context.getString(R.string.bottle))) {

            return context.getResources().getDrawable(R.drawable.ic_bottle_pill);
        } else if (img.equals(context.getString(R.string.drop))) {

            return context.getResources().getDrawable(R.drawable.ic_medicine);
        } else if (img.equals(context.getString(R.string.injection))) {

            return context.getResources().getDrawable(R.drawable.ic_injection__1_);
        } else {

            return null;
        }
    }

    public void close() {
        dialog.cancel();
    }
}
