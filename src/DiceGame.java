/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import acm.program.GraphicsProgram;
import acm.graphics.GLabel;
import svu.csc213.Dialog;

import javax.swing.*;
import java.awt.event.ActionEvent;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

//this is a comment

public class DiceGame extends GraphicsProgram {

    Dice dice;
    GDice gdice;
    JButton playBtn, wagerBtn, quitBtn, rollBtn, loadBtn;
    int point, wager, player, bank;
    GLabel playerLabel, bankLabel, wagerLabel;

    boolean running = true;

    @Override
    public void init() {

        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fc.showOpenDialog(this);

        dice = new Dice();
        gdice = new GDice(dice);
        add(gdice, (getWidth() - gdice.getWidth()) / 2.0, 120);

        playBtn = new JButton("Play");
        wagerBtn = new JButton("Wager");
        quitBtn = new JButton("Quit");
        rollBtn = new JButton("Roll");
        loadBtn = new JButton("Load Game");
        add(loadBtn, SOUTH);
        add(playBtn, SOUTH);
        add(wagerBtn, SOUTH);
        add(quitBtn, SOUTH);
        add(rollBtn, SOUTH);
        rollBtn.setVisible(false);
        
        player = 1000;
        bank = 10000;

        playerLabel = new GLabel("Your Money: " + player);
        bankLabel = new GLabel("Bank Money: " + bank);
        wagerLabel = new GLabel("Current Bet: " + wager);

        add(playerLabel, (getWidth() - playerLabel.getWidth()) / 2, 300);
        add(bankLabel, (getWidth() - bankLabel.getWidth()) / 2, 320);
        add(wagerLabel, (getWidth() - wagerLabel.getWidth()) / 2, 340);

        addActionListeners();

        GDice ex = new GDice(dice);
        add(ex, 0, 0);
    }


    @Override
    public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()) {
            case "Play":
                play();
                break;
            case "Wager":
                wager();
                break;
            case "Quit":
                saveGame();
                break;
            case "Roll":
                roll();
                break;
            case "Load Game":
                loadGame();
                break;
            default:
                Dialog.showMessage("Something went wrong.");
                break;
        }
    }

    private void play() {
        if (wager == 0) {
            Dialog.showMessage("Hold on there buddy, you need to place a bet!");
            wager();
        } else if(!Dialog.getYesOrNo("Would you like to keep your current bet(" + wager + ")?")) {
            wager();
        } else {
            Dialog.showMessage("Alrighty then. Press roll to begin.");
            playBtn.setVisible(false);
            wagerBtn.setVisible(false);
            rollBtn.setVisible(true);
        }
    }

    private void roll() {
       gdice.roll();

        if (point == 0) {
            switch (gdice.getValue()) {
                case 7:
                case 11:
                    win();
                    break;
                case 2:
                case 3:
                case 12:
                    lose();
                    break;

                default:
                    point = gdice.getValue();
                    Dialog.showMessage("The point number is " + point + ". Roll again.");
                    break;
            }
        } else {
            //handle the second series of rolls
            if (gdice.getValue() == point) {
                win();
            } else if (gdice.getValue() == 7) {
              lose();
            } else {
                Dialog.showMessage("No winner.....yet. Roll again.");
            }
        }
    }

    private void wager() {
        wager = Dialog.getInteger("Please enter a bet in round numbers:");
        if (wager < 0 || wager > player) {
            Dialog.showMessage("You have entered an invalid bet. Please enter a number that is non-negative and is not more than the money you have.");
            wager();
        }
        wagerLabel.setLabel("Current Bet: " + wager);
        Dialog.showMessage("Alright. Now that you have a bet, press play to get started.");
    }

    private void quit() {
        saveGame();
    }

    private void win() {
        Dialog.showMessage("You win! Take that bank. Press play to win more!....or lose it all.");
        
        player += wager;
        bank -= wager;
        playerLabel.setLabel("Your Money: " + player);
        bankLabel.setLabel("Bank Money: " + bank);
        point = 0;
        
        rollBtn.setVisible(false);
        playBtn.setVisible(true);
        wagerBtn.setVisible(true);
    }

    private void lose(){
        Dialog.showMessage("You have been defeated. Press play to try again.");
        
        player -= wager;
        bank += wager;
        playerLabel.setLabel("Your Money: " + player);
        bankLabel.setLabel("Bank Money: " + bank);
        point = 0;
        
        rollBtn.setVisible(false);
        playBtn.setVisible(true);
        wagerBtn.setVisible(true);
    }
    
    private void saveGame() {
        
        int saveMe = JOptionPane.showConfirmDialog(this,"Would you like to save your game? Your wager, your money, and the bank's money will be saved, but the point will be lost.");
        
        if(saveMe == JOptionPane.YES_OPTION){
            //get a name for the file and write to it
            String saveName = 
            Dialog.getString("Please enter a name for your save:").trim();
            try (PrintWriter out =
            new PrintWriter("C:\\Users\\wyattadmin\\Documents\\" + saveName + ".txt")) {
                out.println(player);
                out.println(bank);
                out.println(wager);

            } catch (FileNotFoundException ex) {
                Dialog.showMessage("Game failed to save.");
                //Logger.getLogger(DiceGame.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if(saveMe == JOptionPane.NO_OPTION) {
            Dialog.showMessage("Aight. Bye.");
            System.exit(0);
        } else {
            return;
        }

        Dialog.showMessage("Save Successful. Bye now.");
        System.exit(0);
    }

    private void loadGame() {
        String saveName =
        Dialog.getString("Please enter the name of your save game:").trim();
        String fileName =
        "C:\\Users\\wyattadmin\\Documents\\" + saveName + ".txt";

        try {
            FileReader reader = new FileReader(fileName);
            BufferedReader br = new BufferedReader(reader);
            player = Integer.parseInt(br.readLine());
            playerLabel.setLabel("Your Money: " + player);
            bank = Integer.parseInt(br.readLine());
            bankLabel.setLabel("Bank Money: " + bank);
            wager = Integer.parseInt(br.readLine());
            wagerLabel.setLabel("Current Bet: " + wager);
            br.close();
        } catch (FileNotFoundException ex) {
            Dialog.showMessage("Could not find file '" + fileName + "'.");
        } catch (IOException ex) {
            Dialog.showMessage("Whoops. Something went wrong when reading the file.");
        }
    }

    public static void main(String[] args) {
        new DiceGame().start();
    }

}
