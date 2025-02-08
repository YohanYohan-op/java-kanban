package ru.yandex.javacource.korolyov.taskmanager.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.yandex.javacource.korolyov.taskmanager.forweb.adapters.DurationAdapter;
import ru.yandex.javacource.korolyov.taskmanager.forweb.adapters.LocalDateTimeAdapter;
import ru.yandex.javacource.korolyov.taskmanager.manager.inmemorymanaged.FileBackedTaskManager;
import ru.yandex.javacource.korolyov.taskmanager.manager.inmemorymanaged.InMemoryHistoryManager;
import ru.yandex.javacource.korolyov.taskmanager.manager.inmemorymanaged.interfaces.HistoryManager;
import ru.yandex.javacource.korolyov.taskmanager.manager.inmemorymanaged.interfaces.TaskManager;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;

public class Managers {

    public static TaskManager getDefault() {
        return new FileBackedTaskManager(new File("resources/task.csv"));
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static Gson getGson() {
        return new GsonBuilder()
                .serializeNulls()
                .setPrettyPrinting()
                .registerTypeAdapter(Duration.class, new DurationAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
    }

}
