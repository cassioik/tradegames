package br.com.systom.controller;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.systom.domain.Ad;
import br.com.systom.domain.Game;
import br.com.systom.domain.Interested;
import br.com.systom.domain.Role;
import br.com.systom.domain.User;
import br.com.systom.domain.UserComment;
import br.com.systom.repository.AdRepository;
import br.com.systom.repository.CommentRepository;
import br.com.systom.repository.GameRepository;
import br.com.systom.repository.InterestedRepository;
import br.com.systom.repository.RoleRepository;
import br.com.systom.repository.UserRepository;

@Controller
@RequestMapping(path="/public/") 
public class PublicController {
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private AdRepository adRepository;
	private GameRepository gameRepository;
	private CommentRepository commentRepository;
	private InterestedRepository interestedRepository;
	
	@Autowired
	public PublicController(UserRepository userRepository, RoleRepository roleRepository, AdRepository adRepository, GameRepository gameRepository, CommentRepository commentRepository, InterestedRepository interestedRepository){
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.adRepository = adRepository;
		this.gameRepository = gameRepository;
		this.commentRepository = commentRepository;
		this.interestedRepository = interestedRepository;
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
	
	@RequestMapping("/ad/view/{id}")
	public String view(@PathVariable Long id, Model model, Principal principal) {
		model.addAttribute("ad", adRepository.findOne(id));
		Ad ad = adRepository.findOne(id);
		model.addAttribute("comments", commentRepository.findByAdOrderByIdDesc(ad));
		
		try {
			User user = userRepository.findByEmail(principal.getName());
			model.addAttribute("interested", interestedRepository.findByAdAndUser(ad, user));
			model.addAttribute("user_interested", new Interested());
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		model.addAttribute("user_comment", new UserComment());
		return "ad/view";
	}
	
}
