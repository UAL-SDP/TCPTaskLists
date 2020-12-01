package pt.ual.sdp.tasklist.views;

import pt.ual.sdp.tasklist.util.CLIException;
import pt.ual.sdp.tasklist.util.CLIUtil;
import pt.ual.sdp.tasklist.util.SocketUtil;
import pt.ual.sdp.tasklist.util.TaskListClientException;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TaskListClient {
    public static void main(String[] args) throws TaskListClientException, CLIException {
        boolean terminate = false;
        String serverAddress = CLIUtil.getAddress(args, 0);
        int port = CLIUtil.getPort(args, 1);
        Socket socket;
        try {
            socket = new Socket(serverAddress, port);
        } catch (IOException e) {
            e.printStackTrace();
            throw new TaskListClientException("Cannot connect to server at " + serverAddress + ", on port " + port);
        }
        PrintWriter printWritter = SocketUtil.getPrintWritter(socket);
        Scanner serverScanner = SocketUtil.getScanner(socket);

        Scanner localScanner = new Scanner(System.in);
        while (!terminate) {
            String line = localScanner.nextLine();
            printWritter.println(line);
            printWritter.flush();
            String[] splits = line.split(" ");
            switch (splits[0]) {
                case "RT":
                case "DT":
                    break;
                case "LT":
                    int numberOfLines = Integer.parseInt(serverScanner != null ? serverScanner.nextLine() : null);
                    while (numberOfLines > 0) {
                        System.out.println(serverScanner.nextLine());
                        numberOfLines--;
                    }
                    break;
                case "Q":
                case "C":
                    terminate = true;
                    break;
                default:
                    System.out.println("Unsupported operation.");
                    break;
            }
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new TaskListClientException("Could not close socket on exit.");
        }
    }
}
