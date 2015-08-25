package znasy2k.gmail.com.c;

import java.math.BigDecimal;
import znasy2k.gmail.com.provider.Sjjzb;
import znasy2k.gmail.com.util.Constant;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


public class Mxcx_xq extends Activity {
	public static final int MENU_ITEM_BACK = Menu.FIRST;
	private static final String[] ShouruCursorColumns = new String[] {
		Constant.SHOUZHI + "." + Sjjzb.SjjzbColumns._ID, 
		Constant.LEIXIN + "." + Sjjzb.SjjzbColumns.MINGCHENG,
		Constant.SHOUZHI + "." + Sjjzb.SjjzbColumns.JINE, 
		Constant.SHOUZHI + "." + Sjjzb.SjjzbColumns.RIQI, 
		Constant.SHOUZHI + "." + Sjjzb.SjjzbColumns.ZHAIYAO, 
		Constant.SHOUZHI + "." + Sjjzb.SjjzbColumns.LEIBIE, 
	};
	
	private static final String[] ShouruColumns = new String[] {
		Sjjzb.SjjzbColumns.MINGCHENG,
		Sjjzb.SjjzbColumns.JINE, 
		Sjjzb.SjjzbColumns.RIQI, 
		Sjjzb.SjjzbColumns.ZHAIYAO, 
		Sjjzb.SjjzbColumns.LEIBIE, 
	};
    public void onConfigurationChanged(Configuration newConfig) { 
        super.onConfigurationChanged(newConfig); 
    } 
	private ListView mx_list;
	private Long SpinnerId;
	private String sreachjine1;
	private String sreachjine2;
	private String sreachriqi1;
	private String sreachriqi2;
	private String ssqlwhere = "";
	private String ssqlwhere1 = "";
	private String ssqlwhere2 = "";
    private Bundle bl;
    private TextView sreachxq;
    private Button fhimgbt;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getResources().getConfiguration().orientation == 
            Configuration.ORIENTATION_PORTRAIT) {   
        	//竖屏 
        	setContentView(R.layout.mxcx_xq);
		} else { 
		    //横屏时 
			setContentView(R.layout.mxcx_xq_h);
		} 
         
        setTitle(this.getResources().getString(R.string.app_name) + "-" + this.getResources().getString(R.string.mxcxlb));        
        
        Intent intent = getIntent();
        if (intent.getData() == null) {
            intent.setData(Sjjzb.SjjzbColumns.CONTENT_URI_SHOUZHI);
        }

        sreachxq = (TextView) findViewById(R.id.sreachxq);
        mx_list = (ListView) findViewById(R.id.mx_list);
        fhimgbt = (Button) findViewById(R.id.fhimgbt);
        mx_list.setOnCreateContextMenuListener(this);
        
        bl = intent.getExtras();
        SpinnerId = bl.getLong("SpinnerId");
  		sreachjine1 = bl.getString("sreachjine1");
  		sreachjine2 = bl.getString("sreachjine2");
  		sreachriqi1 = bl.getString("sreachriqi1");
  		sreachriqi2 = bl.getString("sreachriqi2");
  		//选择
  		if(SpinnerId == 1) {
    		ssqlwhere1 = Constant.SHOUZHI + "." + Sjjzb.SjjzbColumns.LEIXIN + " = " + Constant.LEIXIN + "."
			+ Sjjzb.SjjzbColumns._ID +" and (" + Constant.SHOUZHI + "."
			+ Sjjzb.SjjzbColumns.LEIBIE + " = 1 or " + Constant.SHOUZHI + "."
			+ Sjjzb.SjjzbColumns.LEIBIE + " = 2 )";
    	} else if(SpinnerId == 2) {
    		ssqlwhere1 = Constant.SHOUZHI + "." + Sjjzb.SjjzbColumns.LEIXIN + " = " + Constant.LEIXIN + "."
			+ Sjjzb.SjjzbColumns._ID +" and " + Constant.SHOUZHI + "."
			+ Sjjzb.SjjzbColumns.LEIBIE + " = 2";
    	} else if(SpinnerId == 3) {
    		ssqlwhere1 = Constant.SHOUZHI + "." + Sjjzb.SjjzbColumns.LEIXIN + " = " + Constant.LEIXIN + "."
			+ Sjjzb.SjjzbColumns._ID +" and " + Constant.SHOUZHI + "."
			+ Sjjzb.SjjzbColumns.LEIBIE + " = 1";
    	} else {
    		ssqlwhere1 = Constant.SHOUZHI + "." + Sjjzb.SjjzbColumns.LEIXIN + " = " + Constant.LEIXIN + "."
			+ Sjjzb.SjjzbColumns._ID +" and (" + Constant.SHOUZHI + "."
			+ Sjjzb.SjjzbColumns.LEIBIE + " = 1 or " + Constant.SHOUZHI + "."
			+ Sjjzb.SjjzbColumns.LEIBIE + " = 2 ) and " + Constant.SHOUZHI + "." + Sjjzb.SjjzbColumns.LEIXIN + " = " + SpinnerId;
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
		
		Cursor cursor = managedQuery(Sjjzb.SjjzbColumns.CONTENT_URI_SHOUZHI, ShouruCursorColumns, ssqlwhere, null,
        		Sjjzb.SjjzbColumns.DEFAULT_RIQI_SORT_ORDER);
		
		BigDecimal bsrjezc = BigDecimal.ZERO;
		BigDecimal bsrjesr = BigDecimal.ZERO;
		while (cursor.moveToNext()) {
			BigDecimal bsrje = new BigDecimal(cursor.getString(2));
			
			long llx = cursor.getLong(5);
			if(llx == 1) {
				bsrjezc = bsrjezc.add(bsrje);
			} else if(llx == 2) {
				bsrjesr = bsrjesr.add(bsrje);
			}
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
        		ShouruColumns, 
                new int[] { R.id.zhichu_leibie, R.id.zhichu_jine, R.id.zhichu_riqi, R.id.zhichu_zhaiyao });
        
        mx_list.setAdapter(adapter); 
  
        String sxqtext = "";
        String srqtext = this.getResources().getString(R.string.riqi) + ": " + sreachriqi1 + " " + this.getResources().getString(R.string.sreach_zhi) + " " + sreachriqi2;
        String sjetext = this.getResources().getString(R.string.jine2) + ": ";
        String zchj = this.getResources().getString(R.string.zhichuhjmh) + " " ;
        String srhj = this.getResources().getString(R.string.shouruhjmh) + " " ;
        String szchj = this.getResources().getString(R.string.heji) + " " ;
        
        sxqtext = sxqtext + srqtext;
        if(bsrjezc == BigDecimal.ZERO){
        	zchj = zchj + "0 " +  this.getResources().getString(R.string.yuan);
	    } else {
	    	zchj = zchj + bsrjezc.toString() +  this.getResources().getString(R.string.yuan);
	    }
        if(bsrjesr == BigDecimal.ZERO){
        	srhj = srhj + "0 " +  this.getResources().getString(R.string.yuan);
	    } else {
	    	srhj = srhj + bsrjesr.toString() +  this.getResources().getString(R.string.yuan);
	    }
        
	     //-1 小于,0 等于,1 大于
	     if(bsrjezc.compareTo(bsrjesr) == 0){
	    	 szchj = szchj + "0 " +  this.getResources().getString(R.string.yuan);
	     } else if(bsrjezc.compareTo(bsrjesr) > 0){//支出大于收入
	    	 szchj = szchj + "-" + bsrjezc.subtract(bsrjesr).toString() + " " +  this.getResources().getString(R.string.yuan);
	     } else if(bsrjezc.compareTo(bsrjesr) < 0){//支出小于收入
	    	 szchj = szchj + bsrjesr.subtract(bsrjezc).toString() + " " +  this.getResources().getString(R.string.yuan);
	     }
        
        if(sreachjine1.equals("") && sreachjine2.equals("")) {
        	sxqtext = sxqtext + " " + zchj + " " + srhj + " " + szchj;
    	} else if(sreachjine1.equals("") && !sreachjine2.equals("")) {
    		sjetext = sjetext + this.getResources().getString(R.string.xiaoyu) + sreachjine2 + this.getResources().getString(R.string.yuan);
    		sxqtext = sxqtext + " " + sjetext + " " + zchj + " " + srhj + " " + szchj;
    	} else if(!sreachjine1.equals("") && sreachjine2.equals("")) {
    		sjetext = sjetext + this.getResources().getString(R.string.dayu) + sreachjine1 + this.getResources().getString(R.string.yuan);
    		sxqtext = sxqtext + " " + sjetext + " " + zchj + " " + szchj + " " + szchj;
    	} else {
    		sjetext = sjetext +  sreachjine1 + this.getResources().getString(R.string.yuan) 
    		+ " " + this.getResources().getString(R.string.sreach_zhi) + " " 
    		+ sreachjine2 + this.getResources().getString(R.string.yuan);
    		sxqtext = sxqtext + " " + sjetext + " " + zchj + " " + srhj + " " + szchj;
    	}
        
        sreachxq.setText(sxqtext);
        
        fhimgbt.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
            	startActivity(new Intent(Mxcx_xq.this, znasy2k.gmail.com.c.Mxcx.class));
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
        menu.add(0, MENU_ITEM_BACK, 0, R.string.menu_back).setIcon(R.drawable.fh36_36);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case MENU_ITEM_BACK: {
				startActivity(new Intent(Mxcx_xq.this, znasy2k.gmail.com.c.Mxcx.class));
	            return true;
        	}
         }
        return super.onOptionsItemSelected(item);
    }  
}