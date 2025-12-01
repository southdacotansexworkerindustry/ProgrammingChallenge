/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.programmingchallenge;
import java.util.*;

/**
 *
 * @author nerubaya
 */
public class Object {
    
    // top-left corner column
    private int topLeftX;   
    
    // top-left corner row
    private int topLeftY;   
    
    private int points;

    public Object(int iY, int iX){
        this.topLeftX = iX;
        this.topLeftY = iY;
        this.points = 0;
    }

    public int getiX(){
        return topLeftX;
    }

    public int getiY(){
        return topLeftY;
    }
    
    public int getPoints(){
        return points;
    }

    // Return object cells from the grid
    public int[] getObjectCoords(int n, int m, int[][] grid){
        int[] obj = new int[n * m];
        int index = 0;
        
        // this loop translates the object coordinates into respective grid values
        for(int r = 0; r < n; r++){
            for(int c = 0; c < m; c++){
                obj[index] = grid[topLeftY + r][topLeftX + c];
                index++;
            }
        }
        return obj;
    }

    // Check if agent is inside object area
    public boolean withinObject(Agent agent, int objRowLength, int objColLength){
        int agentX = agent.getPosx();
        int agentY = agent.getPosy();

        if(agentX >= topLeftX && agentX < topLeftX + objRowLength &&
           agentY >= topLeftY && agentY < topLeftY + objColLength){
            return true;
        }
        return false;
    }
    
    // Create one agent per grid cell
    public Agent[] createAgents(int gridRow, int gridCol){
        Agent[] agents = new Agent[gridRow * gridCol];

        int index = 0;
        for(int row = 0; row < gridRow; row++){
            for(int col = 0; col < gridCol; col++){
                // a new agent is created for that specific cell
                agents[index] = new Agent(row, col);
                // newly created agent then produces a move decision
                agents[index].decideAction();
                index++;
            }
        }
        return agents;
    }

    // Count majority movement among powered agents
    public int decideMove(Agent[] agents){
        // all the agent move votes will be set to 0 initially
        int cLeft = 0;
        int cRight = 0;
        int cUp = 0;
        int cDown = 0;
        int cStill = 0;
        
        // goes through the list of current agents
        for(Agent agent : agents){
            if(agent.getPower() == 1){
                // if the agent is empowered, then the program will get its individual move decision,
                // ---> then it will add it to the ballot for the available moves
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
        
        // a series of if statements that will check which move received the greatest number of votes
        // after deciding which move is most popular, it will return the value of that move (1,2,3,4 or 5)
        if(cRight > max){ max = cRight; move = 2; }
        if(cUp > max){ max = cUp; move = 3; }
        if(cDown > max){ max = cDown; move = 4; }
        if(cStill > max){ max = cStill; move = 5; }

        return move;
    }
    
    // moves the object (its top left coordinate) using the action most popular among the agents
    public void moveObject(Agent[] agents){
        int move = decideMove(agents);
        
        if(move == 1){
            topLeftX--;
        }
        else if(move == 2){
            topLeftX++;
        }
        else if(move == 3){
            topLeftY--;
        }
        else if(move == 4){
            topLeftY++;
        }
        else if(move == 5){
            return;
        }
    }
    
    // finds where the goal area will be located
    // first finds the available space within the grid for the goal to be located, 
    // --> then randomly picks an area within that space
    public int[] calcGoalPos(char[][] grid, int rows, int cols){
        
        // the goalPos is the top left corner of the goal area
        // if the object's top left corner reaches it, then it has fitted into the entire goal area
        // format: goalPos = x,y coordinates of the position
        int[] goalPos = {0,0};
        
        Random rand = new Random();
        
        // these limit variables will determine where the top left corner of the goal area will be located, 
        // --> they're calculuated based on grid values and object starting pos
        
        // upperLimit is equal to the number of rows in the grid, or the very last row
        int upperLimit = rows;
        
        // subtracts the height of the object from the total height of the grid
        // --> thus, lowerLimit represents the earliest row that the goal can be placed in
        int lowerLimit = (rows - topLeftY);
        
        // randomly calculuate where the goal will be located within available space within the grid
        // logic: let's say the grid height is 5, and the object's height is 2
        // upper limit is the last available row, so 5.
        // lower limit is the earliest available row, so 5-2 which is 3
        // 5 - 3 + 1 = 3 --> rand.nextInt(3) gives a range of 0,1,2
        // adding the lower limit keeps it within the available rows
        // --> so 0 becomes 3, which is a valid row. 2 becomes 5, which is the last available row
        
        goalPos[0] = rand.nextInt(upperLimit - lowerLimit + 1) + lowerLimit;
        // column range is from 1 to the second to last position, this accounts for penalty zones
        goalPos[1] = rand.nextInt(cols - 1) + 1;
        
        return goalPos;
    }
    
    // checks if the object has reached the goal location
    public boolean withinGoalArea(int[] goalPos){
        if (topLeftX == goalPos[0]){
            return true;
        }
        return false;
    }
    
    // checks if the object is currently located in a penalty zone
    private boolean objectWithinPenatly(int cols, int objWidth){
        
        // if it is located in the first or last column, it returns true to indicate it is within a penalty zone
        if(topLeftX == 0 || (topLeftX + objWidth - 1) == (cols - 1)){
            return true;
        }
        return false;
    }
    
    // updates the points based on the object location within the grid
    public void updatePoints(int cols, int objWidth, int[] goalPos){
        
        // if the object is currently within the penatly zone, subtract 10 points
        if (objectWithinPenatly(cols, objWidth)){
            points -= 10;
        }
        
        // if the object is fully within the goal area, add 100 points
        else if(withinGoalArea(goalPos)){
            points += 100;
        }
        
        // if the object is not within a penatly zone and not within the goal location, subtract 1 point
        else{
            points -= 1;
        }
    }
}