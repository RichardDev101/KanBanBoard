package de.neuefische.backend.service;

import de.neuefische.backend.model.ToDo;
import de.neuefische.backend.repo.ToDoRepo;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Data
@Service
public class KanbanService {

    private final ToDoRepo toDoRepo;
    private final GenerateUUID uuid;

    public List<ToDo> listToDos() {
        return toDoRepo.listToDosAsList();
    }

    public void addToDos(ToDo toDo) {
        toDo.setId(uuid.getUUID());
        toDoRepo.addToDosToList(toDo);
    }

    public ToDo getToDo(String id) {
        return toDoRepo.getToDosById(id);
    }

    public void editToDos(String id, ToDo toDo) {
        toDoRepo.editRoDosByID(id, toDo);

    }

    public List<ToDo> deleteToDos(String id) {
        return toDoRepo.deleteToDosByID(id);

    }
}
