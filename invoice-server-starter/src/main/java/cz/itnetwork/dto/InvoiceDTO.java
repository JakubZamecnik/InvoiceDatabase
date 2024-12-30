package cz.itnetwork.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDTO {

    private long id;

    private int invoiceNumber;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate issued;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

    private String product;

    private Long price; // Long - můžu zadat null

    private int vat; // int nemůžu zadat null

    private String note;

    private PersonDTO buyer;

    private PersonDTO seller;
}
