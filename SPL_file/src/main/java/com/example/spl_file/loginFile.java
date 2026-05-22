package com.example.spl_file;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class loginFile {
    private static final String fileName = "Login.txt";
    private static void FileExists() {
        try {
            File file = new File(fileName);
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String[]> getAllUsers() {
        FileExists();
        List<String[]> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] data = line.split(",");
                if (data.length >= 5) {
                    users.add(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static void addUser(String username, String password, String question, String answer) {
        FileExists();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
            String data = username + "," + password + "," + question + "," + answer + "," + LocalDate.now();
            bw.write(data);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean updatePassword(String username, String newPassword, String question, String answer) {
        FileExists();
        List<String[]> users = getAllUsers();
        boolean updated = false;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (String[] user : users) {
                if (user[0].equals(username)) {
                    user[1] = newPassword;
                    user[2] = question;
                    user[3] = answer;
                    user[4] = LocalDate.now().toString();
                    updated = true;
                }
                bw.write(String.join(",", user));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return updated;
    }
}
