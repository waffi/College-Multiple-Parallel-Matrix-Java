/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paralel_multiple_matrix;

/**
 *
 * @author Waffi Fatur Rahman
 */

//400
//The time is 5682
//BUILD SUCCESSFUL (total time: 6 seconds)

//900
//The time is 87529
//BUILD SUCCESSFUL (total time: 1 minute 30 seconds)

//1600
//The time is 322038
//BUILD SUCCESSFUL (total time: 5 minutes 28 seconds)

//2500
//The time is 1246919
//BUILD SUCCESSFUL (total time: 21 minutes 1 seconds)

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class ParallelMultipleMatrix {

    public static int[][] a;
    public static int[][] b;
    public static int[][] c;
    
    public static int kolomA;
    public static int barisA;
    public static int kolomB;
    public static int barisB;
    public static int akar;
    
    public static void main(String[] args) throws Exception {
        
        randomMatrix(400,"a.txt");
        randomMatrix(400,"b.txt");
                
        a = loadMatrix("a.txt");
        b = loadMatrix("b.txt");

        kolomA = a.length;
        barisA = a[0].length;
        kolomB = b.length;
        barisB = b[0].length;        
        akar = (int) Math.sqrt(kolomA);
        
        c= new int [kolomA][barisB];
        
//        displayMatrix(a,"A");
//        displayMatrix(b,"B");

        long t1 = System.currentTimeMillis();
        iterasi1();       
        iterasi2();               
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
    
    public static void randomMatrix(int length, String filename) throws Exception {
        Random rand = new Random();
        int rows = length;
        int cols = length;
        PrintWriter pw = new PrintWriter(new FileOutputStream(filename));
        pw.println(rows + "    " + cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                pw.print( rand.nextInt(9)+1+ "    ");
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

    public static class thread extends Thread {
        
        int batasAtas;
        int batasBawah;
        int batasKiri;
        int batasKanan;
            
        public thread(int batasAtas, int batasBawah, int batasKiri, int batasKanan){
            this.batasAtas = batasAtas;
            this.batasBawah = batasBawah;
            this.batasKiri = batasKiri;
            this.batasKanan = batasKanan;
        }
        
        @Override
        public void run() {        

            for (int i = batasAtas; i < batasBawah; i++) {
                for (int j = batasKiri; j < batasKanan; j++) {
                    for (int k = 0; k < akar; k++) {
                        c[i][j] = c[i][j] + a[i][batasKiri+k] * b[batasAtas+k][j];
                    }
                }
            }
        }
    }
    
    public static void multiplyMatrix() throws Exception{
        
        thread m[] = new thread[(int)Math.pow(akar,2)];
        
        for(int i=0;i<akar;i++){
            for(int j=0;j<akar;j++){
                m[i*akar+j] = new thread(i*akar,(i+1)*akar,j*akar,(j+1)*akar);
                m[i*akar+j].start();
            }            
        }
        
        for(int i=0;i<Math.pow(akar, 2);i++){
            m[i].join();
        }
    }
    
    public static void iterasi1() throws Exception{
              
        //Matrix A, semua baris digeser kekiri sebanyak baris keberapa, dan 
        //Matrix B, semua kolom digeser keatas sebanyak kolom keberapa
        //Lakukan Perkalian Matrik C = AxB
        
        //geser kiri
        for(int i=1;i<akar;i++){
            for(int j=i*akar;j<(i+1)*akar;j++){
                geserKiri(a,j,i);
            }
        }
        
        //geser atas
        for(int i=1;i<akar;i++){
            for(int j=i*akar;j<(i+1)*akar;j++){
                geserAtas(b,j,i);
            }
        }
        
        multiplyMatrix();
    }
    
    public static void iterasi2() throws Exception{
     
        //Pergeseran dan perkalian matrik sebanyak sqrt(n)-1 melakukan hal berikut:
        //Setiap Baris Matrix A semua kolom digeser kekiri sebanyak satu dan 
        //Setiap kolom Matrix B semua baris digeser keatas sebanyak satu. 
        //Lalu lakukan Perkalian Matrik dan hasilnya diakumulasi ke hasil sebelumnya. 

        
        for(int i=0;i<akar-1;i++){     
            
            //geser kiri
            for(int j=0;j<Math.pow(akar,2);j++){
                geserKiri(a,j,1);
            }
            
            //geser atas
            for(int j=0;j<Math.pow(akar,2);j++){
                geserAtas(b,j,1);
            }

            multiplyMatrix();   
        }
    }  
    
    public static void geserKiri(int[][] matrix, int baris, int jumlahGeser){
        int temp;
        
        for(int i=0; i<akar*jumlahGeser; i++){
            for(int j=0; j<Math.pow(akar, 2)-1; j++){
                temp = matrix[baris][j];
                matrix[baris][j] = matrix[baris][j+1];
                matrix[baris][j+1] = temp;                
            }
        }
    }
    
    public static void geserAtas(int[][] matrix, int kolom, int jumlahGeser){
        int temp;
        
        for(int i=0; i<akar*jumlahGeser; i++){
            for(int j=0; j<Math.pow(akar, 2)-1; j++){
                temp = matrix[j][kolom];
                matrix[j][kolom] = matrix[j+1][kolom];
                matrix[j+1][kolom] = temp;                
            }            
        }
    }
}