package com.uigitdev.materialtabswithmenu.uigitdev.design;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.uigitdev.materialtabswithmenu.R;

public class ToolbarIconBadge {
    public RelativeLayout toolbar_body_parent;
    private ImageView toolbar_icon;
    private CardView toolbar_badge_parent;
    private TextView toolbar_badge_text;

    private final int BADGE_COUNT_LIMIT = 99;
    private int count = 1;

    public ToolbarIconBadge(View view) {
        setType(view);
    }

    private void setType(View view) {
        toolbar_body_parent = view.findViewById(R.id.toolbar_body_parent);
        toolbar_icon = view.findViewById(R.id.toolbar_icon);
        toolbar_badge_parent = view.findViewById(R.id.toolbar_badge_parent);
        toolbar_badge_text = view.findViewById(R.id.toolbar_badge_text);
    }

    public void setIconColor(String color) {
        toolbar_icon.setColorFilter(Color.parseColor(color), android.graphics.PorterDuff.Mode.SRC_IN);
    }

    public void setBadgeColor(String color) {
        toolbar_badge_parent.setCardBackgroundColor(Color.parseColor(color));
    }

    public void setIcon(int iconId) {
        toolbar_icon.setImageResource(iconId);
    }

    public void setCount(int count) {
        this.count = count;
        if (count > BADGE_COUNT_LIMIT) {
            toolbar_badge_text.setText(BADGE_COUNT_LIMIT + "+");
        } else {
            toolbar_badge_text.setText(String.valueOf(count));
        }
    }

    public void setCountColor(String color) {
        toolbar_badge_text.setTextColor(Color.parseColor(color));
    }

    public int getCount() {
        return count;
    }

    public void hideCount() {
        toolbar_badge_text.setVisibility(View.GONE);
    }

    public void hideBagde() {
        toolbar_badge_parent.setVisibility(View.GONE);
    }
}