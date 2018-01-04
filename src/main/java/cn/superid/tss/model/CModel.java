package cn.superid.tss.model;

/**
 * @author DengrongGuan
 * @create 2017-12-04 下午5:11
 **/
import cn.superid.tss.util.DStatement;
import org.exemodel.orm.ExecutableModel;

public class CModel extends ExecutableModel {
    @Override
    public Object generateId() {
        return DStatement.generateId(this.getClass());
    }
}
