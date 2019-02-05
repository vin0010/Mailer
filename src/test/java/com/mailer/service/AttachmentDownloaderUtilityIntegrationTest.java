/**
 * 
 */
package com.mailer.service;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.mailer.utility.AttachmentDownloaderUtility;

/**
 * @author Vinoth.Gopu
 * Integration test for attachment download utility
 */
public class AttachmentDownloaderUtilityIntegrationTest {

	/**
	 * Test method for
	 * {@link com.mailer.utility.AttachmentDownloaderUtility#downloadFile(java.lang.String)}.
	 * 
	 * @throws IOException
	 */
	@Test
	public final void testDownloadGenericFile() throws IOException {
		String testFileURL = "https://drive.google.com/uc?id=1OjVXW2xK9eHcBAjJJygtcyYtiQBwX-tt&export=download";
		File attachment = AttachmentDownloaderUtility.downloadFile(testFileURL);
		assertTrue(attachment != null);
		assertTrue(attachment.getName().equals("Vinoth_Gopu.pdf"));

	}
	
	/**
	 * Test method for
	 * {@link com.mailer.utility.AttachmentDownloaderUtility#downloadFile(java.lang.String)}.
	 * 
	 * @throws IOException
	 */
	@Test
	public final void testDownloadFTPFile() throws IOException {
		String testFileURL = "https://google.com/robots.txt";
		File attachment = AttachmentDownloaderUtility.downloadFile(testFileURL);
		assertTrue(attachment != null);
		assertTrue(attachment.getName().equals("robots.txt"));

	}
}
