/**
 *
 * @author wyattadmin
 */
public class Dice {
    private Die die1;
    private Die die2;
    
    public Dice(){
        die1 = new Die();
        die2 = new Die();
    }
    
    public void roll(){
        die1.roll();
        die2.roll();
    }
    
    public int getValue(){
        return die1.getValue() + die2.getValue();
    }
    
    public Die getDie1(){
        return die1;
    }
    
    public Die getDie2(){
        return die2;
    }
}
