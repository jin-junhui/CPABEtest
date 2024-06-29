package com.example;

import co.junwei.cpabe.Cpabe;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class App {
    public static void main(String[] args) {
        try {
            System.out.println("Creating Cpabe object...");
            Cpabe cpabe = new Cpabe();
            System.out.println("Cpabe object created successfully.");
            String pubfile = "src/main/java/com/example/keys/pub_key.txt";
            String mskfile = "src/main/java/com/example/keys/msk_key.txt";
            cpabe.setup(pubfile, mskfile);

            File tmpDir = new File("tmp/cpabe");
            if (!tmpDir.exists()) {
                tmpDir.mkdirs();
            }

            // 定义用户属性
            String user0Attributes = "org:org0 dept:dept1 level:0";
            String user1Attributes = "org:org1 dept:dept1 level:1";
            String user2Attributes = "org:org1 dept:dept2 level:2";


            cpabe.keygen(pubfile, "src/main/java/com/example/keys/key0.txt", mskfile, user0Attributes);
            cpabe.keygen(pubfile, "src/main/java/com/example/keys/key1.txt", mskfile, user1Attributes);
            cpabe.keygen(pubfile, "src/main/java/com/example/keys/key2.txt", mskfile, user2Attributes);

            byte[] encKey0 = cpabe.enc(pubfile, "level:0 level:1 1of2 org0 2of2", "AESKey0", "encrypted/encrypted0.txt");

            String policy0 = "level:0 level:1 1of2 org:org0 2of2";
            String policy1 = "dept:dept1 1of1";
            String policy2 = "org:org1 1of1";


            String fileContent0 = new String(Files.readAllBytes(Paths.get("src/main/java/com/example/files/file0.txt")));
            String AESKey0 = "mySecretAESKey0";
            byte[] encryptedFile0 = cpabe.enc(pubfile, policy0, fileContent0, "src/main/java/com/example/encrypted/encrypted0.txt");
            Files.write(Paths.get("src/main/java/com/example/encrypted/encrypted0.txt"), Base64.getEncoder().encode(encryptedFile0));

            String fileContent1 = new String(Files.readAllBytes(Paths.get("src/main/java/com/example/files/file1.txt")));
            String AESKey1 = "mySecretAESKey1";
            byte[] encryptedFile1 = cpabe.enc(pubfile, policy1, fileContent1, "src/main/java/com/example/encrypted/encrypted1.txt");
            Files.write(Paths.get("src/main/java/com/example/encrypted/encrypted1.txt"), Base64.getEncoder().encode(encryptedFile1));

            String fileContent2 = new String(Files.readAllBytes(Paths.get("src/main/java/com/example/files/file2.txt")));
            String AESKey2 = "mySecretAESKey2";
            byte[] encryptedFile2 = cpabe.enc(pubfile, policy2, fileContent2, "src/main/java/com/example/encrypted/encrypted2.txt");
            Files.write(Paths.get("src/main/java/com/example/encrypted/encrypted2.txt"), Base64.getEncoder().encode(encryptedFile2));


            byte[] encFileContent0 = Base64.getDecoder().decode(Files.readAllBytes(Paths.get("src/main/java/com/example/encrypted/encrypted0.txt")));
            byte[] decryptedFile0 = cpabe.dec(pubfile, "src/main/java/com/example/keys/key0.txt", encFileContent0);
            Files.write(Paths.get("src/main/java/com/example/decrypted/decrypted0.txt"), decryptedFile0);

            byte[] encFileContent1 = Base64.getDecoder().decode(Files.readAllBytes(Paths.get("src/main/java/com/example/encrypted/encrypted1.txt")));
            byte[] decryptedFile1 = cpabe.dec(pubfile, "src/main/java/com/example/keys/key1.txt", encFileContent1);
            Files.write(Paths.get("src/main/java/com/example/decrypted/decrypted1.txt"), decryptedFile1);

            byte[] encFileContent2 = Base64.getDecoder().decode(Files.readAllBytes(Paths.get("src/main/java/com/example/encrypted/encrypted2.txt")));
            byte[] decryptedFile2 = cpabe.dec(pubfile, "src/main/java/com/example/keys/key2.txt", encFileContent2);
            Files.write(Paths.get("src/main/java/com/example/decrypted/decrypted2.txt"), decryptedFile2);

            System.out.println("Decrypted content of file0.txt:");
            System.out.println(new String(decryptedFile0));

            System.out.println("Decrypted content of file0.txt:");
            System.out.println(new String(decryptedFile1));

            System.out.println("Decrypted content of file0.txt:");
            System.out.println(new String(decryptedFile2));



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}