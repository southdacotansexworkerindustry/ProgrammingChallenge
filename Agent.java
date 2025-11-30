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
    private int action; // values 1–5: left, right, up, down, still
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

    public int getPosx(){
        return posx;
    }

    public int getPosy(){
        return posy;
    }

    public int getPower(){
        return power;
    }

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