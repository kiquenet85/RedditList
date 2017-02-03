package com.app.ndiazgranados.catalog.util;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

/**
 * @author n.diazgranados
 */
public class FileUtil {

    public static Reader readFile(Context context, int resId) {
        InputStream inputStream = context.getResources().openRawResource(resId);
        return new InputStreamReader(inputStream, Charset.forName("UTF-8"));
    }

    public static void saveLastCatalogData(Context context, String jsonData) {
        String filename = "CatalogData";
        FileOutputStream outputStream;

        try {
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(jsonData.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String readSavedCatalogData(Context context) throws IOException {
        BufferedReader input = null;
        File file = null;
        String result = null;
        try {
            file = new File(context.getFilesDir(), "CatalogData");

            if (!file.exists()) {
                return result;
            }

            input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = input.readLine()) != null) {
                buffer.append(line);
            }

            result = buffer.toString();
            Log.d(context.getClass().getSimpleName(), result);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
