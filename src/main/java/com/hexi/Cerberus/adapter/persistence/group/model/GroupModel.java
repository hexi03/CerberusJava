package com.hexi.Cerberus.adapter.persistence.group.model;

import com.hexi.Cerberus.adapter.persistence.user.model.UserModel;
import com.hexi.Cerberus.domain.group.Group;
import com.hexi.Cerberus.domain.group.GroupID;
import com.hexi.Cerberus.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "group")

public class GroupModel extends Group {
    @Id
    UUID id;

    String name;

    @ManyToMany
    @JoinTable(name = "user_group_assoc",
            joinColumns = @JoinColumn(name = "warehouse_id"),
            inverseJoinColumns = @JoinColumn(name = "factorysite_id"))

    Collection<UserModel> users = new ArrayList<>();

    @Deprecated
    protected GroupModel() {
        super();

    }
    @Override
    public GroupID getId(){
        return new GroupID(id);
    }
    @Override
    public Collection<User> getUsers(){
        return users.stream().map(userModel -> (User) userModel).collect(Collectors.toSet());
    }
    @Override
    public String getName(){
        return name;
    }
    @Override
    public void setName(String n){
        name = n;
    }
}
