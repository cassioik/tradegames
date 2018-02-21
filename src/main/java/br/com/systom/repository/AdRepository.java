package br.com.systom.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.systom.domain.Ad;
import br.com.systom.domain.Game;
import br.com.systom.domain.User;

public interface AdRepository extends CrudRepository<Ad, Long> {
	List<Ad> findFirst6ByOrderByIdDesc();
	
	List<Ad> findByGameAndType(Game game, Integer type);
	
	List<Ad> findByGame(Game game);
	
	List<Ad> findByType(Integer type);
	
	List<Ad> findByUser(User user);
}
