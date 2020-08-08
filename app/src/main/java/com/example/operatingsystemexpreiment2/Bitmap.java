package com.example.operatingsystemexpreiment2;

import java.io.Serializable;

public class Bitmap implements Serializable {
    final static int piece_size = 1024;
    int map_size;
    int[][] bitmap;
    int[][] displaced_partition;
    int free_size;//位示图空闲块数
    int displaced_partition_size;//置换空间空闲块数

    public Bitmap() {
        map_size=64;
        bitmap=new int[map_size/8][map_size/8];
        displaced_partition=new int[12][8];
        free_size=0;
        displaced_partition_size=0;
    }
    public void init_Bitmap(){
        for (int i = 0; i < map_size/ 8; i++) {
            for (int j = 0; j < 8; j++) {
                bitmap[i][j] = ((int) (10 * Math.random())) % 2;
                if (bitmap[i][j] == 0) {
                    free_size++;
                }
            }
        }
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 8; j++) {
                displaced_partition[i][j] = ((int) (10 * Math.random())) % 2;
                if (displaced_partition[i][j] == 0) {
                    displaced_partition_size++;
                }
            }
        }
    }
    public String show_bitmap(){
        String string=new String();
        string+="内存空间"+"\n";
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                //System.out.print(bitmap[i][j] + "\t");
                string+=bitmap[i][j] + " ";
            }
            string+="\n";
        }
        return string;
    }
    public String show_displaced_partition(){
        String string=new String();
        string+="置换空间"+"\n";
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 8; j++) {
                //System.out.print(bitmap[i][j] + "\t");
                string+=displaced_partition[i][j] + " ";
            }
            string+="\n";
        }
        return string;
    }
    public int getout_bitmap(){
        int flag=-1;
        boolean bo=false;
        Stop:
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                flag++;
                if(bitmap[i][j]==0){
                    bitmap[i][j]=1;
                    bo=true;
                    free_size--;
                    break Stop;
                }
            }
        }
        if(bo) return flag;
        else return -1;
    }
    public int getout_displaced_partition(){
        int flag=-1;
        boolean bo=false;
        Stop1:
        for(int i=0;i<12;i++){
            for(int j=0;j<8;j++){
                flag++;
                if(displaced_partition[i][j]==0){
                    displaced_partition[i][j]=1;
                    bo=true;
                    displaced_partition_size--;
                    break Stop1;
                }
            }
        }
        if(bo) return flag;
        else return -1;
    }
    public void return_bitmap(int k){
        int i=k/8;
        int j=k%8;
        bitmap[i][j]=0;
        free_size++;
    }
    public void return_displaced_partition(int k){
        int i=k/8;
        int j=k%8;
        displaced_partition[i][j]=0;
        displaced_partition_size++;
    }
    public static void main(String[] args) {
        Bitmap bitmap=new Bitmap();
        bitmap.init_Bitmap();
        System.out.println(bitmap.show_bitmap());
        int k=bitmap.getout_bitmap();
        System.out.println(k);
        System.out.println(bitmap.show_bitmap());
        bitmap.return_bitmap(k);
        System.out.println(bitmap.show_bitmap());
        //System.out.println(bitmap.show_displaced_partition());
    }
}
