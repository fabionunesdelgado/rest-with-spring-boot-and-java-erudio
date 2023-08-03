package br.com.erudio.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.erudio.controllers.PersonController;
import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.data.vo.v2.PersonVOV2;
import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.mapper.DozerMapper;
import br.com.erudio.mapper.custom.PersonMapper;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;

@Service
public class PersonServices {
	
	private Logger logger = Logger.getLogger(PersonServices.class.getName());

	@Autowired
	PersonRepository repository;
	
	@Autowired
	PersonMapper mapper;
	
	public PersonVOV2 create(PersonVOV2 person) {
		logger.info("Creating one person with V2");
		
		var entity = DozerMapper.parseObject(person, Person.class);
		
		return DozerMapper.parseObject(repository.save(entity), PersonVOV2.class);
	}
	
	public PersonVO create(PersonVO person) {
		logger.info("Creating one person");
		
		var entity = DozerMapper.parseObject(person, Person.class);

		// Mapeamento com Dozer e Persistência com Repository
		var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
				
		// Add Hateoas
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		
		return vo;
	}
	
	public PersonVOV2 createV2(PersonVOV2 person) {
		logger.info("Creating one person");
		
		// Mapeamento customizado para V2 (com novo campo de Data Nascimento)
		var entity = mapper.convertVOToEntity(person);
		
		// Persistencia com Repository
		var vo = mapper.convertEntityToVO(repository.save(entity));
		
		// Adicionar Hateoas
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		
		return vo;
	}
	
	public PersonVO update(PersonVO person) {
		logger.info("Updating one person");
		
		Person entity = repository.findById(person.getKey()).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID."));
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		// Mapeamento com Dozer e Persistencia com Repopsitory
		var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
				
		// Add Hateoas
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
				
		return vo;
	}

	public void delete(Long id) {
		logger.info("Deleting one person");
		
		Person entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID."));
		
		repository.delete(entity);
	}
	
	public PersonVO findById(Long id) {
		logger.info("Finding one person");		
		
		// Conectando com o Repository
		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID."));
		
		// Mapeamento com Dozer
		var vo = DozerMapper.parseObject(entity, PersonVO.class);
		
		// Add Hateoas
		vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		
		return vo;
	}
	
	public List<PersonVO> findAll() {
		logger.info("Finding all person");
		
		// Mapeamento com Dozer
		var vo = DozerMapper.parseListObject(repository.findAll(), PersonVO.class);
			
		// Add Hateoas
		vo.stream().forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
		
		return vo;
	}

}
