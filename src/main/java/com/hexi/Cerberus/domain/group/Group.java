package com.hexi.Cerberus.domain.group;

import com.hexi.Cerberus.domain.group.event.UserAttachedFromGroupEvent;
import com.hexi.Cerberus.domain.group.event.UserDetachedFromGroupEvent;
import com.hexi.Cerberus.domain.user.User;
import com.hexi.Cerberus.domain.user.UserID;
import com.hexi.Cerberus.infrastructure.aggregate.AggregateRoot;
import com.hexi.Cerberus.infrastructure.entity.SecuredEntity;
import com.hexi.Cerberus.infrastructure.event.DomainEvent;
import lombok.Builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Builder
public class Group implements SecuredEntity, AggregateRoot {
    GroupID id;
    Collection<User> users = new ArrayList<>();
    String name;

    List<DomainEvent> events = new ArrayList<>();

    public Group(
            GroupID id,
            String name
    ) {
        this.id = id;
        this.name = name;
    }

    protected Group() {

    }

    public void addUser(User user) {
        user.attachToGroup(this);
        users.add(user);
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
        Optional<User> tmp_user = users.stream().filter((u) -> u.getId().equals(uid)).findAny();
        tmp_user.orElseThrow(() -> new RuntimeException(String.format("There is no user with id %s", uid.toString())));
        tmp_user.get().detachFromGroup(this);
        users.remove(tmp_user);
        events.add(new UserDetachedFromGroupEvent(this.getId(), tmp_user.get().getId()));
        return tmp_user.get();
    }

    public boolean isUserPresented(User user) {
        return users.stream().filter((u) -> u.getId() == user.getId()).findAny().isPresent();
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

    public GroupID getId() {
        return this.id;
    }

    public Collection<User> getUsers() {
        return this.users;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Group)) return false;
        final Group other = (Group) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$users = this.getUsers();
        final Object other$users = other.getUsers();
        if (this$users == null ? other$users != null : !this$users.equals(other$users)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        return true;
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
