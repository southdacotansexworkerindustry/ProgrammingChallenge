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
public class Agent extends Object{
     //  A = {left, right, up, down, still} => 1...5
    private int posx;
    private int posy; 
    private int power;
    
    public Agent(int action, int posx, int posy, int power){
        super(action);
        this.posx = posx;
        this.posy = posy;
        this.power = power;
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
    
    public int calcPower(){
        if(super.withinObject(posx, posy)){
            power = 1;
        }
        else{
            power = 0;
        }
    } 

    public int decideAction(){
        int r = Math.random(0,1);
        if(r <= 0.2){
            action = 1; //left
        }
        else if(r <= 0.4){
            action = 2; //right
        }
        else if(r <= 0.6){
            action = 3; //up
        }
        else if(r <= 0.8){
            action = 4; //down
        }
        else{
            action = 5; //still
        }
    }
}
