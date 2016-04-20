package com.deange.uwaterlooapi.sample.ui.modules.poi;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;

import com.deange.uwaterlooapi.sample.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LayersDialog {

    public static final int FLAG_ATM = 0x01;
    public static final int FLAG_ALL = FLAG_ATM;

    private LayersDialog() {
        throw new AssertionError();
    }

    public static void showDialog(final Context context, final int flags, final OnLayersSelectedListener listener) {
        final View view = LayoutInflater.from(context).inflate(R.layout.dialog_poi_layers, null);
        final LayersViews holder = new LayersViews();

        ButterKnife.bind(holder, view);

        holder.restore(flags);

        new AlertDialog.Builder(context)
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
                .show();
    }

    static final class LayersViews {
        @Bind(R.id.poi_layers_atm_check) CheckBox mCheckAtm;

        @OnClick(R.id.poi_layers_atm_label)
        public void onAtmLabelClicked() {
            mCheckAtm.toggle();
        }

        public void restore(final int flags) {
            mCheckAtm.setChecked((flags & FLAG_ATM) != 0);
        }

        public int save() {
            int flags = 0;
            flags |= mCheckAtm.isChecked() ? FLAG_ATM : 0;
            return flags;
        }
    }

    public interface OnLayersSelectedListener {
        void onLayersSelected(final int flags);
    }
}
