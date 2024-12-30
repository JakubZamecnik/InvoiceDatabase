package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.InvoiceStatisticsDTO;
import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.filter.InvoiceFilter;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public interface InvoiceService {

    InvoiceDTO addInvoice(InvoiceDTO invoiceDTO);
    /*
    List<InvoiceDTO> getAllInvoices();
    */
    List<InvoiceDTO> getFilteredInvoices(InvoiceFilter filter);

    List<InvoiceDTO> getAllInvoicesOfSeller(String identificationNumber);

    List<InvoiceDTO> getAllInvoicesOfBuyer(String identificationNumber);


    InvoiceDTO getInvoiceById(long id);

    InvoiceDTO editInvoice(InvoiceDTO invoiceDTO, long id);

    InvoiceStatisticsDTO getStatistics();


    InvoiceDTO removeInvoice(long id);
}
