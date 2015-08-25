package znasy2k.gmail.com.c;

import java.math.BigDecimal;
import znasy2k.gmail.com.provider.Sjjzb;
import znasy2k.gmail.com.util.Constant;
import znasy2k.gmail.com.util.TimeUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class Shouru extends Activity {
	public static final int MENU_ITEM_BACK = Menu.FIRST;
    public static final int MENU_ITEM_INSERT = Menu.FIRST + 1;
    public static final int MENU_ITEM_SREACH = Menu.FIRST + 2;
    public static final int MENU_ITEM_DELETE = Menu.FIRST + 3;
    public static final int MENU_ITEM_EDIT = Menu.FIRST + 4;
	private static final String[] ShouruCursorColumns = new String[] {
		Constant.SHOUZHI + "." + Sjjzb.SjjzbColumns._ID, 
		Constant.LEIXIN + "." + Sjjzb.SjjzbColumns.MINGCHENG,
		Constant.SHOUZHI + "." + Sjjzb.SjjzbColumns.JINE, 
		Constant.SHOUZHI + "." + Sjjzb.SjjzbColumns.RIQI, 
		Constant.SHOUZHI + "." + Sjjzb.SjjzbColumns.ZHAIYAO, 
	};
	
	private static final String[] ShouruColumns = new String[] {
		Sjjzb.SjjzbColumns.MINGCHENG,
		Sjjzb.SjjzbColumns.JINE, 
		Sjjzb.SjjzbColumns.RIQI, 
		Sjjzb.SjjzbColumns.ZHAIYAO, 
	};
	
	private ListView shouru_list;
	private long lSelectedItemId = 0;
	Long SpinnerId;
	String sreachjine1;
	String sreachjine2;
	String sreachriqi1;
	String sreachriqi2;
    String ssqlwhere = "";
    String ssqlwhere1 = "";
    String ssqlwhere2 = "";
    private TextView dyshouruheji;
    private Button fhimgbt;
    private Button xjimgbt;
    private Button ssimgbt;
    
    public void onConfigurationChanged(Configuration newConfig) { 
        super.onConfigurationChanged(newConfig); 
    } 

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getResources().getConfiguration().orientation == 
            Configuration.ORIENTATION_PORTRAIT) {   
        	//竖屏 
        	setContentView(R.layout.shouru);
		} else { 
		    //横屏时 
			setContentView(R.layout.shouru_h); 
		} 

        setTitle(this.getResources().getString(R.string.app_name) + "-" + this.getResources().getString(R.string.srlb));        
        
        Intent intent = getIntent();
        if (intent.getData() == null) {
            intent.setData(Sjjzb.SjjzbColumns.CONTENT_URI_SHOUZHI);
        }
        
        fhimgbt = (Button) findViewById(R.id.fhimgbt);
        xjimgbt = (Button) findViewById(R.id.xjimgbt);
        ssimgbt = (Button) findViewById(R.id.ssimgbt);
        
        dyshouruheji = (TextView) findViewById(R.id.dyshouruheji);
        shouru_list = (ListView) findViewById(R.id.shouru_list);
        shouru_list.setOnCreateContextMenuListener(this);
        
    	ssqlwhere = Constant.SHOUZHI + "." + Sjjzb.SjjzbColumns.LEIXIN + " = " + Constant.LEIXIN + "."
		+ Sjjzb.SjjzbColumns._ID +" and " + Constant.SHOUZHI + "."
		+ Sjjzb.SjjzbColumns.LEIBIE + " = 2";
     
    	String sqlwhere = ssqlwhere + " and " + Constant.SHOUZHI + "." + Sjjzb.SjjzbColumns.RIQI + " >= '" + TimeUtil.getMonthFirstDay() + "' and "
		+ Constant.SHOUZHI + "." + Sjjzb.SjjzbColumns.RIQI  + " <= '" + TimeUtil.getMonthLastDay() + "'";
    	Cursor dycursor = managedQuery(Sjjzb.SjjzbColumns.CONTENT_URI_SHOUZHI, ShouruCursorColumns, sqlwhere, null,
        		Sjjzb.SjjzbColumns.DEFAULT_RIQI_SORT_ORDER);
        BigDecimal bsrjezj = BigDecimal.ZERO;
        while(dycursor.moveToNext()) {
        	BigDecimal bzcje = new BigDecimal(dycursor.getString(2));
        	bsrjezj = bsrjezj.add(bzcje);
        }
        
        if(bsrjezj == BigDecimal.ZERO){
        	dyshouruheji.setText("0 " +  this.getResources().getString(R.string.yuan));
	    } else {
	    	dyshouruheji.setText(bsrjezj.toString() +  this.getResources().getString(R.string.yuan));
	    }
    	
        Cursor cursor = managedQuery(Sjjzb.SjjzbColumns.CONTENT_URI_SHOUZHI, ShouruCursorColumns, ssqlwhere, null,
        		Sjjzb.SjjzbColumns.DEFAULT_RIQI_SORT_ORDER);
        int ihm = 0;
        if (this.getResources().getConfiguration().orientation == 
            Configuration.ORIENTATION_PORTRAIT) {   
        	//竖屏 
        	ihm = R.layout.zhichu_item;
		} else { 
		    //横屏时 
			ihm = R.layout.zhichu_item_h;
		} 
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, ihm, cursor,
        		ShouruColumns, 
                new int[] { R.id.zhichu_leibie, R.id.zhichu_jine, R.id.zhichu_riqi, R.id.zhichu_zhaiyao });
        
        shouru_list.setAdapter(adapter); 
        
        shouru_list.setOnItemSelectedListener(new OnItemSelectedListener(){
        	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        		lSelectedItemId = shouru_list.getSelectedItemId();
        	}

        	public void onNothingSelected(AdapterView<?> arg0) {
        		lSelectedItemId = 0;
        	}
        });
        fhimgbt.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
            	startActivity(new Intent(Shouru.this, znasy2k.gmail.com.c.GridAnimation.class));
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
        xjimgbt.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
            	startActivity(new Intent(Shouru.this, znasy2k.gmail.com.c.Shouru_Create.class));
            }
         });
        xjimgbt.setOnTouchListener(new Button.OnTouchListener(){   
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
        ssimgbt.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
            	startActivity(new Intent(Shouru.this, znasy2k.gmail.com.c.Shouru_Sreach.class));
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
        menu.add(0, MENU_ITEM_BACK, 0, R.string.menu_back).setIcon(R.drawable.fh36_36);

        menu.add(0, MENU_ITEM_INSERT, 0, R.string.menu_insert).setIcon(R.drawable.xz36_36);  
        
        menu.add(0, MENU_ITEM_SREACH, 0, R.string.menu_sreach).setIcon(R.drawable.ss36_36);   
        return true;
    }
    
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if((shouru_list.getCount() > 0) && (lSelectedItemId != 0)) {
        	if(menu.findItem(MENU_ITEM_DELETE) == null) {
        		menu.add(0, MENU_ITEM_EDIT, 0, R.string.menu_edit).setIcon(R.drawable.xg36_36);
        		menu.add(0, MENU_ITEM_DELETE, 0, R.string.menu_delete).setIcon(R.drawable.sc36_36);
        	}
        }
        if((lSelectedItemId == 0) && (menu.findItem(MENU_ITEM_DELETE) != null)) {
        	menu.removeItem(MENU_ITEM_EDIT);
        	menu.removeItem(MENU_ITEM_DELETE);
        }
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case MENU_ITEM_BACK: {
			startActivity(new Intent(Shouru.this, znasy2k.gmail.com.c.GridAnimation.class));
            return true;
        }
        case MENU_ITEM_INSERT: {
			startActivity(new Intent(Shouru.this, znasy2k.gmail.com.c.Shouru_Create.class));
            return true;
        }
        case MENU_ITEM_SREACH:{
        	startActivity(new Intent(Shouru.this, znasy2k.gmail.com.c.Shouru_Sreach.class));
        	return true;
        }
        case MENU_ITEM_DELETE:{
        	//删除选项
        	AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle(R.string.alert_title).setMessage(R.string.alert_content)
        	.setIcon(R.drawable.sc36_36).setPositiveButton(R.string.sjjzbok, 
            new DialogInterface.OnClickListener() { 
                public void onClick(DialogInterface dialog, int whichButton) { 
					Uri sjjzbUri = ContentUris.withAppendedId(getIntent().getData(), lSelectedItemId);
					getContentResolver().delete(sjjzbUri, null, null);
                } 
            }).setNegativeButton(R.string.sjjzbcancel, 
            new DialogInterface.OnClickListener() { 
                public void onClick(DialogInterface dialog, int whichButton) { 
                } 
            }).show(); 
        	
        	Button sjjzbOK = (Button)((AlertDialog) alertDialog).findViewById(android.R.id.button1);
            sjjzbOK.setTextSize(18);
            Button sjjzbCancel = (Button)((AlertDialog) alertDialog).findViewById(android.R.id.button2);
            sjjzbCancel.setTextSize(18);
            
        	return true;
        	}
        case MENU_ITEM_EDIT:{
        	Intent intent = new Intent();   
            intent.setClass(Shouru.this, znasy2k.gmail.com.c.Shouru_Edit.class);   
            Bundle bundle = new Bundle();   
            bundle.putLong("id", lSelectedItemId);       
            intent.putExtras(bundle);   
            startActivity(intent);   
        	return true;
        	}
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo) {
    	AdapterView.AdapterContextMenuInfo info;
        try {
             info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        } catch (ClassCastException e) {
            Log.e("Zhichu onCreateContextMenu", "错误菜单", e);
            return;
        }

        Cursor cursor = (Cursor) shouru_list.getAdapter().getItem(info.position);
        if (cursor == null) {
            return;
        }

        menu.setHeaderTitle(this.getResources().getString(R.string.xuhao) +
        		":" + cursor.getString(0) + "," +  this.getResources().getString(R.string.zhichu_leibie) +
        		":" + cursor.getString(1) + "," +  this.getResources().getString(R.string.jine) + ":" + cursor.getString(2));
    	menu.add(0, MENU_ITEM_EDIT, 0, R.string.menu_edit);
        menu.add(0, MENU_ITEM_DELETE, 0, R.string.menu_delete);
    }
        
    public boolean onContextItemSelected(MenuItem item) {
    	final AdapterView.AdapterContextMenuInfo info;
        try {
             info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        } catch (ClassCastException e) {
            Log.e("Zhichu onContextItemSelected", "错误菜单选项", e);
            return false;
        }

        switch (item.getItemId()) {
            case MENU_ITEM_DELETE: {
                //删除选项
            	AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle(R.string.alert_title).setMessage(R.string.alert_content)
            	.setIcon(R.drawable.sc36_36).setPositiveButton(R.string.sjjzbok, 
                new DialogInterface.OnClickListener() { 
                    public void onClick(DialogInterface dialog, int whichButton) { 
						Uri sjjzbUri = ContentUris.withAppendedId(getIntent().getData(), info.id);
						getContentResolver().delete(sjjzbUri, null, null);
                    } 
                }).setNegativeButton(R.string.sjjzbcancel, 
                new DialogInterface.OnClickListener() { 
                    public void onClick(DialogInterface dialog, int whichButton) { 
                    	
                    } 
                }).show(); 

            	Button sjjzbOK = (Button)((AlertDialog) alertDialog).findViewById(android.R.id.button1);
                sjjzbOK.setTextSize(18);
                Button sjjzbCancel = (Button)((AlertDialog) alertDialog).findViewById(android.R.id.button2);
                sjjzbCancel.setTextSize(18);
            	
                return true;
            }
            case MENU_ITEM_EDIT: {
                //修改界面
            	Intent intent = new Intent();   
                intent.setClass(Shouru.this, znasy2k.gmail.com.c.Shouru_Edit.class);   
         
                Bundle bundle = new Bundle();   
                bundle.putLong("id", info.id);       
                intent.putExtras(bundle); 
                startActivity(intent);
                return true;
            }
        }
        return false;
    }
    
}