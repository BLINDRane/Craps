/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import acm.graphics.GCompound;

public class GDice extends GCompound{
   private Dice dice;
   private GDie[] gdie = new GDie[2]; //TODO: update to not use array?
   
   public GDice(Dice dice){
       this.dice = dice;
       gdie[0] = new GDie(dice.getDie1());
       add(gdie[0],0,0);
       gdie[1] = new GDie(dice.getDie2());
       add(gdie[1], gdie[0].getWidth()*1.5, 0);
   }
   
   public int getValue(){
       return dice.getValue();
   }
   
   public void roll(){
       gdie[0].roll();
       gdie[1].roll();
   }
}
