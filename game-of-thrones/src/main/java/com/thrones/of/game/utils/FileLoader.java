package com.thrones.of.game.utils;

import com.thrones.of.game.config.ApplicationConfiguration;
import com.thrones.of.game.domain.HousesModel;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class FileLoader {

    public static void loadFiles() throws IOException {
        ApplicationConfiguration applicationConfiguration = ApplicationConfiguration.getApplicationConfiguration();
        Properties properties = getResource("game.properties");
        applicationConfiguration.setGameProperties(properties);
        int i = 1;
        while (null != properties.getProperty("house." + i)) {
            String house = properties.getProperty("house." + i);
            System.out.println("loading house " + house + " ...");
            String houseContent = getResource(house + ".json", StandardCharsets.UTF_8.name());
            ObjectMapper objectMapper = new ObjectMapper();
            HousesModel housesModel = objectMapper.readValue(houseContent, HousesModel.class);
            applicationConfiguration.getHouseMap().put(house,housesModel);
            i++;
        }
    }

    public static Properties getResource(String fileName) throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = FileLoader.class.getClassLoader().getResourceAsStream(fileName);
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;
        } catch (IOException ioException) {
            throw ioException;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

    }

    public static String getResource(String fileName, String charSet) throws IOException {
        InputStream inputStream = null;
        ByteArrayOutputStream result = null;
        try {
            inputStream = FileLoader.class.getClassLoader().getResourceAsStream(fileName);
            result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            return result.toString(charSet);
        } catch (UnsupportedEncodingException exception) {
            throw exception;
        } finally {

            if (inputStream != null)
                inputStream.close();

            if (result != null)
                result.close();
        }


        //return result.toString(StandardCharsets.UTF_8.name());

    }

}
