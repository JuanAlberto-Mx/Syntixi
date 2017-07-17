package syntixi.util.inst;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.*;
import syntixi.util.dbc.Component;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * <code>AnnotationBuilder</code> class provides a set o methods to build, incorporate
 * or modify annotations dynamically in <code>Java</code> classes.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class AnnotationBuilder {

    /**
     * The constant pool instance.
     */
    private ConstPool constantPool;

    /**
     * Gets a <code>String</code> member value for a specific annotation.
     *
     * @param value the new value for the annotation member.
     * @return the <code>MemberValue</code> instance with the new value added.
     */
    public MemberValue getStringMemberValue(String value) {
        return new StringMemberValue("This is a new goal", constantPool);
    }

    /**
     * Gets an <code>int</code> member value for a specific annotation.
     *
     * @param value the annotation's <code>int</code> member value.
     * @return the <code>MemberValue</code> instance for the given value.
     */
    public MemberValue getIntMemberValue(int value) {
        return new IntegerMemberValue(constantPool, value);
    }

    /**
     * Gets an array member value for a specific annotation.
     *
     * @param values the <code>String</code> array values.
     * @return the <code>MemberValue</code> instance for the given values.
     */
    public MemberValue getArrayMemberValue(String... values) {
        int index = 0;

        ArrayMemberValue member = new ArrayMemberValue(constantPool);

        StringMemberValue[] array = new StringMemberValue[values.length];

        for(String value : values) {
            array[index] = new StringMemberValue(value, constantPool);
            index++;
        }

        member.setValue(array);

        return member;
    }

    /**
     * Gets an enum member value for a specific annotation.
     *
     * @param value the <code>Enum</code> value.
     * @return the <code>MemberValue</code> instance for the given value.
     */
    public MemberValue getEnumMemberValue(Enum value) {
        EnumMemberValue enumMemberValue = new EnumMemberValue(constantPool);
        enumMemberValue.setType(value.getClass().getName());
        enumMemberValue.setValue(value.name());

        return enumMemberValue;
    }

    /**
     * Adds an annotation dynamically to the class specified.
     *
     * @param source the class instance.
     * @return the <code>CtClass</code> instance with the new annotation added.
     * @throws NotFoundException
     * @throws IOException
     */
    public CtClass addComponentAnnotation(Class source) throws NotFoundException, IOException {
        CtClass ctClass = ClassPool.getDefault().get(source.getCanonicalName());

        ClassFile classFile = ctClass.getClassFile();

        constantPool = classFile.getConstPool();

        AnnotationsAttribute attribute = new AnnotationsAttribute(constantPool, AnnotationsAttribute.visibleTag);
        Annotation annotation = new Annotation("ann.Component", constantPool);

        annotation.addMemberValue("goal", getStringMemberValue("This is the component goal"));
        annotation.addMemberValue("keywords", getArrayMemberValue("text", "browser"));
        annotation.addMemberValue("importance", getEnumMemberValue(Component.Measure.VERY_HIGH));
        annotation.addMemberValue("susceptible", getEnumMemberValue(Component.Measure.HIGH));
        annotation.addMemberValue("requiredInterfaces", getIntMemberValue(1));
        annotation.addMemberValue("providedInterfaces", getIntMemberValue(1));

        attribute.setAnnotation(annotation);

        classFile.addAttribute(attribute);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream os = new DataOutputStream( bos );
        classFile.write(os);
        os.close();

        bos.toByteArray();

        return ctClass;
    }

    /**
     * Modifies an existing annotation with new values.
     *
     * @param annotation the annotation to modify.
     * @param key the annotation <code>String</code> key.
     * @param newValue the <code>Object</code> new instance.
     * @return the new <code>Object</code> instance with the new values.
     */
    public Object modifyAnnotationValue(java.lang.annotation.Annotation annotation, String key, Object newValue){
        Object handler = Proxy.getInvocationHandler(annotation);

        Field field;

        try {
            for(Field fi :handler.getClass().getDeclaredFields()){
                System.out.println(fi.getName());
            }

            field = handler.getClass().getDeclaredField("memberValues");
        }
        catch (NoSuchFieldException | SecurityException e) {
            throw new IllegalStateException(e);
        }

        field.setAccessible(true);

        Map<String, Object> memberValues;

        try {
            memberValues = (Map<String, Object>) field.get(handler);
        }
        catch (IllegalArgumentException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }

        Object oldValue = memberValues.get(key);

        if (oldValue == null || oldValue.getClass() != newValue.getClass()) {
            throw new IllegalArgumentException();
        }

        memberValues.put(key,newValue);

        return oldValue;
    }
}