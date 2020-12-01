package pt.ual.sdp.tasklist.models;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    private Task task;

    @BeforeEach
    void setUp() {
        this.task = new Task("test description");
    }

    @Test
    void shouldReturnTheDescription() {
        assertEquals("test description", task.getDescription());
    }

    @Test
    void shouldBeEqualByDescription() {
        assertNotEquals(new Object(), task);
        assertNotEquals(new Task("different description"), task);
        assertEquals(new Task("test description"), task);
    }

    @Test
    void shouldReturnJSONWhenToString() {
        assertEquals("{\"description\":\"test description\"}", task.toString());
    }
}