package org.emanuel.offices.application.impl;

import lombok.RequiredArgsConstructor;
import org.emanuel.offices.application.ITypeOfficeService;
import org.emanuel.offices.domain.TypeOffice;
import org.emanuel.offices.domain.exceptions.TypeOfficeNotExistsDomainException;
import org.emanuel.offices.domain.db.repository.TypeOfficeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TypeOfficeServiceImpl implements ITypeOfficeService {
    private final TypeOfficeRepository typeOfficeRepository;

    @Override
    public List<TypeOffice> getTypeOffices() {
        return typeOfficeRepository.findAll();
    }

    @Override
    public TypeOffice getTypeOfficeById(Long id) {
        return typeOfficeRepository.findById(id).orElseThrow(() -> new TypeOfficeNotExistsDomainException("Office type with id " + id + " does not exist"));
    }

    @Override
    public Boolean existsById(Long id) {
        return typeOfficeRepository.existsById(id);
    }
}
