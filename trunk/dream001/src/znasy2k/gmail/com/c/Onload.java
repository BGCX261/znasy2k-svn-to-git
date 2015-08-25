package znasy2k.gmail.com.c;

import java.util.Timer;
import java.util.TimerTask;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.ImageView;

public class Onload extends Activity {
	private Handler mHandler = new Handler();
	private TimerTask task ;
	
    public void onConfigurationChanged(Configuration newConfig) { 
        super.onConfigurationChanged(newConfig); 
    } 
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (this.getResources().getConfiguration().orientation == 
            Configuration.ORIENTATION_PORTRAIT) {   
        	//竖屏 
        	setContentView(R.layout.onload);
		} else { 
		    //横屏时 
			setContentView(R.layout.onload_h);
		} 
        
        //final ImageView loadingImage = (ImageView) findViewById(R.id.logo);
        //Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this, R.anim.loadalpha);
        //loadingImage.startAnimation(hyperspaceJumpAnimation);

        new Thread(new Runnable() {
			public void run() {
				FirstStart(); 
			}
		}).start();

		mHandler = new Handler() {
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				StartIntent();
			}
		};
		
		task = new TimerTask(){   
			  
	        public void run() {       
	            mHandler.sendMessage(mHandler.obtainMessage());     
	        }       
	    };  
    }
    //判定数据库是否存在
    public void FirstStart(){
    	Timer timer = new Timer();   
    	timer.schedule(task, 2000);   
    }

    public void StartIntent() {
    	Intent intent = new Intent();
    	intent.setClass(Onload.this, znasy2k.gmail.com.c.Login.class);
		startActivity(intent);
		Onload.this.finish();  		
    }
 
}