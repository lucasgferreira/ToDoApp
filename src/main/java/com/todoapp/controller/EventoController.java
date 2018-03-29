package com.todoapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.todoapp.model.Convidado;
import com.todoapp.model.Evento;
import com.todoapp.model.Usuario;
import com.todoapp.repository.ConvidadoRepository;
import com.todoapp.repository.EventoRepository;
import com.todoapp.repository.UsuarioRepository;

@Controller
public class EventoController {

	@Autowired
	private EventoRepository er;

	@Autowired
	private ConvidadoRepository cr;

	@Autowired
	private UsuarioRepository ur;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String logar() {
		return "login";
	}

	@RequestMapping(value = "/cadastrarEvento", method = RequestMethod.GET)
	public String form(HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName(); // get logged in username

		System.out.println("----------username----------" + name);
		return "evento/formEvento";
	}

	@RequestMapping(value = "/cadastrarEvento", method = RequestMethod.POST)
	public String cadastrarEvento(@Valid Evento evento, BindingResult result, RedirectAttributes attributes,
			Authentication authentication) {

		if (result.hasErrors()) {
			attributes.addFlashAttribute("erro", "Verifique os campos!");
			return "redirect:/cadastrarEvento";
		}

		authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName(); // get logged in username

		Usuario usuario = ur.findByLogin(username);
		// usuario.setUsername(username);
		evento.setUsuario(usuario);
		er.save(evento);
		attributes.addFlashAttribute("mensagem", "Evento cadastrado com sucesso!");
		return "redirect:/cadastrarEvento";
	}

	@RequestMapping(value = "/eventos", method = RequestMethod.GET)
	public ModelAndView listaEventos() {
		ModelAndView modelAndView = new ModelAndView("listarEventos");

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String user = authentication.getName(); // get logged in username

		Usuario usuario = ur.findByLogin(user);

		Iterable<Evento> eventos = er.findByUsuario(usuario);
		modelAndView.addObject("eventos", eventos);
		return modelAndView;
	}

	@RequestMapping(value = "/{codigo}", method = RequestMethod.GET)
	public ModelAndView detalhesEvento(@PathVariable("codigo") Long codigo) {
		Evento evento = er.findByCodigo(codigo);
		ModelAndView modelAndView = new ModelAndView("evento/detalhesEvento");
		modelAndView.addObject("evento", evento);

		Iterable<Convidado> convidados = cr.findByEvento(evento);
		modelAndView.addObject("convidados", convidados);
		System.out.println("----------codigoevento----------" + codigo);
		return modelAndView;
	}

	@RequestMapping(value = "/{codigo}", method = RequestMethod.POST)
	public String detalhesEventoPost(@PathVariable("codigo") Long codigo, @Valid Convidado convidado,
			BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos!");
			return "redirect:/{codigo}";
		} else {
			Evento evento = new Evento();
			evento = er.findByCodigo(codigo);
			convidado.setEvento(evento);
			convidado.setCodigo(null);
			cr.save(convidado);
			convidado = new Convidado();
			attributes.addFlashAttribute("mensagem", "Convidado adicionado com sucesso!");
			return "redirect:/{codigo}";
		}
	}

	@RequestMapping("/deletarEvento")
	public String deletarEvento(Long codigo) {
		Evento evento = er.findByCodigo(codigo);
		er.delete(evento);
		return "redirect:/eventos";
	}

	@RequestMapping("/deletarConvidado")
	public String deletarConvidado(Long codigo) {
		Convidado convidado = cr.findByCodigo(codigo);
		cr.delete(convidado);

		Evento evento = convidado.getEvento();
		Long codigoLong = evento.getCodigo();

		return "redirect:/" + codigoLong;
	}
}
