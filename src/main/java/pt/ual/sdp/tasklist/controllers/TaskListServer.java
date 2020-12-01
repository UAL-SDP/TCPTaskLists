package pt.ual.sdp.tasklist.controllers;

import pt.ual.sdp.tasklist.models.Task;
import pt.ual.sdp.tasklist.util.CLIException;
import pt.ual.sdp.tasklist.util.CLIUtil;
import pt.ual.sdp.tasklist.util.TaskListServerException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TaskListServer {
    private final List<Task> tasks;
    private boolean running;

    public TaskListServer() {
        tasks = new ArrayList<>();
        running = true;
    }

    public static void main(String[] args) throws CLIException, TaskListServerException {
        int port = CLIUtil.getPort(args, 0);
        TaskListServer taskListServer = new TaskListServer();
        ServerSocket serverSocket;
        Socket socket;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
            throw new TaskListServerException("Cannot start a ServerSocket on port " + port + ".");
        }
        System.out.println("[server started]");
        System.out.println("\t[server port] " + port);
        while (taskListServer.isRunning()) {
            try {
                socket = serverSocket.accept();
                System.out.println("[new connection]");
                System.out.println("\t[socket] " + socket.hashCode());
                System.out.println("\t[client public address] " + socket.getInetAddress());
                System.out.println("\t[client public port] " + socket.getPort());
                System.out.println("\t[client local address] " + socket.getLocalAddress());
                System.out.println("\t[client local port] " + socket.getLocalPort());
                new TaskListServerThread(taskListServer, socket).start();
            } catch (IOException e) {
                e.printStackTrace();
                throw new TaskListServerException("Cannot get socket from connection attempt.");
            }
        }
        System.out.println("[server terminated]");
    }

    public synchronized List<Task> getTasks() {
        return tasks;
    }

    public synchronized String tasksToJson() {
        StringBuilder result = new StringBuilder("{\"tasks\":");
        for (Task task : tasks) {
            result.append(task);
        }
        result.append("}");
        return result.toString();
    }

    private boolean isRunning() {
        return this.running;
    }

    public synchronized void terminate() {
        this.running = false;
    }


}
