package pt.ual.sdp.tasklist.controllers;

import pt.ual.sdp.tasklist.models.Task;
import pt.ual.sdp.tasklist.util.SocketUtil;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;


public class TaskListServerThread extends Thread {
    private TaskListServer taskListServer;
    private Socket socket;

    public TaskListServerThread(TaskListServer taskListServer, Socket socket) {
        super();
        this.taskListServer = taskListServer;
        this.socket = socket;
    }

    @Override
    public void run() {
        Scanner scanner = SocketUtil.getScanner(this.socket);
        PrintWriter printWriter = SocketUtil.getPrintWritter(this.socket);

        while(true) {
            String line = scanner.nextLine();
            String[] splits = line.split(" ");
            debug(line);
            debug(taskListServer.tasksToJson());
            switch (splits[0]) {
                case "RT":
                    taskListServer.getTasks().add(buildTask(splits));
                    break;
                case "DT":
                    taskListServer.getTasks().remove(buildTask(splits));
                    break;
                case "LT":
                    printWriter.println(taskListServer.getTasks().size());
                    printWriter.flush();
                    for (Task task : taskListServer.getTasks()) {
                        printWriter.println(task.getDescription());
                        printWriter.flush();
                    }
                    break;
                case "C":
                    return;
                case "Q":
                    this.taskListServer.terminate();
                    break;
                default:
            }
        }
    }

    private static Task buildTask(String[] splits) {
        String[] descriptionFragments = Arrays.copyOfRange(splits, 1, splits.length);
        String description = String.join(" ", descriptionFragments);
        return new Task(description);
    }

    private void debug(String msg){
        String result = "{\"socket\":" +this.socket.hashCode() +
                ",\"message\":" + msg +
                "}";
        System.out.println(result);
    }
}
