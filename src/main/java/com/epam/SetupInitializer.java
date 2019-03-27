package com.epam;

import java.io.*;
import java.util.Properties;

class SetupInitializer {

    Setup readProperties() throws IOException {
        Properties properties = new Properties();
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("setup.properties");
        properties.load(resourceAsStream);
        return new Setup(properties);
    }
}
