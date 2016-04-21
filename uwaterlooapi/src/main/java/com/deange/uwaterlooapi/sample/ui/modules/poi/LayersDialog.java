package com.deange.uwaterlooapi.sample.ui.modules.poi;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.utils.Dp;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LayersDialog {

    public static final int FLAG_ATM = 0x01;
    public static final int FLAG_GREYHOUND = 0x02;
    public static final int FLAG_PHOTOSPHERE = 0x04;
    public static final int FLAG_ALL = FLAG_ATM | FLAG_GREYHOUND | FLAG_PHOTOSPHERE;

    public static final int LAYERS_COUNT = 3;

    private LayersDialog() {
        throw new AssertionError();
    }

    public static void showDialog(final Context context, final int flags, final OnLayersSelectedListener listener) {
        final View view = LayoutInflater.from(context).inflate(R.layout.dialog_poi_layers, null);
        final LayersViews holder = new LayersViews();

        ButterKnife.bind(holder, view);

        holder.restore(flags);

        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(view)
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        final int newFlags = holder.save();
                        if (listener != null) {
                            listener.onLayersSelected(newFlags);
                        }
                    }
                })
                .create();

        dialog.getWindow().getDecorView(); // Force decor view to be installed
        dialog.getWindow().setLayout((int) (Dp.width() * 0.75f), ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.show();
    }

    static final class LayersViews {
        @Bind(R.id.poi_layers_atm_check) CheckBox mCheckAtm;
        @Bind(R.id.poi_layers_greyhound_check) CheckBox mCheckGreyhound;
        @Bind(R.id.poi_layers_photosphere_check) CheckBox mCheckPhotosphere;

        @OnClick({
                R.id.poi_layers_atm_label,
                R.id.poi_layers_greyhound_label,
                R.id.poi_layers_photosphere_label,
        })
        public void onAtmLabelClicked(final View view) {
            ((CheckBox) ((ViewGroup) view.getParent()).getChildAt(1)).toggle();
        }

        public void restore(final int flags) {
            mCheckAtm.setChecked((flags & FLAG_ATM) != 0);
            mCheckGreyhound.setChecked((flags & FLAG_GREYHOUND) != 0);
            mCheckPhotosphere.setChecked((flags & FLAG_PHOTOSPHERE) != 0);
        }

        public int save() {
            int flags = 0;

            flags |= mCheckAtm.isChecked() ? FLAG_ATM : 0;
            flags |= mCheckGreyhound.isChecked() ? FLAG_GREYHOUND : 0;
            flags |= mCheckPhotosphere.isChecked() ? FLAG_PHOTOSPHERE : 0;

            return flags;
        }
    }

    public interface OnLayersSelectedListener {
        void onLayersSelected(final int flags);
    }
}
