package org.axonframework.serialization.kryo;

import com.esotericsoftware.kryo.Kryo;

/**
 * @author Reda.Housni-Alaoui
 * @since 3.1
 */
public interface KryoFactory {

	/**
	 * @return A new instance of {@link Kryo}
	 */
	Kryo build();

}
