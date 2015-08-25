package znasy2k.gmail.com.c;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class GridAnimation extends Activity {
	private GridView grid;
	private SimpleAdapter saImageItems;
	private int last = 0;//标记：上次的ID
	private String zhichuText = "";
	private String shouruText = "";
	private String mxcxText = ""; 
	private String xtszText = "";
	private String guanyuText = "";
	private String tuichuText = "";
    public void onConfigurationChanged(Configuration newConfig) { 
        super.onConfigurationChanged(newConfig); 
    } 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getResources().getConfiguration().orientation == 
            Configuration.ORIENTATION_PORTRAIT) {   
        	//竖屏 
        	setContentView(R.layout.grid_animation);
		} else { 
		    //横屏时 
			setContentView(R.layout.grid_animation_h);
		} 
        
        setTitle(this.getResources().getString(R.string.app_name));  
        initAll();
        //生成动态数组，并且转入数据   
        ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();   
          HashMap<String, Object> zhichu = new HashMap<String, Object>();   
          zhichu.put("ItemImage", R.drawable.zhichu72_72);//添加图像资源的ID   
          zhichu.put("ItemText", zhichuText);//按序号做ItemText   
          lstImageItem.add(zhichu);  
          
          HashMap<String, Object> shouru = new HashMap<String, Object>();
          shouru.put("ItemImage", R.drawable.shouru72_72);//添加图像资源的ID   
          shouru.put("ItemText", shouruText);//按序号做ItemText   
          lstImageItem.add(shouru); 
          
          HashMap<String, Object> chaxun = new HashMap<String, Object>();
          chaxun.put("ItemImage", R.drawable.search72_72);//添加图像资源的ID   
          chaxun.put("ItemText", mxcxText);//按序号做ItemText   
          lstImageItem.add(chaxun);  
          
          HashMap<String, Object> sezhi = new HashMap<String, Object>();
          sezhi.put("ItemImage", R.drawable.set72_72);//添加图像资源的ID   
          sezhi.put("ItemText", xtszText);//按序号做ItemText   
          lstImageItem.add(sezhi);  
          
          HashMap<String, Object> guanyu = new HashMap<String, Object>();
          guanyu.put("ItemImage", R.drawable.about72_72);//添加图像资源的ID   
          guanyu.put("ItemText", guanyuText);//按序号做ItemText   
          lstImageItem.add(guanyu); 
          
          HashMap<String, Object> tuichu = new HashMap<String, Object>();
          tuichu.put("ItemImage", R.drawable.logout72_72);//添加图像资源的ID   
          tuichu.put("ItemText", tuichuText);//按序号做ItemText   
          lstImageItem.add(tuichu); 

        //生成适配器的ImageItem <====> 动态数组的元素，两者一一对应   
        saImageItems = new SimpleAdapter(this, //没什么解释   
              lstImageItem,//数据来源    
              R.layout.grid_animation_item,//night_item的XML实现
              //动态数组与ImageItem对应的子项           
              new String[] {"ItemImage","ItemText"},
              //ImageItem的XML文件里面的一个ImageView,两个TextView ID   
              new int[] {R.id.ItemImage,R.id.ItemText});   
        //添加并且显示   
        grid.setAdapter(saImageItems);   
        //添加消息处理   
        grid.setOnItemClickListener(new OnItemClickListener(){
        	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        		//在本例中arg2=arg3      
        	    HashMap<String, Object> item = (HashMap<String, Object>) arg0.getItemAtPosition(arg2);      
        	    //显示所选Item的ItemText      
        	    if((String)item.get("ItemText") == zhichuText) {
        	    	Intent intent = new Intent();
        	    	intent.setClass(GridAnimation.this, znasy2k.gmail.com.c.Zhichu.class);
        			startActivity(intent);
            	}
        	    
        	    if((String)item.get("ItemText") == shouruText) {
        	    	Intent intent = new Intent();
        	    	intent.setClass(GridAnimation.this, znasy2k.gmail.com.c.Shouru.class);
        			startActivity(intent);
            	}
        	    
        	    if((String)item.get("ItemText") == mxcxText) {
        	    	Intent intent = new Intent();
        	    	intent.setClass(GridAnimation.this, znasy2k.gmail.com.c.Mxcx.class);
        			startActivity(intent);
            	}
        	    
        	    if((String)item.get("ItemText") == xtszText) {
        	    	Intent intent = new Intent();
        	    	intent.setClass(GridAnimation.this, znasy2k.gmail.com.c.Xtsz.class);
        			startActivity(intent);
            	}
        	    
        	    if((String)item.get("ItemText") == guanyuText) {
        	    	Intent intent = new Intent();
        	    	intent.setClass(GridAnimation.this, znasy2k.gmail.com.c.Guanyu.class);
        			startActivity(intent);
            	}
        	    
        	    if((String)item.get("ItemText") == tuichuText) {
        	    	//GridAnimation.this.finish();
        	    	onDestroygd();
            	}        	    
        	}
        });
        grid.setOnItemSelectedListener(new OnItemSelectedListener(){
        	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
        			long arg3) {
				setLastColorBlack();//重置上次颜色为Color.BLACK
        		TextView ItemText = (TextView)arg1.findViewById(R.id.ItemText);
        		ItemText.setTextColor(Color.WHITE);
				last = arg2;//保存最新的上次ID
        	}

        	public void onNothingSelected(AdapterView<?> arg0) {}
        }); 
    }
    
    public void setLastColorBlack(){
    	TextView lastText = (TextView)grid.getChildAt(last).findViewById(R.id.ItemText);
    	lastText.setTextColor(Color.BLACK);
    }
    
    public void initAll() {
    	grid = (GridView) findViewById(R.id.grid);
    	zhichuText = this.getResources().getString(R.string.zhichu);
    	shouruText = this.getResources().getString(R.string.shouru);
    	mxcxText = this.getResources().getString(R.string.mxcx);
    	xtszText = this.getResources().getString(R.string.xtsz);
    	guanyuText = this.getResources().getString(R.string.guanyu);
    	tuichuText = this.getResources().getString(R.string.tuichu);
    }
    
    public void onDestroygd() {
    	ActivityManager activityMgr = (ActivityManager)this.getSystemService(ACTIVITY_SERVICE);
    	activityMgr.restartPackage(getPackageName());
	}

}