/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.statemachine.data.falkordb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.UUID;

import org.springframework.data.falkordb.connection.FalkorDBConnectionFactory;
import org.springframework.data.falkordb.core.FalkorDBOperations;
import org.springframework.data.falkordb.core.FalkorDBTemplate;
import org.springframework.data.falkordb.serializer.StringFalkorDBSerializer;
import org.springframework.messaging.MessageHeaders;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachineContextRepository;
import org.springframework.statemachine.kryo.MessageHeadersSerializer;
import org.springframework.statemachine.kryo.StateMachineContextSerializer;
import org.springframework.statemachine.kryo.UUIDSerializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**
 * A {@link StateMachineContextRepository} backed by a falkordb and kryo serialization.
 *
 * @author Guy Korland
 *
 * @param <S> the type of state
 * @param <E> the type of event
 */
public class FalkorDBStateMachineContextRepository<S, E> implements StateMachineContextRepository<S, E, StateMachineContext<S, E>> {

	private static final ThreadLocal<Kryo> kryoThreadLocal = new ThreadLocal<Kryo>() {

		@SuppressWarnings("rawtypes")
		@Override
		protected Kryo initialValue() {
			Kryo kryo = new Kryo();
			kryo.addDefaultSerializer(StateMachineContext.class, new StateMachineContextSerializer());
			kryo.addDefaultSerializer(MessageHeaders.class, new MessageHeadersSerializer());
			kryo.addDefaultSerializer(UUID.class, new UUIDSerializer());
			return kryo;
		}
	};

	private final FalkorDBOperations<String,byte[]> falkordbOperations;

	/**
	 * Instantiates a new falkordb state machine context repository.
	 *
	 * @param falkordbConnectionFactory the falkordb connection factory
	 */
	public FalkorDBStateMachineContextRepository(FalkorDBConnectionFactory falkordbConnectionFactory) {
		falkordbOperations = createDefaultTemplate(falkordbConnectionFactory);
	}

	@Override
	public void save(StateMachineContext<S, E> context, String id) {
		falkordbOperations.opsForValue().set(id, serialize(context));
	}

	@Override
	public StateMachineContext<S, E> getContext(String id) {
		return deserialize(falkordbOperations.opsForValue().get(id));
	}

	private static FalkorDBTemplate<String,byte[]> createDefaultTemplate(FalkorDBConnectionFactory connectionFactory) {
		FalkorDBTemplate<String,byte[]> template = new FalkorDBTemplate<String,byte[]>();
		template.setKeySerializer(new StringFalkorDBSerializer());
		template.setHashKeySerializer(new StringFalkorDBSerializer());
		template.setConnectionFactory(connectionFactory);
		template.afterPropertiesSet();
		return template;
	}

	private byte[] serialize(StateMachineContext<S, E> context) {
		Kryo kryo = kryoThreadLocal.get();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Output output = new Output(out);
		kryo.writeObject(output, context);
		output.close();
		return out.toByteArray();
	}

	@SuppressWarnings("unchecked")
	private StateMachineContext<S, E> deserialize(byte[] data) {
		if (data == null || data.length == 0) {
			return null;
		}
		Kryo kryo = kryoThreadLocal.get();
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		Input input = new Input(in);
		return kryo.readObject(input, StateMachineContext.class);
	}

}
