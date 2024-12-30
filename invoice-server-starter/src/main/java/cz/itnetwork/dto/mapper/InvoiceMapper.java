package cz.itnetwork.dto.mapper;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.entity.InvoiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    InvoiceEntity toEntity(InvoiceDTO source);

    InvoiceDTO toDTO(InvoiceEntity source);

    InvoiceEntity updateEntity(InvoiceDTO source, @MappingTarget InvoiceEntity target);

    InvoiceDTO updateDTO(InvoiceEntity source, @MappingTarget InvoiceDTO target);
}
