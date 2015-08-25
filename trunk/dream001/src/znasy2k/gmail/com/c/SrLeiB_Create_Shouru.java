package znasy2k.gmail.com.c;

import znasy2k.gmail.com.provider.Sjjzb;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SrLeiB_Create_Shouru extends Activity {
	public static final int MENU_ITEM_BACK = Menu.FIRST;
    public static final int MENU_ITEM_SAVE = Menu.FIRST + 1; 
    private Button fhimgbt;
    private Button bcimgbt;
    private EditText srleibCreatemingchengedit;
    private EditText srleibCreatezhaoyaoedit;
    public void onConfigurationChanged(Configuration newConfig) { 
        super.onConfigurationChanged(newConfig); 
    } 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getResources().getConfiguration().orientation == 
            Configuration.ORIENTATION_PORTRAIT) {   
        	//竖屏 
        	setContentView(R.layout.srleib_create); 
		} else { 
		    //横屏时 
			setContentView(R.layout.srleib_create_h); 
		} 
        
        Intent intent = getIntent();
        if (intent.getData() == null) {
            intent.setData(Sjjzb.SjjzbColumns.CONTENT_URI_LEIXIN);
        }
        setTitle(this.getResources().getString(R.string.app_name) + "-" + this.getResources().getString(R.string.xjsrleib)); 
        
        srleibCreatemingchengedit = (EditText)findViewById(R.id.srleibCreatemingchengedit);
        srleibCreatezhaoyaoedit = (EditText)findViewById(R.id.srleibCreatezhaoyaoedit);
        fhimgbt = (Button) findViewById(R.id.fhimgbt);
        bcimgbt = (Button) findViewById(R.id.bcimgbt);
        fhimgbt.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
            	backSrLeiBActivity();   
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
            	String mingcheng = srleibCreatemingchengedit.getText().toString();
            	if(mingcheng.equals("")) {
            		new AlertDialog.Builder(SrLeiB_Create_Shouru.this) 
                    .setMessage(R.string.alert_mingcheng) 
                    .setPositiveButton(R.string.sjjzbok, 
                        new DialogInterface.OnClickListener(){ 
                                public void onClick(DialogInterface dialoginterface, int i){} 
                        }).show();
            	} else {
            		sjjzbvalues.put(Sjjzb.SjjzbColumns.MINGCHENG, mingcheng);
                	sjjzbvalues.put(Sjjzb.SjjzbColumns.LEIBIE, 2);
                	sjjzbvalues.put(Sjjzb.SjjzbColumns.ZHAIYAO, srleibCreatezhaoyaoedit.getText().toString());
                    getContentResolver().insert(sjjzbUri, sjjzbvalues);
                    new AlertDialog.Builder(SrLeiB_Create_Shouru.this) 
                    .setMessage(R.string.alert_save) 
                    .setPositiveButton(R.string.sjjzbok, 
                        new DialogInterface.OnClickListener(){ 
                                public void onClick(DialogInterface dialoginterface, int i){ 
                                	backSrLeiBActivity();                                  	
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
        menu.add(0, MENU_ITEM_BACK, 0, R.string.menu_back)
                .setIcon(R.drawable.fh36_36);
        
        menu.add(0, MENU_ITEM_SAVE, 0, R.string.menu_save)
                .setIcon(R.drawable.bc36_36);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case MENU_ITEM_BACK: {
        	backSrLeiBActivity();
            return true;
        }
        case MENU_ITEM_SAVE:{
        	Uri sjjzbUri = getIntent().getData();
        	ContentValues sjjzbvalues = new ContentValues(); 	
        	String mingcheng = srleibCreatemingchengedit.getText().toString();
        	if(mingcheng.equals("")) {
        		new AlertDialog.Builder(this) 
                .setMessage(R.string.alert_mingcheng) 
                .setPositiveButton(R.string.sjjzbok, 
                    new DialogInterface.OnClickListener(){ 
                            public void onClick(DialogInterface dialoginterface, int i){} 
                    }).show();
        	  return false;
        	}
        	sjjzbvalues.put(Sjjzb.SjjzbColumns.MINGCHENG, mingcheng);
        	sjjzbvalues.put(Sjjzb.SjjzbColumns.LEIBIE, 2);
        	sjjzbvalues.put(Sjjzb.SjjzbColumns.ZHAIYAO, srleibCreatezhaoyaoedit.getText().toString());
            getContentResolver().insert(sjjzbUri, sjjzbvalues);
            new AlertDialog.Builder(this) 
            .setMessage(R.string.alert_save) 
            .setPositiveButton(R.string.sjjzbok, 
                new DialogInterface.OnClickListener(){ 
                        public void onClick(DialogInterface dialoginterface, int i){ 
                        	backSrLeiBActivity();                                  	
                        } 
                }).show();
        		return true;
        	}
        }
        return super.onOptionsItemSelected(item);
    }

    private void backSrLeiBActivity() {
    	startActivity(new Intent(SrLeiB_Create_Shouru.this, znasy2k.gmail.com.c.Shouru_Create.class));
    }
}