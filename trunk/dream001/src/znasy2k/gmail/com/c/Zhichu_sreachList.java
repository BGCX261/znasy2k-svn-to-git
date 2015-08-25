package znasy2k.gmail.com.c;

import java.math.BigDecimal;

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
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class Zhichu_sreachList extends Activity {
	public static final int MENU_ITEM_BACK = Menu.FIRST;
    public static final int MENU_ITEM_DELETE = Menu.FIRST + 1;
    public static final int MENU_ITEM_EDIT = Menu.FIRST + 2;
    public void onConfigurationChanged(Configuration newConfig) { 
        super.onConfigurationChanged(newConfig); 
    } 
	private static final String[] ZhichuCursorColumns = new String[] {
		Constant.SHOUZHI + "." + Sjjzb.SjjzbColumns._ID, 
		Constant.LEIXIN + "." + Sjjzb.SjjzbColumns.MINGCHENG,
		Constant.SHOUZHI + "." + Sjjzb.SjjzbColumns.JINE, 
		Constant.SHOUZHI + "." + Sjjzb.SjjzbColumns.RIQI, 
		Constant.SHOUZHI + "." + Sjjzb.SjjzbColumns.ZHAIYAO, 
	};
	
	private static final String[] ZhichuColumns = new String[] {
		Sjjzb.SjjzbColumns.MINGCHENG,
		Sjjzb.SjjzbColumns.JINE, 
		Sjjzb.SjjzbColumns.RIQI, 
		Sjjzb.SjjzbColumns.ZHAIYAO, 
	};
	
	private ListView zhichu_list;
	Long SpinnerId;
	String sreachjine1;
	String sreachjine2;
	String sreachriqi1;
	String sreachriqi2;
    String ssqlwhere = "";
    String ssqlwhere1 = "";
    String ssqlwhere2 = "";
    private TextView zhichureachxq;
    private Bundle bl;
    private long lSelectedItemId = 0;
    private Button fhimgbt;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getResources().getConfiguration().orientation == 
            Configuration.ORIENTATION_PORTRAIT) {   
        	//竖屏 
        	setContentView(R.layout.zhichusreachlist); 
		} else { 
		    //横屏时 
			setContentView(R.layout.zhichusreachlist_h); 
		} 
        
        setTitle(this.getResources().getString(R.string.app_name) + "-" + this.getResources().getString(R.string.cxzcjljg));        
        
        Intent intent = getIntent();
        if (intent.getData() == null) {
            intent.setData(Sjjzb.SjjzbColumns.CONTENT_URI_SHOUZHI);
        }
        
        bl = intent.getExtras();
  		SpinnerId = bl.getLong("SpinnerId");
  		sreachjine1 = bl.getString("sreachjine1");
  		sreachjine2 = bl.getString("sreachjine2");
  		sreachriqi1 = bl.getString("sreachriqi1");
  		sreachriqi2 = bl.getString("sreachriqi2");
  		fhimgbt = (Button) findViewById(R.id.fhimgbt);
  		
  		zhichureachxq = (TextView) findViewById(R.id.zhichusreachxq);
        zhichu_list = (ListView) findViewById(R.id.zhichu_list);
        zhichu_list.setOnCreateContextMenuListener(this);
        
        //选择
    	if(SpinnerId == 3) {
    		ssqlwhere1 = Constant.SHOUZHI + "." + Sjjzb.SjjzbColumns.LEIXIN + " = " + Constant.LEIXIN + "."
			+ Sjjzb.SjjzbColumns._ID +" and " + Constant.SHOUZHI + "."
			+ Sjjzb.SjjzbColumns.LEIBIE + " = 1";
    	} else {
    		ssqlwhere1 = Constant.SHOUZHI + "." + Sjjzb.SjjzbColumns.LEIXIN + " = " + Constant.LEIXIN + "."
			+ Sjjzb.SjjzbColumns._ID +" and " + Constant.SHOUZHI + "."
			+ Sjjzb.SjjzbColumns.LEIBIE + " = 1 and " + Constant.SHOUZHI + "." + Sjjzb.SjjzbColumns.LEIXIN + " = " + SpinnerId;
    	}
    	//金额
    	if(sreachjine1.equals("") && sreachjine2.equals("")) {
    		ssqlwhere2 = ssqlwhere1;
    	} else if(sreachjine1.equals("") && !sreachjine2.equals("")) {
    		ssqlwhere2 = ssqlwhere1 + " and cast(" + Constant.SHOUZHI + "." + Sjjzb.SjjzbColumns.JINE + " as numeric) >= 0 and cast("
    		+ Constant.SHOUZHI + "." + Sjjzb.SjjzbColumns.JINE + " as numeric) <= " + sreachjine2;
    	} else if(!sreachjine1.equals("") && sreachjine2.equals("")) {
    		ssqlwhere2 = ssqlwhere1 + " and cast(" + Constant.SHOUZHI + "." + Sjjzb.SjjzbColumns.JINE + " as numeric) >= " + sreachjine1;
    	} else {
    		ssqlwhere2 = ssqlwhere1 + " and cast(" + Constant.SHOUZHI + "." + Sjjzb.SjjzbColumns.JINE + " as numeric) >= " + sreachjine1 + " and cast("
    		+ Constant.SHOUZHI + "." + Sjjzb.SjjzbColumns.JINE + " as numeric) <= " + sreachjine2;
    	}
    	//日期
		ssqlwhere = ssqlwhere2 + " and " + Constant.SHOUZHI + "." + Sjjzb.SjjzbColumns.RIQI + " >= '" + sreachriqi1 + "' and "
		+ Constant.SHOUZHI + "." + Sjjzb.SjjzbColumns.RIQI  + " <= '" + sreachriqi2 + "'";
    	
        Cursor cursor = managedQuery(Sjjzb.SjjzbColumns.CONTENT_URI_SHOUZHI, ZhichuCursorColumns, ssqlwhere, null,
        		Sjjzb.SjjzbColumns.DEFAULT_RIQI_SORT_ORDER);

    	BigDecimal bzcjezj = BigDecimal.ZERO;
        while(cursor.moveToNext()) {
        	BigDecimal bzcje = new BigDecimal(cursor.getString(2));
        	bzcjezj = bzcjezj.add(bzcje);
        }
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
        		ZhichuColumns, 
                new int[] { R.id.zhichu_leibie, R.id.zhichu_jine, R.id.zhichu_riqi, R.id.zhichu_zhaiyao });
        
        zhichu_list.setAdapter(adapter); 
        String sxqtext = "";
        String srqtext = this.getResources().getString(R.string.riqi) + ": " + sreachriqi1 + " " + this.getResources().getString(R.string.sreach_zhi) + " " + sreachriqi2;
        String sjetext = this.getResources().getString(R.string.jine2) + ": ";
        String szchj = this.getResources().getString(R.string.zhichuhjmh) + " " ;
        sxqtext = sxqtext + srqtext;
        if(bzcjezj == BigDecimal.ZERO){
        	szchj = szchj + "0 " +  this.getResources().getString(R.string.yuan);
	    } else {
	    	szchj = szchj + bzcjezj.toString() +  this.getResources().getString(R.string.yuan);
	    }
        
        if(sreachjine1.equals("") && sreachjine2.equals("")) {
        	sxqtext = sxqtext + " " + szchj;
    	} else if(sreachjine1.equals("") && !sreachjine2.equals("")) {
    		sjetext = sjetext + this.getResources().getString(R.string.xiaoyu) + sreachjine2 + this.getResources().getString(R.string.yuan);
    		sxqtext = sxqtext + " " + sjetext + " " + szchj;
    	} else if(!sreachjine1.equals("") && sreachjine2.equals("")) {
    		sjetext = sjetext + this.getResources().getString(R.string.dayu) + sreachjine1 + this.getResources().getString(R.string.yuan);
    		sxqtext = sxqtext + " " + sjetext + " " + szchj;
    	} else {
    		sjetext = sjetext +  sreachjine1 + this.getResources().getString(R.string.yuan) 
    		+ " " + this.getResources().getString(R.string.sreach_zhi) + " " 
    		+ sreachjine2 + this.getResources().getString(R.string.yuan);
    		sxqtext = sxqtext + " " + sjetext + " " + szchj;
    	}
        
        zhichureachxq.setText(sxqtext);
        
        zhichu_list.setOnItemSelectedListener(new OnItemSelectedListener(){
        	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        		lSelectedItemId = zhichu_list.getSelectedItemId();
        	}

        	public void onNothingSelected(AdapterView<?> arg0) {
        		lSelectedItemId = 0;
        	}
        }); 
        
        fhimgbt.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
            	startActivity(new Intent(Zhichu_sreachList.this, znasy2k.gmail.com.c.Zhichu.class));
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
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, MENU_ITEM_BACK, 0, R.string.menu_back)
                .setIcon(R.drawable.fh36_36);
   
        return true;
    }
    
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if((zhichu_list.getCount() > 0) && (lSelectedItemId != 0)) {
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
			startActivity(new Intent(Zhichu_sreachList.this, znasy2k.gmail.com.c.Zhichu_Sreach.class));
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
            intent.setClass(Zhichu_sreachList.this, znasy2k.gmail.com.c.Zhichu_Edit.class);   
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

        Cursor cursor = (Cursor) zhichu_list.getAdapter().getItem(info.position);
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
                intent.setClass(Zhichu_sreachList.this, znasy2k.gmail.com.c.Zhichu_Edit.class);   
         
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