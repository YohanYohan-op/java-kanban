package ru.yandex.javacource.korolyov.taskmanager.forweb.handlers;

import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import ru.yandex.javacource.korolyov.taskmanager.manager.inmemorymanaged.exceptions.IntersectionException;
import ru.yandex.javacource.korolyov.taskmanager.manager.inmemorymanaged.interfaces.TaskManager;
import ru.yandex.javacource.korolyov.taskmanager.tasks.Subtask;

import java.io.IOException;

public class SubtusksHandler extends BaseHandler {

    public SubtusksHandler(TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String[] path = exchange.getRequestURI().getPath().split("/");

        switch (method) {
            case "GET" -> handleGet(exchange, path);
            case "POST" -> handlePost(exchange);
            case "DELETE" -> handleDelete(exchange, path);
            default -> sendInternalServerError(exchange);
        }
    }

    private void handleGet(HttpExchange exchange, String[] path) throws IOException {
        if (path.length == 2) {
            response = gson.toJson(taskManager.getSubtasks());
            sendText(exchange, response, 200);
        } else {
            try {
                int id = Integer.parseInt(path[2]);
                Subtask subtask = taskManager.getSubtask(id);
                if (subtask != null) {
                    response = gson.toJson(subtask);
                    sendText(exchange, response, 200);
                } else {
                    sendNotFound(exchange);
                }
            } catch (StringIndexOutOfBoundsException | NumberFormatException e) {
                sendNotFound(exchange);
            }
        }
    }

    private void handlePost(HttpExchange exchange) throws IOException {
        String bodyRequest = readText(exchange);
        if (bodyRequest.isEmpty()) {
            sendNotFound(exchange);
            return;
        }
        try {
            Subtask subtask = gson.fromJson(bodyRequest, Subtask.class);
            if (taskManager.getSubtask(subtask.getId()) != null) {
                taskManager.updateSubTask(subtask);
                sendText(exchange, "Subtask id: " + subtask.getId() + " updated", 200);
            } else {
                try {
                    int subtId = taskManager.addNewSubtask(subtask);
                    sendText(exchange, Integer.toString(subtId), 201);
                } catch (IntersectionException e) {
                    sendHasInteractions(exchange);
                }

            }
        } catch (JsonSyntaxException e) {
            sendNotFound(exchange);
        }
    }

    private void handleDelete(HttpExchange exchange, String[] path) throws IOException {
        try {
            int id = Integer.parseInt(path[2]);
            taskManager.deleteSubTask(id);
            sendText(exchange, "success", 200);
        } catch (StringIndexOutOfBoundsException | NumberFormatException | IOException e) {
            sendNotFound(exchange);
        }
    }
}
