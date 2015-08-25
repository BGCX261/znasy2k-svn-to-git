package znasy2k.gmail.com.c;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
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

public class Guanyu extends Activity {
	private GridView grid;
	private SimpleAdapter saImageItems;
	private int last = 0;//标记：上次的ID
	private String fhzcdText = "";
    public void onConfigurationChanged(Configuration newConfig) { 
        super.onConfigurationChanged(newConfig); 
    } 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if (this.getResources().getConfiguration().orientation == 
            Configuration.ORIENTATION_PORTRAIT) {   
        	//竖屏 
        	setContentView(R.layout.guanyu);
		} else { 
		    //横屏时 
			setContentView(R.layout.guanyu_h);
		}         
        setTitle(this.getResources().getString(R.string.app_name) + "-" + this.getResources().getString(R.string.guanyu)); 

        initAll();
        //生成动态数组，并且转入数据   
        ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();   
          HashMap<String, Object> tuichu = new HashMap<String, Object>();
          tuichu.put("ItemImage", R.drawable.home72_72);//添加图像资源的ID   
          tuichu.put("ItemText", fhzcdText);//按序号做ItemText   
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
        	    
        	    if((String)item.get("ItemText") == fhzcdText) {
        	    	startActivity(new Intent(Guanyu.this, znasy2k.gmail.com.c.GridAnimation.class));
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
    	fhzcdText = this.getResources().getString(R.string.fhzcd);
    }
}