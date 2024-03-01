package com.example.webassignment.event_management.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @SequenceGenerator(name = "tb_user_seq_gen", sequenceName = "tb_user_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "tb_user_seq_gen", strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(name = "number", nullable = false)
    private String number;
    @Column(name = "address", nullable = false)
    private String address;

    @Column(nullable = false)
    private String password;
    @Column(name = "OTP")
    private String OTP;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            foreignKey = @ForeignKey(name = "FK_users_roles_userId"),
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseForeignKey = @ForeignKey(name = "FK_users_roles_roleId"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            uniqueConstraints = @UniqueConstraint(name = "UNIQUE_users_roles_userIdRoleId",
                    columnNames = {"user_id", "role_id"})
    )
    private Collection<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }


    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
