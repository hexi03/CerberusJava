package com.hexi.Cerberus.domain.user;

import com.hexi.Cerberus.domain.group.Group;
import com.hexi.Cerberus.infrastructure.aggregate.AggregateRoot;
import com.hexi.Cerberus.infrastructure.entity.SecuredEntity;
import com.hexi.Cerberus.infrastructure.event.DomainEvent;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
public abstract class User implements SecuredEntity, AggregateRoot {


    List<DomainEvent> events = new ArrayList<>();


    protected User() {

    }

    //WARN do not use it manually, it is a part of Group -- User interaction
    public void attachToGroup(Group group) {
        appendGroup(group);
    }


    //WARN do not use it manually, it is a part of Group -- User interaction
    public void detachFromGroup(Group group) {
        Optional<Group> tmp_group = getGroups().stream().filter((u) -> u.getId() == group.getId()).findAny();
        if (tmp_group.isEmpty()) throw new RuntimeException("User was not attached to group");
        disposeGroup(tmp_group.orElseThrow());
    }


    public boolean isAttachedToGroup(Group group) {
        return getGroups().stream().filter((u) -> u.getId() == group.getId()).findAny().isPresent();
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

    public abstract UserID getId();

    public abstract Collection<Group> getGroups();

    public abstract String getName();

    public abstract void setName(String name);

    public abstract String getEmail();

    public abstract void setEmail(String email);

    public abstract String getPasswordHash();

    public abstract void setPasswordHash(String passwordHash);


    protected abstract void disposeGroup(Group group);

    protected abstract void appendGroup(Group group);

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof User other)) return false;
        if (!other.canEqual(this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (!Objects.equals(this$id, other$id)) return false;
        final Object this$groups = this.getGroups();
        final Object other$groups = other.getGroups();
        if (!Objects.equals(this$groups, other$groups)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (!Objects.equals(this$name, other$name)) return false;
        final Object this$email = this.getEmail();
        final Object other$email = other.getEmail();
        if (!Objects.equals(this$email, other$email)) return false;
        final Object this$passwordHash = this.getPasswordHash();
        final Object other$passwordHash = other.getPasswordHash();
        return Objects.equals(this$passwordHash, other$passwordHash);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof User;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $groups = this.getGroups();
        result = result * PRIME + ($groups == null ? 43 : $groups.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        final Object $passwordHash = this.getPasswordHash();
        result = result * PRIME + ($passwordHash == null ? 43 : $passwordHash.hashCode());
        return result;
    }

    public String toString() {
        return "User(id=" + this.getId() + ", groups=" + this.getGroups() + ", name=" + this.getName() + ", email=" + this.getEmail() + ", passwordHash=" + this.getPasswordHash() + ")";
    }
}
