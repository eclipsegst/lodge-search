package com.example.reserve.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.example.reserve.domain.Landlord;

public interface LandlordRepository extends CrudRepository<Landlord, Long> {

    List<Landlord> findByName(String name);
}
