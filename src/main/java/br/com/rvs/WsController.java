package br.com.rvs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.rvs.model.User;
import br.com.rvs.repository.IUserRepository;

@RestController
public class WsController {	
	
	@Autowired
	private IUserRepository userRepository;
	
    @PostMapping(path = "/user", consumes = "application/json", produces = "application/json")
    public @ResponseBody Map<Object, Object> cadastrarUser(@RequestBody User user) {
    	Map<Object, Object> resposta = new HashMap<>();    	    	
    	try {    		    		
    		Optional<String> msgError = this.validarUsuario(user);
    		if (msgError.isPresent()) {
    			resposta.put("status", false);
    			resposta.put("erro", msgError);
    		}else {    	
    			List<User> lstUsers = userRepository.findByLogin(user.getLogin());
	    		if (lstUsers.isEmpty()) {
		    		User newUser = new User(user.getName(), user.getLogin(), user.getPass());
		        	newUser = userRepository.save(newUser);		        	
		        	resposta.put("status", true);
		        	resposta.put("msg", "Usuário cadastrado com sucesso!");        	
		        	resposta.put("id", newUser.getId());
	    		}else {
	    			resposta.put("status", false);
	    			resposta.put("erro", "Usuário já cadastrado");	
	    		}
    		}
    	}catch(Exception e) {
    		resposta.put("status", false);
    		resposta.put("erro", "Erro ao realizar o cadastrado do usuário");
    	}
    	return resposta;
    }
    
    @PostMapping(path = "/login", consumes = "application/json", produces = "application/json")
    public @ResponseBody Map<Object, Object> login(@RequestBody User user) {
    	Map<Object, Object> resposta = new HashMap<>();    	    	
    	try {    	
    		String login = user.getLogin();
    		String pass = user.getPass();
    		if (login == null || login.isEmpty()){
    			resposta.put("status", false);
    			resposta.put("erro","Login Incorreto. ");
    		}else if (pass == null || pass.isEmpty()) {
    			resposta.put("status", false);
    			resposta.put("erro","Senha Incorreta.");
    		}else {    	
    			List<User> lstUsers = userRepository.findByLogin(login);
	    		if (lstUsers.isEmpty()) {		    				        	
		        	resposta.put("status", false);
		        	resposta.put("msg", "Login Incorreto. ");        			        	
	    		}else{
	    			User userRecorded = lstUsers.get(0);
	    			if (userRecorded.getPass().equals(pass)) {
	    				resposta.put("status", true);
		    			resposta.put("erro", "Confirmado");
	    			}else {
	    				resposta.put("status", true);
		    			resposta.put("erro", "Senha Incorreta");
	    			}
	    		}
    		}
    	}catch(Exception e) {
    		resposta.put("status", false);
    		resposta.put("erro", "Erro ao realizar o login do usuário");
    	}
    	return resposta;
    }
    
    @GetMapping(path = "/user/{id}", produces = "application/json")
    public @ResponseBody Optional<User> consultarUsuario(@PathVariable("id") String id) {
    	try {
    		Long longId = Long.valueOf(id);
    		return userRepository.findById(longId);	
    	}catch(Exception e) {
    		return null;
    	}    	
    }
    
    @PatchMapping(path = "/user/{id}", consumes = "application/json", produces = "application/json")
    public @ResponseBody Map<Object, Object> atualizarUsuario(@PathVariable("id") String id, @RequestBody User usuarioAtualizado) {
    	Map<Object, Object> resposta = new HashMap<>();    	
    	try {
    		Optional<String> msgError = this.validarUsuario(usuarioAtualizado);
    		if (msgError.isPresent()) {
    			resposta.put("status", false);
    			resposta.put("erro", msgError);
    		}else {    			
	    		Long longId = Long.valueOf(id);
	    		Optional<User> user = userRepository.findById(longId);
	    		if (user.isPresent()) {
	    			user.get().setLogin(usuarioAtualizado.getLogin());
	        		user.get().setName(usuarioAtualizado.getName());
	        		user.get().setPass(usuarioAtualizado.getPass()); 
	        		userRepository.save(user.get());
	        		resposta.put("status", true);
	            	resposta.put("msg", "Usuário atualizado com sucesso!");        	
	            	resposta.put("name", user.get().getName());
	            	resposta.put("login", user.get().getLogin());
	            	resposta.put("pass", user.get().getPass());
	    		}else {
	    			resposta.put("status", false);
	    			resposta.put("erro", "Usuário não encontrado");
	    		}
    		}
    	}catch(Exception e) {    		
    		resposta.put("status", false);
    		resposta.put("erro", "Erro ao realizar a atualização dos dados do usuário");
    	}
    	return resposta;       	
    }
    
    @DeleteMapping(path = "/user/{id}", produces = "application/json")
    public @ResponseBody Map<Object, Object> deletarUsuario(@PathVariable("id") String id) {
    	Map<Object, Object> resposta = new HashMap<>();    	
    	try {
    		Long longId = Long.valueOf(id);    		
			userRepository.deleteById(longId); 
			resposta.put("status", true);
			resposta.put("msg", "Usuario " + id + " deletado com sucesso.");			
    	}catch(Exception e) {  
    		resposta.put("status", false);
    		resposta.put("msg", "Erro ao deletar o usuario " + id);
    	} 
    	return resposta;
    }
    
    private Optional<String> validarUsuario(User user){
    	boolean hasError = false;
		String msgError = "";
		
		String login = user.getLogin();
    	if (login == null || login.isEmpty()) {
			hasError = true;
			msgError+= "Login inválido. ";
		}
    	String name = user.getName();
		if (name == null || name.isEmpty()) {
			hasError = true;
			msgError+= "Nome inválido. ";
		}
		String pass = user.getPass();
		if (pass == null || pass.isEmpty()) {
			hasError = true;
			msgError+= "Senha inválida.";
		} 
		
		if (hasError) {
			return Optional.of(msgError);
		}
		return Optional.empty();
    }

}
