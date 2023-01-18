package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    int row = 1;
    int column = 1 ;

    TextView lvlView;
    TextView leftView;

    TableLayout textViewTableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewTableLayout = findViewById(R.id.tableTextView);
        Button button = findViewById(R.id.button_buld);
        Button calcBt = findViewById(R.id.button_calculation);
        TableLayout tableLayout = findViewById(R.id.table);
        EditText editRow = findViewById(R.id.row);
        EditText editColumn = findViewById(R.id.column);
        lvlView = findViewById(R.id.text_lvl);
        leftView = findViewById(R.id.text_left);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    row =Integer.parseInt(editRow.getText().toString());
                }catch (Exception e){
                    row = 1;
                }

                try {
                    column =Integer.parseInt(editColumn.getText().toString());
                }catch (Exception e){
                    column = 1;
                }

                if(row == column)
                    createTable(tableLayout , row , column , null);
                else
                    Toast.makeText(getBaseContext() , "Матрица смежности не может быть построена" , Toast.LENGTH_SHORT).show();
            }
        });


        calcBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arr = readTable(row, column);
                int[][] read = readTable(row , column);
                int[][] path = getAnswer(read);
                createTable(textViewTableLayout , t(path));
            }
        });
    }


    int arr[][];
    private int[] out;

    private int[][] getAnswer(int[][] arr){
        int[][] ans = new int[column][row];

        for(int i =0 ; i < arr.length; i++){
            startFindPath(i);
            for(int j =0; j < arr.length; j++){
                if(i == j) ans[i][j] = 0;
                else if(out[j] == 0) ans[i][j] = -1;
                else ans[i][j] = out[j];
            }
        }

        return ans;
    }


    private void startFindPath(int start){
        out = new int[arr.length];

        for(int i = 0; i < arr.length; i++){
            if(arr[i][start] == 1)
                findPath(start, i , 1);
        }
    }



    private void findPath(int start, int h , int step) {
        if (h == start){
            return;
        }else if(out[h] == 0) {
            out[h] = step;
        }else if(out[h] > step) {
            out[h] = step;
        }else {
            return;
        }

        for(int i = 0; i < arr.length;i++){
            if(arr[i][h] == 1)
                findPath(h, i , step+1);
        }
    }


    int[][] t(int[][] arr){
        int[][] tM = new int[arr.length][arr.length];

        for(int i = 0 ; i < arr.length;i++){
            for(int j = 0 ; j < arr.length;j++){
                tM[i][j] = arr[j][i];
            }
        }

        return tM;
    }

    private int[][] readTable(int row , int colums){
        int arr[][] = new int[row][colums];

        for (int i = 0 ; i < row; i++){
            for(int j = 0; j < colums;j++){
                String text = ((EditText) findViewById(i *100 + j)).getText().toString();

                try {
                    arr[i][j] = Integer.parseInt(text);
                }catch (Exception e){
                    arr[i][j] = 0;
                }
            }
        }

        return arr;
    }

    private void createTable(TableLayout tableLayout, int[][] arr){
        tableLayout.removeAllViews();

        int row = arr.length;
        int colums = arr[0].length;

        TableRow tableRow = new TableRow(this);
        tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        tableRow.addView(new TextView(this));

        for(int i = 0; i < row; i++){
            TextView textView = new TextView(this);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setTextSize(18);
            textView.setText((i + 1) + " ");

            tableRow.addView(textView);
        }
        tableLayout.addView(tableRow);

        for (int i = 0; i < row; i++){


            tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            TextView numberTV = new TextView(this);
            numberTV.setText((i + 1) + " ");
            numberTV.setTextSize(18);

            tableRow.addView(numberTV);

            for(int j = 0; j < colums;j++){
                TextView cell = new TextView(this);
                if(arr[i][j] < 0) cell.setText("-");
                else cell.setText(arr[i][j] + "");
                cell.setGravity(Gravity.CENTER_HORIZONTAL);
                cell.setTextSize(18);
                tableRow.addView(cell);
            }
            tableLayout.addView(tableRow);
        }
    }

    private void createTable(TableLayout tableLayout , int row , int colums , int[][] arr){
        tableLayout.removeAllViews();

        TableRow tableRow = new TableRow(this);
        tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        tableRow.addView(new TextView(this));

        for(int i = 0; i < row; i++){
            TextView textView = new TextView(this);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setTextSize(18);
            textView.setText((i + 1) + "  ");

            tableRow.addView(textView);
        }
        tableLayout.addView(tableRow);

        for (int i = 0; i < row; i++){


            tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            TextView textView = new TextView(this);
            textView.setText((i+1) + " ");
            textView.setTextSize(18);

            tableRow.addView(textView);

            for(int j = 0; j < colums;j++){
                EditText editText = new EditText(this);
                editText.setId(i *100 + j);
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                if(arr != null && arr.length >= i && arr[0].length >= j)
                    editText.setText(arr[i][j] + "");
                tableRow.addView(editText);
            }
            tableLayout.addView(tableRow);
        }
    }

}