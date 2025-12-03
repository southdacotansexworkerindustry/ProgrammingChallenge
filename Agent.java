/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.programmingchallenge;
import java.lang.*;
import java.util.Random;
/**
 *
 * @author nerubayacalc
 */

public class Agent {
    private static final Random RNG = new Random(); // random number generator for movement
    
    // values 1–5: left, right, up, down, still
    // value 0 represents an agent within a penalty zone
    // value 6 represents an agent within an object
    private int action; 
    
    // the agent's column (x-coordinate) in the grid
    private int posx;
    
    // the agent's row (y-coordinate) in the grid
    private int posy;
    
    // influence of the agent on object movement; 1 if inside object, 0 otherwise
    private int power;

    // constructor initializes position and default values
    public Agent(int posx, int posy){
        this.posx = posx; // set column
        this.posy = posy; // set row
        this.power = 0;   // default: no influence
        this.action = 5;  // default: still
    }

    // getter for agent's chosen action
    public int getAction(){
        return action;
    }
    
    // setter to manually change the agent's action
    public void changeAction(int num){
        this.action = num;
    }

    // getter for x-coordinate (column)
    public int getPosx(){
        return posx;
    }

    // getter for y-coordinate (row)
    public int getPosy(){
        return posy;
    }

    // getter for power (influence on object)
    public int getPower(){
        return power;
    }
    
    // setter to manually change power (usually for penalty or overrides)
    // mostly used for when an agent is within a penalty zone,
    // --> so its power gets set to 0, even if its under the object
    public void changePower(int num){
        this.power = num;
    }

    // calculate agent's power based on position relative to object
    // power = 1 if agent is within object bounds; 0 otherwise
    // shouldn't icalct be 1 if it's BELOW the object? Not within?
    public void calcPower(int objRow, int objCol, int objHeight, int objWidth){
        if (posx >= objRow && posx < objRow + objHeight &&
            posy >= objCol && posy < objCol + objWidth){
            power = 1; // agent is inside object
        } else {
            power = 0; // agent is outside object
        }
    }

    // randomly select one of five possible actions: left, right, up, down, still
    public void decideAction(){
        double r = RNG.nextDouble(); // generate random number [0.0, 1.0)

        if (r <= 0.2) {
            action = 1; // left
        } else if (r <= 0.4) {
            action = 2; // right
        } else if (r <= 0.6) {
            action = 3; // up
        } else if (r <= 0.8) {
            action = 4; // down
        } else {
            action = 5; // stay still
        }
    }
}
