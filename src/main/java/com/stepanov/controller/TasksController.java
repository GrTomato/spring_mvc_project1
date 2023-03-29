package com.stepanov.controller;

import com.stepanov.domain.Status;
import com.stepanov.domain.Task;
import com.stepanov.service.TaskService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TasksController {

    private final TaskService service;

    @GetMapping("")
    public ModelAndView getTasksList(
            ModelAndView view,
            HttpSession session,
            @CookieValue(name = "pageNumber", defaultValue = "0") String pageNumber,
            @CookieValue(name = "pageSize", defaultValue = "5") String pageSize
    ){
        view.addObject("taskToEdit", new Task());
        view.addObject("countPages", (int) Math.ceil((double)service.getTasksCount() / Long.parseLong(pageSize)));
        view.addObject("taskStatuses", Status.values());
        view.addObject("tasks", service.getAllByPage( Integer.parseInt(pageNumber), Integer.parseInt(pageSize)));
        view.setViewName("tasks");
        return view;
    }

    @GetMapping("/{id}")
    public ModelAndView getTasksListEditOne(
            ModelAndView view,
            @PathVariable String id,
            @CookieValue(name = "pageNumber", defaultValue = "0") String pageNumber,
            @CookieValue(name = "pageSize", defaultValue = "5") String pageSize
    ){
        Task foundTask = service.findById(Long.parseLong(id));
        if (Objects.nonNull(foundTask)){
            view.addObject("taskToEdit", foundTask);
            view.addObject("countPages", (int) Math.ceil((double)service.getTasksCount() / Long.parseLong(pageSize)));
            view.addObject("taskStatuses", Status.values());
            view.addObject("tasks", service.getAllByPage(Integer.parseInt(pageNumber), Integer.parseInt(pageSize)));
            view.setViewName("tasks");
        } else {
            view.setViewName("redirect:/tasks");
        }
        return view;
    }

    @GetMapping("/page/{number}")
    public String changePage(
            HttpServletResponse response,
            @PathVariable String number
    ){
        Cookie pageNumber = new Cookie("pageNumber", number);
        pageNumber.setPath("/tasks");
        response.addCookie(pageNumber);
        return "redirect:/tasks";
    }

    @PostMapping("/{id}")
    public String updateTask(
            Task taskToEdit
    ){

        boolean updateSuccess = service.update(taskToEdit);
        if (!updateSuccess){
            log.warn("Submit error: Task %s was not updated correctly.".formatted(taskToEdit.toString()));
        }

        return "redirect:/tasks";
    }

    @PostMapping("")
    public String createTask(
            @ModelAttribute Task taskToEdit
    ){
        boolean saveSuccess = service.save(taskToEdit);
        if (!saveSuccess){
            log.warn("Submit error: Task %s was not saved correctly.".formatted(taskToEdit.toString()));
        }
        return "redirect:/tasks";
    }

    @PostMapping("/delete/{taskId}")
    public ModelAndView deleteTask(
            ModelAndView view,
            @PathVariable String taskId){

        boolean deleteSuccess = service.deleteById(Long.valueOf(taskId));
        if (!deleteSuccess){
            log.warn("Delete error: Task %s was not found during update.".formatted(taskId.toString()));
        }

        view.setViewName("redirect:/tasks");
        return view;
    }

}
