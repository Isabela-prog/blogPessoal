package com.generation.blogpessoal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;

// api web padrão controller
@RestController
// aceitar os pedidos que chegam no endereco /postagens
@RequestMapping("/postagens")
// gerencia se a API é aberta ou fechada
// nesse caso é pública (ainda nao tem um site/domínio
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class PostagemController {
	// estanciar dentro do spring de modo privado (só funciona dentro dessa classe)
	@Autowired
	private PostagemRepository postagemRepository;
	
	// quando chegar um pedido roda esse método
	@GetMapping
	/* vai no banco (através da postagemRepository (que tem acesso ao bd) 
	 * e busca todas postagens
	 */
	public ResponseEntity<List<Postagem>> getAll(){
		// formato responseEntity -> formato json
		return ResponseEntity.ok(postagemRepository.findAll());
	}
	
	@GetMapping("/{id}")
	/* pathVariable -> o que vier na rota ele entende como id
	 * em Long id poderia ser qlqr nome -> cria a variável
	 */
	public ResponseEntity<Postagem> getById(@PathVariable Long id){
		// encontrar postagem
		return postagemRepository.findById(id)
				/*
				 *  mapear o que pegou
				 *  o que veio -> transforma em resposta 
				 *  se encontrar gera o json
				 */
				.map(resposta -> ResponseEntity.ok(resposta))
				//senão encontrar a resposta -> retorna o erro 404 
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
}
