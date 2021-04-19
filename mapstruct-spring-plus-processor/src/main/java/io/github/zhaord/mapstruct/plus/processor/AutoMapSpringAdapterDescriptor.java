package io.github.zhaord.mapstruct.plus.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class AutoMapSpringAdapterDescriptor {
    private ClassName adapterClassName;
    private String conversionServiceBeanName;
    private List<Pair<TypeName, TypeName>> fromToMappings;

    public ClassName getAdapterClassName() {
        return adapterClassName;
    }

    public void setAdapterClassName(final ClassName adapterClassName) {
        this.adapterClassName = adapterClassName;
    }

    public String getConversionServiceBeanName() {
        return conversionServiceBeanName;
    }

    public void setConversionServiceBeanName(String conversionServiceBeanName) {
        this.conversionServiceBeanName = conversionServiceBeanName;
    }

    public List<Pair<TypeName, TypeName>> getFromToMappings() {
        return fromToMappings;
    }

    public void setFromToMappings(final List<Pair<TypeName, TypeName>> fromToMappings) {
        this.fromToMappings = fromToMappings;
    }
}
