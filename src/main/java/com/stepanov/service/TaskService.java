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

    public Optional<Task> findById(Long id){
        return taskDao.findById(id);
    }

    public Long count(){ return taskDao.count(); }

    public List<Task> getAllByPage(int pageNumber, int pageSize){
        PageRequest pageable = PageRequest.of(pageNumber, pageSize);
        Page<Task> taskPage = taskDao.findAll(pageable);
        return taskPage.stream().toList();
    }

    @Transactional
    public void deleteById(Long id){
        Optional<Task> taskById = taskDao.findById(id);
        if(taskById.isPresent()){
            System.out.println("Task " + taskById.get().getId() + " was deleted.");
            // taskDao.deleteById(id);
        } else {
            // change to correct error
            System.out.println("Task was not deleted because no such task.");
        }
    }


}
