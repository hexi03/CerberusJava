package com.hexi.Cerberus.adapter.persistence.group.base;

import com.hexi.Cerberus.adapter.persistence.user.base.UserModel;
import com.hexi.Cerberus.domain.group.Group;
import com.hexi.Cerberus.domain.group.GroupID;
import com.hexi.Cerberus.domain.user.User;
import jakarta.persistence.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "_group")
@Access(value=AccessType.FIELD)

public class GroupModel extends Group {
    @EmbeddedId
    GroupID id;

    String name;

    @ManyToMany
    @JoinTable(name = "user_group_assoc",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = {@UniqueConstraint(name = "unique_assoc", columnNames = {"group_id", "user_id"})
    })

    Collection<UserModel> users = new ArrayList<>();

    @Deprecated
    private GroupModel() {
        super();

    }

    public GroupModel(GroupID groupID, String name) {
        this.id = new GroupID(groupID);
        this.name = name;
    }

    @Override
    protected void disposeUser(User user) {
        users = users.stream().filter(usr -> !usr.getId().equals(user.getId())).collect(Collectors.toList());
    }

    @SneakyThrows
    @Override
    protected void appendUser(User user) {

        if (user.getClass() == UserModel.class) {
            users.add((UserModel) user);
        } else {
            throw new Exception("Invalid WareHouse child");
        }
    }

    @Override
    public GroupID getId() {
        return new GroupID(id);
    }

    @Override
    public Collection<User> getUsers() {

        return users.stream().map(userModel -> (User) userModel).collect(Collectors.toList());
    }

    @Override
    public void setUsers(List<User> users) {
        this.users = users.stream().map(userModel -> (UserModel) userModel).collect(Collectors.toList());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String n) {
        name = n;
    }
}
