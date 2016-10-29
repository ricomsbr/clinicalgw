/*
 * ObjectStreamSerializer.java		13/04/2015
 *
 * Copyright (C) 2016 ACKTA. All Rights Reserved.
 */
package br.com.ackta.clinicalgw.session;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;

/**
 *
 *
 */
public class ObjectStreamSerializer implements StreamSerializer<Object> {

	private static final ThreadLocal<Kryo> kryoThreadLocal = new ThreadLocal<Kryo>() {

		@Override
		protected Kryo initialValue() {
			Kryo kryo = new Kryo();
			return kryo;
		};
	};

	/*
	 * (non-Javadoc)
	 *
	 * @see com.hazelcast.nio.serialization.Serializer#getTypeId()
	 */
	@Override
	public int getTypeId() {
		return 2;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.hazelcast.nio.serialization.Serializer#destroy()
	 */
	@Override
	public void destroy() {
		// do nothing

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.hazelcast.nio.serialization.StreamSerializer#write(com.hazelcast.
	 * nio.ObjectDataOutput, java.lang.Object)
	 */
	@Override
	public void write(ObjectDataOutput objectOutputStream, Object object) throws IOException {
		Kryo kryo = kryoThreadLocal.get();
		try (Output output = new Output((OutputStream) objectOutputStream)) {
			kryo.writeObject(output, object);
			output.flush();
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.hazelcast.nio.serialization.StreamSerializer#read(com.hazelcast.nio
	 * .ObjectDataInput)
	 */
	@Override
	public Object read(ObjectDataInput objectInputStream) throws IOException {
		try (InputStream in = (InputStream) objectInputStream; Input input = new Input(in)) {
			Kryo kryo = kryoThreadLocal.get();
			return kryo.readObject(input, Object.class);
		}
	}

}
