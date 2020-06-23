import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.InvalidPathException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class GenPass {
	private static final String key = "milindparlawarmi";
	private static final String inVec = "milindparlawarmi";
	public static void main(String args[])
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, InvalidPathException, SecurityException, FileNotFoundException, IOException {
		String userName = "";
		String password = "";
		BufferedReader reader= new BufferedReader(new InputStreamReader( System.in));
		System.out.println("enter user name");
		userName=reader.readLine();
		System.out.println("enter password");
		password=reader.readLine();
		/*
		 * KeyGenerator generator = KeyGenerator.getInstance("AES");
		 * generator.init(256); SecretKey key = generator.generateKey(); //SecureRandom
		 * rando = new SecureRandom("ccc".getBytes()); //SecretKey key =
		 * generator.init(rando);
		 * 
		 * //SecretKey k1= key.toString(); //System.out.println("key "+ key);
		 * 
		 * Cipher aesCipher = Cipher.getInstance("AES");
		 * aesCipher.init(Cipher.ENCRYPT_MODE, key); byte[] byteCipherText =
		 * aesCipher.doFinal(password.getBytes()); String enVal =
		 * DatatypeConverter.printHexBinary(byteCipherText);
		 */
		String  enVal="";
		 try {
		        IvParameterSpec iv = new IvParameterSpec(inVec.getBytes("UTF-8"));
		        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
		 
		        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
		        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		 
		        byte[] encrypted = cipher.doFinal(password.getBytes());
		        enVal=Base64.getEncoder().encodeToString(encrypted);
		    } catch (Exception ex) {
		        ex.printStackTrace();
		    }
		FileProcessor fileProcessor = new FileProcessor("password.txt");
		fileProcessor.writeToFile(userName+" "+ enVal);
		fileProcessor.close();
		/*
		 * aesCipher.init(Cipher.DECRYPT_MODE, key); byte[] bytePlainText =
		 * aesCipher.doFinal(byteCipherText); System.out.println(new
		 * String(bytePlainText));
		 */

	}
}

class FileProcessor {
	private FileWriter file;
	private BufferedWriter fileWriter;

	public FileProcessor(String inputFilePath)
			throws InvalidPathException, SecurityException, FileNotFoundException, IOException {
		file = new FileWriter(inputFilePath, true);
		try {
			fileWriter = new BufferedWriter(file);

		} finally {

		}

	}

	public void writeToFile(String s) throws IOException {

		try {
			fileWriter.write(s);
			fileWriter.newLine();
		} catch (IOException e) {
			System.err.println("error writing file");
			e.printStackTrace();
			System.exit(0);
		} finally {

		}

	}

	public void close() throws IOException {
		try {
			fileWriter.close();
		} catch (IOException e) {
			System.err.print("error closing file");
			e.printStackTrace();
			System.exit(0);
		} finally {

		}
	}
}
