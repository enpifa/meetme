package com.meetme.app;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DataTableAdapter extends ArrayAdapter<String>{

	private ArrayList<String> dataSet;
	private int iconId;
	
	/**
	 * Defineix les dades de la classe
	 * @param context el context actual
	 * @param textViewResourceId n�mero per identificar el camp
	 * @param objects llista d'objectes
	 * @param ic_id n�mero per identificar l'icone
	 */
	
	public DataTableAdapter(Context context, int textViewResourceId,
			ArrayList<String> objects, int ic_id) {
		super(context, textViewResourceId, objects);
		dataSet = objects;
		iconId = ic_id;
	}

	/**
	 * Posa els camps de la taula de dades a la vista
	 * @param position n�mero per identificar el la informaci�
	 * @param convertView la vista actual
	 * @param parent
	 * @return la nova vista amb les dades afegides
	 */
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
            LayoutInflater vi = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.data_item, null);
		}
		
		String data = dataSet.get(position);
		
		TextView dataLabel = (TextView)v.findViewById(R.id.profile_data_label);
		dataLabel.setText(data);
		
		ImageView icon = (ImageView)v.findViewById(R.id.profile_data_icon);
		icon.setImageResource(iconId);
		
		return v;
	}
}