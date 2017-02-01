package org.axonframework.serialization.kryo;

import com.esotericsoftware.kryo.Kryo;

/**
 * Created on 01/02/17.
 *
 * @author Reda.Housni-Alaoui
 */
public class KryoThreadLocalProvider implements KryoProvider {

	/**
	 * Kryo ThreadLocal, because Kryo is not Thread Safe
	 */
	private final ThreadLocal<Kryo> kryos;

	public KryoThreadLocalProvider(){
		this(new DefaultKryoFactory());
	}

	public KryoThreadLocalProvider(KryoFactory kryoFactory){
		this.kryos = ThreadLocal.withInitial(kryoFactory::build);
	}

	@Override
	public Kryo get() {
		return kryos.get();
	}
}
