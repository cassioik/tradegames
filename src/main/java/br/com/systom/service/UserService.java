package br.com.systom.service;

import br.com.systom.domain.User;

public interface UserService {
	public User findByEmail(String email);
}
