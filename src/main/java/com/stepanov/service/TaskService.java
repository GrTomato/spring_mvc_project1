package com.stepanov.service;

import com.stepanov.dao.TaskDao;
import com.stepanov.domain.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskService {
    @Autowired
    private final TaskDao taskDao;

    public List<Task> findAll(){
        return taskDao.findAll();
    }

    public Long count(){ return taskDao.count(); }

}
