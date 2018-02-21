package br.com.systom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.systom.domain.User;
import br.com.systom.repository.RoleRepository;
import br.com.systom.repository.UserRepository;

@Controller
@RequestMapping(path="/user/") 
@Secured({"ROLE_ADMIN"})
public class UserController {
	
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	
	@Autowired
	public UserController(UserRepository userRepository, RoleRepository roleRepository){
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}
	
	@RequestMapping("/add")
	public String add(Model model){
		model.addAttribute("user", new User());
		model.addAttribute("roles", roleRepository.findAll());
		return "user/add";
	}
	
	@RequestMapping( value = "/save", method = RequestMethod.POST )
	public String save(User user) {
		User savedUser = userRepository.save(user);
		return "redirect:/user/view/" + savedUser.getId();
	}
	
	@RequestMapping("/view/{id}")
	public String view(@PathVariable Long id, Model model) {
		model.addAttribute("user", userRepository.findOne(id));
		return "user/view";
	}
	
	@RequestMapping("/list")
	public String list(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "user/list";
	}
	
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable Long id, Model model){
		model.addAttribute("user", userRepository.findOne(id));
		model.addAttribute("roles", roleRepository.findAll());
		return "user/add";		
	}
	
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable Long id, RedirectAttributes redirectAttrs) {
		userRepository.delete(id);
		redirectAttrs.addFlashAttribute("message", "Usuário foi deletado!");
		return "redirect:/user/list";
	}
}
