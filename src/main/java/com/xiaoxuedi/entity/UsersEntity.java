package com.xiaoxuedi.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
public class UsersEntity implements UserDetails {

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    @JoinColumn(name = "id")
    private String id;
    @JoinColumn(nullable = false)
    @Enumerated(EnumType.STRING)
    private User.AuthStatus authStatus = User.AuthStatus.NOT;
    @Transient
    private List<SimpleGrantedAuthority> authorities;

    @JoinColumn(name = "balance")
    private BigDecimal balance;

    @JoinColumn(name = "create_time")
    private Timestamp createTime;

    @JoinColumn(name = "enabled")
    private boolean enabled = true;

    @JoinColumn(name = "id_card")
    private String idCard;

    @JoinColumn(name = "invitation_code")
    private String invitationCode;

    @JoinColumn(name = "invitation_count")
    private int invitationCount;

    @JoinColumn(name = "mobile")
    @Pattern(regexp = "^(13[0-9]|14[579]|15[0-3,5-9]|17[0135678]|18[0-9])\\d{8}$")
    private String mobile;

    @JoinColumn(name = "name")
    private String name;

    @JoinColumn(name = "password")
    private String password;

    @JoinColumn(name = "sex")
    private String sex;

    @JoinColumn(name = "username")
    private String username;

    @JoinColumn(name = "school_id")
    private String schoolId;

    @JoinColumn(name = "photo")
    private String photo;

    @Transient
    private boolean accountNonExpired = true;
    @Transient
    private boolean accountNonLocked = true;
    @Transient
    private boolean credentialsNonExpired = true;

    public static String getUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            User user = (User) principal;
            return user.getId();
        }
        return null;
    }

    public static User getUser() {
        User user = new User();
        user.setId(getUserId());
        return user;
    }

    public boolean isAuth() {
        return authStatus == User.AuthStatus.PASS;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public List<SimpleGrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<SimpleGrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public enum AuthStatus {
        NOT,
        WAIT,
        PASS
    }

}