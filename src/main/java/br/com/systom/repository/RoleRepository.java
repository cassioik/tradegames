package br.com.systom.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.systom.domain.Role;

public interface RoleRepository  extends CrudRepository<Role, Long> {

}
