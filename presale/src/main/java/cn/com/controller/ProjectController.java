package cn.com.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.entity.Project;
import cn.com.service.ProjectService;

/**
 * @author gaopengchao
 * 2019年6月11日
 */
@RequestMapping("project")
@Controller
public class ProjectController extends BaseController
{ 
    @Resource
    private ProjectService projectService;
    
    
    @RequestMapping("/queryProjects")
    public String queryProjects() 
    {
        return "projectList";
    }
    
    @RequestMapping("/queryProjectList")
    @ResponseBody
    public Map<String,Object> queryProjectList(HttpServletRequest request) 
    {
        Map<String, Object> params = getRequestParams(request);
        List<Project> projects = projectService.queryProjects(params);
        params.put("list", projects);
        return params;
    }
}
