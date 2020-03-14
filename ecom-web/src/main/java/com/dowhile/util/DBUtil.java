package com.dowhile.util;

import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.ibatis.common.jdbc.ScriptRunner;

public class DBUtil {
	
	// private static Logger logger = Logger.getLogger(DBUtil.class.getName());
	static String dbName = "ecom";
	static String dbUser = "root";
	static String dbPass = "123456";
	public static void main(String argv[]){
		//Backupdbtosql("C:/backup"+ "/backup.sql");
		try {
			dbRestore("C:/backup"+ "/backup.sql");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();// logger.error(e.getMessage(),e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();// logger.error(e.getMessage(),e);
		}
	}
	public static void Backupdbtosql(String backupPath) {
	    try {

	        /*NOTE: Getting path to the Jar file being executed*/
	        /*NOTE: YourImplementingClass-> replace with the class executing the code*/
	        CodeSource codeSource = DBUtil.class.getProtectionDomain().getCodeSource();
	        File jarFile = new File(codeSource.getLocation().toURI().getPath());
	        String jarDir = jarFile.getParentFile().getPath();


	        /*NOTE: Creating Database Constraints*/
	        

	        /*NOTE: Creating Path Constraints for folder saving*/
	        /*NOTE: Here the backup folder is created for saving inside it*/
	        String folderPath = jarDir + "\\backup";

	        /*NOTE: Creating Folder if it does not exist*/
	        File f1 = new File(folderPath);
	        f1.mkdir();

	        /*NOTE: Creating Path Constraints for backup saving*/
	        /*NOTE: Here the backup is saved in a folder called backup with the name backup.sql*/
	         //String backupPath1 = "\"" + jarDir + "\\backup\\" + "backup.sql\"";

	        /*NOTE: Used to create a cmd command*/
	       // String executeCmd = "C:\\Program Files\\MySQL\\MySQL Server 5.5\\bin\\mysqldump -u" + dbUser + " -p" + dbPass + " --database " + dbName + " -r " + savePath;
	         String executeCmd = "mysqldump -u" + dbUser + " -p" + dbPass + " --database " + dbName + " -r " + backupPath;

	         System.out.println("executeCmd: "+executeCmd);
	        /*NOTE: Executing the command here*/
//	        Process runtimeProcess = Runtime.getRuntime().exec(new String[] { "cmd.exe", "/c", executeCmd });
	        Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
	        int processComplete = runtimeProcess.waitFor();

	        /*NOTE: processComplete=0 if correctly executed, will contain other values if not*/
	        if (processComplete == 0) {
	            System.out.println("Backup Complete");
	        } else {
	            System.out.println("Backup Failure");
	        }

	    } catch (URISyntaxException | IOException | InterruptedException ex) {
	    	ex.printStackTrace();// logger.error(ex.getMessage(),ex);
	       // JOptionPane.showMessageDialog(null, "Error at Backuprestore" + ex.getMessage());
	    }
	}

	public static void Restoredbfromsql(String s) {
        try {
            /*NOTE: String s is the mysql file name including the .sql in its name*/
            /*NOTE: Getting path to the Jar file being executed*/
            /*NOTE: YourImplementingClass-> replace with the class executing the code*/
            CodeSource codeSource = DBUtil.class.getProtectionDomain().getCodeSource();
            File jarFile = new File(codeSource.getLocation().toURI().getPath());
            String jarDir = jarFile.getParentFile().getPath();

         
            /*NOTE: Creating Path Constraints for restoring*/
            String restorePath = jarDir + "\\backup" + "\\" + s;

            /*NOTE: Used to create a cmd command*/
            /*NOTE: Do not create a single large string, this will cause buffer locking, use string array*/
            String[] executeCmd = new String[]{"C:\\Program Files\\MySQL\\MySQL Server 5.5\\bin\\mysql", dbName, "-u" + dbUser, "-p" + dbPass, "-e", " source " + restorePath};

            /*NOTE: processComplete=0 if correctly executed, will contain other values if not*/
            Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();

            /*NOTE: processComplete=0 if correctly executed, will contain other values if not*/
            if (processComplete == 0) {
            	System.out.println( "Successfully restored from SQL : " + s);
            } else {
            	System.out.println("Error at restoring");
            }


        } catch (URISyntaxException | IOException | InterruptedException | HeadlessException ex) {
             System.out.println("Error at Restoredbfromsql" + ex.getMessage());
        }

    }
	
	public static void dbRestore(String aSQLScriptFilePath) throws ClassNotFoundException,
	SQLException {

	//String aSQLScriptFilePath = "C:\\Users\\IBM_ADMIN\\git\\ecom\\ecom-web\\target\\backup\\backup.sql";

	// Create MySql Connection
	Class.forName("com.mysql.jdbc.Driver");
	Connection con = DriverManager.getConnection(
		"jdbc:mysql://localhost:3306/"+dbName, dbUser, dbPass);
	Statement stmt = null;

	try {
		System.out.println("restoring path and file: "+aSQLScriptFilePath);
		// Initialize object for ScripRunner
		ScriptRunner sr = new ScriptRunner(con, false, false);

		// Give the input file to Reader
		Reader reader = new BufferedReader(
                           new FileReader(aSQLScriptFilePath));

		// Exctute script
		sr.runScript(reader);

	} catch (Exception e) {
		System.err.println("Failed to Execute" + aSQLScriptFilePath
				+ " The error is " + e.getMessage());
	}
}
}
