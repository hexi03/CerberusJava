package com.hexi.Cerberus.domain.access;

import com.hexi.Cerberus.infrastructure.entity.UUIDBasedEntityID;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.Collection;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

public class UUIDBasedAuthToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = 620L;
    private final UUIDBasedEntityID principal;

    public UUIDBasedAuthToken(UUIDBasedEntityID principal) {
        super((Collection)null);
        this.principal = principal;
        this.setAuthenticated(false);
    }

    public UUIDBasedAuthToken(UUIDBasedEntityID principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
    }

    public static org.springframework.security.authentication.UsernamePasswordAuthenticationToken unauthenticated(Object principal, Object credentials) {
        return new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(principal, credentials);
    }

    public static org.springframework.security.authentication.UsernamePasswordAuthenticationToken authenticated(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        return new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(principal, credentials, authorities);
    }

    public Object getCredentials() {
        return null;
    }

    public Object getPrincipal() {
        return this.principal;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated, "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }

    public void eraseCredentials() {
        super.eraseCredentials();
    }
}
