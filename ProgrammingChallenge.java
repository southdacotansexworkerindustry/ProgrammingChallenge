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

    public static int iX;
    public static int iY;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Please input the number of rows you want in a grid:");
        int N = input.nextInt();
        System.out.println("Please input the number of columns you want in a grid:");
        int M = input.nextInt();
        System.out.println("Please input the number of rows you want in the object:");
        int n = input.nextInt();
        System.out.println("Please input the number of columns you want in the object:");
        int m = input.nextInt();
        System.out.println("Please input the number of a row you want an object initially to anchor to:");
        iX = input.nextInt();
        System.out.println("Please input the number of a column you want an object initially to anchor to:");
        iY = input.nextInt();
        Agent[] agents = Object.createAgents(N, M);

        
        char[][] grid = new char[N][M];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                grid[r][c] = '.';
            }
        }

        int index = 0;

    for(int r = 0; r < N; r++){
        for(int c = 0; c < M; c++){
            Agent a = agents[index];

            int action = a.getAction();
            char symbol = '?';

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
                symbol = 'o';   // still
            }

            grid[r][c] = symbol;
            index++;
        }
    }


    System.out.println("\nAgent Directions:");
    for(int r = 0; r < N; r++){
        for(int c = 0; c < M; c++){
            System.out.print(grid[r][c] + " ");
        }
        System.out.println();
    }
    }
}
