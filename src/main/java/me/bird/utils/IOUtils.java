package me.bird.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class IOUtils {

	public static String toString(InputStream inputStream,String encoding) {
		try {
			Charset charset = Charset.forName(encoding);
			return new String(toBytes(inputStream), charset);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static byte[] toBytes(InputStream inputStream){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = -1;
		try {
			while ((i = inputStream.read()) != -1) {
				baos.write(i);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return baos.toByteArray();
	}
}
