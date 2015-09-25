package com.design.apparel.appareldesigner;

/*******************************************************************************
 * Created by VasukiSairam on 6/16/2015.
 * This is the class that stores Measure details from user
 ******************************************************************************/

public class Measure {



        public String cust_name;
        protected Float bust;
        protected Float hip;
        protected Float waist;
        protected Float outseam_length;
        protected Float shoulder_width;
        protected Float sleeve_length;
        protected Float back_width;
        protected Float upper_arm_circ;
        protected Float back_waist_length;
        String measure;

        public Measure()
        {
            cust_name=null;
            bust=Float.valueOf(0);
            hip=Float.valueOf(0);
            waist=Float.valueOf(0);
            outseam_length=Float.valueOf(0);
            shoulder_width=Float.valueOf(0);
            sleeve_length=Float.valueOf(0);
            back_width=Float.valueOf(0);
            upper_arm_circ=Float.valueOf(0);
            back_waist_length=Float.valueOf(0);
            measure=getMeasure();

        }


        public Measure(String name,String str) {
           cust_name=name;
           String[] strMeasure= str.split(":");
           bust=Float.valueOf(strMeasure[0]);
           hip=Float.valueOf(strMeasure[1]);
           waist=Float.valueOf(strMeasure[2]);
           outseam_length=Float.valueOf(strMeasure[3]);
           shoulder_width=Float.valueOf(strMeasure[4]);
           sleeve_length=Float.valueOf(strMeasure[5]);
           back_width=Float.valueOf(strMeasure[6]);
           upper_arm_circ=Float.valueOf(strMeasure[7]);
           back_waist_length=Float.valueOf(strMeasure[8]);
         }


        public String getCust_name() {
            return cust_name;
        }

        public void setCust_name(String cust_name) {
            this.cust_name = cust_name;
        }

        public void setBust(Float f) {  bust=f;  }

        public float getBust() {
            return bust.floatValue();
        }

        public void setHip(Float f) {  hip=f;  }

        public Float getHip() {
        return hip.floatValue();
    }

        public void setWaist(Float f) {  waist=f;  }

        public Float getWaist() {
        return waist.floatValue();
    }

        public void setOutseamLength(Float f) {  outseam_length=f;  }

        public Float getOutseamLength() {
        return outseam_length.floatValue();
    }

        public void setShoulderWidth(Float f) {  shoulder_width=f;  }

        public Float getShoulderWidth() {
        return shoulder_width.floatValue();
    }

        public void setSleeveLength(Float f) {  sleeve_length=f.floatValue();  }

        public Float getSleeveLength() {
        return sleeve_length;
    }

        public void setBackWidth(Float f) {  back_width=f;  }

        public Float getBackWidth() {
        return back_width.floatValue();
    }

        public void setUpperArmCirc(Float f) {  upper_arm_circ=f;  }

        public Float getUpperArmCirc() {
        return upper_arm_circ.floatValue();
    }

        public void setBackWaistLength(Float f) {  back_waist_length=f;  }

        public Float getBackWaistLength() {
        return back_waist_length.floatValue() ;
    }

        public String getMeasure()
        {
            String str=new String(bust.toString()+":" +
                    hip.toString()+":" +
                    waist.toString()+":" +
                    outseam_length.toString()+":"+
                    shoulder_width.toString()+":" +
                    sleeve_length.toString()+":" +
                    back_width.toString()+":" +
                    upper_arm_circ.toString()+":" +
                    back_waist_length.toString() +":"

            );

            return str;
        }

    }
