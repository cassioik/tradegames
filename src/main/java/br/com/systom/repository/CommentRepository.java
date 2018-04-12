package br.com.systom.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.systom.domain.Ad;
import br.com.systom.domain.UserComment;

public interface CommentRepository extends CrudRepository<UserComment, Long> {

	List<UserComment> findByAdOrderByIdDesc(Ad ad);

}
