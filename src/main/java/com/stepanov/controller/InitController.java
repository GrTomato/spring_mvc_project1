package com.stepanov.controller;

import com.stepanov.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class InitController {

    private final TaskService service;

    @GetMapping("/")
    public String initPageRedirect(){
        return "redirect:/tasks";
    }

    @GetMapping("/tasks")
    public ModelAndView getAllTasks(ModelAndView view){
        view.addObject("tasks", service.findAll());
        view.setViewName("tasks");
        return view;
    }

}
