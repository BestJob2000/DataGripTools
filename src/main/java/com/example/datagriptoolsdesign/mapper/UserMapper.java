package com.example.datagriptoolsdesign.mapper;

import com.example.datagriptoolsdesign.bean.TraceBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

// 映射器接口，在配置文件中绑定命名空间，通过
@Mapper
@Repository
public interface UserMapper {
    // 根据创建的实体类来定义增删改查方法
    // 查询
    public List<TraceBean> Show_all();
    public List<TraceBean> QueryById(int id);
    // 增加
    public void AddTrace(TraceBean trace);

}
