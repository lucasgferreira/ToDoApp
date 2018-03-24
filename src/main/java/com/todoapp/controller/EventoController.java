package com.todoapp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.todoapp.model.Convidado;
import com.todoapp.model.Evento;
import com.todoapp.repository.ConvidadoRepository;
import com.todoapp.repository.EventoRepository;

@Controller
public class EventoController {

	@Autowired
	private EventoRepository er;

	@Autowired
	private ConvidadoRepository cr;

	@RequestMapping(value = "/cadastrarEvento", method = RequestMethod.GET)
	public String form() {
		return "evento/formEvento";
	}

	@RequestMapping(value = "/cadastrarEvento", method = RequestMethod.POST)
	public String form(@Valid Evento evento, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			attributes.addFlashAttribute("erro", "Verifique os campos!");
			return "redirect:/cadastrarEvento";
		}

		er.save(evento);
		attributes.addFlashAttribute("mensagem", "Evento cadastrado com sucesso!");
		return "redirect:/cadastrarEvento";
	}

	@RequestMapping("/eventos")
	public ModelAndView listaEventos() {
		ModelAndView modelAndView = new ModelAndView("/index");
		Iterable<Evento> eventos = er.findAll();
		modelAndView.addObject("eventos", eventos);
		return modelAndView;
	}

	@RequestMapping(value = "/{codigo}", method = RequestMethod.GET)
	public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo) {
		Evento evento = er.findByCodigo(codigo);
		ModelAndView modelAndView = new ModelAndView("evento/detalhesEvento");
		modelAndView.addObject("evento", evento);

		Iterable<Convidado> convidados = cr.findByEvento(evento);
		modelAndView.addObject("convidados", convidados);
		return modelAndView;
	}

	@RequestMapping(value = "/{codigo}", method = RequestMethod.POST)
	public String detalhesEventoPost(@PathVariable("codigo") long codigo, @Valid Convidado convidado,
			BindingResult result, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("erro", "verifique os campos!");
			return "redirect:/{codigo}";
		}
		else {
			Evento evento = er.findByCodigo(codigo);

			convidado.setEvento(evento);
			cr.save(convidado);
			redirectAttributes.addFlashAttribute("mensagem", "Convidado adicionado com sucesso!");
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
