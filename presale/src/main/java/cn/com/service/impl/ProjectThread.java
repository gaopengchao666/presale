package cn.com.service.impl;

import java.util.List;

import cn.com.entity.Project;
import cn.com.service.ProjectService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gaopengchao
 * 2019年5月9日
 */
@Slf4j
public class ProjectThread extends Thread
{
    private ProjectService projectService;
    private String decbatchId;
    
    public ProjectThread(String id,ProjectService service)
    {
        this.projectService = service;
        this.decbatchId = id;
    }

    @Override
    public void run()
    {
        List<Project> projects = projectService.getAllProjects(decbatchId, true);
        log.info("批次号：" + decbatchId + "添加项目:" + projects.size());
        projectService.addProjects(projects);
    }
}
