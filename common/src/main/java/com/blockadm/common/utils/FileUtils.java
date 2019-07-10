package com.blockadm.common.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import com.blockadm.common.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * File Utils
 * <ul>
 * Read or write file
 * <li>{@link #readFile(String)} read file</li>
 * <li>{@link #writeFile(String, String, boolean)} write file from String</li>
 * <li>{@link #writeFile(String, String)} write file from String</li>
 * <li>{@link #writeFile(String, InputStream)} write file</li>
 * <li>{@link #writeFile(String, InputStream, boolean)} write file</li>
 * <li>{@link #writeFile(File, InputStream)} write file</li>
 * <li>{@link #writeFile(File, InputStream, boolean)} write file</li>
 * </ul>
 * <ul>
 * Operate file
 * <li>{@link #copyFile(String, String)}</li>
 * <li>{@link #getFileExtension(String)}</li>
 * <li>{@link #getFileName(String)}</li>
 * <li>{@link #getFileNameWithoutExtension(String)}</li>
 * <li>{@link #getFileSize(String)}</li>
 * <li>{@link #deleteFileAndDirectory(String)}</li>
 * <li>{@link #isFileExist(String)}</li>
 * <li>{@link #isFolderExist(String)}</li>
 * <li>{@link #makeFolders(String)}</li>
 * <li>{@link #makeDirs(String)}</li>
 * </ul>
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2012-5-12
 */
public class FileUtils {

	public final static String FILE_EXTENSION_SEPARATOR = ".";

	/**
	 * read file
	 * 
	 * @param filePath
	 * @param charsetName
	 *            The name of a supported {@link java.nio.charset.Charset
	 *            </code>charset<code>}
	 * @return if file not exist, return null, else return content of file
	 * @throws RuntimeException
	 *             if an error occurs while operator BufferedReader
	 */
	public static StringBuilder readFile(String filePath, String charsetName) {
		File file = new File(filePath);
		StringBuilder fileContent = new StringBuilder("");
		if (file == null || !file.isFile()) {
			return null;
		}

		BufferedReader reader = null;
		try {
			InputStreamReader is = new InputStreamReader(new FileInputStream(
					file), charsetName);
			reader = new BufferedReader(is);
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (!fileContent.toString().equals("")) {
					fileContent.append("\r\n");
				}
				fileContent.append(line);
			}
			reader.close();
			return fileContent;
		} catch (IOException e) {
			throw new RuntimeException("IOException occurred. ", e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					throw new RuntimeException("IOException occurred. ", e);
				}
			}
		}
	}

	/**
	 * write file
	 * 
	 * @param filePath
	 * @param content
	 * @param append
	 *            is append, if true, write to the end of file, else clear
	 *            content of file and write into it
	 * @return return false if content is empty, true otherwise
	 * @throws RuntimeException
	 *             if an error occurs while operator FileWriter
	 */
	public static boolean writeFile(String filePath, String content,
			boolean append) {
		if (StringUtils.isEmpty(content)) {
			return false;
		}

		FileWriter fileWriter = null;
		try {
			makeDirs(filePath);
			fileWriter = new FileWriter(filePath, append);
			fileWriter.write(content);
			fileWriter.close();
			return true;
		} catch (IOException e) {
			throw new RuntimeException("IOException occurred. ", e);
		} finally {
			if (fileWriter != null) {
				try {
					fileWriter.close();
				} catch (IOException e) {
					throw new RuntimeException("IOException occurred. ", e);
				}
			}
		}
	}

	/**
	 * write file
	 * 
	 * @param filePath
	 * @param contentList
	 * @param append
	 *            is append, if true, write to the end of file, else clear
	 *            content of file and write into it
	 * @return return false if contentList is empty, true otherwise
	 * @throws RuntimeException
	 *             if an error occurs while operator FileWriter
	 */
	// public static boolean writeFile(String filePath, List<String>
	// contentList, boolean append) {
	// if (ListUtils.isEmpty(contentList)) {
	// return false;
	// }
	//
	// FileWriter fileWriter = null;
	// try {
	// makeDirs(filePath);
	// fileWriter = new FileWriter(filePath, append);
	// int i = 0;
	// for (String line : contentList) {
	// if (i++ > 0) {
	// fileWriter.write("\r\n");
	// }
	// fileWriter.write(line);
	// }
	// fileWriter.close();
	// return true;
	// } catch (IOException e) {
	// throw new RuntimeException("IOException occurred. ", e);
	// } finally {
	// if (fileWriter != null) {
	// try {
	// fileWriter.close();
	// } catch (IOException e) {
	// throw new RuntimeException("IOException occurred. ", e);
	// }
	// }
	// }
	// }

	/**
	 * write file, the string will be written to the begin of the file
	 * 
	 * @param filePath
	 * @param content
	 * @return
	 */
	public static boolean writeFile(String filePath, String content) {
		return writeFile(filePath, content, false);
	}

	/**
	 * write file, the string list will be written to the begin of the file
	 * 
	 * @param filePath
	 * @param contentList
	 * @return
	 */
	// public static boolean writeFile(String filePath, List<String>
	// contentList) {
	// return writeFile(filePath, contentList, false);
	// }

	/**
	 * write file, the bytes will be written to the begin of the file
	 * 
	 * @param filePath
	 * @param stream
	 * @return
	 * @see {@link #writeFile(String, InputStream, boolean)}
	 */
	public static boolean writeFile(String filePath, InputStream stream) {
		return writeFile(filePath, stream, false);
	}

	/**
	 * write file
	 * 
	 * @param stream
	 *            the input stream
	 * @param append
	 *            if <code>true</code>, then bytes will be written to the end of
	 *            the file rather than the beginning
	 * @return return true
	 * @throws RuntimeException
	 *             if an error occurs while operator FileOutputStream
	 */
	public static boolean writeFile(String filePath, InputStream stream,
			boolean append) {
		return writeFile(filePath != null ? new File(filePath) : null, stream,
				append);
	}

	/**
	 * write file, the bytes will be written to the begin of the file
	 * 
	 * @param file
	 * @param stream
	 * @return
	 * @see {@link #writeFile(File, InputStream, boolean)}
	 */
	public static boolean writeFile(File file, InputStream stream) {
		return writeFile(file, stream, false);
	}

	/**
	 * write file
	 * 
	 * @param file
	 *            the file to be opened for writing.
	 * @param stream
	 *            the input stream
	 * @param append
	 *            if <code>true</code>, then bytes will be written to the end of
	 *            the file rather than the beginning
	 * @return return true
	 * @throws RuntimeException
	 *             if an error occurs while operator FileOutputStream
	 */
	public static boolean writeFile(File file, InputStream stream,
			boolean append) {
		OutputStream o = null;
		try {
			makeDirs(file.getAbsolutePath());
			o = new FileOutputStream(file, append);
			byte data[] = new byte[1024];
			int length = -1;
			while ((length = stream.read(data)) != -1) {
				o.write(data, 0, length);
			}
			o.flush();
			return true;
		} catch (FileNotFoundException e) {
			throw new RuntimeException("FileNotFoundException occurred. ", e);
		} catch (IOException e) {
			throw new RuntimeException("IOException occurred. ", e);
		} finally {
			if (o != null) {
				try {
					o.close();
					stream.close();
				} catch (IOException e) {
					throw new RuntimeException("IOException occurred. ", e);
				}
			}
		}
	}

	/**
	 * copy file
	 * 
	 * @param sourceFilePath
	 * @param destFilePath
	 * @return
	 * @throws RuntimeException
	 *             if an error occurs while operator FileOutputStream
	 */
	public static boolean copyFile(String sourceFilePath, String destFilePath) {
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(sourceFilePath);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("FileNotFoundException occurred. ", e);
		}
		return writeFile(destFilePath, inputStream);
	}

	/**
	 * read file to string list, a element of list is a line
	 * 
	 * @param filePath
	 * @param charsetName
	 *            The name of a supported {@link java.nio.charset.Charset
	 *            </code>charset<code>}
	 * @return if file not exist, return null, else return content of file
	 * @throws RuntimeException
	 *             if an error occurs while operator BufferedReader
	 */
	public static List<String> readFileToList(String filePath,
			String charsetName) {
		File file = new File(filePath);
		List<String> fileContent = new ArrayList<String>();
		if (file == null || !file.isFile()) {
			return null;
		}

		BufferedReader reader = null;
		try {
			InputStreamReader is = new InputStreamReader(new FileInputStream(
					file), charsetName);
			reader = new BufferedReader(is);
			String line = null;
			while ((line = reader.readLine()) != null) {
				fileContent.add(line);
			}
			reader.close();
			return fileContent;
		} catch (IOException e) {
			throw new RuntimeException("IOException occurred. ", e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					throw new RuntimeException("IOException occurred. ", e);
				}
			}
		}
	}

	/**
	 * get file name from path, not include suffix
	 * 
	 * <pre>
	 *      getFileNameWithoutExtension(null)               =   null
	 *      getFileNameWithoutExtension("")                 =   ""
	 *      getFileNameWithoutExtension("   ")              =   "   "
	 *      getFileNameWithoutExtension("abc")              =   "abc"
	 *      getFileNameWithoutExtension("a.mp3")            =   "a"
	 *      getFileNameWithoutExtension("a.b.rmvb")         =   "a.b"
	 *      getFileNameWithoutExtension("c:\\")              =   ""
	 *      getFileNameWithoutExtension("c:\\a")             =   "a"
	 *      getFileNameWithoutExtension("c:\\a.b")           =   "a"
	 *      getFileNameWithoutExtension("c:a.txt\\a")        =   "a"
	 *      getFileNameWithoutExtension("/home/admin")      =   "admin"
	 *      getFileNameWithoutExtension("/home/admin/a.txt/b.mp3")  =   "b"
	 * </pre>
	 * 
	 * @param filePath
	 * @return file name from path, not include suffix
	 * @see
	 */
	public static String getFileNameWithoutExtension(String filePath) {
		if (StringUtils.isEmpty(filePath)) {
			return filePath;
		}

		int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
		int filePosi = filePath.lastIndexOf(File.separator);
		if (filePosi == -1) {
			return (extenPosi == -1 ? filePath : filePath.substring(0,
					extenPosi));
		}
		if (extenPosi == -1) {
			return filePath.substring(filePosi + 1);
		}
		return (filePosi < extenPosi ? filePath.substring(filePosi + 1,
				extenPosi) : filePath.substring(filePosi + 1));
	}

	/**
	 * get file name from path, include suffix
	 * 
	 * <pre>
	 *      getFileName(null)               =   null
	 *      getFileName("")                 =   ""
	 *      getFileName("   ")              =   "   "
	 *      getFileName("a.mp3")            =   "a.mp3"
	 *      getFileName("a.b.rmvb")         =   "a.b.rmvb"
	 *      getFileName("abc")              =   "abc"
	 *      getFileName("c:\\")              =   ""
	 *      getFileName("c:\\a")             =   "a"
	 *      getFileName("c:\\a.b")           =   "a.b"
	 *      getFileName("c:a.txt\\a")        =   "a"
	 *      getFileName("/home/admin")      =   "admin"
	 *      getFileName("/home/admin/a.txt/b.mp3")  =   "b.mp3"
	 * </pre>
	 * 
	 * @param filePath
	 * @return file name from path, include suffix
	 */
	public static String getFileName(String filePath) {
		if (StringUtils.isEmpty(filePath)) {
			return filePath;
		}

		int filePosi = filePath.lastIndexOf(File.separator);
		return (filePosi == -1) ? filePath : filePath.substring(filePosi + 1);
	}

	/**
	 * get folder name from path
	 * 
	 * <pre>
	 *      getFolderName(null)               =   null
	 *      getFolderName("")                 =   ""
	 *      getFolderName("   ")              =   ""
	 *      getFolderName("a.mp3")            =   ""
	 *      getFolderName("a.b.rmvb")         =   ""
	 *      getFolderName("abc")              =   ""
	 *      getFolderName("c:\\")              =   "c:"
	 *      getFolderName("c:\\a")             =   "c:"
	 *      getFolderName("c:\\a.b")           =   "c:"
	 *      getFolderName("c:a.txt\\a")        =   "c:a.txt"
	 *      getFolderName("c:a\\b\\c\\d.txt")    =   "c:a\\b\\c"
	 *      getFolderName("/home/admin")      =   "/home"
	 *      getFolderName("/home/admin/a.txt/b.mp3")  =   "/home/admin/a.txt"
	 * </pre>
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFolderName(String filePath) {

		if (StringUtils.isEmpty(filePath)) {
			return filePath;
		}

		int filePosi = filePath.lastIndexOf(File.separator);
		return (filePosi == -1) ? "" : filePath.substring(0, filePosi);
	}

	/**
	 * get suffix of file from path
	 * 
	 * <pre>
	 *      getFileExtension(null)               =   ""
	 *      getFileExtension("")                 =   ""
	 *      getFileExtension("   ")              =   "   "
	 *      getFileExtension("a.mp3")            =   "mp3"
	 *      getFileExtension("a.b.rmvb")         =   "rmvb"
	 *      getFileExtension("abc")              =   ""
	 *      getFileExtension("c:\\")              =   ""
	 *      getFileExtension("c:\\a")             =   ""
	 *      getFileExtension("c:\\a.b")           =   "b"
	 *      getFileExtension("c:a.txt\\a")        =   ""
	 *      getFileExtension("/home/admin")      =   ""
	 *      getFileExtension("/home/admin/a.txt/b")  =   ""
	 *      getFileExtension("/home/admin/a.txt/b.mp3")  =   "mp3"
	 * </pre>
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFileExtension(String filePath) {
		if (StringUtils.isBlank(filePath)) {
			return filePath;
		}

		int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
		int filePosi = filePath.lastIndexOf(File.separator);
		if (extenPosi == -1) {
			return "";
		}
		return (filePosi >= extenPosi) ? "" : filePath.substring(extenPosi + 1);
	}

	/**
	 * Creates the directory named by the trailing filename of this file,
	 * including the complete directory path required to create this directory. <br/>
	 * <br/>
	 * <ul>
	 * <strong>Attentions:</strong>
	 * <li>makeDirs("C:\\Users\\Trinea") can only create users folder</li>
	 * <li>makeFolder("C:\\Users\\Trinea\\") can create Trinea folder</li>
	 * </ul>
	 * 
	 * @param filePath
	 * @return true if the necessary directories have been created or the target
	 *         directory already exists, false one of the directories can not be
	 *         created.
	 *         <ul>
	 *         <li>if {@link FileUtils#getFolderName(String)} return null,
	 *         return false</li>
	 *         <li>if target directory already exists, return true</li>
	 *         </ul>
	 */
	public static boolean makeDirs(String filePath) {
		String folderName = getFolderName(filePath);
		if (StringUtils.isEmpty(folderName)) {
			return false;
		}

		File folder = new File(folderName);
		return (folder.exists() && folder.isDirectory()) ? true : folder
				.mkdirs();
	}

	/**
	 * @param filePath
	 * @return
	 * @see #makeDirs(String)
	 */
	public static boolean makeFolders(String filePath) {
		return makeDirs(filePath);
	}

	/**
	 * Indicates if this file represents a file on the underlying file system.
	 *
	 * @param filePath
	 * @return
	 */
	public static boolean isFileExist(String filePath) {
		if (StringUtils.isBlank(filePath)) {
			return false;
		}

		File file = new File(filePath);
		return (file.exists() && file.isFile());
	}

	public static boolean isFileExist(File file) {

		return (file.exists() && file.isFile());
	}

	/**
	 * Indicates if this file represents a directory on the underlying file
	 * system.
	 *
	 * @param directoryPath
	 * @return
	 */
	public static boolean isFolderExist(String directoryPath) {
		if (StringUtils.isBlank(directoryPath)) {
			return false;
		}

		File dire = new File(directoryPath);
		return (dire.exists() && dire.isDirectory());
	}

	/**
	 * delete file or directory
	 * <ul>
	 * <li>if path is null or empty, return true</li>
	 * <li>if path not exist, return true</li>
	 * <li>if path exist, delete recursion. return true</li>
	 * <ul>
	 *
	 * @param path
	 * @return
	 */
	public static boolean deleteFileAndDirectory(String path) {
		if (StringUtils.isBlank(path)) {
			return true;
		}

		File file = new File(path);
		if (!file.exists()) {
			return true;
		}
		if (file.isFile()) {
			return file.delete();
		}
		if (!file.isDirectory()) {
			return false;
		}
		for (File f : file.listFiles()) {
			if (f.isFile()) {
				f.delete();
			} else if (f.isDirectory()) {
				deleteFileAndDirectory(f.getAbsolutePath());
			}
		}
		return file.delete();
	}

	public static boolean deleteFile(String path) {
		if (StringUtils.isBlank(path)) {
			return true;
		}

		File file = new File(path);
		if (!file.exists()) {
			return true;
		}
		if (file.isFile()) {
			return file.delete();
		}
		if (!file.isDirectory()) {
			return false;
		}
		return file.delete();
	}

	/**
	 * get file size
	 * <ul>
	 * <li>if path is null or empty, return -1</li>
	 * <li>if path exist and it is a file, return file size, else return -1</li>
	 * <ul>
	 *
	 * @param path
	 * @return returns the length of this file in bytes. returns -1 if the file
	 *         does not exist.
	 */
	public static long getFileSize(String path) {
		if (StringUtils.isBlank(path)) {
			return -1;
		}

		File file = new File(path);
		return (file.exists() && file.isFile() ? file.length() : -1);
	}

	public static String readTxTFile(String filePath, Context context) {

		try {
			String encode = "UTF-8";
			File file = new File(filePath);

			if (file.isFile() && file.exists()) {
				InputStreamReader reader = new InputStreamReader(
						new FileInputStream(file), encode);
				BufferedReader bufferReader = new BufferedReader(reader);
				StringBuffer stringBuffer = new StringBuffer();
				String txt = null;

				while ((txt = bufferReader.readLine()) != null) {
					stringBuffer.append(txt);
				}

				reader.close();
				bufferReader.close();
				return stringBuffer.toString();
			} else {

				T.showShort(context, R.string.toast_can_not_find_file);
			}

		} catch (Exception e) {
			e.printStackTrace();
			T.showShort(context, R.string.toast_read_file_error);
		}
		return null;
	}

	public static void writeTxTFile(String filePath, String str) {

		try {
			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdirs();
			}
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file)));
			writer.newLine();
			writer.write(str);
			writer.flush();
			writer.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 *
	 *
	 * writeTxTFile(这里用一句话描述这个方法的作用)
	 *
	 * TODO(这里描述这个方法适用条件 – 可选)
	 *
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 *
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 *
	 * TODO(注意事项 – 必须保证数据条数与from个数相当，即每条的数据及key都不同，每条数据保持唯一性)
	 *
	 *
	 * @Title: writeTxTFile
	 * @Description: TODO
	 * @param @param filePath
	 * @param @param data
	 * @param @param from
	 * @return void 返回类型
	 * @throws
	 */
	public static void writeTxTFile(String filePath,
			List<HashMap<String, String>> data, String[] from) {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdirs();
			}
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file)));
			for (int i = 0; i < data.size(); i++) {
				HashMap<String, String> map = data.get(i);
				String str = from[i] + "=" + map.get(from[i]);
				writer.write(str);
				writer.newLine();
			}

			writer.flush();
			writer.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 *
	 *
	 * writeTxTFile(写入TXT文件)
	 *
	 * TODO(这里描述这个方法适用条件 – 可选)
	 *
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 *
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 *
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 *
	 * @Title: writeTxTFile
	 * @Description: TODO
	 * @param @param filePath
	 * @param @param data
	 * @param @param from
	 * @return void 返回类型
	 * @throws
	 */
	public static void writeTxTFile(String filePath,
			HashMap<String, String> data, String[] from) {

		try {
			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdirs();
			}
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file)));

			for (int i = 0; i < from.length; i++) {
				String str = from[i] + "=" + data.get(from[i]);
				writer.write(str);
				writer.newLine();

			}

			writer.flush();
			writer.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 关闭流
	 *
	 * @param closeables
	 */
	public static void closeIO(Closeable... closeables) {
		if (null == closeables || closeables.length <= 0) {
			return;
		}
		for (Closeable cb : closeables) {
			try {
				if (null == cb) {
					continue;
				}
				cb.close();
			} catch (IOException e) {
				throw new RuntimeException(
						FileUtils.class.getClass().getName(), e);
			}
		}
	}

	/**
	 * 检测SD卡是否存在
	 */
	public static boolean checkSDcard() {
		return Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState());
	}

	/**
	 * 将文件保存到本地
	 */
	public static void saveFileCache(byte[] fileData, String folderPath,
			String fileName) {
		File folder = new File(folderPath);
		folder.mkdirs();
		File file = new File(folderPath, fileName);
		ByteArrayInputStream is = new ByteArrayInputStream(fileData);
		OutputStream os = null;
		if (!file.exists()) {
			try {
				file.createNewFile();
				os = new FileOutputStream(file);
				byte[] buffer = new byte[1024];
				int len = 0;
				while (-1 != (len = is.read(buffer))) {
					os.write(buffer, 0, len);
				}
				os.flush();
			} catch (Exception e) {
				throw new RuntimeException(
						FileUtils.class.getClass().getName(), e);
			} finally {
				closeIO(is, os);
			}
		}
	}

	/**
	 * 从指定文件夹获取文件
	 *
	 * @return 如果文件不存在则创建,如果如果无法创建文件或文件名为空则返回null
	 */
	public static File getSaveFile(String folderPath, String fileNmae) {
		File file = new File(getSavePath(folderPath) + File.separator
				+ fileNmae);
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}

	/**
	 * 获取SD卡下指定文件夹的绝对路径
	 *
	 * @return 返回SD卡下的指定文件夹的绝对路径
	 */
	public static String getSavePath(String folderName) {
		return getSaveFolder(folderName).getAbsolutePath();
	}

	/**
	 * 获取文件夹对象
	 *
	 * @return 返回SD卡下的指定文件夹对象，若文件夹不存在则创建
	 */
	public static File getSaveFolder(String folderName) {
		File file = new File(getSDCardPath() + File.separator + folderName
				+ File.separator);
		file.mkdirs();
		return file;
	}

	@SuppressLint("NewApi")
	public static String getSDCardPath() {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())
				|| !Environment.isExternalStorageRemovable()) {
			return Environment.getExternalStorageDirectory().getAbsolutePath();
		} else {
			return getPhoneCardPath();
		}

	}

	/**
	 * 获取手机自身内存路径
	 *
	 */
	public static String getPhoneCardPath() {
		return Environment.getDataDirectory().getPath();

	}

	/**
	 * 输入流转byte[]<br>
	 */
	public static final byte[] input2byte(InputStream inStream) {
		if (inStream == null) {
			return null;
		}
		byte[] in2b = null;
		BufferedInputStream in = new BufferedInputStream(inStream);
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		int rc = 0;
		try {
			while ((rc = in.read()) != -1) {
				swapStream.write(rc);
			}
			in2b = swapStream.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeIO(inStream, in, swapStream);
		}
		return in2b;
	}

	/**
	 * 把uri转为File对象
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	public static File uri2File(Activity aty, Uri uri) {
		if (SystemUtils.getSDKVersion() < 11) {
			// 在API11以下可以使用：managedQuery
			String[] proj = { MediaStore.Images.Media.DATA };
			@SuppressWarnings("deprecation")
			Cursor actualimagecursor = aty.managedQuery(uri, proj, null, null,
					null);
			int actual_image_column_index = actualimagecursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			actualimagecursor.moveToFirst();
			String img_path = actualimagecursor
					.getString(actual_image_column_index);
			return new File(img_path);
		} else {
			// 在API11以上：要转为使用CursorLoader,并使用loadInBackground来返回
			String[] projection = { MediaStore.Images.Media.DATA };
			CursorLoader loader = new CursorLoader(aty, uri, projection, null,
					null, null);
			Cursor cursor = loader.loadInBackground();
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return new File(cursor.getString(column_index));
		}
	}

	/**
	 * 图片写入文件
	 *
	 * @param bitmap
	 *            图片
	 * @param filePath
	 *            文件路径
	 * @return 是否写入成功
	 */
	public static boolean bitmapToFile(Bitmap bitmap, String filePath) {
		boolean isSuccess = false;
		if (bitmap == null) {
			return isSuccess;
		}
		File file = new File(filePath.substring(0,
				filePath.lastIndexOf(File.separator)));
		if (!file.exists()) {
			file.mkdirs();
		}

		OutputStream out = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(filePath),
					8 * 1024);
			isSuccess = bitmap.compress(CompressFormat.PNG, 100, out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			closeIO(out);
		}
		return isSuccess;
	}

	/**
	 * 从文件中读取文本
	 *
	 * @param filePath
	 * @return
	 */
	public static String readFile(String filePath) {
		InputStream is = null;
		try {
			is = new FileInputStream(filePath);
		} catch (Exception e) {
			throw new RuntimeException(FileUtils.class.getName()
					+ "readFile---->" + filePath + " not found");
		}
		return inputStream2String(is);
	}

	/**
	 * 从assets中读取文本
	 *
	 * @param name
	 * @return
	 */
	public static String readFileFromAssets(Context context, String name) {
		InputStream is = null;
		try {
			is = context.getResources().getAssets().open(name);
		} catch (Exception e) {
			throw new RuntimeException(FileUtils.class.getName()
					+ ".readFileFromAssets---->" + name + " not found");
		}
		return inputStream2String(is);
	}

	/**
	 * 输入流转字符串
	 *
	 * @param is
	 * @return 一个流中的字符串
	 */
	public static String inputStream2String(InputStream is) {
		if (null == is) {
			return null;
		}
		StringBuilder resultSb = null;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			resultSb = new StringBuilder();
			String len;
			while (null != (len = br.readLine())) {
				resultSb.append(len);
			}
		} catch (Exception ex) {
		} finally {
			closeIO(is);
		}
		return null == resultSb ? null : resultSb.toString();
	}

	/****************************************
	 * 删除文件夹下所有文件
	 *
	 * @param：无
	 * @return：String 字符串形式的路径名称
	 * @author：黄俊鑫
	 ******************************************/
	/**
	 * 递归删除文件和文件夹
	 *
	 * @param dir
	 *            要删除的根目录
	 */
	public static void deleteFileAndDirectory(File dir) {
		if (dir.exists() == false) {
			return;
		} else {
			if (dir.isFile()) {
				dir.delete();
				return;
			}
			if (dir.isDirectory()) {
				File[] childFile = dir.listFiles();
				if (childFile == null || childFile.length == 0) {
					dir.delete();
					return;
				}
				for (File f : childFile) {
					deleteFileAndDirectory(f);
				}
				dir.delete();
			}
		}
	}

	/****************************************
	 * 将文件内容转为字符串返回
	 *
	 * @param：filePath：文件路径
	 * @return：String 字符串形式的文件内容
	 * @author：黄俊鑫
	 ******************************************/
	public static String LoadFileAsString(String filePath)
			throws java.io.IOException {
		StringBuffer fileData = new StringBuffer(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		char[] buf = new char[1024];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
		}
		reader.close();
		return fileData.toString();
	}

	/**
	 * 保存bitmap文件
	 * 
	 * @param filePath
	 *            ，文件。bitmap，图片
	 * @return
	 */
	public static File getFileFromPath(String filePath) {
		try {
			String dirPath = filePath.substring(0, filePath.lastIndexOf("/"));
			File dir = new File(dirPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			
			File f = new File(filePath);
			if(!f.exists())
			f.createNewFile();
			return f;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}