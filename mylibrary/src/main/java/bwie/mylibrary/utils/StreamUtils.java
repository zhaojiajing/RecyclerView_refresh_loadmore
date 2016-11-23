package bwie.mylibrary.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamUtils {
	public static String toStr(InputStream inputStream,String encode) {
		try {
			int len = 0;
			byte[] buffer = new byte[1024];
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			//***%%%%%%%%%%%%%********len=*********%%%%%%%%%%%%%%********************
			while ((len=inputStream.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			return out.toString(encode);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}
}
