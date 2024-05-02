package com.hexi.Cerberus.domain.group;

import com.hexi.Cerberus.domain.group.event.UserAttachedFromGroupEvent;
import com.hexi.Cerberus.domain.group.event.UserDetachedFromGroupEvent;
import com.hexi.Cerberus.domain.user.User;
import com.hexi.Cerberus.domain.user.UserID;
import com.hexi.Cerberus.infrastructure.aggregate.AggregateRoot;
import com.hexi.Cerberus.infrastructure.entity.SecuredEntity;
import com.hexi.Cerberus.infrastructure.event.DomainEvent;

import java.util.*;


public abstract class Group implements SecuredEntity, AggregateRoot {

    List<DomainEvent> events = new ArrayList<>();

    protected Group() {

    }


    protected abstract void disposeUser(User tmpUser);

    protected abstract void appendUser(User user);

    public void addUser(User user) {
        user.attachToGroup(this);
        appendUser(user);
        events.add(new UserAttachedFromGroupEvent(this.getId(), user.getId()));
    }


//    public List<User> removeUsers(GroupExcludeUsersCmd cmd) {
//        Stream<User> tmp_users = cmd.getUsers()
//                .stream()
//                .map(userID -> users.stream().filter((u) -> u.getId().equals(userID)).findAny())
//                .filter(user -> user.isPresent()).map(user -> user.get());
//        tmp_users.forEach((tmp_user -> {
//            tmp_user.detachFromGroup(this);
//            users.remove(tmp_user);
//            events.add(new UserDetachedFromGroupEvent(this.getId(), tmp_user.getId()));
//
//        }));
//        return tmp_users.collect(Collectors.toList());
//    }

    public User removeUser(UserID uid) {
        Optional<User> tmp_user = getUsers().stream().filter((u) -> u.getId().equals(uid)).findAny();
        tmp_user.orElseThrow(() -> new RuntimeException(String.format("There is no user with id %s", uid.toString())));
        tmp_user.get().detachFromGroup(this);
        disposeUser(tmp_user.orElseThrow());
        events.add(new UserDetachedFromGroupEvent(this.getId(), tmp_user.get().getId()));
        return tmp_user.get();
    }


    public boolean isUserPresented(User user) {
        return getUsers().stream().filter((u) -> u.getId() == user.getId()).findAny().isPresent();
    }

    @Override
    public void clearEvents() {
        events.clear();
    }

    @Override
    public List<DomainEvent> edjectEvents() {
        List<DomainEvent> ev = events;
        events = new ArrayList<>();
        return ev;
    }

    public abstract GroupID getId();

    public abstract Collection<User> getUsers();

    public abstract String getName();

    public abstract void setName(String name);

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Group other)) return false;
        if (!other.canEqual(this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (!Objects.equals(this$id, other$id)) return false;
        final Object this$users = this.getUsers();
        final Object other$users = other.getUsers();
        if (!Objects.equals(this$users, other$users)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        return Objects.equals(this$name, other$name);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Group;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $users = this.getUsers();
        result = result * PRIME + ($users == null ? 43 : $users.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        return result;
    }

    public String toString() {
        return "Group(id=" + this.getId() + ", users=" + this.getUsers() + ", name=" + this.getName() + ")";
    }
}
