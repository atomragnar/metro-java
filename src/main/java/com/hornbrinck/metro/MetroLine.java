package com.hornbrinck.metro;

import com.hornbrinck.graph.AbstractLine;

import java.util.Optional;
import java.util.function.Supplier;

public class MetroLine extends AbstractLine<MetroStation, String> {
    public MetroLine(String name) {
        super(name, MetroStation::new);
    }

    public MetroLine(String name, MetroStation head) {
        super(name, MetroStation::new);
        setHead(head);
    }
    private MetroLine(String name, Supplier<MetroStation> nodeSupplier) {
        super(name, nodeSupplier);
    }

    @Override
    public Optional<MetroStation> getNode(String data) {
        return super.getNode(data);
    }


}
