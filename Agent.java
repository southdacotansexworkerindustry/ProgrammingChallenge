/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.programmingchallenge;
import java.lang.*;
/**
 *
 * @author nerubaya
 */

public class Agent {
    // values 1–5: left, right, up, down, still
    // value 0 represnts an agent within a penalty zone
    // value 6 represents an agent within an object
    private int action; 
    private int posx;
    private int posy;
    private int power;

    public Agent(int posx, int posy){
        this.posx = posx;
        this.posy = posy;
        this.power = 0;
        this.action = 5; // default still
    }

    public int getAction(){
        return action;
    }
    
    public void changeAction(int num){
        this.action = num;
    }

    public int getPosx(){
        return posx;
    }

    public int getPosy(){
        return posy;
    }

    public int getPower(){
        return power;
    }
    
    // this is mostly used for when an agent is within a penalty zone,
    // --> so its power gets set to 0, even if its under the object
    public void changePower(int num){
        this.power = num;
    }

    // ????
    // shouldn't it be 1 if it's BELOW the object? Not within?
    // power = 1 if inside object bounds
    public void calcPower(int objX, int objY, int objWidth, int objHeight){
        if (posx >= objX && posx < objX + objWidth &&
            posy >= objY && posy < objY + objHeight){
            power = 1;
        }
        else{
            power = 0;
        }
    }

    // choose random action 1–5
    public void decideAction(){
        double r = Math.random();   // returns [0,1)

        if(r <= 0.2){
            action = 1;  // left
        }
        else if(r <= 0.4){
            action = 2;  // right
        }
        else if(r <= 0.6){
            action = 3;  // up
        }
        else if(r <= 0.8){
            action = 4;  // down
        }
        else{
            action = 5;  // still
        }
    }
}