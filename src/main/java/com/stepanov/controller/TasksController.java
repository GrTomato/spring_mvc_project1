package com.stepanov.controller;

import com.stepanov.domain.Status;
import com.stepanov.domain.Task;
import com.stepanov.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class TasksController {

    private final TaskService service;

    @GetMapping("/tasks")
    public ModelAndView getAllTasks(
            ModelAndView view,
            @RequestParam(name = "taskToEditId", required = false) String taskToEditId,
            @RequestParam(name = "page", defaultValue = "0") String pageNumber){

        int pageSize = 5;
        List<Task> tasks = service.getAllByPage(Integer.parseInt(pageNumber), pageSize);

        Task taskToEdit = null;
        if (Objects.nonNull(taskToEditId)){
            Optional<Task> taskById = service.findById(Long.parseLong(taskToEditId));
            taskToEdit = taskById.orElse(null);
        }

        view.addObject("countPages", this.calculatePagesNumber(pageSize));
        view.addObject("taskStatuses", Status.values());
        view.addObject("tasks", tasks);
        view.addObject("taskToEdit", taskToEdit);

        view.setViewName("tasks");
        return view;
    }

    @PostMapping("/tasks/edit/{taskId}")
    public ModelAndView showTaskToEdit(
            ModelAndView view,
            @PathVariable String taskId){
        view.getModel().put("taskToEditId", taskId);
        view.setViewName("redirect:/tasks");
        return view;
    }

    @PostMapping("/tasks/delete/{taskId}")
    public ModelAndView deleteTaskById(
            ModelAndView view,
            @PathVariable String taskId){
        service.deleteById(Long.parseLong(taskId));
        view.setViewName("redirect:/tasks");
        return view;
    }

    private Long calculatePagesNumber(int pageSize){
        return service.count() / pageSize;
    }

}
