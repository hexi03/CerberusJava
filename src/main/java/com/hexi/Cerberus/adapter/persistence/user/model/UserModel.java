package com.hexi.Cerberus.adapter.persistence.user.model;

import com.hexi.Cerberus.adapter.persistence.group.model.GroupModel;
import com.hexi.Cerberus.domain.group.Group;
import com.hexi.Cerberus.domain.user.User;
import com.hexi.Cerberus.domain.user.UserID;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "user")

@Getter
@Setter
public class UserModel extends User {
    @Id
    UUID id;
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
    protected UserModel() {
        super();
    }

    @Override
    public UserID getId(){
        return new UserID(id);
    }
    @Override
    public Collection<Group> getGroups(){
        return groups.stream().map(groupModel -> (Group)groupModel).collect(Collectors.toSet());
    }
    @Override
    public String getName(){
        return name;
    }
    @Override
    public String getEmail(){
        return email;
    }

    @Override
    public String getPasswordHash(){
        return passwordHash;
    };

    @Override
    public void setName(String n){
        name = n;
    }

    @Override
    public void setEmail(String e){
        email = e;
    }

    @Override
    public void setPasswordHash(String ph){
        passwordHash = ph;
    }


}
