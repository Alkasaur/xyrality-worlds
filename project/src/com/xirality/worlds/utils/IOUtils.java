/*
 * Copyright (c) 2012 Ergon Informatik AG
 * Kleinstrasse 15, 8008 Zuerich, Switzerland
 * All rights reserved.
 */

package com.xirality.worlds.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.util.Log;

/**
 * Bunch of general methods for IO operations.
 */
public final class IOUtils {
	private static final String TAG = "IOUtils";

    public static byte[] readBytesFromFile (File file) {

        byte[] result = null;

        try {

            FileInputStream fin = new FileInputStream(file);
            result = new byte[(int) file.length()];
            fin.read(result);
            fin.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
    
	public static void saveToFile(File file, InputStream is) throws IOException {
		FileOutputStream fos = null;
		try {
			try {
				fos = new FileOutputStream(file);
			} catch (FileNotFoundException e) {
				Log.e(TAG, "saveFile: '" + file
						+ "' cannot be opened for writing", e);
				throw e;
			}

			byte[] buf = new byte[1024];
			int len;
			try {
				while ((len = is.read(buf)) > 0) {
					fos.write(buf, 0, len);
				}
				fos.flush();
			} catch (IOException e) {
				Log.e(TAG, "saveFile: failed to save '" + file + '\'', e);
				throw e;
			}
		} finally {
			fos.close();
			fos = null;
			is.close();
			is = null;
		}
	}
}
