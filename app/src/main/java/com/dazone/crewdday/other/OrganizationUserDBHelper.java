package com.dazone.crewdday.other;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dazone.crewdday.*;
import com.dazone.crewdday.adapter.ob_belongs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sherry on 12/29/15.
 */
public class OrganizationUserDBHelper {
    public static final String TAG = "OrganizationUserDBHelper";
    public static final String TABLE_NAME = "organization_tbl";
    public static final String ID = "Id";
    public static final String SERVER_SITE_ID = "server_site_id"; // foreign key
    public static final String ORGANIZATION_USER_NO = "organization_user_no";
    public static final String ORGANIZATION_NAME = "organization_name";
    public static final String ORGANIZATION_NAME_EN = "organization_name_en";
    public static final String ORGANIZATION_NAME_DEFAULT = "organization_name_default";
    public static final String ORGANIZATION_EMAIL = "organization_email"; // null able if ORGANIZATION_TYPE = 1
    public static final String ORGANIZATION_AVATAR = "organization_avatar";
    public static final String ORGANIZATION_DEPART_NO = "organization_depart_no";
    public static final String ORGANIZATION_DEPART_PARENT_NO = "organization_depart_parent_no"; // use for department only ( ORGANIZATION_TYPE = 1)
    public static final String ORGANIZATION_POSITION_NAME = "organization_position_name";
    public static final String ORGANIZATION_TYPE = "organization_type"; // 1 department , 2 : user
    public static final String ORGANIZATION_ENABLE = "organization_enable"; // 1 = true  , 0 = false
    public static final String ORGANIZATION_SORT_NO = "organization_sort_no";


    public static final String SQL_EXCUTE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
            + ID + " integer primary key autoincrement not null," + SERVER_SITE_ID + " integer default 0," + ORGANIZATION_USER_NO
            + " integer default 0, " + ORGANIZATION_DEPART_NO + " integer default 0, "
            + ORGANIZATION_DEPART_PARENT_NO + " integer default 0," + ORGANIZATION_TYPE + " integer default 0, "
            + ORGANIZATION_NAME + " text," + ORGANIZATION_NAME_EN + " text,"
            + ORGANIZATION_NAME_DEFAULT + " text, " + ORGANIZATION_EMAIL + " text,"
            + ORGANIZATION_AVATAR + " text, " + ORGANIZATION_POSITION_NAME + " text,"
            + ORGANIZATION_ENABLE + " integer default 1,"
            + ORGANIZATION_SORT_NO + " integer default 0,"
            + " FOREIGN KEY (" + SERVER_SITE_ID + ") REFERENCES " + ServerSiteDBHelper.TABLE_NAME + "(" + ServerSiteDBHelper.ID + "));";


    public static boolean insertItem(PersonData dto, int serverSiteId) {

        try {
            ContentResolver resolver = _Application.getInstance()
                    .getApplicationContext().getContentResolver();
            resolver.insert(AppContentProvider.GET_SERVER_ORGANIZATION_USER_URI, addvalue(dto, serverSiteId));
            return true;
        } catch (Exception e) {
            if (BuildConfig.DEBUG)
                e.printStackTrace();
        }
        return false;
    }

    public static ArrayList<PersonData> getAllOfOrganization(String serverLink) {
        int serverSiteId = ServerSiteDBHelper.getServerSiteId(serverLink);
        String where = SERVER_SITE_ID + " = ?";
        String arg[] = new String[]{serverSiteId + ""};
        return getList(where, arg);
    }

    public static ArrayList<PersonData> getDepartments(String serverLink) {
        int serverSiteId = ServerSiteDBHelper.getServerSiteId(serverLink);
        String where = ORGANIZATION_TYPE + " = 1 AND" + SERVER_SITE_ID + " = ?";
        String arg[] = new String[]{serverSiteId + ""};
        return getList(where, arg);
    }

    public static ArrayList<PersonData> getDepartmentUsers(String serverLink) {
        int serverSiteId = ServerSiteDBHelper.getServerSiteId(serverLink);
        String where = ORGANIZATION_TYPE + " = 2 AND" + SERVER_SITE_ID + " = ?";
        String arg[] = new String[]{serverSiteId + ""};
        return getList(where, arg);
    }

