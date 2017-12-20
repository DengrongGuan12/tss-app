package cn.superid.tss.util;

import cn.superid.id_client.IdClient;
import org.exemodel.orm.ModelMeta;
import org.exemodel.orm.Statement;

/**
 * Created by xiaofengxu on 17/6/19.
 *  for drds application
 *  drds statement
 */
public class DStatement extends Statement<DStatement> {
    private  static IdClient idClient;

    public static DStatement build(Class modelClass){
        return new DStatement(modelClass);
    }

    public DStatement(Class<?> clazz) {
        super(clazz);
    }

    public DStatement partitionId(Object value){
        return eq(getModelMeta().getPartitionColumn().columnName,value);
    }

    /**
     * get auto_increment_id ,when DRDS it get Sequence
     *
     * @return
     */
    public long generateId() {
        return generateId(this.modelClass);
    }

    public static long generateId(Class clazz){
        return idClient.nextId("file", ModelMeta.getModelMeta(clazz).getTableName());
    }

    public static void setIdClient(IdClient client){
        idClient = client;
    }

    public DStatement affairId(long affairId) {
        return and("affairId", "=", affairId);
    }

    public DStatement allianceId(long allianceId) {
        return eq("allianceId", allianceId);
    }

    public DStatement filterLevel(int level) {
        return le("publicType", level);
    }

    public DStatement roleId(long roleId) {
        return eq("roleId", roleId);
    }

    public DStatement pathPrefix(String path) {
        return like("path", path + "%");
    }

    public DStatement parentId(long id) {
        return eq("parentId", id);
    }

    public DStatement folderId(long id) {
        return eq("folderId", id);
    }

    public DStatement taskId(long id) {
        return eq("taskId", id);
    }

    public DStatement type(int id) {
        return eq("type", id);
    }

    public DStatement orderId(long id) {
        return eq("orderId", id);
    }
}
