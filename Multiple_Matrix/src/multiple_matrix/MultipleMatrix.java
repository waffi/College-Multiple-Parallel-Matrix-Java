/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiple_matrix;

/**
 *
 * @author Waffi Fatur Rahman
 */

//400
//The time is 248
//BUILD SUCCESSFUL (total time: 1 second)

//900
//The time is 10124
//BUILD SUCCESSFUL (total time: 12 seconds)

//1600
//The time is 78024
//BUILD SUCCESSFUL (total time: 1 minute 25 seconds)

//2500
//The time is 374397
//BUILD SUCCESSFUL (total time: 6 minute 30 seconds)

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class MultipleMatrix {

    public static int[][] a;
    public static int[][] b;
    public static int[][] c;
    
    public static int kolomA;
    public static int barisA;
    public static int kolomB;
    public static int barisB;
    
    public static void main(String[] args) throws Exception {
                
        a = loadMatrix("a.txt");
        b = loadMatrix("b.txt");

        kolomA = a.length;
        barisA = a[0].length;
        kolomB = b.length;
        barisB = b[0].length;        
        
        c= new int [a.length][b[0].length];
        
//        displayMatrix(a,"A");
//        displayMatrix(b,"B");

        long t1 = System.currentTimeMillis();
        for (int i = 0; i < barisA; i++) {
            for (int j = 0; j < barisB; j++) {
                for (int k = 0; k < kolomB; k++) {
                    c[i][j] = c[i][j] + a[i][k] * b[k][j];
                }
            }
        }
        long t2 = System.currentTimeMillis();
        System.out.println("\nThe time is "+(t2-t1));

        storeMatrix(c,"c.txt");
//        displayMatrix(c,"C");
                
    }

    public static int[][] loadMatrix(String filename) throws Exception {
        Scanner s = new Scanner(new FileInputStream(filename));
        int rows = s.nextInt();
        int cols = s.nextInt();
        int mat[][] = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                mat[i][j] = s.nextInt();
            }
        }
        return mat;
    }

    public static void storeMatrix(int c[][], String filename) throws Exception {
        int rows = c.length;
        int cols = c[0].length;
        PrintWriter pw = new PrintWriter(new FileOutputStream(filename));
        pw.println("rows   " + rows + "      columns     " + cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                pw.print(c[i][j] + "    ");
            }
            pw.println();
        }
        pw.close();
    }
    
    public static void displayMatrix(int m[][], String name) throws Exception {
        int rows = m.length;
        int cols = m[0].length;
        
        System.out.println("\nMatrix "+name);
        System.out.println("rows   " + rows + "      columns     " + cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(m[i][j] + "    ");
            }
            System.out.println();
        }
    }
}