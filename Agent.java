/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.programmingchallenge;

/**
 *
 * @author nerubaya
 */
public class Agent extends Object{
    private int action; //  A = {left, right, up, down, still} => 1...5
    private int posx;
    private int posy; 
    private int power;
    
    public Agent(int action, int posx, int posy, int power){
        this.action = action;
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
}
