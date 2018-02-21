package br.com.systom.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.systom.domain.Game;

public interface GameRepository extends CrudRepository<Game, Long> {

}
