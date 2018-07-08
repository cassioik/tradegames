package br.com.systom.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.systom.domain.Ad;
import br.com.systom.domain.Interested;
import br.com.systom.domain.User;
import br.com.systom.domain.UserComment;
import br.com.systom.repository.AdRepository;
import br.com.systom.repository.CommentRepository;
import br.com.systom.repository.GameRepository;
import br.com.systom.repository.InterestedRepository;
import br.com.systom.repository.UserRepository;

@Controller
@RequestMapping(path="/ad/") 
@Secured({"ROLE_ADMIN"})
public class AdController {
	private AdRepository adRepository;
	private GameRepository gameRepository;
	private UserRepository userRepository;
	private CommentRepository commentRepository;
	private InterestedRepository interestedRepository;
	
	@Autowired
	public AdController(AdRepository adRepository, GameRepository gameRepository, UserRepository userRepository, CommentRepository commentRepository, InterestedRepository interestedRepository){
		this.adRepository = adRepository;
		this.gameRepository = gameRepository;
		this.userRepository = userRepository;
		this.commentRepository = commentRepository;
		this.interestedRepository = interestedRepository;
	}
	
	@RequestMapping("/list")
	public String list(Model model) {
		model.addAttribute("ads", adRepository.findAll());
		return "ad/list";
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@RequestMapping("/list/interested")
	public String list_interested(Model model, Principal principal) {
		User user = userRepository.findByEmail(principal.getName());
		model.addAttribute("interesteds", interestedRepository.findByUser(user));
		return "ad/list_interested";
	}
	
	@RequestMapping("/add")
	public String add(Model model){
		model.addAttribute("ad", new Ad());
		model.addAttribute("games", gameRepository.findAll());
		model.addAttribute("users", userRepository.findAll());
		return "ad/add";
	}
	
	@RequestMapping( value = "/save", method = RequestMethod.POST )
	public String save(Ad ad) {
		ad.setCreated_at(LocalDateTime.now());
		System.out.println(ad.toString());
		Ad savedAd = adRepository.save(ad);
		return "redirect:/public/ad/view/" + savedAd.getId();
	}
	
//	@RequestMapping("/view/{id}")
//	public String view(@PathVariable Long id, Model model) {
//		model.addAttribute("ad", adRepository.findOne(id));
//		Ad ad = adRepository.findOne(id);
//		model.addAttribute("comments", commentRepository.findByAdOrderByIdDesc(ad));
//		model.addAttribute("user_comment", new UserComment());
//		return "ad/view";
//	}
	
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable Long id, Model model){
		model.addAttribute("ad", adRepository.findOne(id));
		model.addAttribute("games", gameRepository.findAll());
		model.addAttribute("users", userRepository.findAll());
		return "ad/add";		
	}
	
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable Long id, RedirectAttributes redirectAttrs) {
		adRepository.delete(id);
		redirectAttrs.addFlashAttribute("message", "Anúncio foi deletado!");
		return "redirect:/ad/list";
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@RequestMapping("/list/mine")
	public String list_mine(Model model, Principal principal) {
		User user = userRepository.findByEmail(principal.getName());
		model.addAttribute("ads", adRepository.findByUser(user));
		return "ad/list_mine";
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@RequestMapping("/add/mine")
	public String add_mine(Model model){
		model.addAttribute("ad", new Ad());
		model.addAttribute("games", gameRepository.findAll());
		return "ad/add_mine";
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@RequestMapping( value = "/save/mine", method = RequestMethod.POST )
	public String save_mine(Ad ad, Principal principal) {
		User user = userRepository.findByEmail(principal.getName());
		ad.setUser(user);
		ad.setCreated_at(LocalDateTime.now());
		Ad savedAd = adRepository.save(ad);
		return "redirect:/ad/view/mine/" + savedAd.getId();
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@RequestMapping("/view/mine/{id}")
	public String view_mine(@PathVariable Long id, Model model) {
		model.addAttribute("ad", adRepository.findOne(id));
		Ad ad = adRepository.findOne(id);
		model.addAttribute("comments", commentRepository.findByAdOrderByIdDesc(ad));
		model.addAttribute("user_comment", new UserComment());
		return "ad/view_mine";
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@RequestMapping("/edit/mine/{id}")
	public String edit_mine(@PathVariable Long id, Model model, Principal principal, RedirectAttributes redirectAttrs){
		Ad ad = adRepository.findOne(id);
		if (ad.getUser().getEmail().equals(principal.getName())) {
			model.addAttribute("ad", adRepository.findOne(id));
			model.addAttribute("games", gameRepository.findAll());
			model.addAttribute("users", userRepository.findAll());
			return "ad/add_mine";
		}else {
			redirectAttrs.addFlashAttribute("message", "Esse anúncio não pertence a você!");
			return "redirect:/ad/list/mine";
		}
				
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@RequestMapping("/delete/mine/{id}")
	public String delete_mine(@PathVariable Long id, RedirectAttributes redirectAttrs, Principal principal) {
		Ad ad = adRepository.findOne(id);
		if (ad.getUser().getEmail().equals(principal.getName())) {
			adRepository.delete(id);
			redirectAttrs.addFlashAttribute("message", "Anúncio foi deletado!");
			return "redirect:/ad/list/mine";
		}else {
			redirectAttrs.addFlashAttribute("message", "Esse anúncio não pertence a você!");
			return "redirect:/ad/list/mine";
		}
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@RequestMapping( value = "/save/comment", method = RequestMethod.POST )
	public String save_comment(UserComment user_comment, Principal principal) {
		User user = userRepository.findByEmail(principal.getName());
		user_comment.setUser(user);
		user_comment.setCreated_at(LocalDateTime.now());
		System.out.println(user_comment.toString());
		commentRepository.save(user_comment);
		return "redirect:/public/ad/view/" + user_comment.getAd().getId();
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@RequestMapping( value = "/save/interest", method = RequestMethod.POST )
	public String save_interest(Interested interested, Principal principal) {
		User user = userRepository.findByEmail(principal.getName());
		interested.setUser(user);
		if(interested.getId() > 0) {
			interestedRepository.delete(interested);
		}else{
			interestedRepository.save(interested);
		}
		return "redirect:/public/ad/view/" + interested.getAd().getId();
	}
}
