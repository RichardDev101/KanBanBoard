package de.neuefische.backend.controller;

import de.neuefische.backend.model.ToDo;
import de.neuefische.backend.service.KanbanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class KanbanController {

    private final KanbanService kanbanService;

    @GetMapping("api/todo")
    public List<ToDo> listToDos(){
        return kanbanService.listToDos();
    }
    @PostMapping("api/todo")
    public ToDo addToDos(@RequestBody ToDo toDo){
        return kanbanService.addToDos(toDo);
    }
    @GetMapping("api/todo/{id}")
    public ToDo getToDo(@PathVariable String id){
        return kanbanService.getToDo(id);
    }



    @PutMapping("api/todo/{id}")
    public ToDo editToDos(@PathVariable String id , @RequestBody ToDo toDo){
        kanbanService.editToDos(id, toDo);
        return kanbanService.getToDo(id);
    }


    @DeleteMapping("api/todo/{id}")
    public List<ToDo>  deleteToDos(@PathVariable String id){
        return kanbanService.deleteToDos(id);
    }

}
