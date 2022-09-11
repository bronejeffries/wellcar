package com.tambula.code.Login;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.tambula.code.Adapters.ServiceAdapter;
import com.tambula.code.Objects.ServiceObject;
import com.tambula.code.R;
import java.util.ArrayList;

public class ServicesFragment extends DialogFragment implements AdapterView.OnItemClickListener {


    private static ArrayList<ServiceObject> serviceModelArrayList;
    GridView serviceGV;
    Toolbar tlbar;
    private View view;
    ArrayList <ServiceObject> cars ;
    Integer serviceIndex;

    //ArrayList<ServiceObject> serviceModelArrayList;

    public ServicesFragment() {

    }



    public interface serviceDialogListener {
        void onSelectServiceDialog(int value, String serviceName, ArrayList<ServiceObject> lst, ServicesFragment servicesFragment);
        void dismiss();
    }



    public interface ServiceClickDialogListener {
        void onFinishClickDialog(String inputText);
    }

    public static ServicesFragment newInstance(String title,ArrayList<ServiceObject> lstv) {

        serviceModelArrayList = lstv;

        ServicesFragment frag = new ServicesFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        frag.setStyle(STYLE_NORMAL, R.style.AppFloatingDialogTheme);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view == null)
            view = inflater.inflate(R.layout.fragment_services, container, false);
        else
            container.removeView(view);

        serviceGV = view.findViewById(R.id.idGServices);

        tlbar = view.findViewById(R.id.services_toolbar);


        if(serviceModelArrayList == null){

            serviceModelArrayList = new ArrayList<ServiceObject>();

        }

        if(serviceModelArrayList.isEmpty()){

            serviceModelArrayList = Lst();
        }

        ServiceAdapter adapter = new ServiceAdapter(getActivity(), serviceModelArrayList);

        serviceGV.setAdapter(adapter);
        serviceGV.setVerticalScrollBarEnabled(true);
        serviceGV.setOnItemClickListener((AdapterView.OnItemClickListener) this);

        return view;

    }

    public ArrayList<ServiceObject> Lst(){

        serviceModelArrayList = new ArrayList<ServiceObject>();

        serviceModelArrayList.add(new ServiceObject(getResources().getString(R.string.repair), R.drawable.ic_iocns_car_repair,"car_repairs",0));
        serviceModelArrayList.add(new ServiceObject(getResources().getString(R.string.breakdown), R.drawable.ic_iocns_car_carrier,"car_wash",0));

        cars = new ArrayList<ServiceObject>();

        cars.add(new ServiceObject(getResources().getString(R.string.car_washing), R.drawable.ic_iocns_car_wash,"",20000));

        serviceModelArrayList.add(new ServiceObject(cars,getResources().getString(R.string.car_washing),R.drawable.ic_iocns_car_wash));
        serviceModelArrayList.add(new ServiceObject(getResources().getString(R.string.car_insurance), R.drawable.ic_iocns_car_insurance,"car_insurance",0));
        serviceModelArrayList.add(new ServiceObject(getResources().getString(R.string.air_conditioning), R.drawable.ic_iocns_air_conditioning,"air_conditioning",0));
        serviceModelArrayList.add(new ServiceObject(getResources().getString(R.string.maintenance), R.drawable.ic_iocns_mechanics,"car_maintenance",0));

        return serviceModelArrayList;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        serviceDialogListener listener = (serviceDialogListener) getActivity();

        listener.onSelectServiceDialog(i,serviceModelArrayList.get(i).getServiceName(),serviceModelArrayList.get(i).getLst(), this);

//        getDialog().dismiss();

    }

    @Override
    public void dismiss() {
        super.dismiss();
        serviceDialogListener listener = (serviceDialogListener) getActivity();
        listener.dismiss();
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        dismiss();
    }
}