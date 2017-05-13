package com.projectmanagement;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Babul Patel on 17/04/2017.
 */

public class AppHeart {


    public static String ClientID = "ClientID";
    public static String UserType = "UserType";


    public static void Toast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static String getText(EditText et) {
        return et.getText().toString().trim();
    }

    public static String getFileName(Context context, Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    public static String getFileExt(String fileName) {
        return "." + fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
    }

}
