package com.design.apparel.appareldesigner;

import java.util.ArrayList;

/*******************************************************************************
 * Created by VasukiSairam on 6/10/2015.
 * This code is the Pattern class that stores pattern details like
 * R - Length of the line
 * Angle- Angle to be taken at the origin
 ******************************************************************************/

public class Pattern {

    private String drawable_id;
    private int numPatternTexts;
    private ArrayList<PatternText> patternTexts;


    public Pattern()
    {
        this.patternTexts=new ArrayList<>();
        this.drawable_id = null;

    }

    public Pattern(String d, int num, ArrayList<PatternText> ptexts) {
        super();
        this.drawable_id = d;
        this.numPatternTexts=num;
        patternTexts=ptexts;

    }

    public void createPatternText(int index,PatternText ptext) {

        this.patternTexts.add(index,ptext);

    }

    public int getNumPatternTexts() {
        return numPatternTexts;
    }

    public void setNumPatternTexts(int numPatternTexts) {
        this.numPatternTexts = numPatternTexts;
    }

    public void setDrawable_id(String drawable_id) {
        this.drawable_id = drawable_id;
    }

    public String getDrawable_id() {
        return this.drawable_id;
    }

    public void setPatternTexts(ArrayList<PatternText> ptext)
    {
        this.patternTexts=ptext;
    }

    public ArrayList<PatternText> getPatternTexts()
    {
        return this.patternTexts;
    }
}
