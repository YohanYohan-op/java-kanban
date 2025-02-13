package ru.yandex.javacource.korolyov.taskmanager.forweb.handlers;

import com.sun.net.httpserver.HttpExchange;
import ru.yandex.javacource.korolyov.taskmanager.manager.inmemorymanaged.interfaces.TaskManager;

import java.io.IOException;

public class HistoryHandler extends BaseHandler {
    public HistoryHandler(TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        switch (method) {
            case "GET" -> handleGet(exchange);
            default -> sendInternalServerError(exchange);
        }
    }

    private void handleGet(HttpExchange httpExchange) throws IOException {
        response = gson.toJson(taskManager.getHistory());
        sendText(httpExchange, response, 200);
    }
}
