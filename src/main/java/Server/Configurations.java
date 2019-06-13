package Server;

import java.io.*;
import java.util.Properties;
import java.util.Set;

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

    public void setProperties(String propName,String propValue)
    {
        try (OutputStream output = new FileOutputStream("resources/config.properties")) {

            // set the properties value
            prop.setProperty(propName, propValue);


            // save properties to project root folder
            prop.store(output, null);

//            System.out.println(prop);


        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    private void initProperties() {
        try (OutputStream output = new FileOutputStream("resources/config.properties")) {

            // set the properties value
            prop.setProperty("GeneratorType", "mymaze");
            prop.setProperty("ThreadPoolNum", "20");
            prop.setProperty("SolverType", "best");
            prop.setProperty("SolverTypePort", "5401");
            prop.setProperty("GeneratorTypePort", "5400");
            prop.setProperty("GeneratorTypeListeningInterval", "1000");
            prop.setProperty("SolverTypeListeningInterval", "1000");

            // save properties to project root folder
            prop.store(output, null);

//            System.out.println(prop);

        } catch (IOException io) {
            io.printStackTrace();
        }
    }


//
//    public Set<Object> loadProperties()
//    {
//        try (InputStream input = new FileInputStream("resources/config.properties")) {
//
//            prop.load(input);
//
//            return prop.keySet();
//
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//
//        return null;
//    }


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
