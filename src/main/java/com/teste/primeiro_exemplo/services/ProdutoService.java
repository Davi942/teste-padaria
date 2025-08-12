package com.teste.primeiro_exemplo.services;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.teste.primeiro_exemplo.model.Produto;
import com.teste.primeiro_exemplo.model.exception.ResourceNotFoundException;
import com.teste.primeiro_exemplo.repository.ProdutoRepository;
import com.teste.primeiro_exemplo.shared.ProdutoDTO;


@Service
public class ProdutoService {
    
    @Autowired
    private ProdutoRepository produtoRepository;

    /**
     * Metodo para retornar uma lista de produtos
     * @return Lista de produtos.
     */
    public List<ProdutoDTO> obterTodos() {

        // Retorna uma lista de produto model
        List<Produto> produtos = produtoRepository.findAll();

        return produtos.stream()
        .map(produto -> new ModelMapper().map(produto, ProdutoDTO.class))
        .collect(Collectors.toList());

    }

    /**
     * Metodo que retorna o produto encontrado pelo seu Id.
     * @param id do produto que será localizado.
     * @return Retorna um produto caso seja encontrado
     */
    public Optional<ProdutoDTO> obterPorId(Integer id) {

        // Obtendo optional de produto pelo id
        Optional<Produto> produto = produtoRepository.findById(id);

        // Se não encontrar, lança exception 
        if(produto.isEmpty()) {
            throw new ResourceNotFoundException("Produto com id:" + id + "Não foi encontrado");
        }

        // Convertendo meu optional de produto em um produtoDTO
        ProdutoDTO dto = new ModelMapper().map(produto.get(), ProdutoDTO.class);

        // Criando e retornando um optional de produtoDTO
          return Optional.of(dto);
    }

     /**
     * Metodo para adicionar produto na lista
     * @param produto que será adicionado
     * @return retorna o produto que foi adicionado na lista
     */
    public ProdutoDTO adicionar(ProdutoDTO produtoDto) {

        //Removendo o id para conseguir fazer o cadastro
        produtoDto.setId(null);

        // Criar um objeto de mapeamento.
        ModelMapper mapper = new ModelMapper();

        // Converter o nosso produtoDTO em um produto
         Produto produto = mapper.map(produtoDto, Produto.class);

         // Salvar o produto no banco
         produto = produtoRepository.save(produto);
         produtoDto.setId(produto.getId());

        // retornar o produtoDTO atualizado.
        return produtoDto;

    }

    /**
     * Metodo para deletar um produto por id
     * @param id do produto que será deletado
     */
    public void deletar(Integer id) {
          // Verificar se o produto existe
          Optional<Produto> produto = produtoRepository.findById(id);
 
          if(produto.isEmpty()) {
            throw new ResourceNotFoundException("Produto com id:" + id + "não existe");
          }
        
        produtoRepository.deleteById(id);
   }

   /**
     * Metodo para atualizar o produto na lista
     * @param produto que será atualizado
     * @param id do produto
     * @return Retorna o produto após atualizar a lista.
     */
    public ProdutoDTO atualizar(Integer id, ProdutoDTO produtoDto) {
       
       // passar o id para o produtoDTO
       produtoDto.setId(id);

       // criar um objeto de mapeamento
       ModelMapper mapper = new ModelMapper();

       // converter o produtoDTO em um produto
       Produto produto = mapper.map(produtoDto, Produto.class);

       // atualizar o produto no banco
         produtoRepository.save(produto);
        
       // retornar o produtoDTO atualizado
       return produtoDto;
    }
}


