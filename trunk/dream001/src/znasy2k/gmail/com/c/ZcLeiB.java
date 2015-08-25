package znasy2k.gmail.com.c;

import znasy2k.gmail.com.provider.Sjjzb;
import znasy2k.gmail.com.util.Constant;
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
import android.widget.AdapterView.OnItemSelectedListener;

public class ZcLeiB extends Activity {
	public static final int MENU_ITEM_BACK = Menu.FIRST;
    public static final int MENU_ITEM_INSERT = Menu.FIRST + 1;
    public static final int MENU_ITEM_DELETE = Menu.FIRST + 2;
    public static final int MENU_ITEM_EDIT = Menu.FIRST + 3;
	private static final String[] LeixinCursorColumns = new String[] {
		Constant.LEIXIN + "." + Sjjzb.SjjzbColumns._ID, 
		Constant.LEIXIN + "." + Sjjzb.SjjzbColumns.MINGCHENG,
		Constant.LEIXIN + "." + Sjjzb.SjjzbColumns.ZHAIYAO, 
	};
	private static final String[] ZhichuCursorColumns = new String[] {
		Constant.SHOUZHI + "." + Sjjzb.SjjzbColumns._ID,
	};
	
	private static final String[] LeixinColumns = new String[] {
		Sjjzb.SjjzbColumns.MINGCHENG,
		Sjjzb.SjjzbColumns.ZHAIYAO, 
	};
    public void onConfigurationChanged(Configuration newConfig) { 
        super.onConfigurationChanged(newConfig); 
    } 
	private ListView leibei_list;
	private long lSelectedItemId = 0;
	Long SpinnerId;
    String ssqlwhere = "";
    private Button fhimgbt;
    private Button xjimgbt;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         
        if (this.getResources().getConfiguration().orientation == 
            Configuration.ORIENTATION_PORTRAIT) {   
        	//竖屏 
        	setContentView(R.layout.srleib);
		} else { 
		    //横屏时 
			setContentView(R.layout.srleib_h);
		} 
        setTitle(this.getResources().getString(R.string.app_name) + "-" + this.getResources().getString(R.string.zcleiblb));        
        
        Intent intent = getIntent();
        if (intent.getData() == null) {
            intent.setData(Sjjzb.SjjzbColumns.CONTENT_URI_LEIXIN);
        }
        fhimgbt = (Button) findViewById(R.id.fhimgbt);
        xjimgbt = (Button) findViewById(R.id.xjimgbt);
        leibei_list = (ListView) findViewById(R.id.leibei_list);
        leibei_list.setOnCreateContextMenuListener(this);
        
    	ssqlwhere = Constant.LEIXIN + "." + Sjjzb.SjjzbColumns.LEIBIE + " = 1";
        
        Cursor cursor = managedQuery(Sjjzb.SjjzbColumns.CONTENT_URI_LEIXIN, LeixinCursorColumns, ssqlwhere, null,
        		Sjjzb.SjjzbColumns.DEFAULT_ID_SORT_ORDER);
        int ihm = 0;
        if (this.getResources().getConfiguration().orientation == 
            Configuration.ORIENTATION_PORTRAIT) {   
        	//竖屏 
        	ihm = R.layout.leibie_item;
		} else { 
		    //横屏时 
			ihm = R.layout.leibie_item_h;
		} 
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, ihm, cursor,
        		LeixinColumns, 
                new int[] { R.id.srlb_mingcheng, R.id.srlb_zhaiyao });
        
        leibei_list.setAdapter(adapter); 
        
        leibei_list.setOnItemSelectedListener(new OnItemSelectedListener(){
        	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        		lSelectedItemId = leibei_list.getSelectedItemId();
        	}

        	public void onNothingSelected(AdapterView<?> arg0) {
        		lSelectedItemId = 0;
        	}
        }); 
        fhimgbt.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
            	startActivity(new Intent(ZcLeiB.this, znasy2k.gmail.com.c.Xtsz.class));
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
            	startActivity(new Intent(ZcLeiB.this, znasy2k.gmail.com.c.ZcLeiB_Create.class));
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
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, MENU_ITEM_BACK, 0, R.string.menu_back)
                .setIcon(R.drawable.fh36_36);
        menu.add(0, MENU_ITEM_INSERT, 0, R.string.menu_insert)
                .setIcon(R.drawable.xz36_36);     
        return true;
    }
    
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if((leibei_list.getCount() > 0) && (lSelectedItemId != 0)) {
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
			startActivity(new Intent(ZcLeiB.this, znasy2k.gmail.com.c.Xtsz.class));
            return true;
        }
        case MENU_ITEM_INSERT: {
			startActivity(new Intent(ZcLeiB.this, znasy2k.gmail.com.c.ZcLeiB_Create.class));
            return true;
        }
        case MENU_ITEM_DELETE:{
        	//删除选项
        	String szsqlwhere = Constant.SHOUZHI + "." + Sjjzb.SjjzbColumns.LEIXIN + " = " + Constant.LEIXIN + "."
    		+ Sjjzb.SjjzbColumns._ID + " and " + Constant.SHOUZHI + "." + Sjjzb.SjjzbColumns.LEIXIN + " = " + lSelectedItemId;
            Cursor cursorzc = managedQuery(Sjjzb.SjjzbColumns.CONTENT_URI_SHOUZHI, ZhichuCursorColumns, szsqlwhere, null,
            		Constant.SHOUZHI + "." + Sjjzb.SjjzbColumns.DEFAULT_ID_SORT_ORDER);
            if(cursorzc.moveToNext()){
            	new AlertDialog.Builder(ZcLeiB.this) 
                .setMessage(R.string.bnsc) 
                .setPositiveButton(R.string.sjjzbok, 
                    new DialogInterface.OnClickListener(){ 
                            public void onClick(DialogInterface dialoginterface, int i){   
                            	
                            } 
                    }).show();
            	return false;
            }
        	
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
            intent.setClass(ZcLeiB.this, znasy2k.gmail.com.c.ZcLeiB_Edit.class);   
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
            Log.e("收入 onCreateContextMenu", "错误菜单", e);
            return;
        }

        Cursor cursor = (Cursor) leibei_list.getAdapter().getItem(info.position);
        if (cursor == null) {
            return;
        }

        menu.setHeaderTitle(this.getResources().getString(R.string.mingcheng) + ":" + cursor.getString(1));
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
            	String szsqlwhere = Constant.SHOUZHI + "." + Sjjzb.SjjzbColumns.LEIXIN + " = " + Constant.LEIXIN + "."
        		+ Sjjzb.SjjzbColumns._ID + " and " + Constant.SHOUZHI + "." + Sjjzb.SjjzbColumns.LEIXIN + " = " + info.id;
                Cursor cursorzc = managedQuery(Sjjzb.SjjzbColumns.CONTENT_URI_SHOUZHI, ZhichuCursorColumns, szsqlwhere, null,
                		Constant.SHOUZHI + "." + Sjjzb.SjjzbColumns.DEFAULT_ID_SORT_ORDER);
                if(cursorzc.moveToNext()){
                	new AlertDialog.Builder(ZcLeiB.this) 
                    .setMessage(R.string.bnsc) 
                    .setPositiveButton(R.string.sjjzbok, 
                        new DialogInterface.OnClickListener(){ 
                                public void onClick(DialogInterface dialoginterface, int i){   
                                	
                                } 
                        }).show();
                	return false;
                }
            	
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
                intent.setClass(ZcLeiB.this, znasy2k.gmail.com.c.ZcLeiB_Edit.class);   
         
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