package com.example.srm.pojo;

import java.util.Date;

public class Department {
    private int id;
    private String departmentName;

    private Date creatDate;

    public Date getCreatDate() {
        return creatDate;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", departmentName='" + departmentName + '\'' +
                ", creatDate=" + creatDate +
                '}';
    }

    public void setCreatDate(Date creatDate) {
        this.creatDate = creatDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
