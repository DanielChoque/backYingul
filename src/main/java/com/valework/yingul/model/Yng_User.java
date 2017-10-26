package com.valework.yingul.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.valework.yingul.model.security.Authority;
import com.valework.yingul.model.security.Yng_UserRole;

@Entity
public class Yng_User implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userId", nullable = false, updatable = false)
    private Long userId;
    private String username;
    private String password;
    private String address;

    @Column(name = "email", nullable = false, unique = true)
    private String email;
    private String phone;

    private boolean enabled=true;

    @OneToOne
    private Yng_City yng_City;

    @OneToOne
    private Yng_Province yng_Province;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Yng_UserRole> userRoles = new HashSet<>();

    public Set<Yng_UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<Yng_UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Yng_City getYng_City() {
		return yng_City;
	}

	public void setYng_City(Yng_City yng_City) {
		this.yng_City = yng_City;
	}

	public Yng_Province getYng_Province() {
		return yng_Province;
	}

	public void setYng_Province(Yng_Province yng_Province) {
		this.yng_Province = yng_Province;
	}

	public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


    @Override
	public String toString() {
		return "Yng_User [userId=" + userId + ", username=" + username + ", password=" + password + ", address="
				+ address + ", email=" + email + ", phone=" + phone + ", enabled=" + enabled + ", yng_City=" + yng_City
				+ ", yng_Province=" + yng_Province + ", userRoles=" + userRoles + "]";
	}

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        userRoles.forEach(ur -> authorities.add(new Authority(ur.getRole().getName())));
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }


}