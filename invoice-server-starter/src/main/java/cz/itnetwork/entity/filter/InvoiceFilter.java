package cz.itnetwork.entity.filter;

import lombok.Data;

@Data
public class InvoiceFilter {

    private Long buyerID = -1L;
    private Long sellerID = -1L;
    private String product = "";
    private Integer minPrice;
    private Integer maxPrice;
    private Integer limit = 10;
}
