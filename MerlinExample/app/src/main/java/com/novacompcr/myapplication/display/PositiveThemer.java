package com.novacompcr.myapplication.display;

import android.content.res.Resources;
import android.util.TypedValue;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.novacompcr.myapplication.R;

class PositiveThemer implements Themer {

    @Override
    public void applyTheme(Resources resources, Snackbar snackbar) {
        Snackbar.SnackbarLayout snackbarView = (Snackbar.SnackbarLayout) snackbar.getView();

        TextView messageView = (TextView) snackbarView.findViewById(R.id.snackbar_text);
        messageView.setTextColor(resources.getColor(R.color.snackbar_message));
        snackbarView.setBackgroundColor(resources.getColor(R.color.snackbar_background_positive));

        int textSize = resources.getDimensionPixelSize(R.dimen.snackbar_text_size);
        messageView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

        int minHeight = resources.getDimensionPixelSize(R.dimen.snackbar_minimum_height);
        snackbarView.setMinimumHeight(minHeight);
    }

}
