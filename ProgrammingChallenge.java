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
        
        Object object = new Object(n, m);
        
    }
}
