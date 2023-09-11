package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String fileName;

    public Wget(String url, int speed, String fileName) {
        this.url = url;
        this.speed = speed;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        if (!urlValidator(this.url)) {
            System.out.println("Invalid URL");
            return;
        }

        File file = new File(this.fileName);
        if (file.exists()) {
            System.out.println("File already exists");
            return;
        }

        try {
            downloadFile(file);
        } catch (IOException e) {
            System.err.println("An error occurred while downloading the file: " + e.getMessage());
        }
    }

    private void downloadFile(File file) throws IOException {
        try (BufferedInputStream in = new BufferedInputStream(new URL(this.url).openStream());
             FileOutputStream out = new FileOutputStream(file)) {
            var dataBuffer = new byte[512];
            int bytesRead;
            var timeDownload = 0;
            var downloadAt = System.nanoTime();
            int totalBytesRead = 0;
            int totalTime = 0;
            double elapsedSeconds = 0;
            while ((bytesRead = in.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                out.write(dataBuffer, 0, bytesRead);
                timeDownload = (int) (System.nanoTime()  - downloadAt);
                System.out.println("Read 512 bytes : " + timeDownload + " nano.");
                totalBytesRead += bytesRead;
                totalTime += timeDownload;
                if (totalBytesRead >= speed) {
                    elapsedSeconds = (double)totalTime / 1_0000_000_000.0;
                }
                if (elapsedSeconds < 1) {
                    long sleepMillis = (long) elapsedSeconds * 1000;
                    Thread.sleep(sleepMillis);
                    totalTime = 0;
                    totalBytesRead = 0;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Files.size(file.toPath()) + " bytes");
    }

    private static boolean urlValidator(String url) {
        try {
            HttpURLConnection.setFollowRedirects(false);
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("HEAD");
            return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
        } catch (Exception e) {
            System.err.println("An error occurred while validating the URL: " + e.getMessage());
            return false;
        }
    }

    private static void validate(String[] args) {
        if (args.length < 3) {
            throw new IllegalArgumentException("Not enough arguments provided. Please provide a URL, download speed and filename");
        }
        try {
            Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Speed provided is not valid integer number");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        try {
            validate(args);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return;
        }
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String fileName = args[2];
        Thread wget = new Thread(new Wget(url, speed, fileName));
        wget.start();
        wget.join();
    }
}
