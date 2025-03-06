package com.generation.blogpessoal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.blogpessoal.model.Postagem;

// na repository só assinatura de métodos
// consegue buscar qualquer objeto dentro da classe Postagem, que tem como id Long
public interface PostagemRepository extends JpaRepository<Postagem, Long>{
	
	public List<Postagem> findAllByTituloContainingIgnoreCase(@Param ("titulo") String titulo);
}
