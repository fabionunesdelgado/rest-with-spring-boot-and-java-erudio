package br.com.erudio.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.erudio.controllers.BookController;
import br.com.erudio.controllers.PersonController;
import br.com.erudio.data.vo.v1.BookVO;
import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.mapper.DozerMapper;
import br.com.erudio.model.Book;
import br.com.erudio.repositories.BookRepository;

@Service
public class BookServices {

	private Logger logger = Logger.getLogger(BookServices.class.getName());
	
	@Autowired
	BookRepository repository;
	
	public BookServices() {}
	
	public List<BookVO> findAll() {
		logger.info("Finding all books");
		
		// Buscando os livros através do repository
		// Mapeando Model to VO
		var vo = DozerMapper.parseListObject(repository.findAll(), BookVO.class);
		
		// Hateoas
		
		
		return vo;
	}
	
	public BookVO findById(Long id) {
		logger.info("Finding a book by id");
		
		// Buscando os livros através do repository
		// Mapeando Model to VO
		var vo = DozerMapper.parseObject(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID.")), BookVO.class);
		
		// Hateoas
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
		
		return vo;
	}
	
	public BookVO create(BookVO book) {
		logger.info("Creating one book");
		
		// Mapeamento utilizando DozerMapper (VO to Model)
		var entity = DozerMapper.parseObject(book, Book.class);
		
		// Persistência		
		// Mapeamento utilizando DozerMapper (Model to VO)
		var vo = DozerMapper.parseObject(repository.save(entity), BookVO.class);
		
		// Hateoas
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
		
		return vo;
	}
	
	public BookVO update(BookVO book) {
		logger.info("Updating one book");
		
		// Consulta se existe o livro e se existir o modifica
		Book entity = repository.findById(book.getKey()).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID."));
		entity.setAuthor(book.getAuthor());
		entity.setLaunchDate(book.getLaunchDate());
		entity.setPrice(book.getPrice());
		entity.setTitle(book.getTitle());
		
		// Persistência	
		// Mapeamento utilizando DozerMapper (Model to VO)
		var vo = DozerMapper.parseObject(repository.save(entity), BookVO.class);
		
		// Hateoas
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());

		return vo;
	}
	
	public void delete(Long id) {
		logger.info("Deleting one book");
		
		// Consulta o livro utilizando o Repository
		Book entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID."));
		
		// Persistência
		repository.delete(entity);

	}

}
