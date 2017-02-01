package org.axonframework.serialization.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.serializers.CompatibleFieldSerializer;
import org.objenesis.strategy.StdInstantiatorStrategy;

/**
 * By default, this implementation:
 * - uses {@link CompatibleFieldSerializer} that makes the serialization forward and backward compatible
 * - uses {@link StdInstantiatorStrategy} that allows deserialization of class without the need to have
 * a no-arg constructor
 *
 * @author Reda.Housni-Alaoui
 * @since 3.1
 */
public class DefaultKryoFactory implements KryoFactory {

	@Override
	public Kryo create() {
		Kryo kryo = new Kryo();
		kryo.setDefaultSerializer(CompatibleFieldSerializer.class);
		kryo.setInstantiatorStrategy(new Kryo.DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));
		return kryo;
	}
}
