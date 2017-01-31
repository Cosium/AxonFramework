package org.axonframework.serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.Supplier;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.CompatibleFieldSerializer;
import org.objenesis.strategy.StdInstantiatorStrategy;

/**
 * Serializer implementation that uses Kryo serialization to serialize and deserialize object instances.
 * By default, this implementation:
 * - uses {@link CompatibleFieldSerializer} that makes the serialization forward and backward compatible
 * - uses {@link StdInstantiatorStrategy} that allows deserialization of class without the need to have
 * a no-arg constructor
 *
 * @author Reda.Housni-Alaoui
 * @since 3.1
 */
public class KryoSerializer extends AbstractJavaSerializer {

	/**
	 * Kryo ThreadLocal, because Kryo is not Thread Safe
	 */
	private final ThreadLocal<Kryo> kryos;

	/**
	 * Initialize the serializer with annotation revision resolver
	 */
	public KryoSerializer() {
		this(new AnnotationRevisionResolver());
	}

	/**
	 * Initialize the serializer
	 *
	 * @param revisionResolver The revision resolver providing the revision numbers for a given class
	 */
	public KryoSerializer(RevisionResolver revisionResolver) {
		this(() -> {
			Kryo kryo = new Kryo();
			kryo.setDefaultSerializer(CompatibleFieldSerializer.class);
			kryo.setInstantiatorStrategy(new Kryo.DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));
			return kryo;
		}, revisionResolver);
	}

	/**
	 * Initialize the serializer
	 * @param kryoSupplier The Kryo supplier that will be used to build Kryo for each thread
	 */
	public KryoSerializer(Supplier<Kryo> kryoSupplier){
		this(kryoSupplier, new AnnotationRevisionResolver());
	}

	/**
	 * Initialize the serializer
	 * @param kryoSupplier The Kryo supplier that will be used to build Kryo for each thread
	 * @param revisionResolver The revision resolver to be used
	 */
	public KryoSerializer(Supplier<Kryo> kryoSupplier, RevisionResolver revisionResolver){
		super(revisionResolver);
		kryos = ThreadLocal.withInitial(kryoSupplier);
	}

	@Override
	protected void doSerialize(OutputStream outputStream, Object instance) throws IOException {
		try (Output output = new Output(outputStream)) {
			kryos.get().writeClassAndObject(output, instance);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected <T> T doDeserialize(InputStream inputStream) throws ClassNotFoundException, IOException {
		try (Input input = new Input(inputStream)) {
			return (T) kryos.get().readClassAndObject(input);
		}
	}
}
