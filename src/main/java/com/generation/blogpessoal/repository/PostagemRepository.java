package com.generation.blogpessoal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.blogpessoal.model.Postagem;

// consegue buscar qualquer objeto dentro da classe Postagem, que tem como id Long
public interface PostagemRepository extends JpaRepository<Postagem, Long>{
	
}
