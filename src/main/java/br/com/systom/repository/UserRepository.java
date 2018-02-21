package br.com.systom.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.systom.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {
	User findByEmail(String email);
}
