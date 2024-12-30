package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.InvoiceStatisticsDTO;
import cz.itnetwork.dto.mapper.InvoiceMapper;
import cz.itnetwork.dto.mapper.PersonMapper;
import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.filter.InvoiceFilter;
import cz.itnetwork.entity.repository.InvoiceRepository;
import cz.itnetwork.entity.repository.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.stream.Collectors;
import java.util.List;


@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceMapper invoiceMapper;
    @Autowired
    private InvoiceRepository invoiceRepository;


    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonMapper personMapper;

    public InvoiceDTO addInvoice(InvoiceDTO invoiceDTO) { // InvoiceDTO - datový typ; invoiceDTO - proměnná
        InvoiceEntity newInvoice = invoiceMapper.toEntity(invoiceDTO);
        // newInvoice.setname(InvoiceDTO.getname) + použít repository a vytáhnout data a ulozit je do objektu
        PersonEntity buyer = newInvoice.getBuyer();
        Long buyerId = buyer.getId();
        PersonEntity buyerFromDatabase = personRepository.getReferenceById(buyerId);
        newInvoice.setBuyer(buyerFromDatabase);


        // newInvoice.setSeller(personRepository.getReferenceById(newInvoice.getSeller().getId()));
        PersonEntity seller = newInvoice.getSeller();
        Long sellerId = seller.getId();
        PersonEntity sellerFromDatabase = personRepository.getReferenceById(sellerId);
        newInvoice.setSeller(sellerFromDatabase);


        newInvoice = invoiceRepository.save(newInvoice);
        return invoiceMapper.toDTO(newInvoice);
    }

    /*
    @Override
    public List<InvoiceDTO> getAllInvoices() {
        return invoiceRepository.findAll()
                .stream()
                .map(i -> invoiceMapper.toDTO(i))
                .collect(Collectors.toList());
    }
    */
    @Override
    public List<InvoiceDTO> getAllInvoicesOfSeller(String identificationNumber) {
        return invoiceRepository.findAll()
                .stream()
                .filter(invoiceEntity -> invoiceEntity.getSeller().getIdentificationNumber().equals(identificationNumber))
                .map(i -> invoiceMapper.toDTO(i))
                .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceDTO> getAllInvoicesOfBuyer(String identificationNumber) {
        return invoiceRepository.findAll()
                .stream()
                .filter(invoiceEntity -> invoiceEntity.getBuyer().getIdentificationNumber().equals(identificationNumber))
                .map(i -> invoiceMapper.toDTO(i))
                .collect(Collectors.toList());
    }

    private InvoiceEntity fetchInvoiceById(long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Invoice with id" + id + "was not found in the database."));
    }

    @Override
    public InvoiceDTO getInvoiceById(long id) {
        InvoiceEntity invoiceEntity = fetchInvoiceById(id);
        return invoiceMapper.toDTO(invoiceEntity);
    }


    @Override
    public InvoiceDTO editInvoice(InvoiceDTO invoiceDTO, long id) {

        InvoiceEntity entity = invoiceRepository.getReferenceById(id);
        invoiceMapper.updateEntity(invoiceDTO, entity);

        PersonEntity buyer = entity.getBuyer();
        Long buyerId = buyer.getId();
        PersonEntity buyerFromDatabase = personRepository.getReferenceById(buyerId);
        entity.setBuyer(buyerFromDatabase);

        PersonEntity seller = entity.getSeller();
        Long sellerId = seller.getId();
        PersonEntity sellerFromDatabase = personRepository.getReferenceById(sellerId);
        entity.setSeller(sellerFromDatabase);


        InvoiceEntity saved = invoiceRepository.save(entity);
        return invoiceMapper.toDTO(saved);

    }
/*
    public MovieDTO editMovie(MovieDTO movieDTO, long id){
        movieDTO.setId(id);
        MovieEntity entity = movieRepository.getReferenceById(id);
        movieMapper.updateEntity(movieDTO, entity);

        mapPeopleToMovie(entity, movieDTO);
        MovieEntity saved = movieRepository.save(entity);
        return movieMapper.toDTO(saved); */
/*
    public void edit(ArticleDTO article) {
        ArticleEntity fetchedArticle = articleRepository
                .findById(article.getArticleId())
                .orElseThrow();

        articleMapper.updateArticleEntity(article, fetchedArticle);
        articleRepository.save(fetchedArticle);
    } */

    /*
        public MovieDTO editMovie(MovieDTO movieDTO, long id){
            movieDTO.setId(id);
            MovieEntity entity = movieRepository.getReferenceById(id);
            movieMapper.updateEntity(movieDTO, entity);

            entity.setActors(new ArrayList<>());
            for(Long actorId : movieDTO.getActorIDs()){
                entity.getActors().add(personRepository.getReferenceById(actorId));
            }
            entity.setDirector(personRepository.getReferenceById(movieDTO.getDirectorID()));
            MovieEntity saved = movieRepository.save(entity);
            return movieMapper.toDTO(saved);
        } */
/*
    public PersonDTO editPerson(long id, PersonDTO personDTO) {
        PersonEntity oldentity = fetchPersonById(id);
        oldentity.setHidden(true);
        personRepository.save(oldentity);
        PersonEntity updatedentity = personMapper.toEntity(personDTO);
        personRepository.saveAndFlush(updatedentity);
        return personMapper.toDTO(updatedentity);
    } */
        @Override
        public InvoiceDTO removeInvoice(long id) {
            InvoiceEntity invoice = invoiceRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Invoice not found with id: " + id));
            InvoiceDTO model = invoiceMapper.toDTO(invoice);
            invoiceRepository.delete(invoice);
            return model;
        }

        @Override
        public InvoiceStatisticsDTO getStatistics () {

            Long currentYearSum = invoiceRepository.getCurrentYearSum();
            Long allTimeSum = invoiceRepository.getAllTimeSum();
            Long invoiceCount = invoiceRepository.getInvoiceCount();

            return new InvoiceStatisticsDTO(currentYearSum, allTimeSum, invoiceCount);
        }


        @Override
        public List<InvoiceDTO> getFilteredInvoices(InvoiceFilter filter) {
            return invoiceRepository.getFilteredInvoices(filter, PageRequest.of(0,  filter.getLimit()))
                    .stream()
                    .map(invoiceMapper::toDTO)
                    .collect(Collectors.toList());
        }
    }
