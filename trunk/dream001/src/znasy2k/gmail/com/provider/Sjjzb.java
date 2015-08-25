/* 
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package znasy2k.gmail.com.provider;

import znasy2k.gmail.com.util.Constant;
import android.net.Uri;
import android.provider.BaseColumns;

public final class Sjjzb {
    public static final String AUTHORITY = "znasy2k.gmail.com.provider.Sjjzb";
    private Sjjzb() {}

    public static final class SjjzbColumns implements BaseColumns {
    	private SjjzbColumns() {};
    	
    	public static final Uri CONTENT_URI_SHOUZHI = Uri.parse("content://" + AUTHORITY + "/" + Constant.SHOUZHI);
    	public static final Uri CONTENT_URI_LEIXIN = Uri.parse("content://" + AUTHORITY + "/" + Constant.LEIXIN);
    	public static final Uri CONTENT_URI_YONGHU = Uri.parse("content://" + AUTHORITY + "/" + Constant.YONGHU);    	
        public static final String DEFAULT_RIQI_SORT_ORDER = "riqi DESC";
        public static final String DEFAULT_ID_SORT_ORDER = "_ID ASC";
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.znasy2k.sjjzb";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.znasy2k.sjjzb";
        //shouzhi
        public static final String JINE = "jine";
        public static final String RIQI = "riqi";
        public static final String LEIBIE = "leibie";
        public static final String LEIXIN = "leixin";
        public static final String ZHAIYAO = "zhaiyao";
        public static final String ZBID = "zbId";
        public static final String USERID = "userId";
        //leixin
        public static final String MINGCHENG = "mingcheng";
        //yonghu
        public static final String YONGHUMING = "yonghuming";
        public static final String MIMA = "mima";
        public static final String BEIZHU = "beizhu";
    }
}
