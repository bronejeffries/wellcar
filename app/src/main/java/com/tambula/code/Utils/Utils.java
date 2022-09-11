package com.tambula.code.Utils;

import android.app.Activity;

import com.tambula.code.Objects.ServiceObject;
import com.tambula.code.Objects.TypeObject;
import com.tambula.code.R;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Utils {


    /**
     * Round a float value to a specific decimal place
     * @param amount - the value to round
     * @param decimalPlace - to what decimal place to round the amount to
     * @return rounded number
     */
    public BigDecimal round(float amount, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(amount));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd;
    }


    /**
     * Returns array list with all of the driver rides available for this
     * application.
     * @param activity - activity that called this function
     * @return typeArrayList - array list with all the driver types
     */
    public static ArrayList<TypeObject> getTypeList(Activity activity){
        ArrayList<TypeObject> typeArrayList = new ArrayList<>();

        typeArrayList.add(new TypeObject("type_1", activity.getResources().getString(R.string.type_1), activity.getResources().getDrawable(R.drawable.ic_type_1), 4,"car_wash",20000));
        typeArrayList.add(new TypeObject("type_2", activity.getResources().getString(R.string.type_2), activity.getResources().getDrawable(R.drawable.ic_type_2), 7,"breakdown_service",30000));
        typeArrayList.add(new TypeObject("type_3", activity.getResources().getString(R.string.type_3), activity.getResources().getDrawable(R.drawable.ic_type_3), 4,"emergency_repair_service",40000));
        typeArrayList.add(new TypeObject("type_4", activity.getResources().getString(R.string.type_4), activity.getResources().getDrawable(R.drawable.ic_type_4), 1,"periodic_maintenance",50000));

        return  typeArrayList;
    }

    public static ArrayList<ServiceObject> getServiceList(){



        return null;
    }


    public static ArrayList<ServiceObject> getServices(Integer sInd){



        return null;
    }


    /**
     * Calculate Ride cost estimate
     */
    public static int rideCostEstimate(double distance, double duration){
        double price;
        price = 36 + distance * 26 + duration * 0.001;
        return (int) price;
    }

    /**
     * get type object that is in the arrayList with a certain id
     * @param activity - activity that called this function
     * @param id - id of the object to find
     * @return - type object
     */
    public static TypeObject getTypeById(Activity activity, String id){
        ArrayList<TypeObject> typeArrayList = getTypeList(activity);
        for(TypeObject mType : typeArrayList){
            if(mType.getId().equals(id)){
                return mType;
            }
        }
        return null;
    }

    /**
     * get type object that is in the arrayList with a certain id
     * @param activity - activity that called this function
     * @param id - id of the object to find
     * @return - type object
     */
    public static ArrayList<TypeObject> getTypeByPost(Activity activity, String id){
        ArrayList<TypeObject> typeList = new ArrayList<>();
        ArrayList<TypeObject> typeArrayList = getTypeList(activity);
        for(TypeObject mType : typeArrayList){
            if(mType.getId().equals(id)){

                typeList.add(mType);
                return typeList;
            }
        }
        return typeList;
    }
}
