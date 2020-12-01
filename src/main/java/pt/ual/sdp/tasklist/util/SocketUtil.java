package pt.ual.sdp.tasklist.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SocketUtil {
    public static Scanner getScanner(Socket socket) {
        try {
            return new Scanner(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            printError("Cannot get the input stream on socket " + socket.hashCode());
        }
        return null;
    }

    public static PrintWriter getPrintWritter(Socket socket) {
        try {
            return new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            printError("Cannot get the output stream on socket " + socket.hashCode());
        }
        return null;
    }

    private static void printError(String msg) {
        System.out.println(msg);
    }
}
