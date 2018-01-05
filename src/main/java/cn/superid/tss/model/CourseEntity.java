package cn.superid.tss.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.exemodel.orm.ExecutableModel;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author DengrongGuan
 * @create 2017-12-22 上午10:08
 **/
@Table(name = "course")
@ApiModel
public class CourseEntity extends CModel{
    @Id
    @Column(name = "id")
    private long id;
    @ApiModelProperty(required = true)
    private String number;
    @ApiModelProperty(value = "2017", required = true)
    private String year;
    @ApiModelProperty(value = "Spring,Summer,Fall,Winter", required = true)
    private int season;
    @ApiModelProperty(required = true, value = "大一,大二,.....")
    private int grade;
    @ApiModelProperty(required = true)
    private Date startDate;
    @ApiModelProperty(required = true)
    private Date endDate;
    @ApiModelProperty(required = true)
    private int credit;
    private String inviteCode;
    @ApiModelProperty(value = "1:required,0:optional",required = true)
    private int type;

    public CourseEntity(){}

    public static CourseEntity mock(long id){
        return new CourseEntity(id,"124","2015",2,0,new Date(),new Date(),4,null,0);
    }

    public CourseEntity(long id, String number, String year, int season, int grade, Date startDate, Date endDate, int credit, String inviteCode, int type) {
        this.id = id;
        this.number = number;
        this.year = year;
        this.season = season;
        this.grade = grade;
        this.startDate = startDate;
        this.endDate = endDate;
        this.credit = credit;
        this.inviteCode = inviteCode;
        this.type = type;
    }

    public CourseEntity(String number, String year, int season, int grade, Date startDate, Date endDate, int credit, String inviteCode, int type){
        this.number = number;
        this.year = year;
        this.season = season;
        this.grade = grade;
        this.startDate = startDate;
        this.endDate = endDate;
        this.credit = credit;
        this.inviteCode = inviteCode;
        this.type = type;
    }

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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
