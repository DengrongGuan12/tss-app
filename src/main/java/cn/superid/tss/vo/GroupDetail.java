package cn.superid.tss.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DengrongGuan
 * @create 2017-12-26 上午10:34
 **/
@ApiModel
public class GroupDetail extends GroupSimple{
    @ApiModelProperty(value = "描述")
    private String description;
    @ApiModelProperty(value = "角色列表")
    private List<Role> roles;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public GroupDetail(long id, String name,boolean isMine, String description, List<Role> roles) {
        super(id, name,isMine);
        this.description = description;
        this.roles = roles;
    }

    public static GroupDetail mockMyGroup(){
        List<Role> roles = new ArrayList<>();
        for (int i = 0;i<6;i++){
            roles.add(Role.MockStudent());
        }
        return new GroupDetail(1,"tss开发小组",true, "tss开发小组tss开发小组tss开发小组tss开发小组tss开发小组",roles);
    }

    public static GroupDetail mockOtherGroup(){
        List<Role> roles = new ArrayList<>();
        for (int i = 0;i<6;i++){
            roles.add(Role.MockStudent());
        }
        return new GroupDetail(1,"tss开发小组",false, "tss开发小组tss开发小组tss开发小组tss开发小组tss开发小组",roles);
    }


}
