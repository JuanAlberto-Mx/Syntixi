package syntixi.util.misc;

import syntixi.util.func.F1;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicReference;

/**
 * The <code>ReflectUtil</code> class.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class ReflectUtil {

	/**
	 * Returns a new class instance in a different data type.
	 *
	 * @param cls the class instance.
	 * @param <A> the target class type.
	 * @return the new instance.
	 */
	public static <A> A newInstance(Class<A> cls) {
		return (A) newInstanceFor(cls);
	}

	/**
	 * Returns a new <code>Object</code> data type instance.
	 *
	 * @param cls the class instance.
	 * @return the <code>Object</code> data type instance.
	 */
	public static Object newInstanceFor(Class cls) {
		try {
			return cls.newInstance();
		}
		catch(InstantiationException e) {
			Throwable cause = e.getCause();

			if (cause == null)
				cause = e;

			throw new RuntimeException(cause);
		}
		catch(IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Gets a method from a specific class by specifying a particular name.
	 *
	 * @param methodName the method's name.
	 * @param cls the source class.
	 * @return the method requested.
	 */
	public static Method getMethod(String methodName, Class cls) {
		for(Method method : cls.getMethods()) {
			if(method.getName().equals(methodName))
				return method;
		}

		if(!cls.equals(Object.class)) {
			Class superClass = cls.getSuperclass();

			if(superClass != null)
				return getMethod(methodName, superClass);
			else
				return null;
		}
		else
			return null;
	}

	/**
	 * Gets a method from a specific class by specifying the name and parameters.
	 *
	 * @param methodName the method's name.
	 * @param parameterClasses the method's parameters.
	 * @param cls the source class.
	 * @return the method requested.
	 */
	public static Method getMethod(String methodName, Class[] parameterClasses, Class<?> cls) {
		try {
			return cls.getMethod(methodName, parameterClasses);
		}
		catch(NoSuchMethodException e) {
			if(!cls.equals(Object.class)) {
				Class<?> superClass = cls.getSuperclass();

				if(superClass != null)
					return getMethod(methodName, parameterClasses, superClass);

				return null;
			}
			else
				return null;
		}
	}

	/**
	 * Invoke the method with given parameters.
	 *
	 * @param method the method to invoke.
	 * @param obj the instance that invokes the method.
	 * @param parameters the method's parameters.
	 * @return the invocation result.
	 */
	public static <T> T invoke(Method method, Object obj, Object... parameters) {
		try {
			return (T) method.invoke(obj, parameters);
		}
		catch(IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		catch(InvocationTargetException e) {
			throw new RuntimeException(e.getCause());
		}
	}

	/**
	 * Sets a field with a given value.
	 *
	 * @param value the value to set.
	 * @param field the field to modify.
	 * @param obj the object instance.
	 */
	public static void setFieldValue(Object value, Field field, Object obj) {
		try {
			field.set(obj, value);
		}
		catch(IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Gets the value of a given field.
	 *
	 * @param field the field name.
	 * @param obj the object instance.
	 * @param <A> the field data type.
	 * @return the field value.
	 */
	public static <A> A getFieldValue(String field, Object obj) {
		return getFieldValue(getField(field, obj.getClass()), obj);
	}

	/**
	 * Gets the value of a given field.
	 *
	 * @param field the field instance.
	 * @param obj the object instance.
	 * @param <A> the field data type.
	 * @return the field value.
	 */
	public static <A> A getFieldValue(Field field, Object obj) {
		try {
			return (A) field.get(obj);
		}
		catch(IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Gets the field of a specific class.
	 *
	 * @param name the field name.
	 * @param cls the class which contains the field.
	 * @return the field requested.
	 */
	public static Field getField(String name, Class<?> cls) {
		try {
			return cls.getDeclaredField(name);
		}
		catch(NoSuchFieldException e) {
			Class<?> superClass = cls.getSuperclass();

			if (Object.class.equals(superClass))
				return null;
			else
				return getField(name, superClass);
		}
	}

	/**
	 * Finds a method of a specific class.
	 *
	 * @param cls the class which contains the method.
	 * @param function the function.
	 * @return the method found.
	 */
	public static Method findMethod(Class cls, F1<Method,Boolean> function) {
		AtomicReference<Method> atomicReference = new AtomicReference<>();

		eachMethod(cls, (Method obj) -> {
			if(function.execute(obj)) {
				atomicReference.set(obj);

				return true;
			}

			return false;
		});

		return atomicReference.get();
	}

	/**
	 * Complements the <code>findMethod</code> functionality.
	 *
	 * @param cls the class which contains the method.
	 * @param function the function.
	 */
	public static void eachMethod(Class cls, F1<Method, Boolean> function) {
		for(Method method : cls.getMethods()) {
			if(function.execute(method))
				return;
		}

		if(!cls.equals(Object.class)) {
			Class superClass = cls.getSuperclass();

			if(superClass != null)
				eachMethod(superClass, function);
		}
	}

	/**
	 * Invoke the method with given parameters.
	 *
	 * @param methodName the method name.
	 * @param obj the object instance.
	 * @param parameters the method parameters.
	 * @return the <code>Object</code> instance of the method.
	 */
	public static Object invoke(String methodName, Object obj, Object... parameters) {
		return invoke(getMethod(methodName, obj.getClass()), obj, parameters);
	}

	/**
	 * Sets a field with a given value.
	 *
	 * @param value the value to set.
	 * @param field the target field name.
	 * @param obj the object instance.
	 */
	public static void setFieldValue(Object value, String field, Object obj) {
		try {
			setFieldValue(value, obj.getClass().getDeclaredField(field), obj);
		}
		catch(NoSuchFieldException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Invokes a static method.
	 *
	 * @param methodName the method name.
	 * @param cls the class which contains the method.
	 */
	public static void invokeStatic(String methodName, Class<?> cls) {
		invoke(getMethod(methodName, cls), null, null);
	}

	/**
	 * Gets the static field value.
	 *
	 * @param field the field name.
	 * @param cls the class which contains the field.
	 * @param <A> the target field data type.
	 * @return the field value.
	 */
	public static <A> A getStaticFieldValue(String field, Class cls) {
		try {
			return (A) getFieldValue(cls.getDeclaredField(field), null);
		}
		catch(NoSuchFieldException e) {
			throw new RuntimeException(e);
		}
	}
}