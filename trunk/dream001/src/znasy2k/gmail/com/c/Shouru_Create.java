package znasy2k.gmail.com.c;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import znasy2k.gmail.com.provider.Sjjzb;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class Shouru_Create extends Activity {
	public static final int MENU_ITEM_BACK = Menu.FIRST;
    public static final int MENU_ITEM_INSERT_LEIBIE = Menu.FIRST + 1;
    public static final int MENU_ITEM_SAVE = Menu.FIRST + 2; 
    private TextView shoururiqi; 
    private Spinner shouruSpinner; 
    private EditText shourujine;
    private EditText shouruzhaoyaoedit;
	private int mYear;
    private int mMonth;
    private int mDay;
    private Button btshourusetriqi;
    private Button fhimgbt;
    private Button bcimgbt;
    private Button xjsrlbimgbt;
    private static final int DATE_DIALOG_ID = 1;
    
	private static final String[] leibieCursorColumns = new String[] {
		Sjjzb.SjjzbColumns._ID, 
		Sjjzb.SjjzbColumns.MINGCHENG,
	};	
	private static final String[] leibieColumns = new String[] {
		Sjjzb.SjjzbColumns.MINGCHENG,
	};
    public void onConfigurationChanged(Configuration newConfig) { 
        super.onConfigurationChanged(newConfig); 
    } 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getResources().getConfiguration().orientation == 
            Configuration.ORIENTATION_PORTRAIT) {   
        	//竖屏 
        	setContentView(R.layout.shouru_create); 
		} else { 
		    //横屏时 
			setContentView(R.layout.shouru_create_h); 
		} 
        
        Intent intent = getIntent();
        if (intent.getData() == null) {
            intent.setData(Sjjzb.SjjzbColumns.CONTENT_URI_SHOUZHI);
        }
        setTitle(this.getResources().getString(R.string.app_name) + "-" + this.getResources().getString(R.string.xjsrjl)); 
        shoururiqi = (TextView) findViewById(R.id.shoururiqiview);
        shouruSpinner =(Spinner) findViewById(R.id.shouruSpinner);    
        btshourusetriqi = (Button)findViewById(R.id.shourusetriqi);
        shourujine = (EditText)findViewById(R.id.shourunewjine);
        shouruzhaoyaoedit = (EditText)findViewById(R.id.shouruzhaoyaoedit);
        fhimgbt = (Button) findViewById(R.id.fhimgbt);
        bcimgbt = (Button) findViewById(R.id.bcimgbt);
        xjsrlbimgbt = (Button) findViewById(R.id.xjsrlbimgbt);
        btshourusetriqi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });
        
        initTime();
        setDatetime();
       
        Cursor cursor = managedQuery(Sjjzb.SjjzbColumns.CONTENT_URI_LEIXIN, leibieCursorColumns, Sjjzb.SjjzbColumns.LEIBIE + " = 2", null,
        		Sjjzb.SjjzbColumns.DEFAULT_ID_SORT_ORDER);

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, cursor,
        		leibieColumns, 
                new int[] { android.R.id.text1 });
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);       
        shouruSpinner.setAdapter(adapter);
        
        fhimgbt.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
            	backshouruActivity();   
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
        bcimgbt.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
            	Uri sjjzbUri = getIntent().getData();
            	ContentValues sjjzbvalues = new ContentValues(); 	
            	String sJine = shourujine.getText().toString();
            	String sSelectedItemId = Long.toString(shouruSpinner.getSelectedItemId()); 
            	if(sSelectedItemId.equals("-9223372036854775808") ) {
            		new AlertDialog.Builder(Shouru_Create.this) 
                    .setMessage(R.string.alert_xzsrlb) 
                    .setPositiveButton(R.string.sjjzbok, 
                        new DialogInterface.OnClickListener(){ 
                                public void onClick(DialogInterface dialoginterface, int i){} 
                        }).show();
            	} else {
            		if(sJine.equals("")) {
                		new AlertDialog.Builder(Shouru_Create.this) 
                        .setMessage(R.string.alert_zhichunewjine) 
                        .setPositiveButton(R.string.sjjzbok, 
                            new DialogInterface.OnClickListener(){ 
                                    public void onClick(DialogInterface dialoginterface, int i){} 
                            }).show();
                	} else {
        				//Pattern pattern = Pattern.compile("^(-)?(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,2})?$");
                		Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,2})?$");
        				Matcher matcher = pattern.matcher(sJine);
        				if(matcher.find() == false) { 
        					Log.d("matcher.find()","matcher.find(): "+ matcher.find()); 
        					new AlertDialog.Builder(Shouru_Create.this) 
        	                .setMessage(R.string.editTextJine) 
        	                .setPositiveButton(R.string.sjjzbok, 
        	                    new DialogInterface.OnClickListener(){ 
        	                            public void onClick(DialogInterface dialoginterface, int i){} 
        	                    }).show();
        		        } else {
        		        	sjjzbvalues.put(Sjjzb.SjjzbColumns.JINE, sJine);
        	            	sjjzbvalues.put(Sjjzb.SjjzbColumns.RIQI, shoururiqi.getText().toString());
        	            	sjjzbvalues.put(Sjjzb.SjjzbColumns.LEIBIE, 2);
        	            	sjjzbvalues.put(Sjjzb.SjjzbColumns.LEIXIN, shouruSpinner.getSelectedItemId());
        	            	sjjzbvalues.put(Sjjzb.SjjzbColumns.ZHAIYAO, shouruzhaoyaoedit.getText().toString());
        	            	sjjzbvalues.put(Sjjzb.SjjzbColumns.USERID, 1);
        	                getContentResolver().insert(sjjzbUri, sjjzbvalues);
        	                new AlertDialog.Builder(Shouru_Create.this) 
        	                .setMessage(R.string.alert_save) 
        	                .setPositiveButton(R.string.sjjzbok, 
        	                    new DialogInterface.OnClickListener(){ 
        	                            public void onClick(DialogInterface dialoginterface, int i){ 
        	                            	backshouruActivity();                                  	
        	                            } 
        	                    }).show();
        		        }
                	}
            	}          	
            }
         });
        bcimgbt.setOnTouchListener(new Button.OnTouchListener(){   
        	public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN){   
					v.setBackgroundResource(R.drawable.bc36_36);
				}
				if(event.getAction() == MotionEvent.ACTION_UP){   
					v.setBackgroundResource(R.drawable.bc32_32);
				}
				return false;
			}   
        });
        xjsrlbimgbt.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
            	startActivity(new Intent(Shouru_Create.this, znasy2k.gmail.com.c.SrLeiB_Create_Shouru.class));
            }
         });
        xjsrlbimgbt.setOnTouchListener(new Button.OnTouchListener(){   
        	public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN){   
					v.setBackgroundResource(R.drawable.xz36_36);
				}
				if(event.getAction() == MotionEvent.ACTION_UP){   
					v.setBackgroundResource(R.drawable.xz32_32);
				}
				return false;
			}   
        });
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, MENU_ITEM_BACK, 0, R.string.menu_back).setIcon(R.drawable.fh36_36);
        
        menu.add(0, MENU_ITEM_SAVE, 0, R.string.menu_save).setIcon(R.drawable.bc36_36);

        menu.add(0, MENU_ITEM_INSERT_LEIBIE, 0, R.string.xjsrleib).setIcon(R.drawable.xz36_36); 
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case MENU_ITEM_BACK: {
        	backshouruActivity();
            return true;
        }
        case MENU_ITEM_INSERT_LEIBIE: {
			//弹出菜单,插入类型代码
        	startActivity(new Intent(Shouru_Create.this, znasy2k.gmail.com.c.SrLeiB_Create_Shouru.class));
            return true;
        }
        case MENU_ITEM_SAVE:{
        	Uri sjjzbUri = getIntent().getData();
        	ContentValues sjjzbvalues = new ContentValues(); 	
        	String sJine = shourujine.getText().toString();
        	String sSelectedItemId = Long.toString(shouruSpinner.getSelectedItemId()); 
        	if(sSelectedItemId.equals("-9223372036854775808") ) {
        		new AlertDialog.Builder(this) 
                .setMessage(R.string.alert_xzsrlb) 
                .setPositiveButton(R.string.sjjzbok, 
                    new DialogInterface.OnClickListener(){ 
                            public void onClick(DialogInterface dialoginterface, int i){} 
                    }).show();
        		return false;
        	} 
        	if(sJine.equals("")) {
        		new AlertDialog.Builder(this) 
                .setMessage(R.string.alert_zhichunewjine) 
                .setPositiveButton(R.string.sjjzbok, 
                    new DialogInterface.OnClickListener(){ 
                            public void onClick(DialogInterface dialoginterface, int i){} 
                    }).show();
        	  return false;
        	} else {
				//Pattern pattern = Pattern.compile("^(-)?(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,2})?$");
        		Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,2})?$");
				Matcher matcher = pattern.matcher(sJine);
				if(matcher.find() == false) { 
					Log.d("matcher.find()","matcher.find(): "+ matcher.find()); 
					new AlertDialog.Builder(this) 
	                .setMessage(R.string.editTextJine) 
	                .setPositiveButton(R.string.sjjzbok, 
	                    new DialogInterface.OnClickListener(){ 
	                            public void onClick(DialogInterface dialoginterface, int i){} 
	                    }).show();
					return false;
		        } 
        	}
        	sjjzbvalues.put(Sjjzb.SjjzbColumns.JINE, sJine);
        	sjjzbvalues.put(Sjjzb.SjjzbColumns.RIQI, shoururiqi.getText().toString());
        	sjjzbvalues.put(Sjjzb.SjjzbColumns.LEIBIE, 2);
        	sjjzbvalues.put(Sjjzb.SjjzbColumns.LEIXIN, shouruSpinner.getSelectedItemId());
        	sjjzbvalues.put(Sjjzb.SjjzbColumns.ZHAIYAO, shouruzhaoyaoedit.getText().toString());
        	sjjzbvalues.put(Sjjzb.SjjzbColumns.USERID, 1);
            getContentResolver().insert(sjjzbUri, sjjzbvalues);
            new AlertDialog.Builder(this) 
            .setMessage(R.string.alert_save) 
            .setPositiveButton(R.string.sjjzbok, 
                new DialogInterface.OnClickListener(){ 
                        public void onClick(DialogInterface dialoginterface, int i){ 
                        	backshouruActivity();                                  	
                        } 
                }).show();
        		return true;
        	}
        }
        return super.onOptionsItemSelected(item);
    }
    
	private void initTime(){
		Calendar c = Calendar. getInstance(TimeZone.getTimeZone("GMT+08:00"));
		mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
	}
	
	private void setDatetime(){
		shoururiqi.setText(mYear + "-" + padDate(mMonth + 1) + "-" + padDate(mDay));
	}
	
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);
        }
        return null;
    }

    protected void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {
            case DATE_DIALOG_ID: {
            	((DatePickerDialog) dialog).setTitle(R.string.setriqi);
                Button sjjzbOK = (Button)((DatePickerDialog) dialog).findViewById(android.R.id.button1);
                sjjzbOK.setTextSize(18);
                sjjzbOK.setText(R.string.sjjzbok);
                Button sjjzbCancel = (Button)((DatePickerDialog) dialog).findViewById(android.R.id.button2);
                sjjzbCancel.setTextSize(18);
                sjjzbCancel.setText(R.string.sjjzbcancel);
                
                ((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
            }
                break;
        }
    }
	
    private DatePickerDialog.OnDateSetListener mDateSetListener =
        new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mYear = year;
                mMonth = monthOfYear;
                mDay = dayOfMonth;
                updateDisplay();
            }
        };
    private void updateDisplay() {
    	shoururiqi.setText(
                new StringBuilder()
                // Month is 0 based so add 1
                .append(mYear).append("-")
                .append(padDate(mMonth + 1)).append("-")
                .append(padDate(mDay)));
    }   
    
    private void backshouruActivity() {
    	startActivity(new Intent(Shouru_Create.this, znasy2k.gmail.com.c.Shouru.class));
    }
    
    private static String padDate(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    } 
}