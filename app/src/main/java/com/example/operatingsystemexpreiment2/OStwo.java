package com.example.operatingsystemexpreiment2;

import android.widget.TextView;

import java.util.Scanner;

public class OStwo {
    final PCB_plus ready_head = new PCB_plus("head", 0);
    final PCB_plus block_head = new PCB_plus("head", 0);
    final PCB_plus run_head = new PCB_plus("head", 0);

    public OStwo() {
    }

    public void Creat(PCB_plus pcb){
        ready_head.add(pcb);
        Automatic_scheduling();
    }
    public void Automatic_scheduling(){
        if(!run_head.hasNext())
            run_head.add(ready_head.deQuene());
    }
    public void End(Bitmap bitmap){
        PCB_plus pcb=run_head.deQuene();
        if(pcb==null) run_head.aBoolean=false;
        else {
            pcb.end_Extended_page_table(bitmap);
            Automatic_scheduling();
        }

    }
    public void Block(){
        PCB_plus pcb=run_head.deQuene();
        if(pcb==null) run_head.aBoolean=false;
        else {
            block_head.add(pcb);
            Automatic_scheduling();
        }
    }
    public void Weak(){
        PCB_plus pcb=block_head.deQuene();
        if(pcb==null) block_head.aBoolean=false;
        else {
            ready_head.add(pcb);
            Automatic_scheduling();
        }
    }
    public void Time_rotation(){
        PCB_plus pcb=run_head.deQuene();
        ready_head.add(pcb);
        Automatic_scheduling();
    }
    public void showAll(TextView textView){

        textView.append("就绪态:\n");
        textView.append(ready_head.show());
        textView.append("********************************************\n");
        textView.append("执行态：\n");
        textView.append(run_head.show());
        textView.append("********************************************\n");
        textView.append("阻塞态：\n");
        textView.append(block_head.show());
        textView.append("********************************************\n");
    }
    public void Help(){
        System.out.println("c:创建一个进程\nt:时间片到\ne:结束一个进程\nb:阻塞一个进程\n" +
                "w:唤醒一个进程\ns:查看状态\nh:帮助\nf:访问当前进程\nx:查看位示图\nz:结束");
    }
//    public void Start(){
//        Scanner sc=new Scanner(System.in);
//        Bitmap bitmap=new Bitmap();
//        bitmap.init_Bitmap();
//        while (true){
//            System.out.println("请输入命令c/t/e/b/w/s/h/f/x/z:");
//            char choose = sc.next().charAt(0);
//            switch (choose){
//                case 'c':
//                    System.out.println("请输入要装入内存的PCB名字和大小");
//                    String s=sc.next();
//                    int i=sc.nextInt();
//                    PCB_plus pcb=new PCB_plus(s,i);
//                    pcb.init_Extended_page_table(bitmap);
//                    Creat(pcb);
//                    break;
//                case 't':
//                    Time_rotation();
//                    break;
//                case 'e':
//                    End(bitmap);
//                    break;
//                case 'b':
//                    Block();
//                    break;
//                case 'w':
//                    Weak();
//                    break;
//                case 's':
//                    showAll();
//                    break;
////                case 'm':
////                    showMemory();
////                    break;
//                case 'h':
//                    Help();
//                    break;
//                case 'f':
//                    PCB_plus pcb_plus=run_head.next;
//                    for(int j=0;j<pcb_plus.page_tables.length;j++)
//                        System.out.println(pcb_plus.page_tables[j].toString());
//                    int [] num=new int[]{0,3,0,4,2,3,0,3,2,1,2,0,1,7,0,1};
//                    for(int j=0;j<num.length;j++){
//                        pcb_plus.exchange_FIFO(num[j]);
//                    }
//                    System.out.println(pcb_plus.answer_fifo);
//                    for(int j=0;j<pcb_plus.page_tables.length;j++)
//                        System.out.println(pcb_plus.page_tables[j].toString());
//                    break;
//                case 'x':
//                    System.out.println(bitmap.show_bitmap());
//                    System.out.println(bitmap.show_displaced_partition());
//                    break;
//                case 'z':
//                    System.exit(0);
//
//            }
//        }
//    }

//    public static void main(String[] args) {
//        OStwo oStwo=new OStwo();
//        oStwo.Start();
//    }
}
