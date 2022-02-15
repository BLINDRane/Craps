/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import acm.graphics.GCompound;
import acm.graphics.GOval;
import acm.graphics.GRect;
import java.awt.Color;

public class GDie extends GCompound {

    private Die die;
    private GRect body;
    private GOval[][] pips;
    
    public GDie() {
        this(new Die());
    }

    public GDie(Die die) {
        this(die, 100, 100);
    }

    public GDie(Die die, double dieWidth, double dieHeight) {
        
        this.die = die;
    
        pips = new GOval[3][3];
        
        body = new GRect(dieWidth, dieHeight);
        body.setFilled(true);
        body.setFillColor(Color.WHITE);
        add(body);
        
        
        //gives the GOvals in pip a value
        for (int row = 0; row < pips.length; ++row) {
            for (int col = 0; col < pips[row].length; ++col) {
                pips[row][col] = new GOval(dieWidth / 7.0,
                        dieHeight / 7.0);
                
                pips[row][col].setFilled(true);
                
                pips[row][col].setFillColor(Color.black);
                
                add(pips[row][col], (col * 2 + 1) * dieWidth / 7.0,
                        (row * 2 + 1) * dieHeight / 7.0);
                
            }
        }    
        setPips();
    }
    
    public int getValue(){
        return die.getValue();
    }
    
    public void roll(){
            die.roll();
            setPips();
    }
    
    public void setPips(){
        //make all pips invisible
        for(int row = 0; row < 3; row++){
            for(int col = 0; col < 3; col++){
                pips[row][col].setVisible(false);
            }
        }

        //decide which pips to make visible
        switch(getValue()){
            case 1:
                pips[1][1].setVisible(true);
                break;
            case 2:
                pips[2][0].setVisible(true);
                pips[0][2].setVisible(true);
                break;
            case 3:
                pips[2][0].setVisible(true);
                pips[0][2].setVisible(true);
                pips[1][1].setVisible(true);
                break;
            case 4:
                pips[2][0].setVisible(true);
                pips[0][2].setVisible(true);
                pips[0][0].setVisible(true);
                pips[2][2].setVisible(true);
                break;
            case 5:
                pips[2][0].setVisible(true);
                pips[0][2].setVisible(true);
                pips[0][0].setVisible(true);
                pips[2][2].setVisible(true);
                pips[1][1].setVisible(true);
                break;
            case 6:
                pips[2][0].setVisible(true);
                pips[0][2].setVisible(true);
                pips[0][0].setVisible(true);
                pips[2][2].setVisible(true);
                pips[1][0].setVisible(true);
                pips[1][2].setVisible(true);
                break;
            default:
                pips[2][0].setVisible(true);
                pips[0][2].setVisible(true);
                pips[0][0].setVisible(true);
                pips[2][2].setVisible(true);
                pips[1][0].setVisible(true);
                pips[1][2].setVisible(true);
                break;
        }

    }

} // end class
