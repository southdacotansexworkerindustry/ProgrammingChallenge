/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.programmingchallenge;
import java.util.*;
/**
 *
 * @author nerubaya
 */

public class ProgrammingChallenge {
    
    // these represent the coordinates of the top left cell of the object
    public static int iX;
    public static int iY;
    
    // a method for printing the grid
    // displays the agent movement decisions along with the object location and location of the goal area
    
    private static void displayGrid(char[][] grid, Object object, int rows, int cols, Agent[] agents, int objRowLength, int objColLength){
    int index = 0;
    
        // updates the grid to have the symbols
        for (int r = 0; r < rows; r++){
            for (int c = 0; c < cols; c++){
                Agent a = agents[index];
                
                int action = a.getAction();
                
                char symbol = '?';
                
                if (object.withinObject(a, objRowLength, objColLength)){
                    symbol = 'o';
                    grid[r][c] = symbol;
                    index++;
                    continue;
                }
                
                if(action == 1){
                    symbol = '<';   // left
                }
                if(action == 2){
                    symbol = '>';   // right
                }
                if(action == 3){
                    symbol = '^';   // up
                }
                if(action == 4){
                    symbol = 'v';   // down
                }
                if(action == 5){
                    symbol = '-';   // still
                }

                grid[r][c] = symbol;
                index++;
            }
        }
        
        // prints the current grid with the agent directions
        System.out.println("\nAgent Directions:");
        for(int r = 0; r < rows; r++){
            for(int c = 0; c < cols; c++){
                System.out.print(grid[r][c] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        
        Scanner input = new Scanner(System.in);
        System.out.println("Please input the number of rows you want in the grid:");
        int N = input.nextInt();
        System.out.println("Please input the number of columns you want in the grid:");
        int M = input.nextInt();
        System.out.println("Please input the height of the object (number of rows):");
        System.out.println("Note: has to be AT LEAST 1 less than the grid height!");
        int objHeight = input.nextInt();
        System.out.println("Please input the width of the obejct (number of columns):");
        System.out.println("Note: has to be AT LEAST 2 less than the grid width!");
        int objWidth = input.nextInt();
        System.out.println("Please input the row that the object will initially anchor to (coordinates of the top left corner of the object respective to the grid):");
        int validRowInput = (N - objHeight); // e.g 5-3 = 2 is the last available position
        System.out.println("Note! Can be equal to 0, but  cannot be greater than " + validRowInput + "!");
        iX = input.nextInt();
        System.out.println("Please input the column that the object will initially anchor to (coordinates of the top left corner of the object respective to the grid):");
        System.out.println("Note: Cannot be equal to 0, or the last column of the grid, (e.g, grid width = 10, cannot be equal to 9 or more)");
        iY = input.nextInt();
        
        // IMPLEMENT FAST AND DEFAULT METHODS
        
        // an object is created with the inputted dimensions
        Object object = new Object(iY, iX);
        
        // a series of agents are created using the createAgents method
        //  --> the grid is populated with agents who will then make a decision
        // --> every time createAgents is called, the grid is repopulated with agents making new decisions
        Agent[] agents = object.createAgents(N, M);
        
        // the grid is created based on the inputted dimensions, chars will be used to represent moves and the object, 
        /// along with penantly areas
        char[][] grid = new char[N][M];
        
        // the grid is initially filled with the character '.' to signify that it is currently empty
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                grid[r][c] = '.';
            }
        }
        
        // ending user input
        System.out.println("");
        System.out.println("Thank you! The trial can now begin.");
        System.out.println("");
        
        // calculuate goal location
        int[] goalPos = object.calcGoalPos(grid, N, M);
        int turns = 1;
        
        
        // GAME LOOP 
        
        /*
        1. show the grid as is, display the current points, and timestep
        2. move the object, update the grid and points, and timestep
        */
        
        // loop will repeat as long as the object has not reached the goal location
        // --> goal location is reached when top left corner of object == top left corner of goal area
        while (!object.withinGoalArea(goalPos)){
            // display the current number of turns
            System.out.println("Turn: " + turns);
            System.out.println("");
            // display grid
            displayGrid(grid, object, N, M, agents, objHeight, objWidth);
            System.out.println("");
            System.out.println("Agent moves:");
            // INSERT AGENT MOVES HERE
            
            
            // create new agents who will make new decisions
            agents = object.createAgents(N, M);
            // move the object based on the decisions made by the new agents
            object.moveObject(agents);
            // update points based on where the object is currently located
            object.updatePoints(M, objWidth, goalPos);
            turns++;
            System.out.println("");
        }
        
        // print end-trial message and data
        if (object.withinGoalArea(goalPos)){
            System.out.println("");
            System.out.println("The trial has ended!");
            System.out.println("");
            System.out.println("The object has successfully reached the target zone. Below are the stats from the trial.");
            System.out.println("Number of turns: " + turns);
            System.out.println("Number of points: " + object.getPoints());
        }
 
    }
}
