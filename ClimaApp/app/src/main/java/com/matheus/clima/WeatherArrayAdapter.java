package com.matheus.clima;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class WeatherArrayAdapter extends ArrayAdapter<Weather> {

    private static class ViewHolder {
        TextView conditionTextView;
        TextView dayTextView;
        TextView descriptionTextView;
        TextView lowTextView;
        TextView hiTextView;
        TextView humidityTextView;
    }

    public WeatherArrayAdapter(Context context, List<Weather> forecast) {
        super(context, -1, forecast);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Weather day = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            viewHolder.conditionTextView = convertView.findViewById(R.id.conditionTextView);
            viewHolder.dayTextView = convertView.findViewById(R.id.dayTextView);
            viewHolder.descriptionTextView = convertView.findViewById(R.id.descriptionTextView);
            viewHolder.lowTextView = convertView.findViewById(R.id.lowTextView);
            viewHolder.hiTextView = convertView.findViewById(R.id.hiTextView);
            viewHolder.humidityTextView = convertView.findViewById(R.id.humidityTextView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (day != null) {
            viewHolder.conditionTextView.setText(day.icon != null ? day.icon : "");
            viewHolder.dayTextView.setText(day.date);
            viewHolder.descriptionTextView.setText(day.description);
            viewHolder.lowTextView.setText(getContext().getString(R.string.low_temp, day.minTemp));
            viewHolder.hiTextView.setText(getContext().getString(R.string.high_temp, day.maxTemp));
            viewHolder.humidityTextView.setText(getContext().getString(R.string.humidity, day.humidity));
        }

        return convertView;
    }
}
