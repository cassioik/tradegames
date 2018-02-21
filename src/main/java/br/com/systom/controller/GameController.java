package br.com.systom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.systom.domain.Game;
import br.com.systom.repository.GameRepository;

@Controller
@RequestMapping(path="/game/") 
@Secured({"ROLE_ADMIN"})
public class GameController {
	
	private GameRepository gameRepository;
	
	@Autowired
	public GameController(GameRepository gameRepository){
		this.gameRepository = gameRepository;
	}
	
	@RequestMapping("/list")
	public String list(Model model) {
		model.addAttribute("games", gameRepository.findAll());
		return "game/list";
	}
	
	@RequestMapping("/view/{id}")
	public String view(@PathVariable Long id, Model model) {
		model.addAttribute("game", gameRepository.findOne(id));
		return "game/view";
	}
	
	@RequestMapping("/add")
	public String add(Model model){
		model.addAttribute("game", new Game());
		return "game/add";
	}
	
	@RequestMapping( value = "/save", method = RequestMethod.POST )
	public String save(Game game) {
		Game savedGame = gameRepository.save(game);
		return "redirect:/game/view/" + savedGame.getId();
	}
	
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable Long id, Model model){
		model.addAttribute("game", gameRepository.findOne(id));
		return "game/add";		
	}
	
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable Long id, RedirectAttributes redirectAttrs) {
		gameRepository.delete(id);
		redirectAttrs.addFlashAttribute("message", "Jogo foi deletado!");
		return "redirect:/game/list";
	}
}
