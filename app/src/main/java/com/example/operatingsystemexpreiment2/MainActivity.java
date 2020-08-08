package com.example.operatingsystemexpreiment2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class MainActivity extends AppCompatActivity {
    OStwo oStwo;
    String input;
    int insize;
    TextView textone;
    TextView texttwo;
    TextView textthree;
    Queue<String> queue = new LinkedList<String>();
    Bitmap bitmap;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textone=(TextView)findViewById(R.id.testone);
        texttwo=(TextView)findViewById(R.id.testtwo);
        textthree=(TextView)findViewById(R.id.testthree);
        textone.setMovementMethod(ScrollingMovementMethod.getInstance());
        texttwo.setMovementMethod(ScrollingMovementMethod.getInstance());
        textthree.setMovementMethod(ScrollingMovementMethod.getInstance());
        Button block=(Button)findViewById(R.id.block);
        Button weak=(Button)findViewById(R.id.weak);
        Button time=(Button)findViewById(R.id.time);
        Button end=(Button)findViewById(R.id.end);
        Button ye=(Button)findViewById(R.id.ye);
        Button fangwen=(Button)findViewById(R.id.fangwen);
        bitmap=new Bitmap();
        bitmap.init_Bitmap();
        texttwo.setText(bitmap.show_bitmap());
        textthree.setText(bitmap.show_displaced_partition());
            oStwo = new OStwo();
            block.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    oStwo.Block();
                    if (oStwo.run_head.aBoolean) {
                        oStwo.showAll(textone);
                        refreshAlarmView(textone, "\n");
                        texttwo.setText(bitmap.show_bitmap());
                        textthree.setText(bitmap.show_displaced_partition());
                    } else
                        Toast.makeText(getApplicationContext(), "无正在执行的进程", Toast.LENGTH_SHORT).show();
                }
            });
            weak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    oStwo.Weak();
                    if (oStwo.block_head.aBoolean) {
                        oStwo.showAll(textone);
                        refreshAlarmView(textone, "\n");
                        texttwo.setText(bitmap.show_bitmap());
                        textthree.setText(bitmap.show_displaced_partition());
                    } else
                        Toast.makeText(getApplicationContext(), "无正在阻塞的进程", Toast.LENGTH_SHORT).show();
                }
            });
            time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    oStwo.Time_rotation();
                    oStwo.showAll(textone);
                    refreshAlarmView(textone, "\n");
                    texttwo.setText(bitmap.show_bitmap());
                    textthree.setText(bitmap.show_displaced_partition());
                }
            });
            end.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PCB_plus p=oStwo.run_head.next;
                    if(p!=null){
//                        for (String q : queue) {
//                            if (q.equals(p.name)) queue.remove(q);
//                        }
                        Iterator<String> it_b=queue.iterator();
                        while(it_b.hasNext()){
                            String a=it_b.next();
                            if (a.equals(p.name)) {
                                it_b.remove();
                            }
                        }
                    }

                    oStwo.End(bitmap);
                    if (oStwo.run_head.aBoolean) {
                        oStwo.showAll(textone);
                        refreshAlarmView(textone, "\n");
                        texttwo.setText(bitmap.show_bitmap());
                        textthree.setText(bitmap.show_displaced_partition());
                    } else
                        Toast.makeText(getApplicationContext(), "无正在执行的进程", Toast.LENGTH_SHORT).show();
                }
            });
            ye.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for(int i=0;i<oStwo.run_head.next.page_tables.length;i++)
                    textone.append(oStwo.run_head.next.page_tables[i].toString()+"\n");
                }
            });
            fangwen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(oStwo.run_head.next!=null) {
                        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                        intent.putExtra("1", oStwo.run_head.next);
                        intent.putExtra("2", oStwo.run_head.next);
                        intent.putExtra("3", bitmap);
                        intent.putExtra("4", bitmap);
                        startActivity(intent);
                    }else
                        Toast.makeText(getApplicationContext(), "无进程处于内存中", Toast.LENGTH_SHORT).show();
                }
            });

    }
    public void alert_edit(View view){
        LayoutInflater inflater=getLayoutInflater();
        final View layout=inflater.inflate(R.layout.mydialog,(ViewGroup)findViewById(R.id.mydialog));
        final EditText name=(EditText)layout.findViewById(R.id.name);
        final EditText size=(EditText)layout.findViewById(R.id.size11);
        new AlertDialog.Builder(this).setTitle("创建一个进程")
                .setIcon(android.R.drawable.sym_def_app_icon)
                .setView(layout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //按下确定键后的事件
                        input = name.getText().toString();
                        insize = Integer.valueOf(size.getText().toString());
                        boolean bol = false;
                        for (String q : queue) {
                            if (q.equals(input)) bol = true;
                        }
                        if (!bol) {
                            queue.offer(input);
                            PCB_plus pcb = new PCB_plus(input, insize);
                            pcb.init_Extended_page_table(bitmap);
                            oStwo.Creat(pcb);
                            oStwo.showAll(textone);
                            refreshAlarmView(textone, "\n");
                            texttwo.setText(bitmap.show_bitmap());
                            textthree.setText(bitmap.show_displaced_partition());
                        }else{
                            Toast.makeText(getApplicationContext(), "该PCB已存在", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("取消",null).show();

    }

    private void refreshAlarmView(TextView textView,String msg){
        textView.append(msg);
        int offset=textView.getLineCount()*textView.getLineHeight();
        if(offset>(textView.getHeight()-textView.getLineHeight()-20)){
            textView.scrollTo(0,offset-textView.getHeight()+textView.getLineHeight()+20);
        }
    }
}
