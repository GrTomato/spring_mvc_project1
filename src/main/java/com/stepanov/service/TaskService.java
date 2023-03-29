package com.stepanov.service;

import com.stepanov.dao.TaskDao;
import com.stepanov.domain.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskService {
    @Autowired
    private final TaskDao taskDao;

    public Task findById(Long id){
        Optional<Task> taskById = taskDao.findById(id);
        return taskById.orElse(null);
    }

    public Long getTasksCount(){ return taskDao.count(); }

    public List<Task> getAllByPage(int pageNumber, int pageSize){
        PageRequest pageable = PageRequest.of(pageNumber, pageSize);
        Page<Task> taskPage = taskDao.findAll(pageable);
        return taskPage.stream().toList();
    }

    @Transactional
    public boolean save(Task task){
        Task savedTask = taskDao.save(task);
        return savedTask.getId() != null;
    }

    @Transactional
    public boolean update(Task task){
        Optional<Task> foundById = taskDao.findById(task.getId());
        if (foundById.isPresent()){
            Task savedTask = taskDao.save(task);
            return savedTask.getId() != null;
        } else {
            return false;
        }
    }

    @Transactional
    public boolean deleteById(Long id){
        Optional<Task> taskById = taskDao.findById(id);
        if(taskById.isPresent()){
            taskDao.deleteById(id);
            return true;
        } else {
            return false;
        }
    }


}
