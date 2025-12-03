/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.programmingchallenge;
import java.util.*;
/**
 *
 * @author nerubaya
 */

//Intro Paragraph:

/*
This program simulates a grid-based movement system where an object navigates through a two-dimensional grid influenced by multiple autonomous agents. 
The grid is defined by user-specified dimensions, and an object of a given height and width is placed at a user-defined starting location. 
Each cell in the grid can contain an agent, which is responsible for “voting” on the object’s next movement. Agents can move left, right, up, down, or remain still. 
Agents within the boundaries of the object have power and contribute to determining the object’s next action based on a majority vote of all powered agents. 
The object’s movement is constrained so that it cannot move outside the grid or into illegal positions, and if the object is near boundaries, random movement decisions are generated to avoid an infinite loop. 
A goal area is randomly calculated within the grid, and the trial continues until the object’s top-left corner aligns with the top-left corner of the goal area. 
During the simulation, points are updated each turn: points are deducted if the object is in a penalty zone (e.g., touching the grid edges) and gained if the object reaches the goal. 
The program displays the grid at each turn, showing the agents’ movement decisions as directional symbols, the object’s location as 'o', and the goal area marked with a superscript 'ᵍ'. 
Agents’ actions are randomly determined each turn, creating a dynamic environment in which the object is guided by the collective influence of the agents. 
The simulation continues in a loop with time steps until the object reaches the goal area, after which the program prints the total number of turns taken and the final score.
*/


public class ProgrammingChallenge {
    
    // these represent the coordinates of the top left cell of the object
    // they are static so they can be accessed anywhere in this class
    public static int iX;
    public static int iY;
    
    // a method for printing the grid
    // displays the agent movement decisions along with the object location and location of the goal area
        private static void displayGrid(
                char[][] grid,
                Object object,
                int rows,
                int cols,
                Agent[] agents,
                int objHeight,
                int objWidth,
                int[] goalPos
        ) {
            int index = 0; // tracks which agent corresponds to each grid cell
            System.out.println("DISPLAY");
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {

                    // retrieve the agent for this specific grid cell
                    Agent a = agents[index];
                    int action = a.getAction();

                    // assign a character based on the agent's chosen action
                    char symbol = '?';
                    if (action == 1) symbol = '<';
                    if (action == 2) symbol = '>';
                    if (action == 3) symbol = '^';
                    if (action == 4) symbol = 'v';
                    if (action == 5) symbol = '-';

                    // if the agent is inside the object area, show 'o' instead
                    if (object.withinObject(a, objHeight, objWidth)) {
                        grid[r][c] = 'o';
                    }
                    // show the agent symbol plus goal marker if this cell is the goal location
                    else if (r == goalPos[0] && c == goalPos[1]) {
                        System.out.print(symbol + "ᵍ");
                        index++; 
                        continue; // skip the usual printing logic since this cell is handled
                    }
                    else {
                        // otherwise show the agent movement symbol
                        grid[r][c] = symbol;
                    }

                    // print the cell character and move to the next agent
                    System.out.print(grid[r][c] + " ");
                    index++;
                }
                System.out.println(); // new line after each grid row
            }
        }


    public static void main(String[] args) {
        
        Scanner input = new Scanner(System.in); 
        
        // user chooses the grid size
        System.out.println("Please input the number of rows you want in the grid:"); 
        int N = input.nextInt(); 
        System.out.println("Please input the number of columns you want in the grid:"); 
        int M = input.nextInt(); 
        
        // user chooses object dimensions
        System.out.println("Please input the height of the object (number of rows):"); 
        System.out.println("Note: has to be AT LEAST 1 less than the grid height!"); 
        int objHeight = input.nextInt(); 
        System.out.println("Please input the width of the obejct (number of columns):"); 
        System.out.println("Note: has to be AT LEAST 2 less than the grid width!"); 
        int objWidth = input.nextInt(); 
        
        input.nextLine(); // consume leftover newline
        
        // user chooses where the object should initially appear
        System.out.println("Please input the row that the object will initially anchor to (coordinates of the top left corner of the object respective to the grid):"); 
        int validRowInput = (N - objHeight); 
        System.out.println("Note! Can be equal to 0, but cannot be greater than " + validRowInput + "!"); 
        iX = input.nextInt(); 
        
        System.out.println("Please input the column that the object will initially anchor to (coordinates of the top left corner of the object respective to the grid):"); 
        System.out.println("Note: Cannot be equal to 0, or the last column of the grid, (e.g, grid width = 10, cannot be equal to 9 or more)"); 
        iY = input.nextInt();
        
        // an object is created with the inputted dimensions
        // the constructor treats iY as row and iX as column
        Object object = new Object(iY, iX);
        
        // create an array of agents—one assigned to each grid cell
        Agent[] agents = object.createAgents(N, M);
        
        // initialize the grid of characters that will be displayed
        char[][] grid = new char[N][M];
        
        // fill grid with '.' so the user sees an empty layout before display
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                grid[r][c] = '.';
            }
        }
        
        // message signaling the start of the simulation
        System.out.println("");
        System.out.println("Thank you! The trial can now begin.");
        System.out.println("");
        
        // determine the grid coordinates of the goal area
        int[] goalPos = object.calcGoalPos(N, M, objHeight, objWidth);
        int turns = 1; // track number of turns the simulation has run
        
        // GAME LOOP
        /*
        1. show the grid as is, display the current points, and timestep
        2. move the object, update the grid and points, and timestep
        */
        
        // loop repeats until object reaches the goal area
        while (!object.withinGoalArea(goalPos)) {
           
           try {
                Thread.sleep(1000); // wait one second between turns
           } catch (InterruptedException e) { }
           
            System.out.println("Turn: " + turns); 
            
            // show the current state of the grid
            displayGrid(grid, object, N, M, agents, objHeight, objWidth, goalPos);

            // generate a completely new set of random agent decisions
            agents = object.createAgents(N, M);

            // determine each agent's influence relative to the object's position
            for (Agent agent : agents) {
                agent.calcPower(object.getiX(), object.getiY(), objHeight, objWidth);
            }
            
            // move the object once based on agent voting
            object.moveObject(agents, N, M, objHeight, objWidth);
            
            // update running score
            object.updatePoints(M, objWidth, goalPos);
            turns++; // advance the turn counter
        }
        
        // once object reaches the goal, print summary results
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
