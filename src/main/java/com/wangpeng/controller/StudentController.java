package com.wangpeng.controller;

import com.wangpeng.pojo.Student;
import com.wangpeng.service.StudentService;
import com.wangpeng.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService service;

    /**
     * 查询所有学生
     * @param page  当前页码
     * @param limit 每页大小
     * @return 数据
     */
    @RequestMapping("queryStudents.do")
    @ResponseBody
    public Map<String,Object> queryStudents(Integer page, Integer limit){
        //获取学生数量
        int count = service.getStudentsCount();
        //获取数据
        List<Student> students = service.findStudentsByPage(page,limit);
        //结果map
        Map<String,Object> res = new HashMap<String,Object>();
        res.put("code", 0);
        res.put("msg", "");
        res.put("count", count);
        res.put("data", students);

        return res;
    }

    /**
     * 删除学生
     * @param json
     * @return 返回成功的行数
     */
    @RequestMapping("deleteStudents.do")
    @ResponseBody
    public Integer deleteStudents(String json){
        if(json.charAt(0) != '[') json = '[' + json + ']';  //如果不是数组形式，变成数组形式
        List<Student> students = JsonUtil.parseList(json, Student.class);
        return service.deleteStudents(students);
    }

    /**
     * 添加一个学生
     * @param json
     * @return 成功标志1
     */
    @RequestMapping("addStudent.do")
    @ResponseBody
    public Integer addStudent(String json){
        Student student = JsonUtil.parseObject(json, Student.class);
        return service.addStudent(student);
    }

    /**
     * 修改一个学生
     * @param json
     * @return 成功标志1
     */
    @RequestMapping("updateStudent.do")
    @ResponseBody
    public Integer updateStudent(String json){
        Student student = JsonUtil.parseObject(json, Student.class);
        return service.updateStudent(student);
    }

    /**
     * 获取学生总数
     * @return
     * @throws IOException
     */
    @RequestMapping("getAmount.do")
    @ResponseBody
    public Integer getAmount() {
        return service.getStudentsCount();
    }

    @RequestMapping("searchStudents.do")
    @ResponseBody
    public Map<String,Object> searchStudents(Integer page, Integer limit, String json){
        //获得搜索的参数
        Map<String, Object> searchParam = JsonUtil.parseMap(json, String.class, Object.class);
        //获取查询个数
        int count = service.getSearchCount(searchParam);
        //查询数据
        List<Student> students = service.searchStudents(page, limit, searchParam);
        //结果map
        Map<String,Object> res = new HashMap<String,Object>();
        res.put("code", 0);
        res.put("msg", "");
        res.put("count", count);
        res.put("data", students);
        return res;
    }

}
