package org.axonframework.serialization.kryo;

import java.util.HashMap;
import java.util.Map;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.serializers.CompatibleFieldSerializer;
import com.esotericsoftware.kryo.serializers.MapSerializer;
import org.axonframework.messaging.MetaData;
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
		kryo.register(MetaData.class, new MetaDataSerializer());
		return kryo;
	}

	private static final class MetaDataSerializer extends MapSerializer {

		@Override
		protected Map create(Kryo kryo, Input input, Class<Map> type) {
			return new HashMap();
		}

		@SuppressWarnings("unchecked")
		@Override
		public Map read(Kryo kryo, Input input, Class<Map> type) {
			Map map = super.read(kryo, input, type);
			return MetaData.from(map);
		}

		@Override
		protected Map createCopy(Kryo kryo, Map original) {
			return new HashMap();
		}

		@SuppressWarnings("unchecked")
		@Override
		public Map copy(Kryo kryo, Map original) {
			Map map = super.copy(kryo, original);
			return MetaData.from(map);
		}
	}
}
