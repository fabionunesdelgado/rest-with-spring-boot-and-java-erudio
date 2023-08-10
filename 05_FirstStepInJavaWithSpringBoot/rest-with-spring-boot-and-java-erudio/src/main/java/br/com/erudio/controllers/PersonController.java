package br.com.erudio.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.data.vo.v2.PersonVOV2;
import br.com.erudio.services.PersonServices;
import br.com.erudio.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

//CORS pode ser adicionado no Controller inteiro ou por operação
//@CrossOrigin(origins = {"http://localhost:8080","https://localhost:443"}) // Desabilitado para ser aplicado global (via yaml)
@RestController
@RequestMapping(value="/api/person")
@Tag(name = "Person", description = "Endpoints para manutenção de pessoas")
public class PersonController {

	@Autowired
	private PersonServices service;
	
	@GetMapping(value = "/v1", produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Listar todas as pessoas", description = "Listar todas as pessoas", 
		tags = {"Person"}, 
		responses = {
				@ApiResponse(description = "Sucess", responseCode = "200", 
						content = { 
								@Content(
										mediaType = MediaType.APPLICATION_JSON,
										array = @ArraySchema(schema = @Schema(implementation = PersonVO.class))
								)
						}),
				@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
				@ApiResponse(description = "Unautorized", responseCode = "401", content = @Content),
				@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
				@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
		})
	public List<PersonVO> findAll() {
		return service.findAll();
	}
	
	@GetMapping(value = "/v1/{id}", produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Consultar uma pessoa específica", description = "Consultar uma pessoa específica", 
	tags = {"Person"}, 
	responses = {
			@ApiResponse(description = "Sucess", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = PersonVO.class))
			),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unautorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
	public PersonVO findById(@PathVariable(value="id") Long id) {
		return service.findById(id);
	}

	@PostMapping(value = "/v1", produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML }, consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Criar uma pessoa", description = "Criar uma pessoa", 
	tags = {"Person"}, 
	responses = {
			@ApiResponse(description = "Sucess", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = PersonVO.class))
			),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unautorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
	public PersonVO create(@RequestBody PersonVO person) {
		return service.create(person);
	}

	@PostMapping(value = "/v2", produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML }, consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Criar uma pessoa com opção de data de nascimento", description = "Criar uma pessoa com opção de data de nascimento", 
	tags = {"Person"}, 
	responses = {
			@ApiResponse(description = "Sucess", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = PersonVO.class))
			),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unautorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
	public PersonVOV2 createV2(@RequestBody PersonVOV2 person) {
		return service.createV2(person);
	}

	@PutMapping(value = "/v1", produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML }, consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Alterar uma pessoa", description = "Alterar uma pessoa", 
	tags = {"Person"}, 
	responses = {
			@ApiResponse(description = "Sucess", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = PersonVO.class))
			),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unautorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
	public PersonVO update(@RequestBody PersonVO person) {
		return service.update(person);
	}
	
	@DeleteMapping(value = "/v1/{id}", produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Excluir uma pessoa", description = "Excluir uma pessoa", 
	tags = {"Person"}, 
	responses = {
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unautorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
	})
	public ResponseEntity<?> delete(@PathVariable(value="id") Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
