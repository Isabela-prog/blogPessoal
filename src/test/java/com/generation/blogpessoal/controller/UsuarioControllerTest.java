package com.generation.blogpessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.repository.UsuarioRepository;
import com.generation.blogpessoal.service.UsuarioService;

/*
 * fazer requisições http Request e http Response verifica
 * se as respostas foram esperadas
 */

//webEnvironment -> se a porta 8080 estiver em uso, o spring cria uma nova para os testes
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//ciclo de vida será por classe
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioControllerTest {
	
	//enviar requisições para a API
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	//persistir objetos no bd de testes com senha criptografada
	@Autowired
	private UsuarioService usuarioService;
	
	//limpar o bd
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	// limpa todos os dados da tabela e cria o usuário root
	@BeforeAll
	void start() {
		
		usuarioRepository.deleteAll();
		
		usuarioService.cadastrarUsuario(new Usuario(null,
				"Root", "root@root.com", "rootroot", " "));
	}
	
	@Test
	@DisplayName("Cadastrar um usuário")
	// recebe um objeto da Classe usuario
	public void deveCriarUmUsuario() {
		HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(new Usuario(
				null, "Isabela Santos", "isabelaSantos@gmail.com","13465278", "-"));
		
		//requisição é enviada pelo exchange e a resposta recebida pelo corpoResposta
		ResponseEntity<Usuario> corpoResposta = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST,
				corpoRequisicao, Usuario.class);
		
		assertEquals(HttpStatus.CREATED, corpoResposta.getStatusCode());
	}
	
	@Test
	@DisplayName("Não deve permitir duplicação de usuário")
	public void naoDeveDuplicarUsuario() {
		usuarioService.cadastrarUsuario(new Usuario(null, "Aline Souza",
				"aline_souza@gmail.com", "12345678", "-"));
		
		//mesmo objeto persistido
		HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(new Usuario(null, "Aline Souza",
				"aline_souza@gmail.com", "12345678", "-"));
		
		ResponseEntity<Usuario> corpoResposta = testRestTemplate.exchange(
				"/usuarios/cadastrar", HttpMethod.POST, corpoRequisicao, Usuario.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, corpoResposta.getStatusCode());
	}
	
	@Test
	@DisplayName("Deve atualizar um Usuário")
	public void deveAtualizarUmUsuario() {
		
		Optional<Usuario> usuarioCadastrado = usuarioService.cadastrarUsuario(
				new Usuario(null, "Juliana Silva", "juliana_silva@gmail.com", "juliana123", "-"));
				
		Usuario usuarioUpdate = new Usuario(usuarioCadastrado.get().getId(),
				"Juliana Silva", "juliana_silva@gmail.com", "juliana123", "-");
		
		HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(usuarioUpdate);
		
		ResponseEntity<Usuario> corpoResposta = testRestTemplate.withBasicAuth("root@root.com", "rootroot").
				exchange("/usuarios/atualizar", HttpMethod.PUT, corpoRequisicao, Usuario.class);
		
		assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());
		
	}
	
	@Test
	@DisplayName("Listar todos os usuários")
	public void deveMostrarTodosUsuarios() {
		
		usuarioService.cadastrarUsuario(new Usuario(null,
				"Sabrina Sanches", "sabrina_sanches@gmail.com", "sabrina123", "-"));
		
		usuarioService.cadastrarUsuario(new Usuario(null,
				"Ricardo Marques", "ricar_marques@gmail.com", "ricardo123", "-"));
		
		ResponseEntity<String> resposta = testRestTemplate.withBasicAuth("root@root.com", "rootroot").
				exchange("/usuarios/all", HttpMethod.GET, null, String.class);
		
		//assert -> checar se a resposta será ok -> 200
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		
	}
}
