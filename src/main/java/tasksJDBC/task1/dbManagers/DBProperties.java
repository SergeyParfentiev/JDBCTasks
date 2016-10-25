package tasksJDBC.task1.dbManagers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBProperties {

    private String classInstance;
    private String url;
    private String user;
    private String password;

    public DBProperties(String propertiesName) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertiesName);

        Properties properties = new Properties();

        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        classInstance = properties.getProperty("classInstance");
        url = properties.getProperty("url");
        user = properties.getProperty("user");
        password = properties.getProperty("password");
    }

    public String getClassInstance() {
        return classInstance;
    }

    public void setClassInstance(String classInstance) {
        this.classInstance = classInstance;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
