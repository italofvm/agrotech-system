package com.agrotech.agro_tech_system.infra.persistence.entity;

import com.agrotech.agro_tech_system.domain.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(of = "id")
public class UsuarioEntity implements UserDetails {

	public UsuarioEntity(String id, String username, String password, UserRole role) {
		this.id = id;
		login = username;
		senha = password;
		this.role = role;
	}
	
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false, unique = true)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

	public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.ADMIN) {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_USER")
            );
        } else {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_USER")
            );
        }
    }
    
    public String getId() { return id; }

    public String getPassword() { return senha; }
    
    public String getUsername() { return login; }
    
    public UserRole getRole() { return role; }
    
    public boolean isAccountNonExpired() { return true; }
    
    public boolean isAccountNonLocked() { return true; }
    
    public boolean isCredentialsNonExpired() { return true; }
    
    public boolean isEnabled() { return true; }
}
