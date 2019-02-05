package com.mailer.utility;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Files;
 
public class AttachmentDownloaderUtility {
    private static final int BUFFER_SIZE = 4096;
 
    /**
     * Downloads a file from a URL
     * @param fileURL HTTP URL of the file to be downloaded
     * @param saveDir path of the directory to save the file
     * @return 
     * @throws IOException
     */
    public static File downloadFile(String fileURL)
            throws IOException {
    	Logger LOGGER = LoggerFactory.getLogger(AttachmentDownloaderUtility.class);
    	
        URL url = new URL(fileURL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();
 
        // always check HTTP response code first
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String fileName = "";
            String disposition = httpConn.getHeaderField("Content-Disposition");
 
            if (disposition != null) {
                // extracts file name from header field
            	int start = disposition.indexOf("\"", disposition.indexOf("filename=\"")) +1;
                if (start > 0) {
                    fileName = disposition.substring(start, disposition.indexOf("\"", start));
                }
            } else {
                // extracts file name from URL
                fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,
                        fileURL.length());
            }
 
            // opens input stream from the HTTP connection
            try(InputStream inputStream = httpConn.getInputStream()){
            	File file = new File(Files.createTempDir(), fileName);
            	
            	// opens an output stream to save into file
            	try(FileOutputStream outputStream = new FileOutputStream(file)){
            		int bytesRead = -1;
            		byte[] buffer = new byte[BUFFER_SIZE];
            		while ((bytesRead = inputStream.read(buffer)) != -1) {
            			outputStream.write(buffer, 0, bytesRead);
            		}
            	}
            	LOGGER.info("File downloaded successfully");
            	return file;
            }
        } else {
        	LOGGER.error("No file to download. Server replied HTTP code: " + responseCode);
        }
        httpConn.disconnect();
		return null;
    }
}