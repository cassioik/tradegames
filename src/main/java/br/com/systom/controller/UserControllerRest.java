package br.com.systom.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.systom.domain.Ad;
import br.com.systom.domain.Game;
import br.com.systom.domain.User;
import br.com.systom.repository.AdRepository;
import br.com.systom.repository.GameRepository;
import br.com.systom.repository.UserRepository;

@RestController
@RequestMapping(path="/public/") 
public class UserControllerRest {
	
	private UserRepository userRepository;
	private AdRepository adRepository;
	private GameRepository gameRepository;
	
	@Autowired
	public UserControllerRest(UserRepository userRepository, AdRepository adRepository, GameRepository gameRepository){
		this.userRepository = userRepository;
		this.adRepository = adRepository;
		this.gameRepository = gameRepository;
	}
	
	@RequestMapping("/teste")
	@Secured({ "ROLE_ADMIN" })
	public String teste(Principal princial) {
//		User user = new User();
//		user = userRepository.findOne(1L);
//		return user.getRoles().toString();
//		//return user.getFullName();
//		//return user.toString();
		return princial.getName();
	}
	
	@RequestMapping("/teste2")
	public String search(){
		int type = 1;
		Game game = gameRepository.findOne(1L);
		List<Ad> ads = adRepository.findByGameAndType(game, type);
		return ads.toString();
	}
	
	@RequestMapping("/teste3/{id}")
	public String edit_mine(@PathVariable Long id, Model model, Principal principal){
		Ad ad = adRepository.findOne(id);
		if (ad.getUser().getEmail().equals(principal.getName())) {
			model.addAttribute("ad", adRepository.findOne(id));
			model.addAttribute("games", gameRepository.findAll());
			model.addAttribute("users", userRepository.findAll());
			return ad.toString();
		}else {
			return "erro";
		}
				
	}
}
