package com.heima.schedule.service.impl;

import com.heima.common.constants.ScheduleConstants;
import com.heima.common.redis.CacheService;
import com.heima.model.schedule.dtos.Task;
import com.heima.schedule.ScheduleApplication;
import com.heima.schedule.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ScheduleApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
class TaskServiceImplTest {
    @Autowired
    private TaskService taskService;
    @Autowired
    private CacheService cacheService;

    @Test
    void addTask() {
        for (int i = 0; i < 6; i++) {
            Task task = new Task();
            task.setTaskType(100);
            task.setPriority(70);
            task.setParameters("task test".getBytes());
            task.setExecuteTime(new Date().getTime() + 50000);

            long taskId = taskService.addTask(task);
            System.out.println(taskId);
        }

    }



}