package com.example.srm.controller;

import com.example.srm.pojo.Department;
import com.example.srm.service.depeartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class departmentController {

    @Autowired
    depeartmentService depeartmentservice;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;


    @RequestMapping("/getdepAllList")
    @ResponseBody
    public List<Department> getdepAllList(HttpServletRequest request) {
        String cursor =request.getParameter("cursor");
        String pageSize=request.getParameter("pageSize");
        Map<String,Object> paramMap=new HashMap<String,Object>();
        paramMap.put("cursor",cursor);
        paramMap.put("pageSize",Integer.parseInt(pageSize));
        List<Department> list = depeartmentservice.getList(paramMap);
        return list;
    }

    @RequestMapping("/addDepartment")
    @ResponseBody
    public Department addDepartment() {
        Department department = new Department();
        department.setDepartmentName("KOBE");
        department.setCreatDate(new Date());
        depeartmentservice.addDepeartment(department);
        redisTemplate.opsForValue().set(String.valueOf(department.getId()),department);
        Department department1= (Department) redisTemplate.opsForValue().get(String.valueOf(department.getId()));
//        stringRedisTemplate.opsForValue().set(department.getId()+"str",department);
        return department1;
    }

    @RequestMapping("/getdepList/{id}")
    @ResponseBody
    public Department getdepList(@PathVariable String id) {
        Department department1= (Department) redisTemplate.opsForValue().get(id);
        if(department1 ==null){
           return null;
        }
        return department1;
    }

    @RequestMapping("/getDepById/{id}")
    @ResponseBody
    public Department getDepById(@PathVariable String id){
        Department department=depeartmentservice.getDepById(Integer.parseInt(id));
        return department;
    }

}
