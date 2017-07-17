package syntixi.util.misc;

import syntixi.util.func.F0;
import syntixi.util.func.F1;

import java.util.*;

/**
 * The <code>CollectionUtil</code> class.
 *
 * @author Juan-Alberto Hern&aacute;ndez-Mart&iacute;nez
 * @version %I%, %G%
 */
public class CollectionUtil {

	/**
	 * Evaluates if a collection is empty.
	 *
	 * @param collection the collection to evaluate.
	 * @return <code>true</code> if the collection is empty. Otherwise, <code>false</code>.
	 */
	public static boolean isEmpty(Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}

	/**
	 * Evaluates if a collection is not empty.
	 *
	 * @param collection the collection to evaluate.
	 * @return <code>true</code> if the collection is not empty. Otherwise, <code>false</code>.
	 */
	public static boolean isNotEmpty(Collection<?> collection) {
		return !isEmpty(collection);
	}

	/**
	 * Gets a single element.
	 *
	 * @param collection the collection to evaluate.
	 * @return the element of the collection or <code>null</code> if the collection
	 * 			is empty.
	 */
	public static <A> A getSingle(Collection<A> collection) {
		if(collection == null)
			return null;

		for(A element : collection)
			return element;

		return null;
	}

	/**
	 * Creates a parameterized list.
	 *
	 * @param size specifies the length of the list to create.
	 * @param function the function to execute.
	 * @param <A> type of the function to execute.
	 * @return the parameterized list.
	 */
	public static <A> List<A> createList(int size, F0<A> function) {
		ArrayList<A> list = new ArrayList<A>(size);

		for(int i = 0; i < size; i++)
			list.add(function.execute());

		return list;
	}

	/**
	 * Creates a string through the connection of all the values contained in a
	 * collection, separated by a delimiter.
	 *
	 * @param iterable the collection to iterate.
	 * @param delimiter specifies the string to use to delimit each element into
	 *                  the iterable collection.
	 * @return the resulting string of joining the collection's elements.
	 */
	public static <A> String join(Iterable<A> iterable, String delimiter) {
		if(iterable == null)
			return "";

		StringBuilder stringBuilder = new StringBuilder();

		for(A element : iterable)
			stringBuilder.append(element).append(delimiter);

		if(stringBuilder.length() > 0)
			stringBuilder.setLength(stringBuilder.length() - delimiter.length());

		return stringBuilder.toString();
	}

	/**
	 * Yields a collection.
	 *
	 * @param collection the collection to use.
	 * @param function the function to execute.
	 * @param <A> specifies the data type for the elements of the collection.
	 * @param <T> specifies the function's return type.
	 * @return the resulting list.
	 */
	public static <A, T> List<T> yield(List<A> collection, F1<A, T> function) {
		if(collection != null)
			return yield(collection, new ArrayList<T>(collection.size()), function);
		else
			return null;
	}

	/**
	 * Applies a function to every element to get a new collection from the returned value.
	 *
	 * @param <A> specifies the data type of the elements in the iterable collection.
	 * @param <T> specifies the function's return type.
	 * @param <C> specifies the type of the new collection.
	 * @param inputIterable the collection to iterate.
	 * @param collection the collection where the resulting elements will be added.
	 * @param function the function to execute for each element in the initial iterable
	 *                 collection.
	 * @return the new collection.
	 */
	public static <A, T, C extends Collection<T>> C yield(Iterable<A> inputIterable, C collection, F1<A, T> function) {
		if(inputIterable != null) {
			for(A element : inputIterable) {
				T value = function.execute(element);

				if(value != null)
					collection.add(value);
			}
		}

		return collection;
	}

	/**
	 * Creates a <code>Map</code> based on the <code>Object</code> parameters. Each two
	 * values is an entry which is a pair of <code>key</code> then <code>value</code>.
	 *
	 * @param objects the parameters that will be converted to a <code>Map</code>
	 *               with the following format: <code>[key1, value1, key2, value2]</code>
	 * @return the map after converted from parameters.
	 */
	public static <A, B> Map<A, B> map(Object... objects) {
		if (objects == null)
			return null;

		Map<A, B> map = new LinkedHashMap<A, B>(objects.length / 2);

		for (int i = 0; i < objects.length; i += 2)
			map.put((A)objects[i], (B)objects[i + 1]);

		return map;
	}
}