package com.todoapp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.todoapp.model.Role;
import com.todoapp.model.Usuario;
import com.todoapp.repository.RoleRepository;
import com.todoapp.repository.UsuarioRepository;

@Controller
public class AutenticacaoController {

	@Autowired
	private UsuarioRepository ur;
	
	@Autowired
	private RoleRepository rr;

	@RequestMapping(value = "/cadastrarUsuario", method = RequestMethod.GET)
	public String cadastrarUsuario() {

		return "cadastroUsuario";
	}

	@RequestMapping(value = "/cadastrarUsuario", method = RequestMethod.POST)
	public String cadastrarUsuario(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			attributes.addFlashAttribute("erro", "Verifique os campos!");
			return "redirect:/cadastrarUsuario";
		} else {
			try {
				Usuario u = new Usuario();
				u = ur.findByLogin(usuario.getLogin());
				if (u != null) {
					attributes.addFlashAttribute("erro", "Usuário já possui cadasto!");
					return "redirect:/cadastrarUsuario";
				} else {
					SimpleHash hash = new SimpleHash("sha-256", usuario.getSenha());
					usuario.setSenha(hash.toHex());
					List<Role> roles = new ArrayList<Role>();
					Role role = new Role();
					role = rr.findByRole("ROLE_ADMIN");
					roles.add(role);
					usuario.setRoles(roles);
					ur.save(usuario);
					attributes.addFlashAttribute("mensagem", "Usuário cadastrado com sucesso!");
					return "redirect:/login";
				}
			} catch (Exception e) {
				// TODO: handle exception
				attributes.addFlashAttribute("erro", "Erro ao cadastrar usuário!");
				return "redirect:/cadastrarUsuario";
			}
		}
	}

	// finaliza sessão
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}

}
