package upload.convert;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.web.multipart.MultipartFile;

import upload.s3.S3TransferProgressSample;


public class FileTypeConverter {
	
	static OutputStream outputStream = null;
	public static void uploadS3(File file) throws Exception{
		S3TransferProgressSample S3process = new S3TransferProgressSample(file);
		file.deleteOnExit();
	}
	
	public static void convert(MultipartFile file) throws Exception
	    {
			 byte [] byteArr= file.getBytes();
			 int read = 0;
			 byte[] bytes = new byte[1024];
			 InputStream inputStream = new ByteArrayInputStream(byteArr);
			 
			 
			 String filename = file.getOriginalFilename();
			 String[] splitResult = filename.split("\\.");
			 
			 File tempfile = File.createTempFile(splitResult[0], "." + splitResult[1]);//("C:\\Downloads\\" + file.getOriginalFilename());
			 
			 outputStream = new FileOutputStream(tempfile);
	 
			
	 
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}

			uploadS3(tempfile);
			
	        
	    }

}
