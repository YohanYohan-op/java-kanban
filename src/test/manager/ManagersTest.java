package manager;

import org.junit.jupiter.api.Test;

import ru.yandex.javacource.korolyov.taskManager.manager.InMemoryHistoryManager;
import ru.yandex.javacource.korolyov.taskManager.manager.InMemoryTaskManager;
import ru.yandex.javacource.korolyov.taskManager.manager.Managers;

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