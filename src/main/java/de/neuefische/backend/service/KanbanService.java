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

    public ToDo addToDos(ToDo toDo) {
        toDo.setId(uuid.getUUID());
        return toDoRepo.addToDosToList(toDo);
    }

    public ToDo getToDo(String id) {
        return toDoRepo.getToDosById(id);
    }

    public ToDo editToDos(String id, ToDo toDo) {
        return toDoRepo.editRoDosByID(id, toDo);

    }

    public List<ToDo> deleteToDos(String id) {
        return toDoRepo.deleteToDosByID(id);
    }
}
