package supham.cntt.tuquanao.mapper;

import org.mapstruct.Mapper;
import supham.cntt.tuquanao.dto.ProductNew;
import supham.cntt.tuquanao.dto.ProductPriceDTO;

@Mapper(componentModel = "spring")
public interface ProductNewMapper {

 ProductNew productPriceDTOToProductNew(ProductPriceDTO productPriceDTO);
}
