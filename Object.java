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

    private int iX;   // top-left corner column
    private int iY;   // top-left corner row

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

    // Return object cells from the grid
    public int[] objectCoords(int n, int m, int[][] grid){
        int[] obj = new int[n * m];
        int index = 0;

        for(int r = 0; r < n; r++){
            for(int c = 0; c < m; c++){
                obj[index] = grid[iY + r][iX + c];
                index++;
            }
        }
        return obj;
    }

    // Check if agent is inside object area
    public boolean withinObject(Agent a, int n, int m){
        int ax = a.getPosx();
        int ay = a.getPosy();

        if(ax >= iX && ax < iX + m &&
           ay >= iY && ay < iY + n){
            return true;
        }
        return false;
    }

    // Create one agent per grid cell
    public Agent[] createAgents(int N, int M){
        Agent[] agents = new Agent[N * M];

        int index = 0;
        for(int r = 0; r < N; r++){
            for(int c = 0; c < M; c++){
                agents[index] = new Agent(c, r);
                agents[index].decideAction();
                index++;
            }
        }
        return agents;
    }

    // Count majority movement among powered agents
    public int decideMove(Agent[] agents){
        int cLeft = 0;
        int cRight = 0;
        int cUp = 0;
        int cDown = 0;
        int cStill = 0;

        for(Agent agent : agents){
            if(agent.getPower() == 1){
                int a = agent.getAction();

                if(a == 1) cLeft++;
                if(a == 2) cRight++;
                if(a == 3) cUp++;
                if(a == 4) cDown++;
                if(a == 5) cStill++;
            }
        }

        // Choose the direction with highest count
        int move = 1;
        int max = cLeft;

        if(cRight > max){ max = cRight; move = 2; }
        if(cUp > max){ max = cUp; move = 3; }
        if(cDown > max){ max = cDown; move = 4; }
        if(cStill > max){ max = cStill; move = 5; }

        return move;
    }

    public void moveObject(Agent[] agents){
        int m = decideMove(agents);

        if(m == 1){
            iX--;
        }
        else if(m == 2){
            iX++;
        }
        else if(m == 3){
            iY--;
        }
        else if(m == 4){
            iY++;
        }
        else if(m == 5){
            
        }
}
}