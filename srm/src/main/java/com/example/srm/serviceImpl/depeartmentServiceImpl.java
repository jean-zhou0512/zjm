package com.example.srm.serviceImpl;

import com.example.srm.dao.depeartmentDao;
import com.example.srm.pojo.Department;
import com.example.srm.service.depeartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class depeartmentServiceImpl implements depeartmentService {

    @Autowired
    depeartmentDao depeartmentdao;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public Department getDepById(Integer id) {
        return depeartmentdao.getDepById(id);
    }

    @Autowired
    StringRedisTemplate stringRedisTemplate;


    /**
     * 查询所有
     */
    @Override
    public List<Department> getList(Map<String,Object> paramMap) {
        List<Department> getList=depeartmentdao.getList(paramMap);
        return getList;
    }

    @Override
    public void addDepeartment(Department department) {
        depeartmentdao.addDepeartment(department);

    }


}
