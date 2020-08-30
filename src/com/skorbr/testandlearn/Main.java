package com.skorbr.testandlearn;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {
        //parse commands
        Map<String, String> commands = new HashMap<>();
        for (int i = 0; i < args.length; i += 2) {
            commands.put(args[i], args[i + 1]);
        }
        //If there is no -mode, the program should work in enc mode.
        String mode = commands.getOrDefault("-mode", "enc");
        //If there is no -key, the program should consider that key = 0.
        int key = commands.containsKey("-key") ? Integer.parseInt(commands.get("-key")) : 0;
        String data;
        //If there are both -data and -in arguments, your program should prefer -data over -in.
        if (commands.containsKey("-data") == true &&
                (commands.containsKey("-in") == true || commands.containsKey("-in") == false)) {
            data = commands.get("-data");
        } else if ((commands.containsKey("-data") == false && commands.containsKey("-in") == true)) {
            data = Utils.readFileAsString(".\\" + commands.get("-in"));
        //If there is no -data, and there is no -in the program should assume that the data is an empty string.
        } else {
            data = "";
        }
        String fileOut = commands.getOrDefault("-out", "");
        String alg = commands.getOrDefault("-alg", "shift");

        //call functions
        ChooseEncryptionStrategy encryptionStrategy = new ChooseEncryptionStrategy();
        switch (mode) {
            case "enc":
                if (alg.equals("unicode")) {
                    encryptionStrategy.chooseEncryptionStrategy(new EncryptionUnicode());
                } else {
                    encryptionStrategy.chooseEncryptionStrategy(new EncryptionShift());
                }
                encryptionStrategy.execute(data.toCharArray(), key, fileOut);
                break;
            case "dec":
                if (alg.equals("unicode")) {
                    encryptionStrategy.chooseEncryptionStrategy(new DecryptionUnicode());
                } else {
                    encryptionStrategy.chooseEncryptionStrategy(new DecryptionShift());
                }
                encryptionStrategy.execute(data.toCharArray(), key, fileOut);
                break;
        }
    }
}

class Utils {
    public static char charEncrypt(char c, int key) {
        if (c >= 'a' && c <= 'z') {
            return (char) (((c - 'a' + key) % 26) + 'a');
        } else if (c >= 'A' && c <= 'Z') {
            return (char) (((c - 'A' + key) % 26) + 'A');
        } else {
            return c;
        }
    }

    public static String readFileAsString(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }
}

interface EncryptionStrategy {
    void encrypt(char[] string, int key, String fileOutPath);
}

class ChooseEncryptionStrategy {
    private EncryptionStrategy encryptionStrategy;

    public void chooseEncryptionStrategy(EncryptionStrategy encryptionStrategy) {
        this.encryptionStrategy = encryptionStrategy;
    }

    public void execute(char[] string, int key, String fileOutPath) {
        encryptionStrategy.encrypt(string, key, fileOutPath);
    }
}

class EncryptionUnicode implements EncryptionStrategy {
    @Override
    public void encrypt(char[] string, int key, String fileOutPath) {
        //If there is no -out argument, the program must print data to the standard output.
        if (fileOutPath.isEmpty() == true) {
            for (char c : string) {
                char shiftChar = (char) (c + key);
                System.out.print(shiftChar);
            }
        } else {
            try (FileWriter fileWriter = new FileWriter(new File(".\\" + fileOutPath))) {
                for (char c : string) {
                    char shiftChar = (char) (c + key);
                    fileWriter.write(shiftChar);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

class DecryptionUnicode implements EncryptionStrategy {
    @Override
    public void encrypt(char[] string, int key, String fileOutPath) {
        //If there is no -out argument, the program must print data to the standard output.
        if (fileOutPath.isEmpty() == true) {
            for (char c : string) {
                char shiftChar = (char) (c - key);
                System.out.print(shiftChar);
            }
        } else {
            try (FileWriter fileWriter = new FileWriter(new File(".\\" + fileOutPath))) {
                for (char c : string) {
                    char shiftChar = (char) (c - key);
                    fileWriter.write(shiftChar);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

class EncryptionShift implements EncryptionStrategy {
    @Override
    public void encrypt(char[] string, int key, String fileOutPath) {
        //If there is no -out argument, the program must print data to the standard output.
        if (fileOutPath.isEmpty() == true) {
            for (char c : string) {
                System.out.print(Utils.charEncrypt(c, key));
            }
        } else {
            try (FileWriter fileWriter = new FileWriter(new File(".\\" + fileOutPath))) {
                for (char c : string) {
                    fileWriter.write(Utils.charEncrypt(c, key));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

class DecryptionShift implements EncryptionStrategy {
    @Override
    public void encrypt(char[] string, int key, String fileOutPath) {
        if (fileOutPath.isEmpty() == true) {
            for (char c : string) {
                System.out.print(Utils.charEncrypt(c, 26 - (key % 26)));
            }
        } else {
            try (FileWriter fileWriter = new FileWriter(new File(".\\" + fileOutPath))) {
                for (char c : string) {
                    fileWriter.write(Utils.charEncrypt(c, 26 - (key % 26)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}