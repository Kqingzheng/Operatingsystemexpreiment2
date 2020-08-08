package com.example.operatingsystemexpreiment2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        final PCB_plus pcb_plus1=(PCB_plus)getIntent().getSerializableExtra("1");
        final PCB_plus pcb_plus2=(PCB_plus)getIntent().getSerializableExtra("2");
        final Bitmap bitmap1=(Bitmap)getIntent().getSerializableExtra("3");
        final Bitmap bitmap2=(Bitmap)getIntent().getSerializableExtra("4");
        final TextView text1=(TextView)findViewById(R.id.test_1);
        final TextView text2=(TextView)findViewById(R.id.test_2);
        final TextView text3=(TextView)findViewById(R.id.test_3);
        text1.setMovementMethod(ScrollingMovementMethod.getInstance());
        text2.setMovementMethod(ScrollingMovementMethod.getInstance());
        text3.setMovementMethod(ScrollingMovementMethod.getInstance());
        Button b1=(Button)findViewById(R.id.button_1);
        Button b2=(Button)findViewById(R.id.button_2);
        Button b3=(Button)findViewById(R.id.button_3);
        Button b4=(Button)findViewById(R.id.button_4);
        Button b5=(Button)findViewById(R.id.button_5);
        Button b6=(Button)findViewById(R.id.button_6);
        Button b7=(Button)findViewById(R.id.button_7);
        Button b8=(Button)findViewById(R.id.button_8);
        Button b9=(Button)findViewById(R.id.button_9);
        text1.setText(pcb_plus1.answer_fifo);
        text2.setText(pcb_plus2.answer_lru);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText inputServer = new EditText(Main2Activity.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
                builder.setTitle("Server").setIcon(android.R.drawable.ic_dialog_info).setView(inputServer)
                        .setNegativeButton("Cancel", null);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
//                        long dec_num = Long.parseLong(inputServer.getText().toString(), 16);
//                        int p= (int) (dec_num/Bitmap.piece_size);
//                        int q= (int) (dec_num%Bitmap.piece_size);
                        int an=Integer.valueOf(inputServer.getText().toString());
                        int p=an/Bitmap.piece_size;
                        int q=an%Bitmap.piece_size;
                        int y=pcb_plus1.exchange_FIFO(p);
                        if(y!=-1){
                            Toast.makeText(getApplicationContext(), "物理地址为"+(y*1024+q), Toast.LENGTH_SHORT).show();
                            text1.setText(pcb_plus1.answer_fifo);
                        }else
                            Toast.makeText(getApplicationContext(), "地址不合法", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                pcb_plus1.end_Extended_page_table(bitmap1);
//                pcb_plus1.init_Extended_page_table(bitmap1);
                int [] num=new int[]{0,3,0,4,2,3,0,3,2,1,2,0,1,7,0,1};
//                pcb_plus1.ecchange_fifo_plus(num);
                for(int i=0;i<num.length;i++){
                    pcb_plus1.exchange_FIFO(num[i]);
                }
                final String s1=pcb_plus1.answer_fifo;
                text1.setText(s1);
               // System.out.println(pcb_plus1.answer_fifo);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "缺页率为："+pcb_plus1.missing_rate_fifo(), Toast.LENGTH_SHORT).show();
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText inputServer = new EditText(Main2Activity.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
                builder.setTitle("Server").setIcon(android.R.drawable.ic_dialog_info).setView(inputServer)
                        .setNegativeButton("Cancel", null);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        int an=Integer.valueOf(inputServer.getText().toString());
                        int p=an/Bitmap.piece_size;
                        int q=an%Bitmap.piece_size;
                        int y=pcb_plus2.exchange_LRU(p);
                        if(y!=-1){
                            Toast.makeText(getApplicationContext(), "物理地址为"+(y*1024+q), Toast.LENGTH_SHORT).show();
                            text2.setText(pcb_plus2.answer_lru);
                        }else
                            Toast.makeText(getApplicationContext(), "地址不合法", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                pcb_plus2.end_Extended_page_table(bitmap2);
//                pcb_plus2.init_Extended_page_table(bitmap2);
                int [] num=new int[]{0,3,0,4,2,3,0,3,2,1,2,0,1,7,0,1};
                for(int i=0;i<num.length;i++){
                    pcb_plus2.exchange_LRU(num[i]);
                }
                text2.setText(pcb_plus2.answer_lru);
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "缺页率为："+pcb_plus2.missing_rate_lru(), Toast.LENGTH_SHORT).show();
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pcb_plus2.end_Extended_page_table(bitmap2);
                pcb_plus2.init_Extended_page_table(bitmap2);
                int [] num=new int[]{0,3,0,4,2,3,0,3,2,1,2,0,1,7,0,1};
                pcb_plus2.exchange_OPT(num);
                text3.setText(pcb_plus2.answer_opt);
                Toast.makeText(getApplicationContext(), "缺页率为："+pcb_plus2.missing_rate_opt(), Toast.LENGTH_SHORT).show();
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s="";
                for(int i=0;i<pcb_plus1.page_tables.length;i++)
                    s+=pcb_plus1.page_tables[i].toString()+"\n";
                text3.setText(s);
            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s="";
                for(int i=0;i<pcb_plus2.page_tables.length;i++)
                   s+=pcb_plus2.page_tables[i].toString()+"\n";
                text3.setText(s);
            }
        });
     }
}
