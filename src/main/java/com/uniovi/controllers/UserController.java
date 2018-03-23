package com.uniovi.controllers;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.Petition;
import com.uniovi.entities.PetitionStatus;
import com.uniovi.entities.User;
import com.uniovi.services.PetitionsService;
import com.uniovi.services.RolesService;
import com.uniovi.services.SecurityService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.SignUpFormValidator;

@Controller
public class UserController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UsersService usersService;

	@Autowired
	private PetitionsService petitionsService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private SignUpFormValidator signUpFormValidator;
	
	@Autowired
	private RolesService rolesService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		return "login";
	}

	@RequestMapping(value = "/logoutFromLogin", method = RequestMethod.GET)
	public String logout(Model model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		log.info("User: " + auth.getName() + " logged out from the application");

		return "redirect:/";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String setUser(@Validated User user, BindingResult result, Model model) {
		signUpFormValidator.validate(user, result);
		if (result.hasErrors()) {
			return "signup";
		}
		
		user.setRole(rolesService.getRoles()[0]);
		usersService.addUser(user);
		securityService.autoLogin(user.getEmail(), user.getPasswordConfirm());

		return "redirect:user/list";
	}
	
	@RequestMapping("/log")
	public String log() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		log.info("User: " + auth.getName()  +" logged in the application");
		return "redirect:user/list";
	}

	@RequestMapping("/user/list")
	public String getList(Model model, Pageable pageable,
			@RequestParam(defaultValue = "", required = false) String searchText) {

		// poner al usuario en linea
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		
		User currentUser = usersService.getUserByEmail(email);

		Page<User> users;
		if (searchText != null && !searchText.isEmpty()) {
			users = usersService.searchUsersByEmailAndName(pageable, searchText);
		} else {
			users = usersService.getUsers(pageable);
		}

		List<User> usuariosDestinos = usersService.searchUsersDestinosForUser(currentUser);
		Page<User> peticionesSolicitadas = usersService.searchSentPetitionsForUser(pageable,currentUser);
		Page<User> amigosPage = usersService.searchFriendsForUser(pageable, currentUser);

		model.addAttribute("usersList", users);
		model.addAttribute("currentUser", currentUser);
		model.addAttribute("usuariosDestinos", usuariosDestinos);
		model.addAttribute("peticionesSolicitadas", peticionesSolicitadas.getContent());
        model.addAttribute("amigos", amigosPage.getContent());
		model.addAttribute("page", users);
		model.addAttribute("searchText", searchText);
		return "user/list";
	}

	@RequestMapping("/user/petitions")
	public String getPetitions(Model model, Principal principal) {

		List<User> users = new LinkedList<User>();

		String email = principal.getName();
		User userDestino = usersService.getUserByEmail(email);

		List<Petition> petitions = petitionsService.searchPetitionByDestinationUser(userDestino);

		for (Petition petition : petitions)
			if (petition.getStatus().equals(PetitionStatus.EN_PROCESO))
				users.add(petition.getUserOrigen());

		Page<User> filteredUsers = new PageImpl<User>(users);

		model.addAttribute("usersList", filteredUsers);
		model.addAttribute("page", filteredUsers);
		return "user/petitions";
	}

	@RequestMapping("/user/friends")
	public String getFriends(Model model, Pageable pageable, Principal principal) {

		String email = principal.getName();
		User currentUser = usersService.getUserByEmail(email);

		Page<User> amigos = usersService.searchFriendsForUser(pageable, currentUser);

		model.addAttribute("amigos", amigos);
		model.addAttribute("page", amigos);

		return "user/friends";
	}

	@RequestMapping(value = "/user/{id}/sendPetition", method = RequestMethod.GET)
	public String sendPetition(Model model, @PathVariable Long id) {
		
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        List<Petition> petitions = petitionsService.searchPetitions(email);

        if(petitions.isEmpty()) {
        	long idOrigin = usersService.getIdOriginUser();
    		User userOrigin = usersService.getUser(idOrigin);

    		long idDestino = id;
    		User userDestino = usersService.getUser(idDestino);

    		// CREAMOS LA PETICIÓN
    		Petition peticion = new Petition(userOrigin, userDestino);
    		
    		// se mete en la tabla de peticiones
    		petitionsService.addPetition(peticion);// añadimos la petición al repositorio
        }

		return "redirect:/user/list";
	}

	@RequestMapping(value = "/user/{id}/cancelPetition", method = RequestMethod.GET)
	public String cancelPetition(Model model, @PathVariable Long id) {
		long idOrigin = usersService.getIdOriginUser();
		User userOrigin = usersService.getUser(idOrigin);

		long idDestino = id;
		User userDestino = usersService.getUser(idDestino);

		petitionsService.cancelarPetition(userOrigin, userDestino);
		return "redirect:/user/list";
	}

	@RequestMapping(value = "/user/{id}/acceptPetition", method = RequestMethod.GET)
	public String acceptPetition(Model model, @PathVariable Long id) {
		long idDestination = usersService.getIdOriginUser();
		User userDestination = usersService.getUser(idDestination);

		long idOrigin = id;
		User userOrigin = usersService.getUser(idOrigin);
		
		List<Petition> petitions = petitionsService.searchPetitionByOriginUserAndDestinationUser(userOrigin,
				userDestination);

		long idPetition = petitions.get(0).getId();

		petitionsService.updateStatus(PetitionStatus.TERMINADA, idPetition);
		return "redirect:/user/petitions";
	}

	@RequestMapping("/user/list/update")
	public String updateList(Model model, Pageable pageable) {		
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		
		User currentUser = usersService.getUserByEmail(email);

		Page<User> users = usersService.getUsers(pageable);

		List<User> usuariosDestinos = usersService.searchUsersDestinosForUser(currentUser);
		Page<User> peticionesSolicitadas = usersService.searchSentPetitionsForUser(pageable,currentUser);
		Page<User> amigosPage = usersService.searchFriendsForUser(pageable, currentUser);

		model.addAttribute("usersList", users);
		model.addAttribute("currentUser", currentUser);
		model.addAttribute("usuariosDestinos", usuariosDestinos);
		model.addAttribute("peticionesSolicitadas", peticionesSolicitadas.getContent());
        model.addAttribute("amigos", amigosPage.getContent());
		model.addAttribute("page", users);

		return "user/list :: tableUsers";
	}

	@RequestMapping("/user/petitions/update")
	public String updatePetitions(Model model, Pageable pageable, Principal principal) {

		List<User> users = new LinkedList<User>();

		String email = principal.getName();
		User userDestino = usersService.getUserByEmail(email);

		List<Petition> petitions = petitionsService.searchPetitionByDestinationUser(userDestino);

		for (Petition petition : petitions)
			if (petition.getStatus().equals(PetitionStatus.EN_PROCESO))
				users.add(petition.getUserOrigen());

		Page<User> filteredUsers = new PageImpl<User>(users);

		model.addAttribute("usersList", filteredUsers);

		return "user/petitions :: tablePetitions";
	}

}
