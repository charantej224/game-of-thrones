package com.thrones.of.game.utils;

import java.io.*;

/**
 * Class : InputOutputHelper
 * @param <T>
 * Class that helps to read files from the disk and write it to the disk.
 */
public class InputOutputHelper<T> {

    /**
     * Method : readFile
     * @param fileName
     * @return
     * @throws IOException
     * helps to read content from the disk and return the object choosen as generic type
     */
    public T readFile(String fileName) throws IOException {
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;

        try {
            fileInputStream = new FileInputStream(fileName);
            objectInputStream = new ObjectInputStream(fileInputStream);
            T readObject = (T) objectInputStream.readObject();
            return readObject;
        } catch (FileNotFoundException e) {
            System.out.println("Cache File not present, create new");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (objectInputStream != null)
                objectInputStream.close();
            if (fileInputStream != null)
                fileInputStream.close();
        }
        return null;
    }

    /**
     * Method : writeFile
     * @param fileName
     * @param t
     * @throws IOException
     * helps to write content with given object and file name to disk into the class path.
     */
    public void writeFile(String fileName, T t) throws IOException {
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(fileName);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(t);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (objectOutputStream != null)
                objectOutputStream.close();
            if (fileOutputStream != null)
                fileOutputStream.close();
        }
    }
}
