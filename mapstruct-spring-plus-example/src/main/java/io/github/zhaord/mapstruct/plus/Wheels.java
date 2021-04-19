package io.github.zhaord.mapstruct.plus;

import lombok.Data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

@Data
public class Wheels implements Iterable<Wheel> {
    private List<Wheel> wheelsList = new ArrayList<>();

    public void add(final Wheel wheel) {
        wheelsList.add(wheel);
    }

    @Override
    public Iterator<Wheel> iterator() {
        return getWheelsList().iterator();
    }

    @Override
    public void forEach(Consumer<? super Wheel> action) {
        getWheelsList().forEach(action);
    }

    @Override
    public Spliterator<Wheel> spliterator() {
        return  getWheelsList().spliterator();
    }
}
