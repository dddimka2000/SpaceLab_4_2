package org.example.mapper;

import org.example.dto.BranchDto;
import org.example.entity.Branch;
import org.springframework.stereotype.Component;

@Component
public class BranchMapper {
    public Branch toEntity(BranchDto branchDto){
        Branch branch = new Branch();
        branch.setId(branchDto.getId());
        branch.setCode(branchDto.getCode());
        branch.setName(branchDto.getName());
        branch.setAddress(branchDto.getAddress());
        branch.setTelephone(branchDto.getTelephone());
        branch.setEmail(branchDto.getEmail());
        return branch;
    }
}