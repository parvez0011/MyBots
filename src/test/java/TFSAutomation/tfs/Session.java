package TFSAutomation.tfs;


import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;



public class Session {
	
	static Properties prop = new Properties();
	static String defaultConfigFile="./Config.properties";
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
}
