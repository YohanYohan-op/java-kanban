package manager;

import org.junit.jupiter.api.Test;

import ru.yandex.javacource.korolyov.taskmanager.manager.InMemoryHistoryManager;
import ru.yandex.javacource.korolyov.taskmanager.manager.InMemoryTaskManager;
import ru.yandex.javacource.korolyov.taskmanager.manager.Managers;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    @Test
    void getDefaultShouldInitializeInMemoryTaskManager() {
        assertInstanceOf(InMemoryTaskManager.class, Managers.getDefault());
    }

    @Test
    void getDefaultHistoryShouldInitializeInMemoryHistoryManager() {
        assertInstanceOf(InMemoryHistoryManager.class, Managers.getDefaultHistory());
    }
}