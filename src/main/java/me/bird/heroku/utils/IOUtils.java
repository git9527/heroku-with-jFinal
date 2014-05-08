package me.bird.heroku.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class IOUtils {

	public static String toString(InputStream inputStream,String encoding) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = -1;
		try {
			while ((i = inputStream.read()) != -1) {
				baos.write(i);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		try {
			Charset charset = Charset.forName(encoding);
			return new String(baos.toByteArray(), charset);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
