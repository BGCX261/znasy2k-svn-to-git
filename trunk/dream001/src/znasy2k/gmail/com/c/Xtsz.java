package znasy2k.gmail.com.c;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class Xtsz extends Activity {
	private GridView grid;
	private SimpleAdapter saImageItems;
	private int last = 0;//标记：上次的ID
	private String fhzcdText = "";
	private String srleibText = "";
	private String zcleibText = ""; 
	private String pwdText = ""; 
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_animation);
        setTitle(this.getResources().getString(R.string.app_name) + "-" + this.getResources().getString(R.string.xtsz));  
        initAll();
        //生成动态数组，并且转入数据   
        ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();   
          HashMap<String, Object> fanhui = new HashMap<String, Object>();   
          fanhui.put("ItemImage", R.drawable.home72_72);//添加图像资源的ID   
          fanhui.put("ItemText", fhzcdText);//按序号做ItemText   
          lstImageItem.add(fanhui); 
          
          HashMap<String, Object> zhichu = new HashMap<String, Object>();
          zhichu.put("ItemImage", R.drawable.zhichu72_72);//添加图像资源的ID   
          zhichu.put("ItemText", zcleibText);//按序号做ItemText   
          lstImageItem.add(zhichu);  

          HashMap<String, Object> shouru = new HashMap<String, Object>();
          shouru.put("ItemImage", R.drawable.shouru72_72);//添加图像资源的ID   
          shouru.put("ItemText", srleibText);//按序号做ItemText   
          lstImageItem.add(shouru); 
          
          HashMap<String, Object> pwd = new HashMap<String, Object>();
          pwd.put("ItemImage", R.drawable.pwd72_72);//添加图像资源的ID   
          pwd.put("ItemText", pwdText);//按序号做ItemText   
          lstImageItem.add(pwd);  

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
        	    	Intent intent = new Intent();
        	    	intent.setClass(Xtsz.this, znasy2k.gmail.com.c.GridAnimation.class);
        			startActivity(intent);
        			Xtsz.this.finish();
            	}
        	    
        	    if((String)item.get("ItemText") == srleibText) {
        	    	Intent intent = new Intent();
        	    	intent.setClass(Xtsz.this, znasy2k.gmail.com.c.SrLeiB.class);
        			startActivity(intent);
        			Xtsz.this.finish();
            	}
        	    
        	    if((String)item.get("ItemText") == zcleibText) {
        	    	Intent intent = new Intent();
        	    	intent.setClass(Xtsz.this, znasy2k.gmail.com.c.ZcLeiB.class);
        			startActivity(intent);
        			Xtsz.this.finish();
            	}
        	    
        	    if((String)item.get("ItemText") == pwdText) {
        	    	Intent intent = new Intent();
        	    	intent.setClass(Xtsz.this, znasy2k.gmail.com.c.PwdSet.class);
        			startActivity(intent);
        			Xtsz.this.finish();
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
		Log.d("setLastColorBlack","Black: "+ grid.getChildAt(last).findViewById(R.id.ItemText)); 
    	TextView lastText = (TextView)grid.getChildAt(last).findViewById(R.id.ItemText);
    	lastText.setTextColor(Color.BLACK);
    }
    
    public void initAll() {
    	grid = (GridView) findViewById(R.id.grid);
    	fhzcdText = this.getResources().getString(R.string.fhzcd);
    	srleibText = this.getResources().getString(R.string.shouru_leibie);
    	zcleibText = this.getResources().getString(R.string.zhichu_leibie);
    	pwdText = this.getResources().getString(R.string.pwdmodif);
    }
}