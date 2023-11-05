package trw.jroom;

import java.io.*;


public class FileManager {

    private static BufferedReader read = null;

    /**
     * Gets all filenames of a directory in a String Array.
     * With this you can iterate through the Array and read each filename.
     */
    public static String[] getArrayOfFilesInDirectory(String directoryPath) {
        File folder = new File(directoryPath);
        return folder.list();
    }


    /**
     * Takes in a filepath as a String and returns the file data as a String.
     */
    public static String readFromTxtFile(String filePath) throws IOException {
        if (!checkForFileExistence(filePath))
            return null;
        StringBuilder content = new StringBuilder();
        try {
            read = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
            while (read.ready()) {
                content.append(read.readLine());//.append("\n"); perhaps this is necessary
            }
        } catch (IOException e) {
            System.out.println("oh, things ain't going so well, perhaps in the readIO place?");
            throw new IOException("Failed to read file.");
        } finally {
            if (read != null) {
                read.close();
            }
        }
        return content.toString();
    }

    public static void writeToFile(String filePath, String content, boolean append) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(filePath, append)))) {
            writer.write(content);
        } catch (IOException e) {
            System.out.println("Failed to write to file.");
            throw new IOException("Failed to write File");
        }
    }

    /***
     * I'm starting to think returning just true or false and doing UI in another class would
     * be ideal
     * @return true if and only if the file is deleted.
     */
    public static boolean deleteFileFromDirectory(String directoryPath, String filename) {
        File toDelete = new File(directoryPath + "\\" + filename);
        if (toDelete.exists()) {
            return toDelete.delete();
        } else {
            return false;
        }
    }

    public static void createDirectory(String directoryPath) {
        File newDirectory = new File(directoryPath);
        if (newDirectory.mkdir()) {
            System.out.println("Directory path create \"" + directoryPath + "\"");
        }
    }

    public static boolean checkForFileExistence(String filepath) {
        File toCheck = new File(filepath);
        return toCheck.exists();
    }

    public static boolean checkForFileExistence(String directoryPath, String filename) {
        File toCheck = new File(directoryPath + "\\" + filename);
        return toCheck.exists();
    }

    public static void serializeObject(String filePath, Object object) throws IOException {
        FileOutputStream file = new FileOutputStream(filePath);
        ObjectOutputStream out = new ObjectOutputStream(file);

        out.writeObject(object);

        out.close();
        file.close();
    }

    public static Object deserializeObject(String filePath) throws IOException, ClassNotFoundException {
        FileInputStream file = new FileInputStream(filePath);
        ObjectInputStream in = new ObjectInputStream(file);

        Object object = in.readObject();

        in.close();
        file.close();
        return object;
    }

}
