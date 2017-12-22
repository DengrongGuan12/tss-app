package cn.superid.tss.model;

import org.exemodel.annotation.CacheOrder;
import org.exemodel.annotation.Cacheable;
import org.exemodel.orm.ExecutableModel;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author DengrongGuan
 * @create 2017-12-20 下午4:25
 **/
@Table(name = "user")
@Cacheable(key = "user")
public class UserEntity extends ExecutableModel{
    private long id;
    @CacheOrder(0)
    private String number;
    @CacheOrder(1)
    private String department;
    @CacheOrder(2)
    private long departmentId;
    @CacheOrder(3)
    private long schoolId;
    @CacheOrder(4)
    private String grade;// 年级
    @CacheOrder(5)
    private int degree;
    @CacheOrder(6)
    private int type;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    public long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(long schoolId) {
        this.schoolId = schoolId;
    }
}
