package com.uigitdev.materialtabswithmenu.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.uigitdev.materialtabswithmenu.R;

public class FragmentLibrary extends Fragment {
//    private TextView textView;

    public FragmentLibrary() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_library, container, false);
//        setSomething(view);
        return view;
    }

    private void setSomething(View view) {
//        textView = view.findViewById(R.id.your_text_view_id);
    }
}
