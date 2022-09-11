package com.tambula.code.Objects;

import com.tambula.code.R;

import java.util.ArrayList;

public class Services {

        ArrayList<ServiceObject> serviceModelArrayList;

        public Services(){

            serviceModelArrayList = new ArrayList<ServiceObject>();

        }

        public void add(String  name){

            serviceModelArrayList.add(new ServiceObject(name, R.drawable.ic_type_1,"",0));

        }

        public ArrayList<ServiceObject> get(){

             return serviceModelArrayList;

        }



}
