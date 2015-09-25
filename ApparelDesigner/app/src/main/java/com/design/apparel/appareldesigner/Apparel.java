package com.design.apparel.appareldesigner;

import java.text.DecimalFormat;
import java.util.ArrayList;

/*******************************************************************************
 * Created by VasukiSairam on 6/27/2015.
 * This is the Apparel class that stores apparel details.
 * It contains -
 *  1. Basic Apparel Details
 *  2. ArrayList of Pieces
 *  3. Measure instance
 ******************************************************************************/
public class Apparel {

    protected String apparel_img_id;
    protected String all_pieces_img_id;
    protected String apparel_name;
    protected String apparel_type;
    protected String bolt_width;
    protected String length;
    protected int num_pieces;
    protected String str_pieces;
    protected byte[] blob_img_apparel;
    protected byte[] blob_img_all_pieces;
    protected ArrayList<Piece> pieces;

    protected float[] H;
    protected float[] W;
    protected Measure m;

    public String getDrawable_id() {
        return apparel_img_id;
    }

    public void setDrawable_id(String drawable_id) {
        this.apparel_img_id = drawable_id;
    }

    public String getPieces_Drawable_id() {
        return all_pieces_img_id;
    }

    public void setPieces_Drawable_id(String drawable_id) {
        this.all_pieces_img_id = drawable_id;
    }

    public String getApparel_name() {
        return apparel_name;
    }

    public void setApparel_name(String apparel_name) {
        this.apparel_name = apparel_name;
    }

    public String getApparel_type() {
        return apparel_type;
    }

    public void setApparel_type(String apparel_type) {
        this.apparel_type = apparel_type;
    }

    public String getBolt_Width() {
        return bolt_width;
    }

