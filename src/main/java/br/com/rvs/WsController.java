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
    		List<User> lstUsers = userRepository.findByLogin(user.getLogin());
    		
    		Optional<String> msgError = this.validateUser(user);
    		if (msgError.isPresent()) {
    			resposta.put("status", false);
    			resposta.put("erro", msgError);
    		}else {    		    		
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
    		Optional<String> msgError = this.validateUser(usuarioAtualizado);
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
    
    private Optional<String> validateUser(User user){
    	boolean hasError = false;
		String msgError = "";
    	if (user.getLogin().isEmpty()) {
			hasError = true;
			msgError+= "Login inválido. ";
		}
		if (user.getName().isEmpty()) {
			hasError = true;
			msgError+= "Nome inválido. ";
		}
		if (user.getPass().isEmpty()) {
			hasError = true;
			msgError+= "Senha inválida.";
		} 
		if (hasError) {
			return Optional.of(msgError);
		}
		return null;
    }

}
