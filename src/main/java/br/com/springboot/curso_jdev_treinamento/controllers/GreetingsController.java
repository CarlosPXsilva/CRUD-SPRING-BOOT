package br.com.springboot.curso_jdev_treinamento.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.curso_jdev_treinamento.model.Usuario;
import br.com.springboot.curso_jdev_treinamento.repository.UsuarioRepository;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
public class GreetingsController {
	
	@Autowired /*IC/CD ou CDI - Injeção de dependencia*/
	private UsuarioRepository usuarioRepository;
	/**
	 *
	 * @param name the name to greet
	 * @return greeting text
	 */
	@RequestMapping(value = "/mostrarnome/{name}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String greetingText(@PathVariable String name) {
		return "Curso Spring Boot API: " + name + "!";
	}

	@RequestMapping(value = "/olamundo/{nome}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String retornaOlaMundo(@PathVariable String nome) {
		
		Usuario usuario = new Usuario();
		usuario.setNome(nome);
		usuarioRepository.save(usuario);/*Gravar no banco de dados*/
		return "Olá mundo " + nome;
	}
	
	@GetMapping(value = "listatodos") /*Nosso primeiro método de api*/
	@ResponseBody /*Retorna os dados para o corpo da resposta*/
	public ResponseEntity<List<Usuario>> listaUsuario(){
		
		List<Usuario> usuarios = usuarioRepository.findAll(); /*executa a consulta no banco de dados*/
		
		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
	}
	
	@PostMapping(value = "salvar")/*Mapeia a url*/
	@ResponseBody /*Descrição da resposta*/
	public ResponseEntity<Usuario> salvar (@RequestBody Usuario usuario){/*recebe os dados para salvar*/
		Usuario user = usuarioRepository.save(usuario);
		
		return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
		
	}
	
	@DeleteMapping(value = "delete")/*Mapeia a url*/
	@ResponseBody /*Descrição da reposta*/
	public ResponseEntity<String> delete(@RequestParam Long iduser){/*pra deletar se utiliza o @RequestParam e o tipo do parametro */
		
		usuarioRepository.deleteById(iduser);
		return new ResponseEntity<String>("Usuário deletado com sucesso", HttpStatus.OK);
	}
	
	@GetMapping(value = "buscaruserid")/*Mapeia a url*/
	@ResponseBody /*Descrição da reposta*/
	public ResponseEntity<Usuario> buscaruserid(@RequestParam(name = "iduser") Long iduser){/*recebe os dados para consultar*/
		
		Usuario usuario = usuarioRepository.findById(iduser).get();
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}
	
	@PutMapping(value = "atualizar")/*Mapeia a url*/
	@ResponseBody /*Descrição da resposta*/
	public ResponseEntity<?> atualizar (@RequestBody Usuario usuario){/*recebe os dados para salvar*/
		
		if(usuario.getId() == null) {
			return new ResponseEntity<String>("Id não foi informado para atualização.", HttpStatus.OK );
		}
		
		Usuario user = usuarioRepository.saveAndFlush(usuario);
		
		return new ResponseEntity<Usuario>(user, HttpStatus.OK);
		
	}
	
	@GetMapping(value = "buscarPorNome")/*Mapeia a url*/
	@ResponseBody /*Descrição da reposta*/
	public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam(name = "name") String name ){/*recebe os dados para consultar*/
		
		List<Usuario> usuario = usuarioRepository.buscarPorNome(name.trim().toUpperCase());/*trim retira o espaço na pesquisa*/
		
		return new ResponseEntity<List<Usuario>>(usuario, HttpStatus.OK);
	}
}
