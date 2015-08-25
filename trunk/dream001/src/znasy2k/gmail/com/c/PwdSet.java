package znasy2k.gmail.com.c;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import znasy2k.gmail.com.provider.Sjjzb;
import znasy2k.gmail.com.util.Constant;
import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.EditText;

public class PwdSet extends Activity {
	public static final int MENU_ITEM_BACK = Menu.FIRST;
    public static final int MENU_ITEM_SAVE = Menu.FIRST + 1; 
    
    private EditText password_edit;
	private EditText repassword_edit;
	private EditText password_start;
	private String ssqlwhere;
	private String spwd = "";
    private Button fhimgbt;
    private Button bcimgbt;
    public void onConfigurationChanged(Configuration newConfig) { 
        super.onConfigurationChanged(newConfig); 
    } 
	private static final String[] YongHuCursorColumns = new String[] {
		Constant.YONGHU + "." + Sjjzb.SjjzbColumns._ID,
		Constant.YONGHU + "." + Sjjzb.SjjzbColumns.MIMA, 
	};
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getResources().getConfiguration().orientation == 
            Configuration.ORIENTATION_PORTRAIT) {   
        	//竖屏 
        	setContentView(R.layout.pwdset); 
		} else { 
		    //横屏时 
			setContentView(R.layout.pwdset_h); 
		} 
        
        Intent intent = getIntent();
        if (intent.getData() == null) {
            intent.setData(Sjjzb.SjjzbColumns.CONTENT_URI_YONGHU);
        }
        
        setTitle(this.getResources().getString(R.string.app_name) + "-" + this.getResources().getString(R.string.pwdmodif)); 
        
        password_edit = (EditText) findViewById(R.id.password_edit);
        repassword_edit = (EditText) findViewById(R.id.repassword_edit);
        password_start = (EditText) findViewById(R.id.password_start);
        fhimgbt = (Button) findViewById(R.id.fhimgbt);
        bcimgbt = (Button) findViewById(R.id.bcimgbt);
        //验证是否第一次使用
        ssqlwhere = Constant.YONGHU + "." + Sjjzb.SjjzbColumns._ID + " = 1";
        Cursor cursor = managedQuery(Sjjzb.SjjzbColumns.CONTENT_URI_YONGHU, YongHuCursorColumns, ssqlwhere, null,
        		Sjjzb.SjjzbColumns.DEFAULT_ID_SORT_ORDER);
        if(cursor.moveToNext()){
        	spwd = cursor.getString(1);   
        }
        fhimgbt.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
            	backXtszActivity();   
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
            	String pwd = password_edit.getText().toString();
	            String repwd = repassword_edit.getText().toString();
	            String pwdstart = password_start.getText().toString();
	            
	            
	            if(pwdstart == null || pwdstart.equals("")) {
	        		new AlertDialog.Builder(PwdSet.this) 
	                .setMessage(R.string.ppwdstart) 
	                .setPositiveButton(R.string.sjjzbok, 
	                    new DialogInterface.OnClickListener(){ 
	                            public void onClick(DialogInterface dialoginterface, int i){} 
	                    }).show();
	        	} else if(!pwdstart.equals(spwd)) {
	        		new AlertDialog.Builder(PwdSet.this) 
	                .setMessage(R.string.pwdstarterror) 
	                .setPositiveButton(R.string.sjjzbok, 
	                    new DialogInterface.OnClickListener(){ 
	                            public void onClick(DialogInterface dialoginterface, int i){} 
	                    }).show();
	        	} else if(pwd == null || pwd.equals("")) {
	        		new AlertDialog.Builder(PwdSet.this) 
	                .setMessage(R.string.ppwdone) 
	                .setPositiveButton(R.string.sjjzbok, 
	                    new DialogInterface.OnClickListener(){ 
	                            public void onClick(DialogInterface dialoginterface, int i){} 
	                    }).show();
	        	} else if(repwd == null || repwd.equals("")) {
	        		new AlertDialog.Builder(PwdSet.this) 
	                .setMessage(R.string.ppwdtwo) 
	                .setPositiveButton(R.string.sjjzbok, 
	                    new DialogInterface.OnClickListener(){ 
	                            public void onClick(DialogInterface dialoginterface, int i){} 
	                    }).show();
	        	} else if(!pwd.equals(repwd)) {
	        		new AlertDialog.Builder(PwdSet.this) 
	                .setMessage(R.string.repwd) 
	                .setPositiveButton(R.string.sjjzbok, 
	                    new DialogInterface.OnClickListener(){ 
	                            public void onClick(DialogInterface dialoginterface, int i){} 
	                    }).show();
	        	} else if(pwd.equals(repwd)) {
	        		Uri sjjzbUri = getIntent().getData();
	        		ContentValues sjjzbvalues = new ContentValues();	
	            	sjjzbvalues.put(Sjjzb.SjjzbColumns.MIMA, pwd);
	            	getContentResolver().update(sjjzbUri, sjjzbvalues, Constant.YONGHU + "." + Sjjzb.SjjzbColumns._ID + " = 1", null);
	            	
	            	new AlertDialog.Builder(PwdSet.this) 
	                .setMessage(R.string.pwdmodifsuc) 
	                .setPositiveButton(R.string.sjjzbok, 
	                    new DialogInterface.OnClickListener(){ 
	                            public void onClick(DialogInterface dialoginterface, int i){
	                            	backXtszActivity();	
	                            } 
	                    }).show();
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
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, MENU_ITEM_BACK, 0, R.string.menu_back).setIcon(R.drawable.fh36_36);
        menu.add(0, MENU_ITEM_SAVE, 0, R.string.menu_save).setIcon(R.drawable.bc36_36);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case MENU_ITEM_BACK: {
        	backXtszActivity();
            return true;
        }
        case MENU_ITEM_SAVE:{
	        	String pwd = password_edit.getText().toString();
	            String repwd = repassword_edit.getText().toString();
	            String pwdstart = password_start.getText().toString();
	            
	            
	            if(pwdstart == null || pwdstart.equals("")) {
	        		new AlertDialog.Builder(PwdSet.this) 
	                .setMessage(R.string.ppwdstart) 
	                .setPositiveButton(R.string.sjjzbok, 
	                    new DialogInterface.OnClickListener(){ 
	                            public void onClick(DialogInterface dialoginterface, int i){} 
	                    }).show();
	        	} else if(!pwdstart.equals(spwd)) {
	        		new AlertDialog.Builder(PwdSet.this) 
	                .setMessage(R.string.pwdstarterror) 
	                .setPositiveButton(R.string.sjjzbok, 
	                    new DialogInterface.OnClickListener(){ 
	                            public void onClick(DialogInterface dialoginterface, int i){} 
	                    }).show();
	        	} else if(pwd == null || pwd.equals("")) {
	        		new AlertDialog.Builder(PwdSet.this) 
	                .setMessage(R.string.ppwdone) 
	                .setPositiveButton(R.string.sjjzbok, 
	                    new DialogInterface.OnClickListener(){ 
	                            public void onClick(DialogInterface dialoginterface, int i){} 
	                    }).show();
	        	} else if(repwd == null || repwd.equals("")) {
	        		new AlertDialog.Builder(PwdSet.this) 
	                .setMessage(R.string.ppwdtwo) 
	                .setPositiveButton(R.string.sjjzbok, 
	                    new DialogInterface.OnClickListener(){ 
	                            public void onClick(DialogInterface dialoginterface, int i){} 
	                    }).show();
	        	} else if(!pwd.equals(repwd)) {
	        		new AlertDialog.Builder(PwdSet.this) 
	                .setMessage(R.string.repwd) 
	                .setPositiveButton(R.string.sjjzbok, 
	                    new DialogInterface.OnClickListener(){ 
	                            public void onClick(DialogInterface dialoginterface, int i){} 
	                    }).show();
	        	} else if(pwd.equals(repwd)) {
	        		Uri sjjzbUri = getIntent().getData();
	        		ContentValues sjjzbvalues = new ContentValues();	
	            	sjjzbvalues.put(Sjjzb.SjjzbColumns.MIMA, pwd);
	            	getContentResolver().update(sjjzbUri, sjjzbvalues, Constant.YONGHU + "." + Sjjzb.SjjzbColumns._ID + " = 1", null);
	            	
	            	new AlertDialog.Builder(PwdSet.this) 
	                .setMessage(R.string.pwdmodifsuc) 
	                .setPositiveButton(R.string.sjjzbok, 
	                    new DialogInterface.OnClickListener(){ 
	                            public void onClick(DialogInterface dialoginterface, int i){
	                            	backXtszActivity();	
	                            } 
	                    }).show();
	        	}
        	}
        }
        return super.onOptionsItemSelected(item);
    }

    private void backXtszActivity() {
    	startActivity(new Intent(PwdSet.this, znasy2k.gmail.com.c.Xtsz.class));
    }
}