package com.braunster.chatsdk.dao;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

import com.braunster.chatsdk.dao.BUserAccount;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table BUSER_ACCOUNT.
*/
public class BUserAccountDao extends AbstractDao<BUserAccount, Long> {

    public static final String TABLENAME = "BUSER_ACCOUNT";

    /**
     * Properties of entity BUserAccount.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Token = new Property(1, String.class, "Token", false, "TOKEN");
        public final static Property Type = new Property(2, Integer.class, "type", false, "TYPE");
        public final static Property User = new Property(3, Long.class, "user", false, "USER");
    };

    private DaoSession daoSession;

    private Query<BUserAccount> bUser_BLinkedAccountsQuery;

    public BUserAccountDao(DaoConfig config) {
        super(config);
    }
    
    public BUserAccountDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'BUSER_ACCOUNT' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'TOKEN' TEXT," + // 1: Token
                "'TYPE' INTEGER," + // 2: type
                "'USER' INTEGER);"); // 3: user
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'BUSER_ACCOUNT'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, BUserAccount entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String Token = entity.getToken();
        if (Token != null) {
            stmt.bindString(2, Token);
        }
 
        Integer type = entity.getType();
        if (type != null) {
            stmt.bindLong(3, type);
        }
 
        Long user = entity.getUser();
        if (user != null) {
            stmt.bindLong(4, user);
        }
    }

    @Override
    protected void attachEntity(BUserAccount entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public BUserAccount readEntity(Cursor cursor, int offset) {
        BUserAccount entity = new BUserAccount( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // Token
            cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // type
            cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3) // user
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, BUserAccount entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setToken(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setType(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setUser(cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(BUserAccount entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(BUserAccount entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "BLinkedAccounts" to-many relationship of BUser. */
    public List<BUserAccount> _queryBUser_BLinkedAccounts(Long user) {
        synchronized (this) {
            if (bUser_BLinkedAccountsQuery == null) {
                QueryBuilder<BUserAccount> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.User.eq(null));
                bUser_BLinkedAccountsQuery = queryBuilder.build();
            }
        }
        Query<BUserAccount> query = bUser_BLinkedAccountsQuery.forCurrentThread();
        query.setParameter(0, user);
        return query.list();
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getBUserDao().getAllColumns());
            builder.append(" FROM BUSER_ACCOUNT T");
            builder.append(" LEFT JOIN BUSER T0 ON T.'USER'=T0.'_id'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected BUserAccount loadCurrentDeep(Cursor cursor, boolean lock) {
        BUserAccount entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        BUser bUser = loadCurrentOther(daoSession.getBUserDao(), cursor, offset);
        entity.setBUser(bUser);

        return entity;    
    }

    public BUserAccount loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<BUserAccount> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<BUserAccount> list = new ArrayList<BUserAccount>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<BUserAccount> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<BUserAccount> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}