package br.com.erudio.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.data.vo.v1.BookVO;
import br.com.erudio.services.BookServices;
import br.com.erudio.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

// CORS pode ser adicionado no Controller inteiro ou por operação
//@CrossOrigin(origins = {"http://localhost:8080","https://localhost:443"})// Desabilitado para ser aplicado global (via yaml)
@RestController
@RequestMapping(value = "/api/book")
@Tag(name = "Book", description = "Endpoints para manutenção de livros")
public class BookController {

	@Autowired
	BookServices service;
		
	@GetMapping(value = "/v1", produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Listar todos os livros", description = "Listar todos os livros", 
	tags = {"Book"}, 
	responses = {
			@ApiResponse(description = "Sucess", responseCode = "200", 
					content = { 
							@Content(
									mediaType = MediaType.APPLICATION_JSON,
									array = @ArraySchema(schema = @Schema(implementation = BookVO.class))
							)
					}),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unautorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
	public List<BookVO> findAll() {
		return service.findAll();
	}
	
	@GetMapping(value = "/v1/{id}", produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Consultar um livro específico", description = "Consultar um livro específico", 
	tags = {"Book"}, 
	responses = {
			@ApiResponse(description = "Sucess", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = BookVO.class))
			),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unautorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
	public BookVO findById(@PathVariable(value="id") Long id) {
		return service.findById(id);
	}
	
	@PostMapping(value = "/v1", produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML }, 
								consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Criar um livro", description = "Criar um livro", 
	tags = {"Book"}, 
	responses = {
			@ApiResponse(description = "Sucess", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = BookVO.class))
			),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unautorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
	public BookVO create(@RequestBody BookVO book) {
		return service.create(book);
	}
	
	@PutMapping(value = "/v1", produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML }, 
							   consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Alterar um livro", description = "Alterar um livro", 
	tags = {"Book"}, 
	responses = {
			@ApiResponse(description = "Sucess", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = BookVO.class))
			),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unautorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
	public BookVO update(@RequestBody BookVO book) {
		return service.update(book);
	}
	
	@DeleteMapping(value = "/v1", produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML }, consumes = MediaType.APPLICATION_JSON)
	@Operation(summary = "Excluir um livro", description = "Excluir um livro", 
	tags = {"Book"}, 
	responses = {
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unautorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
	public void delete(@PathVariable(value="id") Long id) {
		service.delete(id);
	}

}
