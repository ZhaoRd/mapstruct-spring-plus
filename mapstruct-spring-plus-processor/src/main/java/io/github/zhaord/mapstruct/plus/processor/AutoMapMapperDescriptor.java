package io.github.zhaord.mapstruct.plus.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import io.github.zhaord.mapstruct.plus.annotations.AutoMapField;
import lombok.Data;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.control.MappingControl;

import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class AutoMapMapperDescriptor {

    private ClassName sourceClassName;
    private ClassName targetClassName;

    private List<ClassName> usesClassNameList;
    private List<AutoMapFieldDescriptor> mapFieldDescriptorList;


    public String sourcePackageName(){
        return sourceClassName.packageName();
    }

    public String mapperName(){
        return sourceClassName.simpleName()+"To"+ targetClassName.simpleName()+"Mapper";
    }

    @Data
    public static class AutoMapFieldDescriptor{
        private String source;
        private String target;
        private String dateFormat;
        private String numberFormat;
        private String constant;
        private String expression;
        private String defaultExpression;
        private boolean ignore;
        private String[] qualifiedByName;
        private String[] dependsOn;
        private String defaultValue;
        private NullValueCheckStrategy nullValueCheckStrategy;
        private NullValuePropertyMappingStrategy nullValuePropertyMappingStrategy;

        private List<ClassName> qualifiedByClassNameList;
        private TypeName resultTypeTypeName;
        private ClassName mappingControl;

        public static AutoMapFieldDescriptor ofAutoMapField(AutoMapField autoMapField) {

            AutoMapFieldDescriptor descriptor = new AutoMapFieldDescriptor();
            descriptor.target = fillString(autoMapField.target());
            descriptor.dateFormat = fillString(autoMapField.dateFormat());
            descriptor.numberFormat = fillString(autoMapField.numberFormat());
            descriptor.constant = fillString(autoMapField.constant());
            descriptor.expression = fillString(autoMapField.expression());
            descriptor.defaultExpression = fillString(autoMapField.defaultExpression());
            descriptor.ignore = autoMapField.ignore();
            descriptor.qualifiedByName = autoMapField.qualifiedByName().length == 0 ? null : autoMapField.qualifiedByName();
            ;
            descriptor.dependsOn = autoMapField.dependsOn().length == 0 ? null : autoMapField.dependsOn();
            descriptor.defaultValue = fillString(autoMapField.defaultValue());

            descriptor.nullValueCheckStrategy = autoMapField.nullValueCheckStrategy() == NullValueCheckStrategy.ON_IMPLICIT_CONVERSION
                    ? null : autoMapField.nullValueCheckStrategy();
            descriptor.nullValuePropertyMappingStrategy = autoMapField.nullValuePropertyMappingStrategy()
                    == NullValuePropertyMappingStrategy.SET_TO_NULL ? null : autoMapField.nullValuePropertyMappingStrategy();


            fillResultType(descriptor, autoMapField);
            fillQualifiedBy(descriptor, autoMapField);
            fillMappingControl(descriptor, autoMapField);
            return descriptor;
        }

        private static String fillString(String value) {
            final String defStr = "";
            return value.equals(defStr) ? null : value;
        }


        private static void fillMappingControl(AutoMapFieldDescriptor descriptor, AutoMapField autoMapField) {
            try {
                Class<?> resultType = autoMapField.mappingControl();
                if (resultType == MappingControl.class) {
                    descriptor.mappingControl = null;
                } else {
                    descriptor.mappingControl = ClassName.get(resultType);
                }

            } catch (MirroredTypeException mte) {
                TypeMirror typeMirror = mte.getTypeMirror();
                ClassName ctrlClassName = (ClassName) ClassName.get(typeMirror);
                ClassName def = ClassName.get(MappingControl.class);
                if (ctrlClassName.equals(def)) {
                    descriptor.mappingControl = null;
                } else {
                    descriptor.mappingControl = ctrlClassName;
                }


            }

        }

        private static void fillResultType(AutoMapFieldDescriptor descriptor, AutoMapField autoMapField) {
            try {
                Class<?> resultType = autoMapField.resultType();
                if (resultType.equals(void.class)) {
                    descriptor.resultTypeTypeName = null;
                }
                {
                    descriptor.resultTypeTypeName = TypeName.get(resultType);
                }

            } catch (MirroredTypeException mte) {
                TypeMirror typeMirror = mte.getTypeMirror();
                TypeName tn = ClassName.get(typeMirror);
                TypeName def = TypeName.get(void.class);
                if (tn.equals(def)) {
                    descriptor.resultTypeTypeName = null;
                } else {
                    descriptor.resultTypeTypeName = tn;
                }

            }

        }

        private static void fillQualifiedBy(AutoMapFieldDescriptor descriptor, AutoMapField autoMapField) {
            try {
                Class<?>[] resultType = autoMapField.qualifiedBy();
                if (resultType.length == 0) {
                    descriptor.qualifiedByClassNameList = null;
                } else {
                    descriptor.qualifiedByClassNameList = Arrays.stream(resultType)
                            .map(c -> ClassName.get(c))
                            .collect(Collectors.toList());
                }

            } catch (MirroredTypesException mte) {
                List<? extends TypeMirror> typeMirrors = mte.getTypeMirrors();
                if (typeMirrors == null || typeMirrors.size() == 0) {
                    descriptor.qualifiedByClassNameList = null;
                } else {
                    descriptor.qualifiedByClassNameList =
                            typeMirrors.stream()
                                    .map(c -> (ClassName) ClassName.get(c))
                                    .collect(Collectors.toList());
                }
            }

        }
    }
}
