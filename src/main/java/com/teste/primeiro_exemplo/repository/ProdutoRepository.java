package com.teste.primeiro_exemplo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teste.primeiro_exemplo.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    
}
