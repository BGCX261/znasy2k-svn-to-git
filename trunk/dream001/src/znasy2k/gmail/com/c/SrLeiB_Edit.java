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
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SrLeiB_Edit extends Activity {
	public static final int MENU_ITEM_BACK = Menu.FIRST;
    public static final int MENU_ITEM_SAVE = Menu.FIRST + 1;
	
    private EditText srleibCreatemingchengedit;
    private EditText srleibCreatezhaoyaoedit;
    private long lSelectedItemId;
    private Bundle bl;
    private String ssqlwhere = "";
    private Button fhimgbt;
    private Button bcimgbt;
	private static final String[] LeiXinCursorColumns = new String[] {
		Constant.LEIXIN + "." + Sjjzb.SjjzbColumns._ID, 
		Constant.LEIXIN + "." + Sjjzb.SjjzbColumns.MINGCHENG, 
		Constant.LEIXIN + "." + Sjjzb.SjjzbColumns.LEIBIE, 
		Constant.LEIXIN + "." + Sjjzb.SjjzbColumns.ZHAIYAO, 
	};
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
        setTitle(this.getResources().getString(R.string.app_name) + "-" + this.getResources().getString(R.string.xgsrleib)); 
        
        Intent intent = getIntent();
        if (intent.getData() == null) {
            intent.setData(Sjjzb.SjjzbColumns.CONTENT_URI_LEIXIN);
        }
        
        //获取Intent中的Bundle数据
        bl = intent.getExtras();
		lSelectedItemId = bl.getLong("id");
		
		srleibCreatemingchengedit = (EditText)findViewById(R.id.srleibCreatemingchengedit);
        srleibCreatezhaoyaoedit = (EditText)findViewById(R.id.srleibCreatezhaoyaoedit);
        fhimgbt = (Button) findViewById(R.id.fhimgbt);
        bcimgbt = (Button) findViewById(R.id.bcimgbt);
		ssqlwhere =  Constant.LEIXIN + "." + Sjjzb.SjjzbColumns._ID + " = " + lSelectedItemId;
		
        Cursor cursorMz = managedQuery(Sjjzb.SjjzbColumns.CONTENT_URI_LEIXIN,
        		LeiXinCursorColumns, ssqlwhere, null,
        		Sjjzb.SjjzbColumns.DEFAULT_ID_SORT_ORDER);
        
        if(cursorMz.moveToNext()) {
        	srleibCreatemingchengedit.setText(cursorMz.getString(1));
        	srleibCreatezhaoyaoedit.setText(cursorMz.getString(3)); 
        }    
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
            		new AlertDialog.Builder(SrLeiB_Edit.this) 
                    .setMessage(R.string.alert_zhichunewjine) 
                    .setPositiveButton(R.string.sjjzbok, 
                        new DialogInterface.OnClickListener(){ 
                                public void onClick(DialogInterface dialoginterface, int i){} 
                        }).show();
            	} else {
            		sjjzbvalues.put(Sjjzb.SjjzbColumns.MINGCHENG, mingcheng);
                	sjjzbvalues.put(Sjjzb.SjjzbColumns.LEIBIE, 2);
                	sjjzbvalues.put(Sjjzb.SjjzbColumns.ZHAIYAO, srleibCreatezhaoyaoedit.getText().toString());

                    getContentResolver().update(sjjzbUri, sjjzbvalues, Constant.LEIXIN + "." + Sjjzb.SjjzbColumns._ID + " = " + lSelectedItemId, null);
                    
                    new AlertDialog.Builder(SrLeiB_Edit.this) 
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
                .setMessage(R.string.alert_zhichunewjine) 
                .setPositiveButton(R.string.sjjzbok, 
                    new DialogInterface.OnClickListener(){ 
                            public void onClick(DialogInterface dialoginterface, int i){} 
                    }).show();
        	  return false;
        	} 
        	sjjzbvalues.put(Sjjzb.SjjzbColumns.MINGCHENG, mingcheng);
        	sjjzbvalues.put(Sjjzb.SjjzbColumns.LEIBIE, 2);
        	sjjzbvalues.put(Sjjzb.SjjzbColumns.ZHAIYAO, srleibCreatezhaoyaoedit.getText().toString());

            getContentResolver().update(sjjzbUri, sjjzbvalues, Constant.LEIXIN + "." + Sjjzb.SjjzbColumns._ID + " = " + lSelectedItemId, null);
            
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
        	startActivity(new Intent(SrLeiB_Edit.this, znasy2k.gmail.com.c.SrLeiB.class));
        }
}