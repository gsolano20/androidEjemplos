package com.novacompcr.myapplication.display;

import android.content.res.Resources;

import com.google.android.material.snackbar.Snackbar;

interface Themer {

    void applyTheme(Resources resources, Snackbar snackbar);

}
