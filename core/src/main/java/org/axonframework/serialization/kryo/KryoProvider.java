package org.axonframework.serialization.kryo;

import com.esotericsoftware.kryo.Kryo;

/**
 * @author Reda.Housni-Alaoui
 * @since 3.1
 */
public interface KryoProvider {

	/**
	 * @return A new or existing instance of Kryo
	 */
	Kryo get();

}
