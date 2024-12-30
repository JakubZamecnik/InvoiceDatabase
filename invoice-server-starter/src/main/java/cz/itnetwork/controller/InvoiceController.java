package cz.itnetwork.controller;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.InvoiceStatisticsDTO;
import cz.itnetwork.entity.filter.InvoiceFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import cz.itnetwork.service.InvoiceService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;



    @PostMapping("/invoices")
    public InvoiceDTO addInvoice(@RequestBody InvoiceDTO invoiceDTO) {
        return invoiceService.addInvoice(invoiceDTO);
    }
    /*
    @GetMapping("/invoices")
    public List<InvoiceDTO> getInvoices(InvoiceDTO invoiceDTO) {
        return invoiceService.getAllInvoices();
    }
    */
    @GetMapping("/identification/{identificationNumber}/sales")
    public List<InvoiceDTO> getInvoicesOfSeller(@PathVariable String identificationNumber, InvoiceDTO invoiceDTO) {
        return invoiceService.getAllInvoicesOfSeller(identificationNumber);
    }

    @GetMapping("/identification/{identificationNumber}/purchases")
    public List<InvoiceDTO> getInvoicesOfBuyer(@PathVariable String identificationNumber, InvoiceDTO invoiceDTO) {
        return invoiceService.getAllInvoicesOfBuyer(identificationNumber);
    }

    @GetMapping("invoices/{id}")
    public InvoiceDTO getInvoiceById(@PathVariable long id) {
        return invoiceService.getInvoiceById(id);
    }

    @PutMapping({"/invoices/{id}", "/invoices/{id}"})
    public InvoiceDTO editInvoice(@PathVariable long id, @RequestBody InvoiceDTO invoiceDTO){
       return invoiceService.editInvoice(invoiceDTO, id);
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("invoices/{id}")
    public void deleteInvoice(@PathVariable long id) {
        invoiceService.removeInvoice(id);
    }

    @GetMapping("/invoices/statistics")
    public InvoiceStatisticsDTO getStatistics() {
        return invoiceService.getStatistics();

    }

    @GetMapping({"/invoices"})
    public List<InvoiceDTO> getFilteredInvoices (InvoiceFilter filter) {
        return invoiceService.getFilteredInvoices(filter);
    }


}
