package ru.yandex.javacource.korolyov.taskmanager.forweb.server;

import com.sun.net.httpserver.HttpServer;
import ru.yandex.javacource.korolyov.taskmanager.forweb.handlers.*;
import ru.yandex.javacource.korolyov.taskmanager.manager.Managers;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        HttpServer httpServer = HttpServer.create();

        httpServer.bind(new InetSocketAddress(PORT), 0); // связываем сервер с сетевым портом
        httpServer.createContext("/hello", new BaseHandler(Managers.getDefault()));
        httpServer.createContext("/epics", new EpicsHandler(Managers.getDefault()));
        httpServer.createContext("/history", new HistoryHandler(Managers.getDefault()));
        httpServer.createContext("/prioritized", new PrioritizedHandler(Managers.getDefault()));
        httpServer.createContext("/subtasks", new SubtusksHandler(Managers.getDefault()));
        httpServer.createContext("/tasks", new TasksHandler(Managers.getDefault()));// связываем путь и обработчик
        httpServer.start(); // запускаем сервер

        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");


    }
}