package com.ririjin.adminmobile.ftp_tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Properties;
import java.util.Vector;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.ririjin.adminmobile.AddCarAcitivity;
import com.ririjin.adminmobile.UpdateCarActivity;

public class SFTP {

	/**
	 * 连接sftp服务器
	 * 
	 * @param host
	 *            主机
	 * @param port
	 *            端口
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return
	 */
	public ChannelSftp connect(String host, int port, String username,
			String password) {
		ChannelSftp sftp = null;
		try {
			JSch jsch = new JSch();
			jsch.getSession(username, host, port);
			Session sshSession = jsch.getSession(username, host, port);
			System.out.println("Session created.");
			sshSession.setPassword(password);
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(sshConfig);
			sshSession.connect();
			System.out.println("Session connected.");
			System.out.println("Opening Channel.");
			Channel channel = sshSession.openChannel("sftp");
			channel.connect();
			sftp = (ChannelSftp) channel;
			System.out.println("Connected to " + host + ".");
			return sftp;
		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * 上传文件
	 * 
	 * @param directory
	 *            上传的目录
	 * @param uploadFile
	 *            要上传的文件
	 * @param sftp
	 */
	public void upload(String directory, String uploadFile, ChannelSftp sftp,
			MyProgressMonitor monitor) {
		try {
			Log.d("上传的目录", directory);

			sftp.cd(directory);
			File file = new File(uploadFile);
			sftp.put(new FileInputStream(file), file.getName(), monitor);
			// sftp.put(src, dst, monitor)

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 下载文件
	 * 
	 * @param directory
	 *            下载目录
	 * @param downloadFile
	 *            下载的文件
	 * @param saveFile
	 *            存在本地的路径
	 * @param sftp
	 */
	public void download(String directory, String downloadFile,
			String saveFile, ChannelSftp sftp) {
		try {

			sftp.cd(directory);
			File file = new File(saveFile);
			sftp.get(downloadFile, new FileOutputStream(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param directory
	 *            要删除文件所在目录
	 * @param deleteFile
	 *            要删除的文件
	 * @param sftp
	 */
	public void delete(String directory, String deleteFile, ChannelSftp sftp) {
		try {
			sftp.cd(directory);
			sftp.rm(deleteFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 列出目录下的文件
	 * 
	 * @param directory
	 *            要列出的目录
	 * @param sftp
	 * @return
	 * @throws SftpException
	 */
	public Vector listFiles(String directory, ChannelSftp sftp)
			throws SftpException {
		return sftp.ls(directory);
	}

	public static void main(String[] args) {
		SFTP sf = new SFTP();
		String host = "rijin.com";
		int port = 2021;
		String username = "work";
		String password = "5tgb%TGBrijin";
		String directory = "/home/work/gopath/src/git.rd.rijin.com/platform/file_server_pc/static/asset";
		String uploadFile = "";
		String downloadFile = "upload.txt";
		String saveFile = "D:\\tmp\\download.txt";
		String deleteFile = "delete.txt";
		ChannelSftp sftp = sf.connect(host, port, username, password);
		sf.upload(directory, uploadFile, sftp, null);
		sf.download(directory, downloadFile, saveFile, sftp);
		sf.delete(directory, deleteFile, sftp);
		try {
			sftp.cd(directory);
			sftp.mkdir("ss");
			System.out.println("finished");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void UploadImage(String path,String type, MyProgressMonitor monitor) {
		SFTP sf = new SFTP();
		String host = "rijin.com";
		int port = 2021;
		String username = "work";
		String password = "5tgb%TGBrijin";
		String directory = "/home/work/gopath/src/git.rd.rijin.com/platform/file_server_pc/static/asset/";
		String uploadFile = path;
		ChannelSftp sftp = sf.connect(host, port, username, password);
		Log.i("UploadImage path",
				directory + GetNowTime() + AddCarAcitivity.Carlicense + "/"
						+ type);
		sf.upload(directory + GetNowTime() + AddCarAcitivity.Carlicense + "/"
				+ type, uploadFile, sftp,
				monitor);
	}
	public static void UpdateImage(String path,String type, MyProgressMonitor monitor) {
		SFTP sf = new SFTP();
		String host = "rijin.com";
		int port = 2021;
		String username = "work";
		String password = "5tgb%TGBrijin";
		String directory = "/home/work/gopath/src/git.rd.rijin.com/platform/file_server_pc/static/asset/";
		String uploadFile = path;
		ChannelSftp sftp = sf.connect(host, port, username, password);
		Log.i("UploadImage path",
				directory + GetNowTime() + UpdateCarActivity.Carlicense + "/"
						+ type);
		sf.upload(directory + GetNowTime() + UpdateCarActivity.Carlicense + "/"
				+ type, uploadFile, sftp,
				monitor);
	}

	public static void MkdirFile(String path,String[] list,Context context) {
		SFTP sf = new SFTP();
		String host = "rijin.com";
		int port = 2021;
		String username = "work";
		String password = "5tgb%TGBrijin";
		String directory = "/home/work/gopath/src/git.rd.rijin.com/platform/file_server_pc/static/asset/";

		String uploadFile = path;

		ChannelSftp sftp = sf.connect(host, port, username, password);
		
		try {
			sftp.mkdir(directory + GetNowTime() + GetImageName(uploadFile));
		} catch (SftpException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for (int i = 0; i < list.length; i++) {
			try {
				sftp.mkdir(directory + GetNowTime() + GetImageName(uploadFile)+"/"+list[i]);
			} catch (SftpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static String GetImageName(String uploadFile) {
		File file = new File(uploadFile);
		return file.getName();
	}
	
	public static String GetFile(String uploadFile) {
		File file = new File(uploadFile);
		return file.getName().replace(".jpg", "");
	}


	public static String GetNowTime() {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		String monthString=null;
		String dayString=null;
		if (month<10) {
			 monthString="0"+month;
		}else{
			 monthString=""+month;

		}
		int day = c.get(Calendar.DAY_OF_MONTH);
		if (day<10) {
			dayString="0"+day;
		}else{
			dayString=""+day;
		}
				
		return "" + year + monthString + dayString;

	}
}