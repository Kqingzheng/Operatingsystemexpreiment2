package com.example.operatingsystemexpreiment2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PCB_plus extends PCB implements Serializable {
    Extended_page_table[] page_tables;
    PCB_plus next;
    final static int init_page_size = 3;
    Queue<Integer> fifo = new LinkedList<Integer>();
    Queue<Integer> lru = new LinkedList<Integer>();
    List<Weight> list = new ArrayList<Weight>();
    String answer_fifo = "";
    String answer_lru = "";
    String answer_opt = "";
    int fifo_success = 0;//成功次数
    int fifo_fail = 0;//失败次数
    int lru_success = 0;//成功次数
    int lru_fail = 0;//失败次数
    int opt_fail = 0;
    int opt_success = 0;

    public PCB_plus(String name, int start, int length, PCB next) {
        super(name, start, length, next);
    }

    public PCB_plus(String name, int length) {
        super(name, length);
//        int i=length/1024+1;
//        page_tables=new Extended_page_table[i];
    }
    public PCB_plus(PCB pcb){
        super(pcb.name,pcb.length);
    }
    @Override
    public boolean hasNext(){
        return this.next == null ? false : true;
    }

    public void add(PCB_plus pcb){
        PCB_plus t = this;
        while (t.hasNext()) {
            t = t.next;
        }
        t.next = pcb;
        aBoolean=true;
    }

    public PCB_plus deQuene(){
        PCB_plus pcb=this.next;
        if(pcb==null){
            System.out.println("队列为空");
            aBoolean=false;
            return null;
        }else{
            this.next=pcb.next;
            pcb.next=null;
            return pcb;
        }
    }

    @Override
    public String toString() {
        return "PCB_plus{" +
                "name='" + name + '\'' +
                ", length=" + length +
                '}';
    }
    public String show(){
        String string=" ";
        PCB_plus pcb=this;
        while(pcb.hasNext()){
            pcb=pcb.next;
            string+=pcb.toString();
        }
        if(string==null) return "\n";
        else return string.substring(1);
    }
    public void showFIFO(){
        for(int q : fifo){
            answer_fifo+=q+" ";
        }
        answer_fifo+="\n";
    }
    public void showLRU(){
        for(int q:lru){
            answer_lru+=q+" ";
        }
        answer_lru+="\n";
    }
    public void showOPT(){
        for(Weight t:list){
            answer_opt+=t.pagename+" ";
        }
        answer_opt+="\n";
    }
    public void init_Extended_page_table(Bitmap bitmap) {//初始化页表
        int size = length / Bitmap.piece_size ;
        if(length%Bitmap.piece_size!=0) size++;
        page_tables = new Extended_page_table[size];
        //System.out.println(size);
        for (int i = 0; i < page_tables.length; i++) {
            page_tables[i]=new Extended_page_table();
            page_tables[i].page_number = i;
            page_tables[i].block_number = -1;
            page_tables[i].modify_bit = false;
            page_tables[i].status_bit = false;
            page_tables[i].external_storage_address=-1;
        }
        if(bitmap.free_size>=size) {
            if (size <= init_page_size) {
                for (int i = 0; i < size; i++) {
                    page_tables[i].block_number = bitmap.getout_bitmap();
                    page_tables[i].status_bit=true;
                    fifo.offer(page_tables[i].page_number);//FIFO初始化
                    this.fifo_fail++;
                    showFIFO();
                    lru.offer(page_tables[i].page_number);//LRU初始化
                    this.lru_fail++;
                    showLRU();
                }
            } else {
                for (int i = 0; i < size; i++) {
                    if (i < init_page_size) {
                        page_tables[i].block_number = bitmap.getout_bitmap();
                        page_tables[i].status_bit=true;
                        this.fifo_fail++;//FIFO初始化
                        fifo.offer(page_tables[i].page_number);
                        showFIFO();
                        lru.offer(page_tables[i].page_number);//LRU初始化
                        this.lru_fail++;
                        showLRU();
                    } else
                        page_tables[i].external_storage_address = bitmap.getout_displaced_partition();
                }
            }
        }else{
            System.out.println("内存块数不足，无法分配");
        }
    }
    public int exchange_FIFO( int location) {
        if(location<page_tables.length) {
            if (this.page_tables[location].block_number == -1) {
                // 置换算法
                this.fifo_fail++;
                // System.out.println("fail"+this.page_table[location].fail);
                //System.out.println("您访问的地址块不在内存，已使用FIFO算法进行置换");
                int key = fifo.poll();
                this.page_tables[location].block_number = this.page_tables[key].block_number;
                this.page_tables[key].block_number = -1;
                this.page_tables[key].external_storage_address = this.page_tables[location].external_storage_address;
                this.page_tables[location].external_storage_address = -1;
                this.page_tables[location].status_bit = false;
                fifo.offer(location);
                showFIFO();
                return page_tables[location].block_number;
            } else {
                this.fifo_success++;
                this.page_tables[location].status_bit = true;
                // System.out.println("success"+this.page_table[location].success);
                //System.out.println("您访问成功");
                showFIFO();
                return page_tables[location].block_number;
            }
        }else {
            //System.out.println("页表号错误");
            return -1;
        }
    }
    public int exchange_LRU(int location){
        if(location<page_tables.length) {
            if (this.page_tables[location].block_number == -1) {
                // 置换算法
                this.lru_fail++;
                // System.out.println("fail"+this.page_table[location].fail);
                //System.out.println("您访问的地址块不在内存，已使用LRU算法进行置换");
                int key = lru.poll();
                this.page_tables[location].block_number = this.page_tables[key].block_number;
                this.page_tables[key].block_number = -1;
                this.page_tables[key].status_bit = false;
                this.page_tables[key].external_storage_address = this.page_tables[location].external_storage_address;
                this.page_tables[location].external_storage_address = -1;
                this.page_tables[location].status_bit = true;
                lru.offer(location);
                showLRU();
                return page_tables[location].block_number;
            } else {
                this.lru_success++;
                // System.out.println("success"+this.page_table[location].success);
                // System.out.println("您访问成功");
                int[] lrutest = new int[lru.size()];
                int key = -1;
                for (int i = 0; i < lrutest.length; i++) {
                    lrutest[i] = lru.poll();
                    if (lrutest[i] == location) key = i;
                }
                for (int i = 0; i < lrutest.length; i++) {
                    if (i != key) {
                        lru.offer(lrutest[i]);
                    }
                }
                lru.offer(location);
                showLRU();
                return page_tables[location].block_number;
            }
        }else {
            //System.out.println("页表号错误");
            return -1;
        }
    }
    public void end_Extended_page_table(Bitmap bitmap){//页表回收
        for(int i=0;i<page_tables.length;i++){
            if(page_tables[i].block_number!=-1){
                bitmap.return_bitmap(page_tables[i].block_number);
            }
            if(page_tables[i].external_storage_address!=-1){
                bitmap.return_displaced_partition(page_tables[i].external_storage_address);
            }
        }
        page_tables=null;
        answer_fifo="";
        answer_lru="";
        answer_opt="";
        fifo_success=0;//成功次数
        fifo_fail=0;//失败次数
        lru_success=0;//成功次数
        lru_fail=0;//失败次数
        opt_fail=0;
        opt_success=0;

    }
    public void exchange_OPT(int []x){
        list.add(new Weight(0));//初始化前三块
        showOPT();
        list.add(new Weight(1));
        showOPT();
        list.add(new Weight(2));
        showOPT();
        opt_fail+=init_page_size;
        for(int i=0;i<x.length;i++){
            boolean flag=false;
            for(int k=0;k<init_page_size;k++)
            {
                if(list.get(k).pagename==x[i]){
                    flag=true;//不需要置换
                    this.opt_success++;
                    showOPT();
                }
            }
            if(!flag){//需要置换
                this.opt_fail++;
                list.get(0).weight=1000;
                list.get(1).weight=1000;
                list.get(2).weight=1000;
                for(int j=x.length-1;j>i;j--){
                    if(list.get(0).pagename==x[j]){
                        list.get(0).weight=j;
                    }else if(list.get(1).pagename==x[j]){
                        list.get(1).weight=j;
                    }else if(list.get(2).pagename==x[j]){
                        list.get(2).weight=j;
                    }
                }
                int key=0;
                for(int a=1;a<init_page_size;a++){
                    if(list.get(key).weight<list.get(a).weight){
                        key=a;
                    }
                }
                list.remove(key);
                list.add(key,new Weight(x[i]));
                showOPT();
            }
        }
    }
    public void ecchange_fifo_plus(int x[]){
        for(int i=0;i<x.length;i++){
            this.exchange_LRU(x[i]);
        }
    }
    public String missing_rate_fifo(){
        double answer=(double)( 100*fifo_fail)/(double) (fifo_success+fifo_fail);
        return String.format("%.2f",answer)+"%";
    }
    public String missing_rate_lru(){
        double answer=(double)( 100*lru_fail)/(double) (lru_success+lru_fail);
        return String.format("%.2f",answer)+"%";
    }
    public String missing_rate_opt(){
        double answer=(double)( 100*opt_fail)/(double) (opt_success+fifo_fail);
        //System.out.println(opt_fail+"  "+opt_success);
        return String.format("%.2f",answer)+"%";
    }
    public static void main(String[] args) {
        //PCB_plus head= new PCB_plus("head",0);
        Bitmap bitmap=new Bitmap();
        bitmap.init_Bitmap();
        System.out.println(bitmap.show_bitmap());
        PCB_plus pcb_plus=new PCB_plus("1",7200);
        pcb_plus.init_Extended_page_table(bitmap);
//        for(int i=0;i<pcb_plus.page_tables.length;i++)
//            System.out.println(pcb_plus.page_tables[i].toString());
//        pcb_plus.exchange_FIFO(5);
//        for(int i=0;i<pcb_plus.page_tables.length;i++)
//            System.out.println(pcb_plus.page_tables[i].toString());
//        pcb_plus.exchange_FIFO(5);
//        for(int i=0;i<pcb_plus.page_tables.length;i++)
//            System.out.println(pcb_plus.page_tables[i].toString());
        for(int i=0;i<pcb_plus.page_tables.length;i++)
            System.out.println(pcb_plus.page_tables[i].toString());
        int [] num=new int[]{0,3,0,4,2,3,0,3,2,1,2,0,1,7,0,1};
        for(int i=0;i<num.length;i++){
            pcb_plus.exchange_LRU(num[i]);
        }
        System.out.println(pcb_plus.answer_lru);
        for(int i=0;i<pcb_plus.page_tables.length;i++)
            System.out.println(pcb_plus.page_tables[i].toString());
        System.out.println(pcb_plus.missing_rate_lru());
//        pcb_plus.exchange_OPT(num);
//        System.out.println(pcb_plus.answer_opt);
//        System.out.println(pcb_plus.missing_rate_opt());
    }

}
