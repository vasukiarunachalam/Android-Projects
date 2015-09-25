package com.design.apparel.appareldesigner;

/*******************************************************************************
* Created by VasukiSairam on 6/22/2015.
 * * This is the Singleton class to store the current Apparel and Measure instance
 * ******************************************************************************/

public class Controller {

    private static final String TAG = "Controller";
    private static Controller ainstance;
    private Apparel apparel_obj;

    private Controller() {
        apparel_obj=new Apparel();
    }

    public static Controller getInstance() {
        if (ainstance == null) {
            ainstance = new Controller();
        }
        return ainstance;
    }

    public void setApparel(Apparel apparel) {
        this.apparel_obj=apparel;
    }

    public Apparel getApparel() {
        return apparel_obj;
    }

    public void setMeasure(Measure cus_measure) {
        this.apparel_obj.m=cus_measure;
        if(cus_measure.getCust_name()!="Test") {
            this.apparel_obj.calculateMaterial();
        }
    }

    public Measure getMeasure() {
        return apparel_obj.m;
    }


}
