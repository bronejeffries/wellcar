package com.tambula.code.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.tambula.code.Objects.CardObject;
import com.tambula.code.Objects.ServiceObject;
import com.tambula.code.Payment.PaymentActivity;
import com.tambula.code.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Adapter responsible for displaying type of cars in the CustomerActivity.class
 */

public class ServiceAdapter extends ArrayAdapter<ServiceObject> {

    public ServiceAdapter(@NonNull Context context, ArrayList<ServiceObject> courseModelArrayList) {
        super(context, 0, courseModelArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitemView = convertView;

        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.services_card, parent, false);
        }
        ServiceObject serviceModel = getItem(position);
        TextView serviceTV = listitemView.findViewById(R.id.idTService);
        ImageView serviceIV = listitemView.findViewById(R.id.idIService);
        serviceTV.setText(serviceModel.getServiceName());
        serviceIV.setImageResource(serviceModel.getDrawableId());



        return listitemView;
    }
}