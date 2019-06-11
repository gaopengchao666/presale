package cn.com.service;

import java.util.List;
import java.util.Map;

import cn.com.entity.Project;

/**
 * 项目服务接口类
 * @author gaopengchao
 * 2019年5月10日
 */
public interface ProjectService
{
    /**
     * 
     */
    public void processProject();

    /**
     * 
     */
    public void processAllProject();

    /**
     * 根据批次号获取当前批次所有项目
     */
    List<Project> getAllProjects(String decbatchId, boolean isFull);

    /**
     * @param projects
     */
    void addProjects(List<Project> projects);

    /**
     * @param params
     * @return
     */
    public List<Project> queryProjects(Map<String, Object> params);
}
