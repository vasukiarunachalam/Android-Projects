package com.design.apparel.appareldesigner;

import java.util.ArrayList;

/*******************************************************************************
 * Created by VasukiSairam on 6/31/2015.
 * This code is the Piece class that stores Pattern details
 ******************************************************************************/

public class Piece {

    private String piece_height;
    private String piece_width;
    private String piece_shape;
    private String piece_fold;
    private String piece_info;
    private String piece_image_id;
    private byte[] piece_blob_img;
    private int numPatternTexts;
    private ArrayList<PatternText> patternTexts;

    public Piece() {
        piece_height=null;
        piece_width=null;
        piece_shape=null;
        piece_fold=null;
        piece_info=null;
        piece_image_id=null;
        piece_blob_img=null;
        patternTexts=new ArrayList<>();
    }

    public Piece(String h, String w, String s, String f, String i, String img, byte b[], ArrayList<PatternText> p) {
        this.piece_height=h;
        this.piece_width=w;
        this.piece_shape=s;
        this.piece_fold=f;
        this.piece_info=i;
        this.piece_image_id=img;
        this.piece_blob_img=b;
        this.patternTexts=p;
    }

    public String getPiece_height() {
        return piece_height;
    }

    public void setPiece_height(String piece_height) {
        this.piece_height = piece_height;
    }

    public String getPiece_width() {
        return piece_width;
    }

    public void setPiece_width(String piece_width) {
        this.piece_width = piece_width;
    }

    public String getPiece_shape() {
        return piece_shape;
    }

    public void setPiece_shape(String piece_shape) {
        this.piece_shape = piece_shape;
    }

    public String getPiece_fold() {
        return piece_fold;
    }

    public void setPiece_fold(String piece_fold) {
        this.piece_fold = piece_fold;
    }

    public String getPiece_info() {
        return piece_info;
    }

    public void setPiece_info(String piece_info) {
        this.piece_info = piece_info;
    }

    public String getPiece_image_id() {
        return piece_image_id;
    }

    public void setPiece_image_id(String piece_image_id) {
        this.piece_image_id = piece_image_id;
    }

    public byte[] getPiece_blob_img() {
        return piece_blob_img;
    }

    public void setPiece_blob_img(byte[] piece_blob_img) {
        this.piece_blob_img = piece_blob_img;
    }

    public int getNumPatternTexts() {
        return numPatternTexts;
    }

    public void setNumPatternTexts(int numPatternTexts) {
        this.numPatternTexts = numPatternTexts;
    }

    public ArrayList<PatternText> getPatternTexts() {
        return patternTexts;
    }

    public void setPatternTexts(ArrayList<PatternText> patternTexts) {
        this.patternTexts = patternTexts;
    }

    public void addPatternText(PatternText p)
    {
        patternTexts.add(p);
    }

    public void updatePatternText(int k,PatternText p)
    {
        if(p.getR()==null && p.getAngle()==null)
        {
            patternTexts.remove(k);
        }
        else {
            patternTexts.remove(k);
            patternTexts.add(k, p);
        }
    }



  /*  public Piece1(ArrayList<Pattern1> p) {
        this.patterns=p;
    }

    public int getPatternCount()
    {
        return this.patterns.size();
    }

    public Pattern1 getPattern(int i) {
        return this.patterns.get(i);
    }

    public ArrayList<Pattern1> getPatterns() {
        return this.patterns;
    }

    public void setPatterns(ArrayList<Pattern1> patterns) {
        this.patterns = patterns;
    }


    public byte[] getBlob_img_pattern() {
        return this.blob_img_pattern;
    }

    public void setBlob_img_pattern(byte[] blob_img_pattern) {
        this.blob_img_pattern = blob_img_pattern;
    }*/


}
