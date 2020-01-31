package TFSAutomation.tfs;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;



public class Session {
	
	static Properties prop = new Properties();
	static Properties log = new Properties();
	static String defaultConfigFile="./Config.properties";
	static String logFile="./logFile.properties";
	static public Map<String, String> _getSessionConfig() {
        String[] configKeys = {"Release", "Sprint", "AppUrl", "UserName", "Password","Testplan","TestType"};
        Map<String, String> config;
        config = new HashMap<String, String>();
        for (String string : configKeys) {
        	if(System.getProperty(string)==null || System.getProperty(string).isEmpty())
            config.put(string, getProperty(string));
        	
        	else
        	config.put(string, System.getProperty(string));
        }
        return config;
    }
	static public String getProperty(String Property) {
        try {
        	prop.load(new FileInputStream(new File(defaultConfigFile)) );
            return prop.getProperty(Property);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
	
	/*
    Create new property file if does not exist
    */
   
	public static void createNewPropertyFile() {
       File propertyFile = new File(logFile);
       try {
           if(!propertyFile.exists()) 
           {
               System.out.println("####################################################################");
               System.out.println("Creating new log file...");
               propertyFile.createNewFile();
           } 
           else
           {
               System.out.println("log file is already created");
           }
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
	
	 /**
     * This method is used to store a new property in the properties file.
     *
     * @param property : name for the new property.
     * @param value : value for the new property.
     * @throws IOException
     */
    public static void writeProperty(String property, String value) {
        // Write properties file.
        // OutputStream outPropFile = null;
    	clearProperty();

        try {
            InputStream inPropFile = new FileInputStream(logFile);
            log.load(inPropFile);
            inPropFile.close();
            OutputStream outPropFile = new FileOutputStream(logFile);
            // System.getProperty("user.dir")+"\\AnswerKeys\\"+top+".properties"

            log.setProperty(property, value);
            log.store(outPropFile, null);
            outPropFile.close();
        } catch (IOException e) {
        }
    }
    
    public static void clearProperty() {
        try {
            InputStream inPropFile = new FileInputStream(logFile);
            log.load(inPropFile);
            inPropFile.close();
            OutputStream outPropFile = new FileOutputStream(logFile);
            log.clear();
            outPropFile.close();
        } catch (IOException e) {
        }
    }

}
