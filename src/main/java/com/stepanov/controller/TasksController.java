package com.stepanov.controller;

import com.stepanov.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class TasksController {

    private final TaskService service;

    @GetMapping("/tasks")
    public ModelAndView getAllTasks(
            ModelAndView view,
            @RequestParam(name = "page", defaultValue = "0") String pageNumber){

        int pageSize = 5;
        view.addObject("countPages", this.calculatePagesNumber(pageSize));

        view.addObject("tasks", service.getAllByPage(Integer.parseInt(pageNumber), pageSize));
        view.setViewName("tasks");
        return view;
    }

    private Long calculatePagesNumber(int pageSize){
        return service.count() / pageSize;
    }

}
