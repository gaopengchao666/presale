package cn.com.dao;

import java.util.List;

import cn.com.entity.Project;

/**
 * @author gaopengchao
 * 2019年6月5日
 */
public interface ProjectMapper
{

    /**
     * 通过业务id查询项目
     * @param projectId
     */
    Project queryProject(String projectId);
    
    /**
     * 查询已有项目数量
     * @return
     */
    int queryProjectCount();

    /**
     * 添加所有项目
     */
    void insertProjects(List<Project> projects);
}