    public void setBolt_Width(String bolt_width) {
        this.bolt_width = bolt_width;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public int getNum_pieces() {
        return num_pieces;
    }

    public void setNum_pieces(int num_pieces) {
        this.num_pieces = num_pieces;
    }

    public byte[] getBlob_img_apparel() {
        return blob_img_apparel;
    }

    public void setBlob_img_apparel(byte[] blob_image) {
        this.blob_img_apparel = blob_image;
    }

    public byte[] getBlob_img_all_pieces() {
        return blob_img_all_pieces;
    }

    public void setBlob_img_all_pieces(byte[] blob_image) {
        this.blob_img_all_pieces = blob_image;
    }

    public String getStr_pieces() {
        return str_pieces;
    }

    public void setStr_pieces(String str_pieces) {
        this.str_pieces = str_pieces;
    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }

    public void setPieces(ArrayList<Piece> pieces) {
        this.pieces = pieces;
    }

    public Apparel()
    {
        this.num_pieces=0;
        this.m=new Measure();
        this.pieces=new ArrayList<>();
        this.blob_img_apparel=null;
        this.apparel_img_id=null;
        this.all_pieces_img_id=null;
        this.apparel_name=null;
        this.apparel_type=null;
    }

    public Apparel(String name) {
        super();
        this.num_pieces=0;
        this.m=new Measure();
        this.pieces=new ArrayList<>();
        this.blob_img_apparel=null;
        this.apparel_img_id=null;
        this.all_pieces_img_id=null;
        this.apparel_type=null;
        this.apparel_name = name;

    }

    public Apparel(String name,byte[] img) {
        super();
        this.num_pieces=0;
        this.m=new Measure();
        this.pieces=new ArrayList<>();
        this.blob_img_apparel=img;
        this.apparel_type=null;
        this.apparel_img_id = null;
        this.all_pieces_img_id=null;
        this.apparel_name = name;

    }

    public Measure getMeasure()
    {
        return this.m;
    }

    public void createPieces(int num)
    {
        for(int i=0;i<num;i++) {
            this.pieces.add(new Piece());
        }
        this.H=new float[num_pieces];
        this.W =new float[num_pieces];
        for(int i=0;i<num_pieces;i++)
        {
            this.H[i]=0;
            this.W[i]=0;
        }
    }

    public void calculateMaterial() {
        PatternMaker pt_maker = new PatternMaker();
        H = new float[num_pieces];
        W = new float[num_pieces];
        for (int i = 0; i < num_pieces; i++) {
            H[i] = Float.parseFloat((pt_maker.Evaluate(pieces.get(i).getPiece_height(), 1)));
            W[i] = Float.parseFloat((pt_maker.Evaluate(pieces.get(i).getPiece_height(), 1)));
        }
    }






  /*  public void createPiece1() {


        String[] strpiece1 = new String[14];
        ArrayList<Pattern> patterns=new ArrayList<> ();
        ArrayList<PatternText> patternTexts1 = new ArrayList<> ();


        patternTexts1.add(new PatternText(Float.toString(h[0]),10,20));
        patternTexts1.add(new PatternText(Float.toString(w[0]),12,30));
        patterns.add(new Pattern(R.drawable.img_piece1_pattern1,"Piece1-Step1",patternTexts1));


        ArrayList<PatternText> patternTexts2 = new ArrayList<> ();
        patternTexts2.add(new PatternText(Float.toString(h[1]),10,20));
        patternTexts2.add(new PatternText(Float.toString(w[1]),12,30));
        patterns.add(new Pattern(R.drawable.img_piece1_pattern2,"Piece1-Step2",patternTexts2));

        ArrayList<PatternText> patternTexts3 = new ArrayList<> ();

        r = ((w[0] / 2) - (m.shoulder_width + 1));
        angle = 0;
        strpiece1[0] = "(" + df_r.format(r) + " , " + df_angle.format(angle) + ")";
        patternTexts3.add(new PatternText(strpiece1[0],10,20));

        angle = 30;
        strpiece1[1] = "(" + df_r.format(r) + " , " + df_angle.format(angle) + ")";
        patternTexts3.add(new PatternText(strpiece1[1],10,20));

        angle = 60;
        strpiece1[2] = "(" + df_r.format(r) + " , " + df_angle.format(angle) + ")";
        patternTexts3.add(new PatternText(strpiece1[2],12,23));

        angle = 90;
        strpiece1[3] = "(" + df_r.format(r) + " , " + df_angle.format(angle) + ")";
        patternTexts3.add(new PatternText(strpiece1[3],10,20));

        angle = 120;
        strpiece1[4] = "(" + df_r.format(r) + " , " + df_angle.format(angle) + ")";
        patternTexts3.add(new PatternText(strpiece1[4],10,20));

        angle = 150;
        strpiece1[5] = "(" + df_r.format(r) + " , " + df_angle.format(angle) + ")";
        patternTexts3.add(new PatternText(strpiece1[5],10,20));

        angle = 180;
        strpiece1[6] = "(" + df_r.format(r) + " , " + df_angle.format(angle) + ")";
        patternTexts3.add(new PatternText(strpiece1[6],10,20));

        r = (w[0] / 2);
        angle = 0;
        strpiece1[7] = "(" + df_r.format(r) + " , " + df_angle.format(angle) + ")";
        patternTexts3.add(new PatternText(strpiece1[7],10,20));

        angle = 30;
        strpiece1[8] = "(" + df_r.format(r) + " , " + df_angle.format(angle) + ")";
        patternTexts3.add(new PatternText(strpiece1[8],10,20));

        angle = 60;
        strpiece1[9] = "(" + df_r.format(r) + " , " + df_angle.format(angle) + ")";
        patternTexts3.add(new PatternText(strpiece1[9],10,20));

        angle = 90;
        strpiece1[10] = "(" + df_r.format(r) + " , " + df_angle.format(angle) + ")";
        patternTexts3.add(new PatternText(strpiece1[10],10,20));

        angle = 120;
        strpiece1[11] = "(" + df_r.format(r) + " , " + df_angle.format(angle) + ")";
        patternTexts3.add(new PatternText(strpiece1[11],10,20));

        angle = 150;
        strpiece1[12] = "(" + df_r.format(r) + " , " + df_angle.format(angle) + ")";
        patternTexts3.add(new PatternText(strpiece1[12],10,20));

        angle = 180;
        strpiece1[13] = "(" + df_r.format(r) + " , " + df_angle.format(angle) + ")";
        patternTexts3.add(new PatternText(strpiece1[13],10,20));

        patterns.add(new Pattern(R.drawable.img_piece1_pattern3,"Piece1-Step3",patternTexts3));

        pieces.add(new Piece1( patterns));
    }

    public void createPiece2() {

        String[] strpiece2 = new String[5];
        ArrayList<Pattern> patterns=new ArrayList<> ();

        ArrayList<PatternText> patternTexts1=new ArrayList<> ();
        patternTexts1.add(new PatternText(Float.toString(h[0]),10,20));
        patternTexts1.add(new PatternText(Float.toString(w[0]),12,30));
        patterns.add(new Pattern(R.drawable.img_piece1_pattern1,"Piece2-Step1",patternTexts1));


        ArrayList<PatternText> patternTexts2 = new ArrayList<> ();
        patternTexts2.add(new PatternText(Float.toString(h[1]),10,20));
        patternTexts2.add(new PatternText(Float.toString(w[1]),12,30));
        patterns.add(new Pattern(R.drawable.img_piece1_pattern2,"Piece2-Step2",patternTexts2));

        ArrayList<PatternText> patternTexts3 = new ArrayList<> ();

        r = h[0];
        angle = 0;
        strpiece2[0] = "(" + df_r.format(r) + " , " + df_angle.format(angle) + ")";
        patternTexts3.add(new PatternText(strpiece2[0],10,20));

        r = (float) Math.sqrt((Math.pow(3 * m.bust / 8, 2) + Math.pow(h[0], 2)));
        angle = (int) (1 / Math.tan((3 * m.bust / 8) / h[0]));
        strpiece2[1] = "(" + df_r.format(r) + " , " + df_angle.format(angle) + ")";
        patternTexts3.add(new PatternText(strpiece2[1],10,20));

        r = (float) Math.sqrt((Math.pow((h[0]) - ((1.15 * m.bust) / 12), 2) + Math.pow(((2.15 * m.bust) / 12), 2)));
        angle = (int) (1 / Math.tan(((h[0]) - ((1.15 * m.bust) / 12)) / ((2.15 * m.bust) / 12)));
        strpiece2[2] = "(" + df_r.format(r) + " , " + df_angle.format(angle) + ")";
        patternTexts3.add(new PatternText(strpiece2[1],10,20));

        r = (float) Math.sqrt(Math.pow((h[0]) - (m.bust / 12), 2) + Math.pow(((m.bust) / 4), 2));
        angle = (int) (1 / Math.tan(((h[0]) - (m.bust) / 12)) / ((2.15 * m.bust) / 12));
        strpiece2[3] = "(" + df_r.format(r) + " , " + df_angle.format(angle) + ")";
        patternTexts3.add(new PatternText(strpiece2[3],10,20));

        r = (3 * m.bust) / 8;
        angle = 0;
        strpiece2[4] = "(" + df_r.format(r) + " , " + df_angle.format(angle) + ")";
        patternTexts3.add(new PatternText(strpiece2[4],10,20));

        patterns.add(new Pattern(R.drawable.img_piece2_pattern3,"Piece2-Step3",patternTexts3));

        pieces.add(new Piece1(patterns));


    }
    public void createPiece3()
    {

        String[] strpiece3=new String[2];
        ArrayList<Pattern> patterns=new ArrayList<> ();
        ArrayList<PatternText> patternTexts1 = new ArrayList<> ();



        strpiece3[0]="5";
        patternTexts1.add(new PatternText1(strpiece3[0],10,20));
        r=w[1]*4;
        strpiece3[1]= df_r.format(r);
        patternTexts1.add(new PatternText1(strpiece3[1],20,20));

        patterns.add(new Pattern1(R.drawable.img_piece1_pattern1,"Piece3-Step1",patternTexts1));

        pieces.add(new Piece1(patterns));

    }*/

}
