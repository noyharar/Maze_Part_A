package Server;

import java.io.*;
import java.util.Properties;

public class Configurations {
    private static Configurations ourInstance = new Configurations();
    private Properties prop;
    public static Configurations getInstance() {
        return ourInstance;
    }

    private Configurations() {
        prop = new Properties();
        initProperties();

    }

    private void initProperties() {
        try (OutputStream output = new FileOutputStream("resources/config.properties")) {

            // set the properties value
            prop.setProperty("GeneratorType", "myMazeGenerator");
            prop.setProperty("ThreadPoolNum", "100");
            prop.setProperty("SolverType", "best");
            prop.setProperty("SolverTypePort", "5401");
            prop.setProperty("GeneratorTypePort", "5400");
            prop.setProperty("GeneratorTypeListeningInterval", "1000");
            prop.setProperty("SolverTypeListeningInterval", "1000");
            prop.setProperty("LogFilePath", "server.log");

            // save properties to project root folder
            prop.store(output, null);

            System.out.println(prop);

        } catch (IOException io) {
            io.printStackTrace();
        }
    }



    public Properties loadProperties()
    {
        try (InputStream input = new FileInputStream("resources/config.properties")) {

            prop.load(input);

            return prop;

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }


    public String getProperty(String key)
    {
        try (InputStream input = new FileInputStream("resources/config.properties")) {

            prop.load(input);
            String value = prop.getProperty(key);

            return value;


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return "";
    }
}
