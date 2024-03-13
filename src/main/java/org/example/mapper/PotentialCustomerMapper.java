package org.example.mapper;

import org.example.dto.PotentialCustomerDtoForView;
import org.example.entity.PotentialCustomer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PotentialCustomerMapper {
    @Mappings({
            @Mapping(target = "realtorFullName", ignore = true),
            @Mapping(target = "builderObject", ignore = true)
    })
    PotentialCustomerDtoForView toDto(PotentialCustomer potentialCustomer);
    default PotentialCustomerDtoForView toDtoFromEntity(PotentialCustomer potentialCustomer){
        PotentialCustomerDtoForView potentialCustomerDtoForView = toDto(potentialCustomer);
        if(potentialCustomer.getBuilderObject()!=null)potentialCustomerDtoForView.setBuilderObject(potentialCustomer.getBuilderObject().getName());
        potentialCustomerDtoForView.setRealtorFullName(potentialCustomer.getRealtor().getSurname()+" "+potentialCustomer.getRealtor().getName());
        return potentialCustomerDtoForView;
    }
    default Page<PotentialCustomerDtoForView> toDtoList(Page<PotentialCustomer> potentialCustomers){
        return new PageImpl<>(potentialCustomers.getContent().stream()
                .map(potentialCustomer -> toDtoFromEntity(potentialCustomer))
                .collect(Collectors.toList()), potentialCustomers.getPageable(), potentialCustomers.getTotalElements());
    }
}