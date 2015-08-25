package znasy2k.gmail.com.c;

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
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity {
	private static final String[] YongHuCursorColumns = new String[] {
		Constant.YONGHU + "." + Sjjzb.SjjzbColumns._ID,
		Constant.YONGHU + "." + Sjjzb.SjjzbColumns.MIMA, 
	};
	
	private String ssqlwhere;
	private EditText password_edit;
	private EditText repassword_edit;
	private String spwd = "";
	private Button dlimgbt;
	
    public void onConfigurationChanged(Configuration newConfig) { 
        super.onConfigurationChanged(newConfig); 
    } 
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if (this.getResources().getConfiguration().orientation == 
            Configuration.ORIENTATION_PORTRAIT) {   
        	//竖屏 
        	setContentView(R.layout.bg);
		} else { 
		    //横屏时 
			setContentView(R.layout.bg_h);
		} 
        
        setTitle(this.getResources().getString(R.string.app_name));  
        
        Intent intent = getIntent();
        if (intent.getData() == null) {
            intent.setData(Sjjzb.SjjzbColumns.CONTENT_URI_YONGHU);
        }
        
        //验证是否第一次使用
        ssqlwhere = Constant.YONGHU + "." + Sjjzb.SjjzbColumns._ID + " = 1";
        Cursor cursor = managedQuery(Sjjzb.SjjzbColumns.CONTENT_URI_YONGHU, YongHuCursorColumns, ssqlwhere, null,
        		Sjjzb.SjjzbColumns.DEFAULT_ID_SORT_ORDER);
        if(cursor.moveToNext()){
        	spwd = cursor.getString(1);   
        }
        dlv();
        dlimgbt = (Button) findViewById(R.id.dlimgbt);
        dlimgbt.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
            	dlv();
            }
         });
        dlimgbt.setOnTouchListener(new Button.OnTouchListener(){   
        	public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN){   
					v.setBackgroundResource(R.drawable.dl2);
				}
				if(event.getAction() == MotionEvent.ACTION_UP){   
					v.setBackgroundResource(R.drawable.dl);
				}
				return false;
			}   
        });
    }
    
    public void dlv() {
    	//密码验证开始
        LayoutInflater factory = LayoutInflater.from(this);
        final View textEntryView;
        if(spwd == null || spwd.equals("")) {
        	textEntryView = factory.inflate(R.layout.loginrepwd, null);
        	new AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle(R.string.pwdset)
            .setView(textEntryView)
            .setPositiveButton(R.string.sjjzbok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                	password_edit = (EditText) textEntryView.findViewById(R.id.password_edit);
                    repassword_edit = (EditText) textEntryView.findViewById(R.id.repassword_edit);
                    String pwd = password_edit.getText().toString();
                    String repwd = repassword_edit.getText().toString();

                	if(pwd == null || pwd.equals("")) {
                		new AlertDialog.Builder(Login.this) 
                        .setMessage(R.string.ppwdone) 
                        .setPositiveButton(R.string.sjjzbok, 
                            new DialogInterface.OnClickListener(){ 
                                    public void onClick(DialogInterface dialoginterface, int i){ 
                                    	StartLoginIntent();                                  	
                                    } 
                            }).show();
                	} else if(repwd == null || repwd.equals("")) {
                		new AlertDialog.Builder(Login.this) 
                        .setMessage(R.string.ppwdtwo) 
                        .setPositiveButton(R.string.sjjzbok, 
                            new DialogInterface.OnClickListener(){ 
                                    public void onClick(DialogInterface dialoginterface, int i){ 
                                    	StartLoginIntent();                                	
                                    } 
                            }).show();
                	} else if(!pwd.equals(repwd)) {
                		new AlertDialog.Builder(Login.this) 
                        .setMessage(R.string.repwd) 
                        .setPositiveButton(R.string.sjjzbok, 
                            new DialogInterface.OnClickListener(){ 
                                    public void onClick(DialogInterface dialoginterface, int i){ 
                                    	StartLoginIntent();                                  	
                                    } 
                            }).show();
                	} else if(pwd.equals(repwd)) {
                		Uri sjjzbUri = getIntent().getData();
                		ContentValues sjjzbvalues = new ContentValues();	
                    	sjjzbvalues.put(Sjjzb.SjjzbColumns.MIMA, pwd);
                    	getContentResolver().update(sjjzbUri, sjjzbvalues, Constant.YONGHU + "." + Sjjzb.SjjzbColumns._ID + " = 1", null);
                    	StartGridAnimationIntent();	
                	}
                }
            })
            .setNegativeButton(R.string.sjjzbcancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                	finish();
                }
            }).show();
        } else {
        	textEntryView = factory.inflate(R.layout.loginpwd, null);
        	new AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle(R.string.pwdinfo)
            .setView(textEntryView)
            .setPositiveButton(R.string.sjjzbok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                	password_edit = (EditText) textEntryView.findViewById(R.id.password_edit);
                    String pwd = password_edit.getText().toString();
                	if(pwd == null || pwd.equals("")) {
                		new AlertDialog.Builder(Login.this) 
                        .setMessage(R.string.ppwdone) 
                        .setPositiveButton(R.string.sjjzbok, 
                            new DialogInterface.OnClickListener(){ 
                                    public void onClick(DialogInterface dialoginterface, int i){ 
                                    	StartLoginIntent();                                  	
                                    } 
                            }).show();
                	} else {
                		
                		if(spwd.equals(pwd)) {
                    		StartGridAnimationIntent();
                    	} else {
                    		new AlertDialog.Builder(Login.this) 
                            .setMessage(R.string.pwderror) 
                            .setPositiveButton(R.string.sjjzbok, 
                                new DialogInterface.OnClickListener(){ 
                                        public void onClick(DialogInterface dialoginterface, int i){ 
                                        	StartLoginIntent();                                  	
                                        } 
                                }).show();
                    	}
                		
                	}
                	
                }
            })
            .setNegativeButton(R.string.sjjzbcancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                	finish();
                }
            }).show();
        }
        //密码验证结束
    }
    
    public void StartGridAnimationIntent() {
    	Intent intent = new Intent();
    	intent.setClass(Login.this, znasy2k.gmail.com.c.GridAnimation.class);
		startActivity(intent);
		Login.this.finish();	
    }
    public void StartLoginIntent() {
    	Intent intent = new Intent();
    	intent.setClass(Login.this, znasy2k.gmail.com.c.Login.class);
		startActivity(intent);
		Login.this.finish();	
    }
}