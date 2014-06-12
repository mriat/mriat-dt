package com.codepath.simpletipcalc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class CalcActivity extends Activity {
	
	private EditText etBill;
	private EditText etBillPost;
	private EditText etSplit;
	
	private TextView tvTip;
	private TextView tvTax;
	private TextView tvPercent;
	private TextView tvTipAdded;
	private TextView tvTotal;
	private TextView tvTipPP;
	
	private SeekBar mSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);
        
        // inflate EditText
        etBill = (EditText) findViewById(R.id.etBill);
        etBillPost = (EditText) findViewById(R.id.etBillPost);
        etSplit = (EditText) findViewById(R.id.etSplit);
        
        // inflate TextView
		tvTip = (TextView) findViewById(R.id.tvTip);
		tvTax = (TextView) findViewById(R.id.tvTax);
		tvTipAdded = (TextView) findViewById(R.id.tvTipAdded);
		tvPercent = (TextView) findViewById(R.id.tvPercent);
		tvTotal = (TextView) findViewById(R.id.tvTotal);
		tvTipPP = (TextView) findViewById(R.id.tvTipPP);
		
		// inflate SeekBar
		mSeekBar = (SeekBar) findViewById(R.id.seekBar);

		// initial values
		etBill.setText("54.89");
		etBillPost.setText("59.42");
        etSplit.setText("1.0");
        
        // initial cursor position
        etBill.setSelection(etBill.length());
        etBillPost.setSelection(etBillPost.length());
        etSplit.setSelection(etSplit.length());
        
        // seekbar object - added most code into onProgressChanged for ease of use
        // need to add updater model so if any input is changed, the output is automatically updated
        // currently updates only happen when changes to seekbar occur
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
        	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
            	
        		// use floats for all math
        		double itemDouble = 0;
            	double split = 0;
            	double billPost = 0;
            	double tax = 0;
                double progressChanged = 0;
                double total = 0;
                double totalpp = 0;
               
            	progressChanged = progress*0.25;
 
            	// get pre-tax bill amount and check for errors
            	String itemText = etBill.getText().toString();
             	try {
      		      itemDouble = Double.parseDouble(itemText);
            		} catch (NumberFormatException e) {
            			Toast.makeText(CalcActivity.this, "Please enter numerical format", Toast.LENGTH_SHORT).show();
            		}

             	// get number of people to split with, and check for errors      		
            	String itemSplit = etSplit.getText().toString();
            	try {
        		      split = Double.parseDouble(itemSplit);
              		} catch (NumberFormatException e) {
              			Toast.makeText(CalcActivity.this, "Please enter numerical format", Toast.LENGTH_SHORT).show();
              		}

            	// get post-tax bill amount and check for errors
            	String itemPost = etBillPost.getText().toString();
            	try {
      		      	billPost = Double.parseDouble(itemPost);
            		} catch (NumberFormatException e) {
            			Toast.makeText(CalcActivity.this, "Please enter numerical format", Toast.LENGTH_SHORT).show();
            		}
            	
            	tax = (billPost/itemDouble-1)*100;
            	tax = Math.round(tax*10.0)/10.0;
        		String taxCalc = String.valueOf(tax);
            	tvTax.setText(taxCalc);
            	
            	total = billPost+itemDouble*(progressChanged/100);
            	totalpp = total/split;
            	total = Math.round(total*100.0)/100.0;
            	totalpp = Math.round(totalpp*100.0)/100.0;
            	
            	if (tax < 15) { tvTipAdded.setText("Unlikely"); } else { tvTipAdded.setText("Likely"); }
 
            	itemDouble = itemDouble*progressChanged/100/split;
        		itemDouble = Math.round(itemDouble*100.0)/100.0;
            	
        		String tipCalc = String.valueOf(itemDouble);
        		tvTip.setText(tipCalc);
        		
        		String percentCalc = String.valueOf(progressChanged);
        		tvPercent.setText(percentCalc);
        		
        		String totalCalc = String.valueOf(total);
        		tvTotal.setText(totalCalc);
        		
        		String totalPP = String.valueOf(totalpp);
        		tvTipPP.setText(totalPP);
        		
             }
  
             public void onStartTrackingTouch(SeekBar seekBar) {
     
             }
  
             public void onStopTrackingTouch(SeekBar seekBar) {
          
            }		
        });	
    }
    
	public void on10(View v) {
		getPreTip(10);
	}
	
	public void on15(View v) {
		getPreTip(15);
	}

	public void on20(View v) {	
		getPreTip(20);
	}
	
	public void getPreTip(int tipPercent) {
		mSeekBar.setProgress(tipPercent*4);
	}	
}
