package com.mailer.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

public class FetchFile {
	public static File getResource(URI uri) throws Exception {
		  URL url = uri.toURL();
			InputStream in = url.openStream();
			File file = new File("file.text");
			FileOutputStream fos = new FileOutputStream(file);
			byte[] buf = new byte[512];
			while (true) {
			    int len = in.read(buf);
			    if (len == -1) {
			        break;
			    }
			    fos.write(buf, 0, len);
			}
			in.close();
			fos.flush();
			fos.close();
			return file;
	}
}
