package com.uigitdev.materialtabswithmenu.uigitdev.design;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uigitdev.materialtabswithmenu.R;

public class CustomPlayLine {
    private TextView name_of_music;
    private RelativeLayout play_line_parent;

    public CustomPlayLine(View view) {
        setType(view);
    }

    private void setType(View view) {
        name_of_music = view.findViewById(R.id.name_of_music);
        play_line_parent = view.findViewById(R.id.play_line_parent);
    }

    public void setMusicName(String musicName){
        show();
        name_of_music.setText(musicName);
    }

    public void hide(){
        play_line_parent.setVisibility(View.GONE);
    }

    public void show(){
        play_line_parent.setVisibility(View.VISIBLE);
    }
}
