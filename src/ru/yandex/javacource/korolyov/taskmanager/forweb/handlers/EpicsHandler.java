package ru.yandex.javacource.korolyov.taskmanager.forweb.handlers;

import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import ru.yandex.javacource.korolyov.taskmanager.manager.inmemorymanaged.exceptions.IntersectionException;
import ru.yandex.javacource.korolyov.taskmanager.manager.inmemorymanaged.interfaces.TaskManager;
import ru.yandex.javacource.korolyov.taskmanager.tasks.Epic;

import java.io.IOException;

public class EpicsHandler extends BaseHandler {
    public EpicsHandler(TaskManager taskManager) {
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
            response = gson.toJson(taskManager.getEpics());
            sendText(exchange, response, 200);
        } else if (path.length == 3) {
            try {
                int id = Integer.parseInt(path[2]);
                Epic epic = taskManager.getEpic(id);
                if (epic != null) {
                    response = gson.toJson(epic);
                    sendText(exchange, response, 200);
                } else {
                    sendNotFound(exchange);
                }
            } catch (StringIndexOutOfBoundsException | NumberFormatException e) {
                sendNotFound(exchange);
            }
        } else if (path.length == 4 && path[3].equals("subtasks")) {
            try {
                int id = Integer.parseInt(path[2]);
                Epic epic = taskManager.getEpic(id);
                if (epic != null) {
                    response = gson.toJson(epic);
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
            Epic epic = gson.fromJson(bodyRequest, Epic.class);
            if (taskManager.getEpic(epic.getId()) != null) {
                taskManager.updateEpic(epic);
                sendText(exchange, "Epic id: " + epic.getId() + " updated", 200);
            } else {
                try {
                    int epicId = taskManager.addNewEpic(epic);
                    sendText(exchange, Integer.toString(epicId), 201);
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
            taskManager.deleteEpic(id);
            sendText(exchange, "success", 200);
        } catch (StringIndexOutOfBoundsException | NumberFormatException | IOException e) {
            sendNotFound(exchange);
        }
    }
}
