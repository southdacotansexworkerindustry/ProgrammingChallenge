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
    public boolean withinObject(Agent agent, int objHeight, int objWidth) {
        // retrieve agent coordinates (column/row)
        int ax = agent.getPosx();  
        int ay = agent.getPosy();  

        // check if agent falls into object's bounding box
        return (
            ax >= topLeftX &&
            ax <  topLeftX + objWidth &&   

            ay >= topLeftY &&
            ay <  topLeftY + objHeight
        );
    }
    
    // Create one agent per grid cell
    public Agent[] createAgents(int rows, int cols){

        // total number of agents equals number of grid cells
        Agent[] agents = new Agent[rows * cols];
        int index = 0;

        // iterate over every cell and create an agent tied to that position
        for(int y = 0; y < rows; y++){
            for(int x = 0; x < cols; x++){
                agents[index] = new Agent(x, y); // (column, row)
                agents[index].decideAction();    // each agent chooses its action immediately
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

            // only agents with power contribute to the vote
            if(agent.getPower() == 1){

                int a = agent.getAction();  // get agent's individual decision

                // add vote to the matching counter
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
        if(cRight > max){ max = cRight; move = 2; }
        if(cUp > max){ max = cUp; move = 3; }
        if(cDown > max){ max = cDown; move = 4; }
        if(cStill > max){ max = cStill; move = 5; }
        
        // print which action won the vote
        System.out.println("ACTION:");
        switch(move){
            case 1: System.out.println("Left");
            break;
            case 2: System.out.println("Right");
            break;
            case 3: System.out.println("Up");
            break;
            case 4: System.out.println("Down");
            break;
            case 5: System.out.println("Still");
            break;
        }

        return move;
    }
    
       // Checks if the object's next move keeps all "o" tiles inside the grid
    public boolean isMoveValid(int move, int gridRows, int gridCols, int objHeight, int objWidth){

        // copy current position so we can test a hypothetical move
        int newX = topLeftX;
        int newY = topLeftY;

        // adjust coordinates based on the movement direction
        if(move == 1) newX--;   // left
        else if(move == 2) newX++; // right
        else if(move == 3) newY--; // up
        else if(move == 4) newY++; // down

        // boundary check → ensure entire object stays inside grid
        if(newX < 0) return false;
        if(newY < 0) return false;

        if(newX + objWidth  > gridCols) return false;
        if(newY + objHeight > gridRows) return false;

        return true;
    }

    
    // moves the object (its top left coordinate) using the action most popular among the agents
    public void moveObject(Agent[] agents, int gridRows, int gridCols, int objHeight, int objWidth){

    int attempts = 0;

    int move = decideMove(agents);

    // If illegal → regenerate direction up to 10 times
    while(!isMoveValid(move, gridRows, gridCols, objHeight, objWidth) && attempts < 10){
        move = (int)(Math.random() * 5) + 1;  // 1–5
        attempts++;
    }

    // If STILL invalid after attempts → stay still
    if(!isMoveValid(move, gridRows, gridCols, objHeight, objWidth)){
        move = 5;
    }

    // Apply the valid movement
    if(move == 1) topLeftX--;
    else if(move == 2) topLeftX++;
    else if(move == 3) topLeftY--;
    else if(move == 4) topLeftY++;
}
    
    // finds where the goal area will be located
    // first finds the available space within the grid for the goal to be located, 
    // --> then randomly picks an area within that space
    public int[] calcGoalPos(int gridRows, int gridCols, int objHeight, int objWidth){
        
        // the goalPos is the top left corner of the goal area
        // if the object's top left corner reaches it, then it has fitted into the entire goal area
        // format: goalPos = x,y coordinates of the position
        int[] goal = new int[2];
        Random rand = new Random();

        
        // these limit variables will determine where the top left corner of the goal area will be located, 
        // --> they're calculuated based on grid values and object starting pos
        
        // upperLimit is equal to the number of rows in the grid, or the very last row
        int maxY = gridRows - objHeight;
        
        // subtracts the height of the object from the total height of the grid
        // --> thus, lowerLimit represents the earliest row that the goal can be placed in
        int maxX = gridCols - objWidth;
        
        // randomly calculuate where the goal will be located within available space within the grid
        // logic: let's say the grid height is 5, and the object's height is 2
        // upper limit is the last available row, so 5.
        // lower limit is the earliest available row, so 5-2 which is 3
        // 5 - 3 + 1 = 3 --> rand.nextInt(3) gives a range of 0,1,2
        // adding the lower limit keeps it within the available rows
        // --> so 0 becomes 3, which is a valid row. 2 becomes 5, which is the last available row
        
        
            goal[1] = rand.nextInt(maxY + 1); // Y row
            goal[0] = rand.nextInt(maxX + 1); // X col
            
          System.out.println("Goal Coordinates X: " + goal[1] + " Y: " + goal[0]);
        return goal;
    }
    
    // checks if the object has reached the goal location
    public boolean withinGoalArea(int[] goal){
        return (topLeftX == goal[1] && topLeftY == goal[0]); //accounting for a natural shift in counting systems
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