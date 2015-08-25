package znasy2k.gmail.com.util;

import znasy2k.gmail.com.provider.Sjjzb;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class SjjzbProvider extends ContentProvider {
    private static final int SHOUZHI_Table = 1;
    private static final int LEIXIN_Table = 2;
    private static final int YONGHU_Table = 3;
    private static final int SHOUZHI_Table_ID = 11;
    private static final int LEIXIN_Table_ID = 22;
    private static final int YONGHU_Table_ID = 33;   
    
    private static final UriMatcher uriMatcher;      
	private DatabaseHelper mOpenHelper;
    private SQLiteDatabase sjjzbDB;  

    static{   
       uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);   
       uriMatcher.addURI(Sjjzb.AUTHORITY, Constant.SHOUZHI, SHOUZHI_Table);   
       uriMatcher.addURI(Sjjzb.AUTHORITY, Constant.LEIXIN, LEIXIN_Table);  
       uriMatcher.addURI(Sjjzb.AUTHORITY, Constant.YONGHU, YONGHU_Table);  
       
       uriMatcher.addURI(Sjjzb.AUTHORITY, Constant.SHOUZHI + "/#", SHOUZHI_Table_ID);   
       uriMatcher.addURI(Sjjzb.AUTHORITY, Constant.LEIXIN + "/#", LEIXIN_Table_ID); 
       uriMatcher.addURI(Sjjzb.AUTHORITY, Constant.YONGHU + "/#", YONGHU_Table_ID); 
    }  

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, Constant.DB_NAME, null, Constant.DB_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
        	//判定数据库是否存在
        	String[] col = {"type", "name" };
        	Cursor c = db.query("sqlite_master", col, "name='" + Constant.SHOUZHI + "'", null, null, null, null);
        	
        	int n = c.getCount();
        	if (n == 0){
        		//收支表
            	db.execSQL("CREATE TABLE " + Constant.SHOUZHI + " ("
    	                + "_id integer primary key autoincrement,"
    	                + "jine text not null,"
    	                + "riqi text not null,"
    	                + "leibie integer not null,"//1支出2收入
    	                + "leixin integer not null,"//leixin表id
    	                + "zhaiyao text,"  
    	                + "zbId integer null,"//账本id
    	                + "userId integer null"//用户id
    	                + ");");
            	
//            	for(int i = 0; i < 10;i++ ) {
//            		db.execSQL("insert into " + Constant.SHOUZHI + " values (null,0.02,'2010-08-11',1,4,'日常吃喝日常吃喝',null,1)");
//                	db.execSQL("insert into " + Constant.SHOUZHI + " values (null,299.68,'2010-08-11',1,5,'购物',null,1)");
//                	db.execSQL("insert into " + Constant.SHOUZHI + " values (null,2090.32,'2010-08-11',1,6,'购物',null,1)");
//                	db.execSQL("insert into " + Constant.SHOUZHI + " values (null,599,'2010-08-01',1,4,'购物',null,1)");
//                	db.execSQL("insert into " + Constant.SHOUZHI + " values (null,209.99,'2010-08-23',2,7,'工资',null,1)");
//                	db.execSQL("insert into " + Constant.SHOUZHI + " values (null,9909,'2010-05-01',2,8,'奖金',null,1)");
//            	}
            	
            	//类型表
            	db.execSQL("CREATE TABLE " + Constant.LEIXIN + " (" 
        				+ "_id integer primary key autoincrement," 
        		        + "mingcheng varchar(60) not null, " 
        		        + "leibie integer not null,"//1支出2收入3默认全部选项4全部收入类型5全部支出类型
        		        + "zhaiyao text null"
        		        + ");");
            	
            	db.execSQL("insert into " + Constant.LEIXIN + " values (null, '全部类型', 3, '默认选择全部')");
            	db.execSQL("insert into " + Constant.LEIXIN + " values (null, '全部收入类型', 4, '全部收入类型')");
            	db.execSQL("insert into " + Constant.LEIXIN + " values (null, '全部支出类型', 5, '全部支出类型')");
//            	db.execSQL("insert into " + Constant.LEIXIN + " values (null, '饮食', 1, '日常吃喝')");
//            	db.execSQL("insert into " + Constant.LEIXIN + " values (null, '购物', 1, '购物')");
//            	db.execSQL("insert into " + Constant.LEIXIN + " values (null, '娱乐', 1, '娱乐')");
//            	db.execSQL("insert into " + Constant.LEIXIN + " values (null, '工资', 2, '每月')");
//            	db.execSQL("insert into " + Constant.LEIXIN + " values (null, '奖金', 2, '每月')");
//            	db.execSQL("insert into " + Constant.LEIXIN + " values (null, '外快', 2, '每月')");
//            	for(int i = 0; i < 20;i++ ) {
//            		db.execSQL("insert into " + Constant.LEIXIN + " values (null, '饮食', 1, '日常吃喝')");
//                	db.execSQL("insert into " + Constant.LEIXIN + " values (null, '购物', 1, '购物')");
//                	db.execSQL("insert into " + Constant.LEIXIN + " values (null, '娱乐', 1, '娱乐')");
//                	db.execSQL("insert into " + Constant.LEIXIN + " values (null, '工资', 2, '每月')");
//                	db.execSQL("insert into " + Constant.LEIXIN + " values (null, '奖金', 2, '每月')");
//                	db.execSQL("insert into " + Constant.LEIXIN + " values (null, '外快', 2, '每月')");
//            	}
            	//用户表
            	db.execSQL("CREATE TABLE " + Constant.YONGHU + " (" 
        				+ "_id integer primary key autoincrement," 
        		        + "yonghuming varchar(60) not null, " 
        		        + "mima varchar(60) null,"
        		        + "beizhu text null"
        		        + ");");
            	db.execSQL("insert into " + Constant.YONGHU + " values (null,'admin','','defaultUser')");
        	}
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS shouzhi");
            db.execSQL("DROP TABLE IF EXISTS leixin");
            db.execSQL("DROP TABLE IF EXISTS yonghu");
            onCreate(db);
        }
    }

	public int delete(Uri arg0, String arg1, String[] arg2) {
        int count;
        switch (uriMatcher.match(arg0)) {
        case SHOUZHI_Table: {
            count = sjjzbDB.delete(Constant.SHOUZHI, arg1, arg2);
            break;
            }
        case SHOUZHI_Table_ID: {
            String shouzhiId = arg0.getPathSegments().get(1);
            count = sjjzbDB.delete(Constant.SHOUZHI, Sjjzb.SjjzbColumns._ID + "=" + shouzhiId
                    + (!TextUtils.isEmpty(arg1) ? " AND (" + arg1 + ')' : ""), arg2);
            break;
            }
        case LEIXIN_Table: {
            count = sjjzbDB.delete(Constant.LEIXIN, arg1, arg2);
            break;
            }
        case LEIXIN_Table_ID: {
            String leixinId = arg0.getPathSegments().get(1); 
            count = sjjzbDB.delete(Constant.LEIXIN, Sjjzb.SjjzbColumns._ID + "=" + leixinId
                    + (!TextUtils.isEmpty(arg1) ? " AND (" + arg1 + ')' : ""), arg2);
            break;
            }
        default:
            throw new IllegalArgumentException("Unknown URI " + arg0);
        }

        getContext().getContentResolver().notifyChange(arg0, null);
        return count;
	}

	public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
        case SHOUZHI_Table:
            return Sjjzb.SjjzbColumns.CONTENT_TYPE;
        case LEIXIN_Table:
            return Sjjzb.SjjzbColumns.CONTENT_TYPE;
        case YONGHU_Table:
            return Sjjzb.SjjzbColumns.CONTENT_TYPE;
            
        case SHOUZHI_Table_ID:
            return Sjjzb.SjjzbColumns.CONTENT_ITEM_TYPE;
        case LEIXIN_Table_ID:
            return Sjjzb.SjjzbColumns.CONTENT_ITEM_TYPE;     
        case YONGHU_Table_ID:
            return Sjjzb.SjjzbColumns.CONTENT_ITEM_TYPE;
        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
	}

	public Uri insert(Uri uri, ContentValues initialValues) {
        ContentValues values;
        if (initialValues != null) {
            values = new ContentValues(initialValues);
        } else {
            values = new ContentValues();
        }
        switch (uriMatcher.match(uri)) {
        case SHOUZHI_Table: {
	        	long rowId = sjjzbDB.insert(Constant.SHOUZHI, "", values);
	            if (rowId > 0) {
	                Uri sjjzbUri = ContentUris.withAppendedId(Sjjzb.SjjzbColumns.CONTENT_URI_SHOUZHI, rowId);
	                getContext().getContentResolver().notifyChange(sjjzbUri, null);
	                return sjjzbUri;
	            }
            }
        case LEIXIN_Table: {
	        	long rowId = sjjzbDB.insert(Constant.LEIXIN, "", values);
	            if (rowId > 0) {
	                Uri sjjzbUri = ContentUris.withAppendedId(Sjjzb.SjjzbColumns.CONTENT_URI_LEIXIN, rowId);
	                getContext().getContentResolver().notifyChange(sjjzbUri, null);
	                return sjjzbUri;
	            }
    
        	}
        }
        
        throw new SQLException("Failed to insert row into " + uri);
	}
    
	public boolean onCreate() {
		mOpenHelper = new DatabaseHelper(getContext());
		sjjzbDB = mOpenHelper.getWritableDatabase();  
		return (sjjzbDB == null)? false:true;  
	}

	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		if ((uriMatcher.match(uri) == SHOUZHI_Table) || (uriMatcher.match(uri) == SHOUZHI_Table_ID)) {
			qb.setTables(Constant.SHOUZHI + " , " + Constant.LEIXIN);
		}
		if ((uriMatcher.match(uri) == LEIXIN_Table) || (uriMatcher.match(uri) == LEIXIN_Table_ID)) {
			qb.setTables(Constant.LEIXIN);
		}
		if ((uriMatcher.match(uri) == YONGHU_Table) || (uriMatcher.match(uri) == YONGHU_Table_ID)) {
			qb.setTables(Constant.YONGHU);
		}
			          
		String orderBy = "";
        if (TextUtils.isEmpty(sortOrder)) {
            orderBy = Sjjzb.SjjzbColumns.DEFAULT_RIQI_SORT_ORDER;
        } else {
            orderBy = sortOrder;
        }
	  
        Cursor c = qb.query(sjjzbDB, projection, selection, selectionArgs, null, null, sortOrder);   
	    c.setNotificationUri(getContext().getContentResolver(), uri);   
	    return c;  
	}

	public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
		 int count = 0;
		 switch (uriMatcher.match(uri)) {
	        case SHOUZHI_Table: {
	        	count = sjjzbDB.update(Constant.SHOUZHI, values, where, whereArgs);
	            break; 
	            }
	        case SHOUZHI_Table_ID: {
	        	String shouzhiId = uri.getPathSegments().get(1);
	            count = sjjzbDB.update(Constant.SHOUZHI, values, Sjjzb.SjjzbColumns._ID + "=" + shouzhiId
	                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
	            break; 
	            }
	        case LEIXIN_Table: {
	        	count = sjjzbDB.update(Constant.LEIXIN, values, where, whereArgs);
	            break; 
	            }
	        case LEIXIN_Table_ID: {
	        	String leixinId = uri.getPathSegments().get(1);
	            count = sjjzbDB.update(Constant.LEIXIN, values, Sjjzb.SjjzbColumns._ID + "=" + leixinId
	                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
	            break; 
	            }
	        case YONGHU_Table: {
	        	count = sjjzbDB.update(Constant.YONGHU, values, where, whereArgs);
	            break; 
	            }
	        case YONGHU_Table_ID: {
	        	String leixinId = uri.getPathSegments().get(1);
	            count = sjjzbDB.update(Constant.YONGHU, values, Sjjzb.SjjzbColumns._ID + "=" + leixinId
	                    + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
	            break; 
	            }
	        default:
	            throw new IllegalArgumentException("Unknown URI " + uri);
	        }
		
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

}
