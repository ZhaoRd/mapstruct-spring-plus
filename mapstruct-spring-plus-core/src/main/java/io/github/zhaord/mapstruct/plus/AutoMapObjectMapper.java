package io.github.zhaord.mapstruct.plus;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class AutoMapObjectMapper implements IObjectMapper {

    private final AutoMapperFactory factory;

    @Override
    public <S, D> D map(S source, Class<?> destinationCls) {
        BaseAutoMapper<S, D> autoMapper = factory.find(source.getClass(), destinationCls);
        if (autoMapper != null) {
            return autoMapper.map(source);

        }

        BaseAutoMapper<D, S> revAutoMapper = factory.find(destinationCls, source.getClass());
        if (revAutoMapper != null) {
            return revAutoMapper.reverseMap(source);
        }

        throw new RuntimeException("no mapping from " + source.getClass().getName() + " to " + destinationCls.getName() + ". check @AutoMap on " + source.getClass().getName() + " or " + destinationCls.getName());
    }

    @Override
    public <S, D> D map(S source, D destination) {
        BaseAutoMapper<S, D> autoMapper = factory.find(source.getClass(), destination.getClass());
        if (autoMapper != null) {
            return autoMapper.mapTarget(source, destination);
        }

        BaseAutoMapper<D, S> revAutoMapper = factory.find(destination.getClass(), source.getClass());
        if (revAutoMapper != null) {
            return revAutoMapper.reverseMapTarget(source, destination);
        }

        throw new RuntimeException("no mapping from " + source.getClass().getName() + " to " + destination.getClass().getName() + ". check @AutoMap on " + source.getClass().getName() + " or " + destination.getClass().getName());

    }

    @Override
    public <S, D> List<D> mapList(List<S> source, Class<?> destinationCls) {
        if (source == null) {
            return null;
        }

        List<D> list = new ArrayList<>(source.size());

        for (S element : source) {
            list.add(map(element, destinationCls));
        }

        return list;
    }
}
