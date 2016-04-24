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

    public static final int FLAG_ATM           = 0x01;
    public static final int FLAG_GREYHOUND     = 0x02;
    public static final int FLAG_PHOTOSPHERE   = 0x04;
    public static final int FLAG_HELPLINE      = 0x08;
    public static final int FLAG_LIBRARY       = 0x10;
    public static final int FLAG_DEFIBRILLATOR = 0x20;
    public static final int FLAG_ALL =
            FLAG_ATM | FLAG_GREYHOUND | FLAG_PHOTOSPHERE | FLAG_HELPLINE | FLAG_LIBRARY | FLAG_DEFIBRILLATOR;

    public static final int LAYERS_COUNT = Integer.bitCount(FLAG_ALL);

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
        @Bind(R.id.poi_layers_helplines_check) CheckBox mCheckHelplines;
        @Bind(R.id.poi_layers_libraries_check) CheckBox mCheckLibraries;
        @Bind(R.id.poi_layers_defibrillators_check) CheckBox mCheckDefibrillators;

        @OnClick({
                R.id.poi_layers_atm_label,
                R.id.poi_layers_greyhound_label,
                R.id.poi_layers_photosphere_label,
                R.id.poi_layers_helplines_label,
                R.id.poi_layers_libraries_label,
                R.id.poi_layers_defibrillators_label,
        })
        public void onAtmLabelClicked(final View view) {
            ((CheckBox) ((ViewGroup) view.getParent()).getChildAt(1)).toggle();
        }

        public void restore(final int flags) {
            mCheckAtm.setChecked((flags & FLAG_ATM) != 0);
            mCheckGreyhound.setChecked((flags & FLAG_GREYHOUND) != 0);
            mCheckPhotosphere.setChecked((flags & FLAG_PHOTOSPHERE) != 0);
            mCheckHelplines.setChecked((flags & FLAG_HELPLINE) != 0);
            mCheckLibraries.setChecked((flags & FLAG_LIBRARY) != 0);
            mCheckDefibrillators.setChecked((flags & FLAG_DEFIBRILLATOR) != 0);
        }

        public int save() {
            int flags = 0;

            flags |= mCheckAtm.isChecked() ? FLAG_ATM : 0;
            flags |= mCheckGreyhound.isChecked() ? FLAG_GREYHOUND : 0;
            flags |= mCheckPhotosphere.isChecked() ? FLAG_PHOTOSPHERE : 0;
            flags |= mCheckHelplines.isChecked() ? FLAG_HELPLINE : 0;
            flags |= mCheckLibraries.isChecked() ? FLAG_LIBRARY : 0;
            flags |= mCheckDefibrillators.isChecked() ? FLAG_DEFIBRILLATOR : 0;

            return flags;
        }
    }

    public interface OnLayersSelectedListener {
        void onLayersSelected(final int flags);
    }
}
