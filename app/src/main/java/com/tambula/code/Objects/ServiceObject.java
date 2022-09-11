package com.tambula.code.Objects;

import java.util.ArrayList;

public class ServiceObject {

    private String  service_name;
    private int drawable_id;
    private ArrayList<ServiceObject> list;
    String code;
    private int price ;


    public ServiceObject(String service_name, int drawable_id,String code,int price) {
        this.service_name = service_name;
        this.drawable_id = drawable_id;
        this.code =  code;
    }

    public ServiceObject (ArrayList<ServiceObject> lst , String service_name, int drawable_id){

        this.list = lst;
        this.service_name = service_name;
        this.drawable_id = drawable_id;
    }

    public ArrayList<ServiceObject> getLst() {
        return list;
    }

    public void setLst(ArrayList<ServiceObject> lst) {
        this.list = lst;
    }

    public String getServiceName() {
        return service_name;
    }

    public void setServiceName(String service_name) {
        this.service_name = service_name;
    }

    public int getDrawableId() {
        return drawable_id;
    }

    public void setDrawableId(int service_id) {
        this.drawable_id = service_id;
    }


}
