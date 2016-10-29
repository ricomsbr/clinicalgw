/*
 * DefaultStreamSerializer.java		14/04/2015
 *
 * Copyright (C) 2016 ACKTA. All Rights Reserved.
 */
package br.com.ackta.clinicalgw.session;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;

/**
 *
 *
 */
public class DefaultStreamSerializer implements StreamSerializer<Object> {

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

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.hazelcast.nio.serialization.StreamSerializer#write(com.hazelcast.nio.
	 * ObjectDataOutput, java.lang.Object)
	 */
	@Override
	public void write(ObjectDataOutput objectDataOutput, Object object) throws IOException {
		ObjectOutputStream out = new ObjectOutputStream((OutputStream) objectDataOutput);
		out.writeObject(object);
		out.flush();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.hazelcast.nio.serialization.StreamSerializer#read(com.hazelcast.nio.
	 * ObjectDataInput)
	 */
	@Override
	public Object read(ObjectDataInput objectDataInput) throws IOException {
		ObjectInputStream in = new ObjectInputStream((InputStream) objectDataInput);
		try {
			return in.readObject();
		} catch (ClassNotFoundException e) {
			throw new IOException(e);
		}
	}

}
