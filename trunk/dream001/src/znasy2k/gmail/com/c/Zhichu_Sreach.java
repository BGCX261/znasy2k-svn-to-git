package znasy2k.gmail.com.c;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import znasy2k.gmail.com.provider.Sjjzb;
import znasy2k.gmail.com.util.TimeUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class Zhichu_Sreach extends Activity {
	public static final int MENU_ITEM_BACK = Menu.FIRST;
    public static final int MENU_ITEM_SREACH = Menu.FIRST + 1;
    private static final int DATE_DIALOG_ID1 = 1;
    private static final int DATE_DIALOG_ID2 = 2;
    private Spinner sreachSpinner;
    private Button sreachriqibt1;
    private Button sreachriqibt2;
    private TextView sreachriqi1; 
    private TextView sreachriqi2; 
    private TextView sreachjine1; 
    private TextView sreachjine2;    
	private int mYear1, mYear2;
    private int mMonth1, mMonth2;
    private int mDay1, mDay2;
    private Button fhimgbt;
    private Button ssimgbt;
    
    public void onConfigurationChanged(Configuration newConfig) { 
        super.onConfigurationChanged(newConfig); 
    } 
	private static final String[] leibieCursorColumns = new String[] {
		Sjjzb.SjjzbColumns._ID, 
		Sjjzb.SjjzbColumns.MINGCHENG,
	};
	
	private static final String[] leibieColumns = new String[] {
		Sjjzb.SjjzbColumns.MINGCHENG,
	};
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getResources().getConfiguration().orientation == 
            Configuration.ORIENTATION_PORTRAIT) {   
        	//竖屏 
        	setContentView(R.layout.zhichu_sreach); 
		} else { 
		    //横屏时 
			setContentView(R.layout.zhichu_sreach_h); 
		} 
        
        Intent intent = getIntent();
        if (intent.getData() == null) {
            intent.setData(Sjjzb.SjjzbColumns.CONTENT_URI_SHOUZHI);
        }
        
        sreachriqibt1 = (Button)findViewById(R.id.sreachriqibt1);
        sreachriqibt2 = (Button)findViewById(R.id.sreachriqibt2);       
        sreachriqi1 = (TextView)findViewById(R.id.sreachriqi1);
        sreachriqi2 = (TextView)findViewById(R.id.sreachriqi2);       
        sreachjine1 = (TextView)findViewById(R.id.sreachjine1);
        sreachjine2 = (TextView)findViewById(R.id.sreachjine2);    
        fhimgbt = (Button) findViewById(R.id.fhimgbt);
        ssimgbt = (Button) findViewById(R.id.ssimgbt);
        
        sreachriqibt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID1);
            }
        });
        
        sreachriqibt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID2);
            }
        });
        
        initTime();
        setDatetime();
        setTitle(this.getResources().getString(R.string.app_name) + "-" + this.getResources().getString(R.string.cxzcjl)); 
 
        sreachSpinner =(Spinner) findViewById(R.id.zhichuSpinner); 
        Cursor cursor = managedQuery(Sjjzb.SjjzbColumns.CONTENT_URI_LEIXIN, leibieCursorColumns, Sjjzb.SjjzbColumns.LEIBIE + " = 1 or " + Sjjzb.SjjzbColumns.LEIBIE + " = 5", null,
        		Sjjzb.SjjzbColumns.DEFAULT_ID_SORT_ORDER);

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, cursor,
        		leibieColumns, 
                new int[] { android.R.id.text1 });
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
        sreachSpinner.setAdapter(adapter);
        
        fhimgbt.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
            	startActivity(new Intent(Zhichu_Sreach.this, znasy2k.gmail.com.c.Zhichu.class));
            }
         });
        fhimgbt.setOnTouchListener(new Button.OnTouchListener(){   
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN){   
					v.setBackgroundResource(R.drawable.fh36_36);
				}
				if(event.getAction() == MotionEvent.ACTION_UP){   
					v.setBackgroundResource(R.drawable.fh32_32);
				}
				return false;
			}   
        });
        ssimgbt.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
            	onClickss();
            }
         });
        ssimgbt.setOnTouchListener(new Button.OnTouchListener(){   
        	public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN){   
					v.setBackgroundResource(R.drawable.ss36_36);
				}
				if(event.getAction() == MotionEvent.ACTION_UP){   
					v.setBackgroundResource(R.drawable.ss32_32);
				}
				return false;
			}   
        });
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, MENU_ITEM_BACK, 0, R.string.menu_back)
                .setIcon(R.drawable.fh36_36);
        menu.add(0, MENU_ITEM_SREACH, 0, R.string.menu_sreach)
        .setIcon(R.drawable.ss36_36);   
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case MENU_ITEM_BACK: {
			startActivity(new Intent(Zhichu_Sreach.this, znasy2k.gmail.com.c.Zhichu.class));
            return true;
            }
        case MENU_ITEM_SREACH:{
        	Bundle bundle = new Bundle();
            sreachriqi1 = (TextView)findViewById(R.id.sreachriqi1);
            sreachriqi2 = (TextView)findViewById(R.id.sreachriqi2);
            sreachjine1 = (TextView)findViewById(R.id.sreachjine1);
            sreachjine2 = (TextView)findViewById(R.id.sreachjine2);
            
            String sSreachriqi1 = sreachriqi1.getText().toString();
            String sSreachriqi2 = sreachriqi2.getText().toString();
            String sSreachjine1 = sreachjine1.getText().toString();
            String sSreachjine2 = sreachjine2.getText().toString();

            if(!sSreachjine1.equals("")) {
            	Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,2})?$");
				Matcher matcher = pattern.matcher(sSreachjine1);
				if(matcher.find() == false) {
					new AlertDialog.Builder(this) 
	                .setMessage(R.string.editTextJine) 
	                .setPositiveButton(R.string.sjjzbok, 
                        new DialogInterface.OnClickListener(){ 
                                public void onClick(DialogInterface dialoginterface, int i){ } 
                        }).show();
					return false;
				}
            }
            
            if(!sSreachjine2.equals("")) {
            	Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,2})?$");
				Matcher matcher = pattern.matcher(sSreachjine2);
				if(matcher.find() == false) {
					new AlertDialog.Builder(this) 
	                .setMessage(R.string.editTextJine) 
	                .setPositiveButton(R.string.sjjzbok, 
                        new DialogInterface.OnClickListener(){ 
                                public void onClick(DialogInterface dialoginterface, int i){ } 
                        }).show();
					return false;
				}
            }
            
            if(!sSreachjine1.equals("") && !sSreachjine2.equals("")) {
                BigDecimal bSreachjine1 = new BigDecimal(sSreachjine1);
                BigDecimal bSreachjine2 = new BigDecimal(sSreachjine2);
                //-1 小于,0 等于,1 大于
            	if(bSreachjine1.compareTo(bSreachjine2) == 1) {
            		new AlertDialog.Builder(this) 
	                .setMessage(R.string.sreach_jinedaxiaotishi) 
	                .setPositiveButton(R.string.sjjzbok, 
                        new DialogInterface.OnClickListener(){ 
                                public void onClick(DialogInterface dialoginterface, int i){  } 
                        }).show();
            		return false;
            	}
            }
            
            if(!sSreachriqi1.equals("") && !sSreachriqi2.equals("")) {
            	if(TimeUtil.CompleTime(sSreachriqi1, sSreachriqi2) == false) {
            		new AlertDialog.Builder(this) 
	                .setMessage(R.string.sreach_riqidaxiaotishi) 
	                .setPositiveButton(R.string.sjjzbok, 
                        new DialogInterface.OnClickListener(){ 
                                public void onClick(DialogInterface dialoginterface, int i){ } 
                        }).show();
            		return false;
            	}
            }
 
        	bundle.putLong("SpinnerId", sreachSpinner.getSelectedItemId());//给bundle 写入数据
        	bundle.putString("sreachjine1", sSreachjine1);
        	bundle.putString("sreachjine2", sSreachjine2);
        	bundle.putString("sreachriqi1", sSreachriqi1);
        	bundle.putString("sreachriqi2", sSreachriqi2);
        	
        	Intent mIntent = new Intent();
        	mIntent.setClass(Zhichu_Sreach.this, znasy2k.gmail.com.c.Zhichu_sreachList.class);   
        	mIntent.putExtras(bundle);
        	startActivity(mIntent);
        	return true;
        	}

        }
        return super.onOptionsItemSelected(item);
    }
    
	private void initTime(){
		Calendar c = Calendar. getInstance(TimeZone.getTimeZone("GMT+08:00"));
		mYear1 = c.get(Calendar.YEAR);
        mMonth1 = c.get(Calendar.MONTH);
        mDay1 = c.get(Calendar.DAY_OF_MONTH);
        
        mYear2 = c.get(Calendar.YEAR);
        mMonth2 = c.get(Calendar.MONTH);
        mDay2 = c.get(Calendar.DAY_OF_MONTH);
	}
	
	private void setDatetime(){
		sreachriqi1.setText(mYear1 + "-" + padDate(mMonth1 + 1) + "-" + padDate(mDay1));
		sreachriqi2.setText(mYear2 + "-" + padDate(mMonth2 + 1) + "-" + padDate(mDay2));
	}
	
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID1: {
                return new DatePickerDialog(this,
                        mDateSetListener1,
                        mYear1, mMonth1, mDay1);
            }
            case DATE_DIALOG_ID2: {
                return new DatePickerDialog(this,
                        mDateSetListener2,
                        mYear2, mMonth2, mDay2);
            }
        }
        return null;
    }
	
    protected void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {
            case DATE_DIALOG_ID1: {
            	((DatePickerDialog) dialog).setTitle(R.string.setriqi);
                Button sjjzbOK = (Button)((DatePickerDialog) dialog).findViewById(android.R.id.button1);
                sjjzbOK.setTextSize(18);
                sjjzbOK.setText(R.string.sjjzbok);
                Button sjjzbCancel = (Button)((DatePickerDialog) dialog).findViewById(android.R.id.button2);
                sjjzbCancel.setTextSize(18);
                sjjzbCancel.setText(R.string.sjjzbcancel);
                ((DatePickerDialog) dialog).updateDate(mYear1, mMonth1, mDay1);
                break;
            }
            case DATE_DIALOG_ID2: {
            	((DatePickerDialog) dialog).setTitle(R.string.setriqi);
                Button sjjzbOK = (Button)((DatePickerDialog) dialog).findViewById(android.R.id.button1);
                sjjzbOK.setTextSize(18);
                sjjzbOK.setText(R.string.sjjzbok);
                Button sjjzbCancel = (Button)((DatePickerDialog) dialog).findViewById(android.R.id.button2);
                sjjzbCancel.setTextSize(18);
                sjjzbCancel.setText(R.string.sjjzbcancel);
                ((DatePickerDialog) dialog).updateDate(mYear2, mMonth2, mDay2);
                break;
            }   
           
        }
    }
    
    private DatePickerDialog.OnDateSetListener mDateSetListener1 =
        new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mYear1 = year;
                mMonth1 = monthOfYear;
                mDay1 = dayOfMonth;
                updateDisplay1();
            }
        };
        
    private DatePickerDialog.OnDateSetListener mDateSetListener2 =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYear2 = year;
                    mMonth2 = monthOfYear;
                    mDay2 = dayOfMonth;
                    updateDisplay2();
                }
            };
        
    private void updateDisplay1() {
        	sreachriqi1.setText(
                    new StringBuilder()
                    .append(mYear1).append("-")
                    .append(padDate(mMonth1 + 1)).append("-")
                    .append(padDate(mDay1)));
    } 
    
    private void updateDisplay2() {
    	sreachriqi2.setText(
                new StringBuilder()
                .append(mYear2).append("-")
                .append(padDate(mMonth2 + 1)).append("-")
                .append(padDate(mDay2)));
    } 
    
    private static String padDate(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    } 
    
    public boolean onClickss() {
		Bundle bundle = new Bundle();
	    sreachriqi1 = (TextView)findViewById(R.id.sreachriqi1);
	    sreachriqi2 = (TextView)findViewById(R.id.sreachriqi2);
	    sreachjine1 = (TextView)findViewById(R.id.sreachjine1);
	    sreachjine2 = (TextView)findViewById(R.id.sreachjine2);
	    
	    String sSreachriqi1 = sreachriqi1.getText().toString();
	    String sSreachriqi2 = sreachriqi2.getText().toString();
	    String sSreachjine1 = sreachjine1.getText().toString();
	    String sSreachjine2 = sreachjine2.getText().toString();
	
	    if(!sSreachjine1.equals("")) {
	    	Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,2})?$");
			Matcher matcher = pattern.matcher(sSreachjine1);
			if(matcher.find() == false) {
				new AlertDialog.Builder(this) 
	            .setMessage(R.string.editTextJine) 
	            .setPositiveButton(R.string.sjjzbok, 
	                new DialogInterface.OnClickListener(){ 
	                        public void onClick(DialogInterface dialoginterface, int i){ } 
	                }).show();
				return false;
			}
	    }
	    
	    if(!sSreachjine2.equals("")) {
	    	Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,2})?$");
			Matcher matcher = pattern.matcher(sSreachjine2);
			if(matcher.find() == false) {
				new AlertDialog.Builder(this) 
	            .setMessage(R.string.editTextJine) 
	            .setPositiveButton(R.string.sjjzbok, 
	                new DialogInterface.OnClickListener(){ 
	                        public void onClick(DialogInterface dialoginterface, int i){ } 
	                }).show();
				return false;
			}
	    }
	    
	    if(!sSreachjine1.equals("") && !sSreachjine2.equals("")) {
	        BigDecimal bSreachjine1 = new BigDecimal(sSreachjine1);
	        BigDecimal bSreachjine2 = new BigDecimal(sSreachjine2);
	        //-1 小于,0 等于,1 大于
	    	if(bSreachjine1.compareTo(bSreachjine2) == 1) {
	    		new AlertDialog.Builder(this) 
	            .setMessage(R.string.sreach_jinedaxiaotishi) 
	            .setPositiveButton(R.string.sjjzbok, 
	                new DialogInterface.OnClickListener(){ 
	                        public void onClick(DialogInterface dialoginterface, int i){  } 
	                }).show();
	    		return false;
	    	}
	    }
	    
	    if(!sSreachriqi1.equals("") && !sSreachriqi2.equals("")) {
	            	if(TimeUtil.CompleTime(sSreachriqi1, sSreachriqi2) == false) {
	            		new AlertDialog.Builder(this) 
		                .setMessage(R.string.sreach_riqidaxiaotishi) 
		                .setPositiveButton(R.string.sjjzbok, 
	                        new DialogInterface.OnClickListener(){ 
	                                public void onClick(DialogInterface dialoginterface, int i){ } 
	                        }).show();
	            		return false;
	            	}
	            }
	 
	    bundle.putLong("SpinnerId", sreachSpinner.getSelectedItemId());//给bundle 写入数据
		bundle.putString("sreachjine1", sSreachjine1);
		bundle.putString("sreachjine2", sSreachjine2);
		bundle.putString("sreachriqi1", sSreachriqi1);
		bundle.putString("sreachriqi2", sSreachriqi2);
		
		Intent mIntent = new Intent();
		mIntent.setClass(Zhichu_Sreach.this, znasy2k.gmail.com.c.Zhichu_sreachList.class);   
		mIntent.putExtras(bundle);
		startActivity(mIntent);
		return true;

    }
}