    private static ArrayList<PersonData> getList(String where, String[] params) {
        String[] columns = new String[]{"*"};
        ContentResolver resolver = _Application.getInstance()
                .getApplicationContext().getContentResolver();
        ArrayList<PersonData> arrayList = new ArrayList<>();
        Cursor cursor = resolver.query(
                AppContentProvider.GET_SERVER_ORGANIZATION_USER_URI, columns, where,
                params, null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                try {

                    while (!cursor.isLast()) {
                        cursor.moveToNext();
                        arrayList.add(getValue(cursor));
                    }
                } finally {
                    cursor.close();
                }
            }
        }
        return arrayList;
    }

    public static synchronized boolean updateItem(PersonData dto, int id, int serverSiteId) {
        try {
            ContentResolver resolver = _Application.getInstance()
                    .getApplicationContext().getContentResolver();
            resolver.update(AppContentProvider.GET_SERVER_ORGANIZATION_USER_URI, addvalue(dto, serverSiteId), ID + "=?", new String[]{id + ""});
            return true;
        } catch (Exception e) {
            if (BuildConfig.DEBUG)
                e.printStackTrace();
        }
        return false;
    }

    public static synchronized void addTreeUser(List<PersonData> dtos, int serverSiteId) {
        if (dtos != null && dtos.size() != 0) {
//            clearData();

            for (int i = 0; i < dtos.size(); i++) {
                PersonData dto = dtos.get(i);
//                int id = hasData(dto, serverSiteId);
                insertItem(dto, serverSiteId);
//                if (id == 0) {
//                    insertItem(dto, serverSiteId);
//                } else {
//                    //updateItem(dto,id,serverSiteId);
//                }
                if (dto.getPersonList() != null && dto.getPersonList().size() > 0) {
                    addTreeUser(dto.getPersonList(), serverSiteId);
                }
            }
//            for (PersonData dto : dtos) {
//                int id = hasData(dto, serverSiteId);
//                if (id == 0) {
//                    insertItem(dto, serverSiteId);
//                } else {
//                    //updateItem(dto,id,serverSiteId);
//                }
//                if (dto.getPersonList() != null && dto.getPersonList().size() > 0) {
//                    addTreeUser(dto.getPersonList(), serverSiteId);
//                }
//            }
        }
    }

    private static synchronized ContentValues addvalue(PersonData dto, int serverSiteId) {
        ContentValues values = new ContentValues();
        values.put(SERVER_SITE_ID, serverSiteId);
        values.put(ORGANIZATION_USER_NO, dto.getUserNo());
        values.put(ORGANIZATION_NAME, dto.getFullName());
        values.put(ORGANIZATION_NAME_EN, dto.getNameEn());
        values.put(ORGANIZATION_NAME_DEFAULT, dto.getNameDefault());
        values.put(ORGANIZATION_EMAIL, dto.getmEmail());
        values.put(ORGANIZATION_AVATAR, dto.getUrlAvatar());
        values.put(ORGANIZATION_DEPART_NO, dto.getDepartNo());
        values.put(ORGANIZATION_POSITION_NAME, dto.getPositionName());
        values.put(ORGANIZATION_TYPE, dto.getType());
        values.put(ORGANIZATION_ENABLE, (dto.isEnabled()) ? 1 : 0);
        values.put(ORGANIZATION_SORT_NO, dto.getSortNo());
        values.put(ORGANIZATION_DEPART_PARENT_NO, dto.getDepartmentParentNo());
//        Log.e(TAG,dto.getFullName()+":"+dto.getDepartNo());
        return values;
    }

    private static PersonData getValue(Cursor cursor) {
        PersonData data = new PersonData();
        data.setUserNo(cursor.getInt(cursor.getColumnIndex(ORGANIZATION_USER_NO)));
        data.setFullName(cursor.getString(cursor.getColumnIndex(ORGANIZATION_NAME)));
//        Log.e("getValue",cursor.getString(cursor.getColumnIndex(ORGANIZATION_NAME_EN)));
        data.setNameEn(cursor.getString(cursor.getColumnIndex(ORGANIZATION_NAME_EN)));
        data.setNameDefault(cursor.getString(cursor.getColumnIndex(ORGANIZATION_NAME_DEFAULT)));
        data.setmEmail(cursor.getString(cursor.getColumnIndex(ORGANIZATION_EMAIL)));
        data.setUrlAvatar(cursor.getString(cursor.getColumnIndex(ORGANIZATION_AVATAR)));
        data.setDepartNo(cursor.getInt(cursor.getColumnIndex(ORGANIZATION_DEPART_NO)));
        data.setPositionName(cursor.getString(cursor.getColumnIndex(ORGANIZATION_POSITION_NAME)));
        data.setType(cursor.getInt(cursor.getColumnIndex(ORGANIZATION_TYPE)));
        int enable = cursor.getInt(cursor.getColumnIndex(ORGANIZATION_ENABLE));
        data.setIsEnabled((enable == 1));
        data.setSortNo(cursor.getInt(cursor.getColumnIndex(ORGANIZATION_SORT_NO)));
        data.setDepartmentParentNo(cursor.getInt(cursor.getColumnIndex(ORGANIZATION_DEPART_PARENT_NO)));

        return data;
    }

    private static synchronized int hasData(PersonData person, int serverSiteId) {

        String[] columns = new String[]{ID};
        String where = ORGANIZATION_USER_NO + " = ? AND " + SERVER_SITE_ID + " = ? AND " + ORGANIZATION_TYPE + " = 2";
        String arg[] = new String[]{person.getUserNo() + "", serverSiteId + ""};
        if (person.getType() == 1) { // category
            where = ORGANIZATION_DEPART_NO + " = ? AND " + SERVER_SITE_ID + " = ? AND " + ORGANIZATION_TYPE + " = 1";
            arg[0] = person.getDepartNo() + "";
        }
        ContentResolver resolver = _Application.getInstance()
                .getApplicationContext().getContentResolver();
        Cursor cursor = resolver.query(
                AppContentProvider.GET_SERVER_ORGANIZATION_USER_URI, columns, where,
                arg, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                try {
                    if (cursor.moveToFirst()) {
                        return cursor.getInt(cursor.getColumnIndex(ID));
                    }
                } finally {
                    cursor.close();
                }
            }
        }
        return 0;

    }

    public static boolean clearData() {
        try {
//            ContentValues value = new ContentValues();
            ContentResolver resolver = _Application.getInstance()
                    .getApplicationContext().getContentResolver();
            resolver.delete(AppContentProvider.GET_SERVER_ORGANIZATION_USER_URI, null, null);

            return true;
        } catch (Exception e) {
            // TODO: handle exception
        }
        return false;
    }
}
