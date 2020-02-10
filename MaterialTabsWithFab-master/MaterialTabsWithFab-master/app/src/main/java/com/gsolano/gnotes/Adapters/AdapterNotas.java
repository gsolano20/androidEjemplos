package com.gsolano.gnotes.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gsolano.gnotes.Models.NotasModel;
import com.gsolano.gnotes.R;

import java.util.List;

public class AdapterNotas extends ArrayAdapter<NotasModel> {
    List<NotasModel> mListItems;

    public AdapterNotas(Context context, List<NotasModel> items) {
        super(context, R.layout.item_list_note, items);
        mListItems = items;
    }

    @Override
    public int getCount() {
        return mListItems.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_list_note, null);
        }
        NotasModel objAhorro = mListItems.get(position);
        final TextView txtNote = (TextView)convertView.findViewById(R.id.limTextNote);
        final TextView txtTit = (TextView)convertView.findViewById(R.id.limTextTitulo);

        txtNote.setText(objAhorro.getNota());
        txtTit.setText(objAhorro.getTitulo());

        return convertView;
    }
}
