package com.senla.api.dao;

import com.senla.model.Maintenance;
import com.senla.model.dto.filter.MaintenanceFilter;

import javax.persistence.Entity;
import java.util.List;

public interface IMaintenanceDao {

    List<Maintenance> getAll();

    List<Maintenance> getByFilter(Object entity);

    Maintenance get(Long id);

    Maintenance update(Maintenance maintenance);

    void save(Maintenance maintenance);

    void delete(Long id);
}
