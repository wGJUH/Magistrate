package com.example.wgjuh.magistrateuiexamplefirsttest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by wGJUH on 16.01.2017.
 */

public class SqlWorker extends SQLiteOpenHelper{
    public static final String DB_LOCATION = "/data/data/" + BuildConfig.APPLICATION_ID + "/databases/";
    public static final String DB_NAME = "Students.db";
    public static final int DB_VERSION = 1;
    public static final String LOG_TAG = "MY_LOGGER";
    private Context context;
    private SQLiteDatabase database;
    private static SqlWorker instance;

    public static SqlWorker getInstance(Context context){
        if(instance != null)
            return instance;
        else instance = new SqlWorker(context);
        return instance;
    }


    private SqlWorker(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
        if (isDatabaseExists()) {
            System.out.println("Database exists");
            opendatabase();

           // checkVersion();
        } else {
            System.out.println("Database doesn't exist");
            try {
                createdatabase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void createdatabase() throws IOException {
        long start = System.currentTimeMillis();
        if (isDatabaseExists()) {
            System.out.println(" Database exists.");
        } else {
            this.getReadableDatabase();
            try {
                copydatabase();
            } catch (IOException e) {
                e.printStackTrace();
                throw new Error("Error copying database");
            }
        }
        System.out.println(" createDB method finished: " + (System.currentTimeMillis() - start));
    }
    private void copydatabase() throws IOException {
        //Open your local db as the input stream
        System.out.println(" START COPYING");
        InputStream myinput = null;
        try {
            myinput = context.getAssets().open(DB_NAME);
        } catch (IOException e) {
            System.out.println(" get from assets failed ");
            e.printStackTrace();
        }
        System.out.println(" assets: " + myinput.available());
        // Path to the just created empty db
        String outfilename = DB_LOCATION + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myoutput = new FileOutputStream(outfilename);

        // transfer byte to inputfile to outputfile
        byte[] buffer = new byte[myinput.available()];
        System.out.println(" BUFFER: " + buffer.length);
        int length;
        while ((length = myinput.read(buffer)) > 0) {
            myoutput.write(buffer, 0, length);
        }
        //Close the streams
        myoutput.flush();
        myoutput.close();
        myinput.close();
        opendatabase();
        database.setVersion(DB_VERSION);
        close();
    }
    public void opendatabase() throws SQLException {
        //Open the database
        String mypath = DB_LOCATION + DB_NAME;
        database = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);

    }
    public boolean isDatabaseExists() {
        boolean checkdb = false;
        try {
            String myPath = DB_LOCATION + DB_NAME;
            File dbfile = new File(myPath);
            checkdb = dbfile.exists();
        } catch (SQLiteException e) {
            System.out.println("Database doesn't exist");
        }
        return checkdb;    }

    public long addNewUser(String login, String password, int role, String name){
        long inserted = 0L;
        opendatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("account_name",login);
        contentValues.put("account_password",password);
        contentValues.put("account_role",role);
        contentValues.put("account_user_name",name);
        inserted = database.insert("users",null,contentValues);
        close();
        return inserted;
    }
    public int getGroupId(String name, int admin){
        int id = -7733;
        opendatabase();
            Cursor cursor = database.rawQuery("select id from groups where group_name = ? and group_admin = ?", new String[]{name,""+admin});
        if(cursor.moveToFirst())
            id = cursor.getInt(cursor.getColumnIndex("id"));
        cursor.close();
        close();
        return id;
    }
    public long addNewGroup(String group_name, Object[] ids, int id_administrator){
        long inserted = 0L;
        opendatabase();
        database.execSQL("insert into groups (group_name, group_admin) values (?,?)",new Object[]{group_name,id_administrator});
        int groupId = getGroupId(group_name,id_administrator);
        for (Object o:ids) {
            database.execSQL("insert into manage (account_id, group_id) values (?,?)",new Object[]{o,groupId});
        }
//        database.execSQL("insert into groups (group_name, group_admin) values (?,?)",new Object[]{group_name,id_administrator});

/*        ContentValues contentValues = new ContentValues();
        contentValues.put("gr",group_name);
        contentValues.put("account_password",ids.toString());
        contentValues.put("account_role",id_administrator);
        inserted = database.insert("users",null,contentValues);*/
        close();
        return inserted;
    }
    public int getUserId(String email){
        Integer id = -7733;
        opendatabase();
        Cursor cursor = database.rawQuery("select id from user where user_name = ?", new String[]{email});
        if (cursor.moveToFirst())
        id = cursor.getInt(cursor.getColumnIndex("id"));
        cursor.close();
        close();
        return id;
    }
    public long updateUserPassword(String password, String id){
        long updated = 0L;
        opendatabase();
        database.execSQL("update user set user_password = ? where id = ?", new String[]{password, id});
        close();
        return updated;
    }
    boolean isUserExist(String email, String password){
        opendatabase();
        String sqlQuery = "select user_name as name, user_password as password "
                +"from user "
                +"where name = ? AND password = ?";
        Cursor cursor = database.rawQuery(sqlQuery, new String[]{email, password});
        close();
        Boolean isExist = cursor.moveToFirst();
        cursor.close();
        return isExist;

    }

    public void executeSql(String sqlQuery, String[] args){
        System.out.println("LOGIN_TEST " + sqlQuery);
        opendatabase();
        Cursor cursor = database.rawQuery(sqlQuery, args);
        logCursor(cursor);
        cursor.close();
        close();
    }

    void logCursor(Cursor c) {
        if (c != null) {
            if (c.moveToFirst()) {
                String str;
                do {
                    str = "";
                    for (String cn : c.getColumnNames()) {
                        str = str.concat(cn + " = " + c.getString(c.getColumnIndex(cn)) + "; ");
                    }
                    Log.d(LOG_TAG, str);
                } while (c.moveToNext());
            }
        } else
            Log.d(LOG_TAG, "Cursor is null");
    }


    public boolean isEmailInBase(String email) {
        opendatabase();
        String sqlQuery = "select account_name "
                +"from users "
                +"where users.account_name = ?";
        Cursor cursor = database.rawQuery(sqlQuery, new String[]{email});
        close();
        Boolean isExist = cursor.moveToFirst();
        cursor.close();
        return isExist;
    }
    public boolean isUserInBase(String email, String password){
        opendatabase();
        String sqlQuery = "select account_name "
                +"from users "
                +"where users.account_name = ? and users.account_password = ?";
        Cursor cursor = database.rawQuery(sqlQuery, new String[]{email, password});
        close();
        Boolean isExist = cursor.moveToFirst();
        cursor.close();
        return isExist;
    }
    public int getUsersCountInGroup(int groupId){
        int count = -7733;
        opendatabase();
        Cursor cursor = database.rawQuery("select count(account_id) from manage where manage.group_id = ?", new String[]{""+groupId});
        if(cursor.moveToFirst())
            count = Integer.parseInt(cursor.getString(cursor.getColumnCount()-1));
        cursor.close();
        close();
        return count;
    }
    public ArrayList<String> getUsersFromGroup(int groupId){
        ArrayList<String>names = new ArrayList<>();
        opendatabase();
        Cursor cursor = database.rawQuery("select users.account_user_name from users left join manage on users.id = manage.account_id inner join groups on manage.group_id = groups.id where groups.id = ? ", new String[]{""+groupId});
        int i = 0;
        if(cursor.moveToFirst())
            do {
                names.add(cursor.getString(cursor.getColumnIndex("account_user_name")));
            }while (cursor.moveToNext());
        logCursor(cursor);
        close();
        return names;
    }

    public int getGroupsCount(int admin){
        int count = -7733;
        opendatabase();
        Cursor cursor = database.rawQuery("select count(id) from groups where group_admin = ?",new String[]{""+admin});
        if(cursor.moveToFirst())
            count = Integer.parseInt(cursor.getString(cursor.getColumnCount()-1));
        cursor.close();
        close();
        return count;
    }
    public int getGroupId(String groupName, String adminAcc){
        int id = -7733;
        opendatabase();
        Cursor cursor = database.rawQuery("select id from groups where group_name = ? and group_admin = ?", new String[]{groupName,adminAcc});
        if(cursor.moveToFirst())
            id = cursor.getInt(cursor.getColumnIndex("id"));
        cursor.close();
        close();
        return id;
    }
    public ArrayList<Group> getGroupsForAdminId(int id){
        ArrayList<Group> groups = new ArrayList<>();
        int group_id;
        String group_name;
        String account_name;
        opendatabase();
        Cursor cursor = database.rawQuery("select groups.id, group_name, account_name from groups left join users on groups.group_admin = users.id where groups.group_admin = ? ", new String[]{""+id});
        if(cursor.moveToFirst())
            do {
                group_id = cursor.getInt(cursor.getColumnIndex("id"));
                group_name = cursor.getString(cursor.getColumnIndex("group_name"));
                account_name = cursor.getString(cursor.getColumnIndex("account_name"));
                Log.d(SqlWorker.LOG_TAG," group_id: " + group_id + " group_name: " + group_name);
                groups.add(new Group(group_id,group_name,account_name));
            }while (cursor.moveToNext());
        cursor.close();
        close();
        return groups;
    }

    public ArrayList<User> getAllUsers(){
        ArrayList<User> users = new ArrayList<>();
        int id;
        String account_name;
        String account_user_name;
        opendatabase();
        Cursor cursor = database.rawQuery("select id, account_name, account_user_name from users",null);
        if(cursor.moveToFirst())
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"));
                account_name = cursor.getString(cursor.getColumnIndex("account_name"));
                account_user_name = cursor.getString(cursor.getColumnIndex("account_user_name"));
                users.add(new User(id,account_user_name,account_name));
            }while (cursor.moveToNext());
        cursor.close();
        close();
        return users;
    }


    public ArrayList<String> getQuestions(int theme){
        ArrayList<String> questions = new ArrayList<>();
        String sqlCmd = "select question from Questions where theme = ?";
        Cursor cursor = database.rawQuery(sqlCmd,new String[]{Integer.toString(theme)});
        if (cursor.moveToFirst())
            do{
                questions.add(cursor.getString(cursor.getColumnIndex("question")));
            }while (cursor.moveToNext());
        return questions;
    }
    public ArrayList<String> getThemes(int article){
        ArrayList<String> themes = new ArrayList<>();
        String sqlCmd = "select name from Themes where article = ?";
        Cursor cursor = database.rawQuery(sqlCmd,new String[]{Integer.toString(article)});
        if (cursor.moveToFirst())
            do{
                themes.add(cursor.getString(cursor.getColumnIndex("question")));
            }while (cursor.moveToNext());
        return themes;
    }
    public ArrayList<String> getArticles(){
        Log.d(UserActivity.TAG,"getArticles");
        ArrayList<String> articles = new ArrayList<>();
        String sqlCmd = "select name from articles";
        Cursor cursor = database.rawQuery(sqlCmd,null);
        if (cursor.moveToFirst())
            do{
                articles.add(cursor.getString(cursor.getColumnIndex("name")));
                Log.d(UserActivity.TAG,articles.get(articles.size()-1));
            }while (cursor.moveToNext());
        return articles;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
