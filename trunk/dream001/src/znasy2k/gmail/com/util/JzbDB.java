package znasy2k.gmail.com.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class JzbDB extends SQLiteOpenHelper {
	public JzbDB(Context context) {
		super(context, Constant.DB_NAME, null, Constant.DB_VERSION);
	}

	public void onCreate(SQLiteDatabase db) {}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
 
    public void onOpen(SQLiteDatabase db) {   
        super.onOpen(db);    
    }   
}
