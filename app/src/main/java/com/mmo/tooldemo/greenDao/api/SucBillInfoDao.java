package com.mmo.tooldemo.greenDao.api;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.mmo.tooldemo.greenDao.bean.SucBillInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SUC_BILL_INFO".
*/
public class SucBillInfoDao extends AbstractDao<SucBillInfo, Long> {

    public static final String TABLENAME = "SUC_BILL_INFO";

    /**
     * Properties of entity SucBillInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Bid = new Property(0, Long.class, "bid", true, "_id");
        public final static Property Time = new Property(1, String.class, "time", false, "TIME");
        public final static Property InternId = new Property(2, String.class, "internId", false, "INTERN_ID");
        public final static Property Type = new Property(3, String.class, "type", false, "TYPE");
        public final static Property Data = new Property(4, String.class, "data", false, "DATA");
        public final static Property ThirdtranNo = new Property(5, String.class, "thirdtranNo", false, "THIRDTRAN_NO");
        public final static Property ErrTime = new Property(6, String.class, "errTime", false, "ERR_TIME");
    }


    public SucBillInfoDao(DaoConfig config) {
        super(config);
    }
    
    public SucBillInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SUC_BILL_INFO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: bid
                "\"TIME\" TEXT," + // 1: time
                "\"INTERN_ID\" TEXT UNIQUE ," + // 2: internId
                "\"TYPE\" TEXT NOT NULL ," + // 3: type
                "\"DATA\" TEXT NOT NULL ," + // 4: data
                "\"THIRDTRAN_NO\" TEXT NOT NULL ," + // 5: thirdtranNo
                "\"ERR_TIME\" TEXT);"); // 6: errTime
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SUC_BILL_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, SucBillInfo entity) {
        stmt.clearBindings();
 
        Long bid = entity.getBid();
        if (bid != null) {
            stmt.bindLong(1, bid);
        }
 
        String time = entity.getTime();
        if (time != null) {
            stmt.bindString(2, time);
        }
 
        String internId = entity.getInternId();
        if (internId != null) {
            stmt.bindString(3, internId);
        }
        stmt.bindString(4, entity.getType());
        stmt.bindString(5, entity.getData());
        stmt.bindString(6, entity.getThirdtranNo());
 
        String errTime = entity.getErrTime();
        if (errTime != null) {
            stmt.bindString(7, errTime);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, SucBillInfo entity) {
        stmt.clearBindings();
 
        Long bid = entity.getBid();
        if (bid != null) {
            stmt.bindLong(1, bid);
        }
 
        String time = entity.getTime();
        if (time != null) {
            stmt.bindString(2, time);
        }
 
        String internId = entity.getInternId();
        if (internId != null) {
            stmt.bindString(3, internId);
        }
        stmt.bindString(4, entity.getType());
        stmt.bindString(5, entity.getData());
        stmt.bindString(6, entity.getThirdtranNo());
 
        String errTime = entity.getErrTime();
        if (errTime != null) {
            stmt.bindString(7, errTime);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public SucBillInfo readEntity(Cursor cursor, int offset) {
        SucBillInfo entity = new SucBillInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // bid
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // time
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // internId
            cursor.getString(offset + 3), // type
            cursor.getString(offset + 4), // data
            cursor.getString(offset + 5), // thirdtranNo
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6) // errTime
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, SucBillInfo entity, int offset) {
        entity.setBid(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTime(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setInternId(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setType(cursor.getString(offset + 3));
        entity.setData(cursor.getString(offset + 4));
        entity.setThirdtranNo(cursor.getString(offset + 5));
        entity.setErrTime(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(SucBillInfo entity, long rowId) {
        entity.setBid(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(SucBillInfo entity) {
        if(entity != null) {
            return entity.getBid();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(SucBillInfo entity) {
        return entity.getBid() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
