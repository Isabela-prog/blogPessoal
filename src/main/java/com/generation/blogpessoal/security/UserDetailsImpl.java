package com.generation.blogpessoal.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.generation.blogpessoal.model.Usuario;

/*
 * a Security é que válida se o usuário existe etc
 * por isso recebe o email e a senha
 */

public class UserDetailsImpl implements UserDetails {
	
	/*
	 * faz controle de versionamento para essa classe
	 * final -> constante
	 * static -> limita acesso dentro da classe apenas
	 */
	private static final long serialVersionUID = 1L;

	private String userName;
	private String password;
	private List<GrantedAuthority> authorities;
	
	//método contrutor 
	public UserDetailsImpl(Usuario user) {
		this.userName = user.getUsuario();
		this.password = user.getSenha();
	}
	
	//quando o usuário já tá logado não recebe usuário e senha
	public UserDetailsImpl() {	}

	@Override
	/*
	 * collections -> pode receber mais de um tipo de dado
	 */
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return authorities;
	}

	@Override
	public String getPassword() {

		return password;
	}

	@Override
	public String getUsername() {

		return userName;
	}
	
	//ajudar a verificar se o acesso já expirou
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	// auxiliar a validação do usuário está bloqueada
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	//validar se a credencial expirou
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	//validar se o usuário está ativo
	@Override
	public boolean isEnabled() {
		return true;
	}

}
