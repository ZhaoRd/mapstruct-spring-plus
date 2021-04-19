package io.github.zhaord.mapstruct.plus.processor;

import com.squareup.javapoet.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.time.Clock;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static io.github.zhaord.mapstruct.plus.processor.Const.BASE_PACKAGE;
import static java.util.stream.Collectors.toList;
import static javax.lang.model.element.Modifier.*;
import static javax.lang.model.element.Modifier.FINAL;

public class AutoMapSpringAdapterGenerator {
    private final Clock clock;

    public AutoMapSpringAdapterGenerator(final Clock clock) {
        this.clock = clock;
    }

    public void writeConversionServiceAdapter(
            AutoMapSpringAdapterDescriptor descriptor, Writer out) {
        try {
            JavaFile.builder(
                    descriptor.getAdapterClassName().packageName(),
                    createConversionServiceTypeSpec(descriptor))
                    .build()
                    .writeTo(out);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private TypeSpec createConversionServiceTypeSpec(
            final AutoMapSpringAdapterDescriptor descriptor) {
        final FieldSpec conversionServiceFieldSpec = buildConversionServiceFieldSpec();
        return TypeSpec.classBuilder(descriptor.getAdapterClassName())
                .addModifiers(PUBLIC)
                .addAnnotation(buildGeneratedAnnotationSpec())
                .addAnnotation(ClassName.get("org.springframework.stereotype", "Component"))
                .addField(conversionServiceFieldSpec)
                .addMethod(buildConstructorSpec(descriptor, conversionServiceFieldSpec))
                .addMethods(buildMappingMethods(descriptor, conversionServiceFieldSpec))
                .build();
    }

    private static MethodSpec buildConstructorSpec(final AutoMapSpringAdapterDescriptor descriptor, final FieldSpec conversionServiceFieldSpec) {
        final ParameterSpec constructorParameterSpec = buildConstructorParameterSpec(conversionServiceFieldSpec);
        return MethodSpec.constructorBuilder().addModifiers(PUBLIC).addParameter(constructorParameterSpec).addStatement("this.$N = $N", conversionServiceFieldSpec, constructorParameterSpec).build();
    }

    private static ParameterSpec buildConstructorParameterSpec( final FieldSpec conversionServiceFieldSpec) {
        final ParameterSpec.Builder parameterBuilder = ParameterSpec.builder(conversionServiceFieldSpec.type, conversionServiceFieldSpec.name, FINAL);

        return parameterBuilder.build();
    }

    private static String simpleName(final TypeName typeName) {
        return rawType(typeName).simpleName();
    }

    private static ClassName rawType(final TypeName typeName) {
        if (typeName instanceof ParameterizedTypeName) {
            return ((ParameterizedTypeName)typeName).rawType;
        }
        return (ClassName) typeName;
    }

    private static Iterable<MethodSpec> buildMappingMethods(
            final AutoMapSpringAdapterDescriptor descriptor,
            final FieldSpec injectedConversionServiceFieldSpec) {
         List<MethodSpec> mapList =  descriptor.getFromToMappings().stream()
                .map(
                        sourceTargetPair -> {
                            final ParameterSpec sourceParameterSpec =
                                    buildSourceParameterSpec(sourceTargetPair.getLeft());
                            return MethodSpec.methodBuilder(
                                    "map"
                                            + simpleName(sourceTargetPair.getLeft())
                                            + "To"
                                            + simpleName(sourceTargetPair.getRight()))
                                    .addParameter(sourceParameterSpec)
                                    .addModifiers(PUBLIC)
                                    .returns(sourceTargetPair.getRight())
                                    .addStatement(
                                            "return $N.map($N, $T.class)",
                                            injectedConversionServiceFieldSpec,
                                            sourceParameterSpec,
                                            rawType(sourceTargetPair.getRight()))
                                    .build();
                        })
                .collect(toList());

        List<MethodSpec> revMapList =  descriptor.getFromToMappings().stream()
                .map(
                        sourceTargetPair -> {
                            final ParameterSpec sourceParameterSpec =
                                    buildSourceParameterSpec(sourceTargetPair.getRight());
                            return MethodSpec.methodBuilder(
                                    "map"
                                            + simpleName(sourceTargetPair.getRight())
                                            + "To"
                                            + simpleName(sourceTargetPair.getLeft()))
                                    .addParameter(sourceParameterSpec)
                                    .addModifiers(PUBLIC)
                                    .returns(sourceTargetPair.getLeft())
                                    .addStatement(
                                            "return $N.map($N, $T.class)",
                                            injectedConversionServiceFieldSpec,
                                            sourceParameterSpec,
                                            rawType(sourceTargetPair.getLeft()))
                                    .build();
                        })
                .collect(toList());
        mapList.addAll(revMapList);
        return mapList;
    }

    private static ParameterSpec buildSourceParameterSpec(final TypeName sourceClassName) {
        return ParameterSpec.builder(sourceClassName, "source", FINAL).build();
    }

    private static FieldSpec buildConversionServiceFieldSpec() {
        return FieldSpec.builder(
                ClassName.get(
                        BASE_PACKAGE,
                        "IObjectMapper"),
                "objectMapper", PRIVATE, FINAL).build();
    }

    private AnnotationSpec buildGeneratedAnnotationSpec() {
        return AnnotationSpec.builder(ClassName.get("javax.annotation", "Generated"))
                .addMember("value", "$S", AutoMapSpringAdapterGenerator.class.getName())
                .addMember("date", "$S", DateTimeFormatter.ISO_INSTANT.format(ZonedDateTime.now(clock)))
                .build();
    }
}
