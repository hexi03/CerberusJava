package com.hexi.Cerberus.adapter.persistence.user.base;

import com.hexi.Cerberus.adapter.persistence.group.base.GroupModel;
import com.hexi.Cerberus.domain.group.Group;
import com.hexi.Cerberus.domain.user.User;
import com.hexi.Cerberus.domain.user.UserID;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.SuperBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "_user")
@Access(value=AccessType.FIELD)
@Getter
@Setter
@Transactional
public class UserModel extends User {
    @EmbeddedId
    UserID id;
    String name = "";
    @Column(unique = true)
    String email = "";
    String passwordHash = "";

    @ManyToMany
    @JoinTable(name = "user_group_assoc",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    Collection<GroupModel> groups = new ArrayList<>();

    @Deprecated
    public UserModel() {
        super();
    }

    public UserModel(UserID userID, String name, String email, String passwordHash) {
        this.id = new UserID(userID);
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    @Override
    public UserID getId() {
        return new UserID(id);
    }

    @Override
    public Collection<Group> getGroups() {
        return groups.stream().map(groupModel -> (Group) groupModel).collect(Collectors.toSet());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String n) {
        name = n;
    }

    @Override
    public String getEmail() {
        return email;
    }

    ;

    @Override
    public void setEmail(String e) {
        email = e;
    }

    @Override
    public String getPasswordHash() {
        return passwordHash;
    }

    @Override
    public void setPasswordHash(String ph) {
        passwordHash = ph;
    }

    @Override
    protected void disposeGroup(Group group) {
        groups = groups.stream().filter(gr -> !group.getId().equals(group.getId())).collect(Collectors.toList());
    }

    @SneakyThrows
    @Override
    protected void appendGroup(Group group) {
        if (group.getClass() == GroupModel.class) {
            groups.add((GroupModel) group);
        } else {
            throw new Exception("Ivalid WareHouse child");
        }
    }


}
