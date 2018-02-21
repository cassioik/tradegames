package br.com.systom.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.systom.domain.Ad;
import br.com.systom.domain.Game;
import br.com.systom.domain.Role;
import br.com.systom.domain.User;
import br.com.systom.repository.AdRepository;
import br.com.systom.repository.GameRepository;
import br.com.systom.repository.RoleRepository;
import br.com.systom.repository.UserRepository;

@Controller
@RequestMapping(path="/public/") 
public class PublicController {
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private AdRepository adRepository;
	private GameRepository gameRepository;
	
	@Autowired
	public PublicController(UserRepository userRepository, RoleRepository roleRepository, AdRepository adRepository, GameRepository gameRepository){
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.adRepository = adRepository;
		this.gameRepository = gameRepository;
	}
	
	@RequestMapping("/signup")
	public String add(Model model){
		model.addAttribute("user", new User());
		return "user/signup";
	}
	
	@RequestMapping(value = "/user/save", method = RequestMethod.POST)
	public String user_save(User user) {
		Role role = roleRepository.findOne(2L); // Pega a Role de USER
		Set<Role> roles = new HashSet<>(); 
		roles.add(role); // Adiciona a Role USER no Set<Roles>
		user.setRoles(roles);
		userRepository.save(user);
		return "redirect:/login";
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String ad_search(Model model, Ad search){
		if(search.getGame() == null && search.getType() == 0) {
			model.addAttribute("ads", adRepository.findAll());
		}else if(search.getGame() == null) {
			model.addAttribute("ads", adRepository.findByType(search.getType()));
		}else if(search.getType() == 0) {
			model.addAttribute("ads", adRepository.findByGame(search.getGame()));
		}else {
			model.addAttribute("ads", adRepository.findByGameAndType(search.getGame(), search.getType()));
		}
		model.addAttribute("game", search.getGame());
		model.addAttribute("type", search.getType());
		//model.addAttribute("ads", adRepository.findByGameAndType(g, type));
		model.addAttribute("games", gameRepository.findAll());
		return "ad/search";
	}
}
