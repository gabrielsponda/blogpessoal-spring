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
import com.generation.blogpessoal.model.UsuarioLogin;
import com.generation.blogpessoal.repository.UsuarioRepository;
import com.generation.blogpessoal.service.UsuarioService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@BeforeAll
	public void start() {
		
		usuarioRepository.deleteAll();
		
		usuarioService.cadastrarUsuario(new Usuario(0L, "root", "root@root.com", "rootroot", ""));
	}
	
	@Test
	@DisplayName("Cadastrar Usuário")
	public void deveCriarUsuario() {
		
		// Corpo da requisição
		HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(new Usuario(0L, "Gabriel Rodrigues", "queridola@generation.com.br", "12345678", ""));
		
		// Requisição HTTP
		ResponseEntity<Usuario> corpoResposta = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST, corpoRequisicao, Usuario.class);
		
		assertEquals(HttpStatus.CREATED, corpoResposta.getStatusCode());
	}
	
	@Test
	@DisplayName("Não Deve Duplicar Usuário")
	public void naoDeveDuplicarUsuario() {
		
		usuarioService.cadastrarUsuario(new Usuario(0L, "Amanda Tsai", "amanda@email.com.br", "12345678", ""));
		
		// Corpo da requisição
		HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(new Usuario(0L, "Amanda Tsai", "amanda@email.com.br", "12345678", ""));
		
		// Requisição HTTP
		ResponseEntity<Usuario> corpoResposta = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST, corpoRequisicao, Usuario.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, corpoResposta.getStatusCode());
	}
	
	@Test
    @DisplayName("Deve Atualizar Usuário")
    public void deveAtualizarUmUsuario() {

        Optional<Usuario> usuarioCadastrado = usuarioService.cadastrarUsuario(new Usuario(0L,
                "Kendal Katherine", "kendal@email.com.br", "12345678", ""));

 
        HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(new Usuario(usuarioCadastrado
                .get().getId(), "Kendal Katherine Correia", "kendal@email.com.br", "87654321", ""));

        ResponseEntity<Usuario> corpoResposta = testRestTemplate
                .withBasicAuth("root@root.com", "rootroot")
                .exchange("/usuarios/atualizar", HttpMethod.PUT, corpoRequisicao, Usuario.class);

        assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());
	}
	
	@Test
	@DisplayName("Deve Listar Todos Usuários")
	public void deveListarTodosUsuarios() {

		usuarioService.cadastrarUsuario(new Usuario(0L, "Vitor Nascimento", "vitor@email.com.br", "12345678", ""));
		usuarioService.cadastrarUsuario(new Usuario(0L, "Samara Almeida", "samara@email.com.br", "12345678", ""));
		
		// Requisição HTTP
		ResponseEntity<String> corpoResposta = testRestTemplate.withBasicAuth("root@root.com", "rootroot").exchange("/usuarios", HttpMethod.GET, null, String.class);
		
		assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());
	}
	
    @Test
    @DisplayName("Deve Autenticar Usuário")
    public void deveAutenticarUsuario() {
        
        UsuarioLogin usuarioLogin = new UsuarioLogin();
        usuarioLogin.setUsuario("root@root.com");
        usuarioLogin.setSenha("rootroot");

        // Corpo da requisição
        HttpEntity<UsuarioLogin> corpoRequisicao = new HttpEntity<UsuarioLogin>(usuarioLogin);
     
        // Requisição HTTP
        ResponseEntity<UsuarioLogin> corpoResposta = testRestTemplate
                .exchange("/usuarios/logar", HttpMethod.POST, corpoRequisicao, UsuarioLogin.class);

        assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());
    }
    
	@Test
	@DisplayName("Deve Buscar Usuário Por ID")
	public void deveBuscarUsuarioId() {

		usuarioService.cadastrarUsuario(new Usuario(0L, "Gabriel Sponda", "sponda@email.com.br", "12345678", ""));
		
		// Requisição HTTP
		ResponseEntity<String> corpoResposta = testRestTemplate.withBasicAuth("root@root.com", "rootroot").exchange("/usuarios/1", HttpMethod.GET, null, String.class);
		
		assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());
	}
	
}
