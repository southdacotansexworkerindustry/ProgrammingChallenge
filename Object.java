/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.programmingchallenge;

/**
 *
 * @author nerubaya
 */
public class Object {
    private int iX;
    private int iY;
    private int action;
    
    public Object(int iY, int iX){
        this.iX = iX;
        this.iY = iY;
    }
    
    public int getiX(){
        return iX;
    }
    public int getiY(){
        return iY;
    }

public int[] objectCoords(int n, int m, int[][] grid){
    int[] obj = new int[n * m];
    int index = 0;
    for(int i = 0; i < n; i++){
        for(int j = 0; j < m; j++){
            obj[index] = grid[iY + i][iX + j];
            index++;
        }
    }
    return obj;
}

public boolean withinObject(Agent a, int n, int m){
    int ax = a.getPosx();
    int ay = a.getPosy();

    if(ax >= iX && ax < iX + m && ay >= iY && ay < iY + n){
        return true;
    }
    return false;
}
    

public Agent[] createAgents(int N, int M){
    Agent[] agents = new Agent[N * M];

    for(int i = 0; i < agents.length; i++){
        agents[i] = new Agent();   
        agents[i].decideAction();  
    }
    return agents;
}

    
public int decideMove(Agent[] agents){
    int cLeft = 0;
    int cRight = 0;
    int cUp = 0;
    int cDown = 0;
    int cStill = 0;

    for (Agent agent : agents) {
        if(agent.getPower() == 1){
            int a = agent.getAction();
            if(a == 1){
                cLeft++;
            }
            if(a == 2){
                cRight++;
            }
            if(a == 3){
                cUp++;
            }
            if(a == 4){
                cDown++;
            }
            if(a == 5){
                cStill++;
            }
        }
    }

    int max = cLeft;
    int move = 1;

    if(cRight > max){
        max = cRight;
        move = 2;
    }
    if(cUp > max){
        max = cUp;
        move = 3;
    }
    if(cDown > max){
        max = cDown;
        move = 4;
    }
    if(cStill > max){
        max = cStill;
        move = 5;
    }

    return move;
}

public void moveObject(Agent[] agents){
    int m = decideMove(agents);
    if(m == 1){
        iX = iX - 1;
    }
    else if(m == 2){
        iX = iX + 1;
    }
    else if(m == 3){
        iY = iY + 1;
    }
    else if(m == 4){
        iY = iY - 1;
    }
    else{
        continue;
    }
}
}