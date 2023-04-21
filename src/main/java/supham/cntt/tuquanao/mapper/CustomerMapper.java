package supham.cntt.tuquanao.mapper;

import org.mapstruct.Mapper;
import supham.cntt.tuquanao.dto.CustomerRegisterDTO;
import supham.cntt.tuquanao.model.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

  Customer customerRegisterDTOToCustomer(CustomerRegisterDTO registerDTO);
}
