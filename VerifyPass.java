import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class VerifyPass {
	private static final String key = "milindparlawarmi";
	private static final String inVec = "milindparlawarmi";
	public static void main(String args[]) throws IllegalBlockSizeException, BadPaddingException,
			NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidPathException, SecurityException, FileNotFoundException, IOException {
		String userName = "";
		String password = "";
		BufferedReader reader= new BufferedReader(new InputStreamReader( System.in));
		System.out.println("enter user name");
		userName=reader.readLine();
		System.out.println("enter password");
		password=reader.readLine();
		/*
		 * KeyGenerator generator = KeyGenerator.getInstance("AES");
		 * generator.init(256); SecretKey key = generator.generateKey();
		 * 
		 * Cipher aesCipher = Cipher.getInstance("AES");
		 */
		String readLine = null;
		FileProcessor1 fileProcessor = new FileProcessor1("password.txt");
		boolean isIdPassMat=false;
		while ((readLine = fileProcessor.poll()) != null) {
			if (readLine.split(" ")[0].equals(userName)) {
				String pas= readLine.split(" ")[1];	
				/*
				 * aesCipher.init(Cipher.DECRYPT_MODE, key); byte[] bytePlainText =
				 * aesCipher.doFinal(pas.getBytes()); String passFile=new String(bytePlainText);
				 * System.out.println(new String(passFile));
				 */

			    try {
			        IvParameterSpec ivParameterSpec = new IvParameterSpec(inVec.getBytes("UTF-8"));
			        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
			 
			        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
			        byte[] original = cipher.doFinal(Base64.getDecoder().decode(pas));
			 
			        String passFile= new String(original);
			        if(passFile.equals(password)) {
			        	isIdPassMat=true;
			        	break;
			        }
			    } catch (Exception ex) {
			        ex.printStackTrace();
			    }
			 					
			}					
		}
		if(isIdPassMat) {
			System.out.println("correct ID and password");
		}else {
			System.out.println("incorrect ID and password");
		}

	}
}
	class FileProcessor1 {
		private BufferedReader reader;
		private String line;

		public FileProcessor1(String inputFilePath)
				throws InvalidPathException, SecurityException, FileNotFoundException, IOException {

			if (!Files.exists(Paths.get(inputFilePath))) {
				throw new FileNotFoundException("fild not found");
			}

			reader = new BufferedReader(new FileReader(new File(inputFilePath)));
			line = reader.readLine();
			if (line == null) {
				System.out.println(inputFilePath + "empty file");
				System.exit(0);
			}
		}

		public String poll() throws IOException {
			if (null == line)
				return null;

			String newValue = line.trim();
			line = reader.readLine();
			return newValue;
		}

		public void close() throws IOException {
			try {
				reader.close();
			} catch (IOException e) {
				throw new IOException("error while closing file", e);
			} finally {

			}
		}
	}

