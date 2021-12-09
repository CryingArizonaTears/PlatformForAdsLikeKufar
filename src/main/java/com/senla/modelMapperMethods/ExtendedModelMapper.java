package com.senla.modelMapperMethods;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExtendedModelMapper extends ModelMapper {

    public <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> super.map(element, targetClass))
                .collect(Collectors.toList());
    }
}
