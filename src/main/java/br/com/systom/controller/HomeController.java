package br.com.systom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.systom.repository.AdRepository;

@Controller
public class HomeController {
	private AdRepository adRepository;
	
	@Autowired
	public HomeController(AdRepository adRepository){
		this.adRepository = adRepository;
	}
	
	@RequestMapping("/")
	public String home(Model model) {
		model.addAttribute("ads", adRepository.findFirst6ByOrderByIdDesc());
		return "home/index";
	}
}
