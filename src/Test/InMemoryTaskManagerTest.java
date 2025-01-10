package Test;

import Manager.HistoryManager;
import Manager.Managers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import Class.*;

import java.util.ArrayList;
import java.util.HashMap;

class InMemoryTaskManagerTest {

    @BeforeAll
    static void BeforeAll() {
        HashMap<Integer, Task> tasks = new HashMap<>();
        HashMap<Integer, SubTask> subTasks = new HashMap<>();
        HashMap<Integer, Epic> epics = new HashMap<>();
        ArrayList<Task> history = new ArrayList<>();
        HistoryManager historyManager = Managers.getDefaultHistory();
    }

    //    проверьте, что экземпл€ры класса Task равны друг другу, если равен их id;
    @Test
    void equalsTaskByID() {


    }


    //    проверьте, что наследники класса Task равны друг другу, если равен их id;
    @Test
    void equalsSubTaskByID() {


    }

    @Test
    void equalsEpicByID() {


    }
//    проверьте, что объект Epic нельз€ добавить в самого себ€ в виде подзадачи;
//    проверьте, что объект Subtask нельз€ сделать своим же эпиком;
//    убедитесь, что утилитарный класс всегда возвращает проинициализированные и готовые к работе экземпл€ры менеджеров;
//    проверьте, что InMemoryTaskManager действительно добавл€ет задачи разного типа и может найти их по id;
//    проверьте, что задачи с заданным id и сгенерированным id не конфликтуют внутри менеджера;
//    создайте тест, в котором провер€етс€ неизменность задачи (по всем пол€м) при добавлении задачи в менеджер
//    убедитесь, что задачи, добавл€емые в HistoryManager, сохран€ют предыдущую версию задачи и еЄ данных.

}