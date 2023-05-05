package de.neuefische.backend.repo;

import de.neuefische.backend.model.ToDo;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Data
@Repository
public class ToDoRepo {

    private final Map<String, ToDo> todos = new HashMap<>();

    public List<ToDo> listToDosAsList() {
        return new ArrayList<>(todos.values());
    }

    public void addToDosToList(ToDo toDo) {
         todos.put(toDo.getId(),toDo);
    }

    public ToDo getToDosById(String id) {
        return todos.get(id);
    }

    public void editRoDosByID(String id, ToDo toDo) {
        todos.put(id, toDo);
    }

    public List<ToDo> deleteToDosByID(String id) {
        todos.remove(id);
        return listToDosAsList();
    }
}
