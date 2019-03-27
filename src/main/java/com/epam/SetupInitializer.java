package com.epam;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

class SetupInitializer {

    Setup readProperties() throws IOException {
        Properties properties = new Properties();
        String path = System.getProperty("java.class.path").split(";")[0];
        FileInputStream fileInputStream = new FileInputStream(path + "\\setup.properties");
        properties.load(fileInputStream);
        return new Setup(properties);
    }
}
