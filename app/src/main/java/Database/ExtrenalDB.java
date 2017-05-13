package Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

import Model.InboxcModel;
import Model.NotifiactionModel;
import Model.ProjectModel;
import Model.TaskModel;
import Model.UserModel;

/**
 * Created by Babul Patel on 14/04/2017.
 */

public class ExtrenalDB extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "MehulDB.db";
    private static final int DATABASE_VERSION = 1;

    public ExtrenalDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public ArrayList<InboxcModel> getData() {

        ArrayList<InboxcModel> data = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String query = "Select * from inbox";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {
                InboxcModel in = new InboxcModel();
                in.setId(cursor.getInt(cursor.getColumnIndex("id")));
                in.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                in.setContent(cursor.getString(cursor.getColumnIndex("content")));
                in.setType(cursor.getString(cursor.getColumnIndex("type")));
                in.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                in.setDesc(cursor.getString(cursor.getColumnIndex("desc")));

                data.add(in);
            }
        }

        cursor.close();
        db.close();

        return data;
    }

    public ArrayList<TaskModel> getData2() {

        ArrayList<TaskModel> data = new ArrayList<>();

        SQLiteDatabase db1 = getReadableDatabase();
        String query = "Select * from tasks";
        Cursor cursor1 = db1.rawQuery(query, null);

        if (cursor1.getCount() > 0) {

            while (cursor1.moveToNext()) {
                TaskModel in = new TaskModel();
                in.setId(cursor1.getInt(cursor1.getColumnIndex("id")));
                in.setTitle(cursor1.getString(cursor1.getColumnIndex("title")));
                in.setDesc(cursor1.getString(cursor1.getColumnIndex("desc")));
                in.setStatus(cursor1.getString(cursor1.getColumnIndex("status")));
                in.setAssigned_by(cursor1.getString(cursor1.getColumnIndex("assigned_by")));
                in.setAssigned_to(cursor1.getString(cursor1.getColumnIndex("assigned_to")));

                data.add(in);
            }
        }

        cursor1.close();
        db1.close();

        return data;
    }


    public ArrayList<ProjectModel> getData3() {

        ArrayList<ProjectModel> data = new ArrayList<>();

        SQLiteDatabase db1 = getReadableDatabase();
        String query = "Select * from Projects";
        Cursor cursor2 = db1.rawQuery(query, null);

        if (cursor2.getCount() > 0) {

            while (cursor2.moveToNext()) {
                ProjectModel in = new ProjectModel();
                in.setId(cursor2.getInt(cursor2.getColumnIndex("id")));
                in.setName(cursor2.getString(cursor2.getColumnIndex("name")));
                in.setDesc(cursor2.getString(cursor2.getColumnIndex("status")));
                in.setStatus(cursor2.getString(cursor2.getColumnIndex("status")));
                in.setNo_member(cursor2.getString(cursor2.getColumnIndex("no_member")));
                in.setName_member(cursor2.getString(cursor2.getColumnIndex("name_member")));

                data.add(in);
            }
        }

        cursor2.close();
        db1.close();

        return data;
    }

    public ArrayList<UserModel> getData4() {
        ArrayList<UserModel> data = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "Select * from User";
        Cursor cursor3 = db.rawQuery(query, null);
        if (cursor3.getCount() > 0) {

            while (cursor3.moveToNext()) {
                UserModel in = new UserModel();
                in.setId(cursor3.getInt(cursor3.getColumnIndex("id")));
                in.setUsername(cursor3.getString(cursor3.getColumnIndex("username")));
                in.setPassword(cursor3.getString(cursor3.getColumnIndex("password")));
                in.setName(cursor3.getString(cursor3.getColumnIndex("name")));
                in.setEmail(cursor3.getString(cursor3.getColumnIndex("email")));
                data.add(in);
            }
        }

        cursor3.close();
        db.close();

        return data;
    }

    public ArrayList<NotifiactionModel> getData5() {

        ArrayList<NotifiactionModel> data = new ArrayList<>();

        SQLiteDatabase db1 = getReadableDatabase();
        String query = "Select * from Notification";
        Cursor cursor4 = db1.rawQuery(query, null);

        if (cursor4.getCount() > 0) {

            while (cursor4.moveToNext()) {
                NotifiactionModel in = new NotifiactionModel();
                in.setId(cursor4.getInt(cursor4.getColumnIndex("id")));
                in.setTitle(cursor4.getString(cursor4.getColumnIndex("title")));
                in.setContent(cursor4.getString(cursor4.getColumnIndex("content")));
                in.setRead(cursor4.getString(cursor4.getColumnIndex("read")));
                in.setDesc(cursor4.getString(cursor4.getColumnIndex("desc")));
                data.add(in);
            }
        }

        cursor4.close();
        db1.close();

        return data;
    }
}
