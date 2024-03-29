package com.senla.dao;

import com.senla.api.dao.IMaintenanceDao;
import com.senla.model.Maintenance;
import com.senla.model.Maintenance_;
import com.senla.model.dto.filter.MaintenanceFilter;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MaintenanceDao extends AbstractFilterDao<Maintenance, MaintenanceFilter> implements IMaintenanceDao {

    @Override
    protected Class<Maintenance> getClazz() {
        return Maintenance.class;
    }

    @Override
    protected Predicate[] getPredicates(MaintenanceFilter maintenanceFilter, CriteriaBuilder builder, Root<Maintenance> root) {

        List<Predicate> predicates = new ArrayList<>();

        if (!ObjectUtils.isEmpty(maintenanceFilter.getName())) {
            predicates.add(builder.like(root.get(Maintenance_.NAME), "%" + maintenanceFilter.getName() + "%"));
        }
        if (!ObjectUtils.isEmpty(maintenanceFilter.getDescription())) {
            predicates.add(builder.like(root.get(Maintenance_.DESCRIPTION), "%" + maintenanceFilter.getDescription() + "%"));
        }
        if (maintenanceFilter.getPriceFrom() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get(Maintenance_.PRICE), maintenanceFilter.getPriceFrom()));
        }
        if (maintenanceFilter.getPriceTo() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get(Maintenance_.PRICE), maintenanceFilter.getPriceTo()));
        }
        if (maintenanceFilter.getPlusDays() != null) {
            predicates.add(builder.equal(root.get(Maintenance_.PLUS_DAYS), maintenanceFilter.getPlusDays()));
        }
        return predicates.toArray(new Predicate[]{});
    }
}
