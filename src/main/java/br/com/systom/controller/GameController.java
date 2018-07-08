package br.com.systom.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
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
	public String save(@RequestParam("image") MultipartFile image, @RequestParam("name") String name, @RequestParam("description") String description) {
		Game game = new Game();
		game.setName(name);
		game.setDescription(description);
		game.setImage(image.getOriginalFilename());
		Game savedGame = gameRepository.save(game);

        try {
            // Get the file and save it somewhere
            byte[] bytes = image.getBytes();
            Path path = Paths.get("src/main/webapp/upload/" + image.getOriginalFilename());
            Files.write(path, bytes);

            System.out.println("Foi");
        } catch (Exception e) {
        	System.out.println("Erro");
        }
		
		return "redirect:/game/view/" + savedGame.getId();
	}
	
	@RequestMapping( value = "/save_edit", method = RequestMethod.POST )
	public String save_edit(@RequestParam("image") MultipartFile image, @RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("id") Long id) {
		Game game = new Game();
		game.setId(id);
		game.setName(name);
		game.setDescription(description);
		game.setImage(image.getOriginalFilename());
		Game savedGame = gameRepository.save(game);

        try {
            // Get the file and save it somewhere
            byte[] bytes = image.getBytes();
            Path path = Paths.get("src/main/webapp/upload/" + image.getOriginalFilename());
            Files.write(path, bytes);

            System.out.println("Foi");
        } catch (Exception e) {
        	System.out.println("Erro");
        }
		
		return "redirect:/game/view/" + savedGame.getId();
	}
	
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable Long id, Model model){
		model.addAttribute("game", gameRepository.findOne(id));
		return "game/edit";		
	}
	
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable Long id, RedirectAttributes redirectAttrs) {
		gameRepository.delete(id);
		redirectAttrs.addFlashAttribute("message", "Jogo foi deletado!");
		return "redirect:/game/list";
	}
}
