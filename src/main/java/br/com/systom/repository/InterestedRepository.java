package br.com.systom.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.systom.domain.Ad;
import br.com.systom.domain.Interested;
import br.com.systom.domain.User;

public interface InterestedRepository extends CrudRepository<Interested, Long> {
	Interested findByAdAndUser(Ad ad, User user);
	
	List<Interested> findByUser(User user);
}
