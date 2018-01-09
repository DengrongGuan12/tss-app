package cn.superid.tss.model;

import org.exemodel.orm.ExecutableModel;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * @author liushao
 */
@Table(name = "activity")
public class ActivityInfoEntity extends CModel{

    @Id
    @Column(name = "id")
    private long id;
    @Column(name = "type")
    private int type;
    @Column(name = "homework_type")
    private int homeworkType;
    @Column(name = "deadline")
    private Timestamp deadline;
    @Column(name = "parent_id")
    private long parentId;

    public ActivityInfoEntity(long id, int type, int homeworkType, Timestamp deadline, long parentId) {
        this.id = id;
        this.type = type;
        this.homeworkType = homeworkType;
        this.deadline = deadline;
        this.parentId = parentId;
    }

    public ActivityInfoEntity(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getHomeworkType() {
        return homeworkType;
    }

    public void setHomeworkType(int homeworkType) {
        this.homeworkType = homeworkType;
    }

    public Timestamp getDeadline() {
        return deadline;
    }

    public void setDeadline(Timestamp deadline) {
        this.deadline = deadline;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }
}
