/*
 * Copyright 2017-2018 the original author or authors.
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

import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.data.RepositoryStateMachinePersist;
import org.springframework.statemachine.data.StateMachineRepository;
import org.springframework.statemachine.service.StateMachineSerialisationService;

/**
 * {@code FalkorDB} based implementation of a {@link RepositoryStateMachinePersist}.
 *
 * @author Guy Korland
 *
 * @param <S> the type of state
 * @param <E> the type of event
 */
public class FalkorDBRepositoryStateMachinePersist<S, E> extends RepositoryStateMachinePersist<FalkorDBRepositoryStateMachine, S, E> {

	private final FalkorDBStateMachineRepository FalkorDBStateMachineRepository;

	/**
	 * Instantiates a new falkordb repository state machine persist.
	 *
	 * @param FalkorDBStateMachineRepository the falkordb state machine repository
	 */
	public FalkorDBRepositoryStateMachinePersist(FalkorDBStateMachineRepository FalkorDBStateMachineRepository) {
		super();
		this.FalkorDBStateMachineRepository = FalkorDBStateMachineRepository;
	}

	/**
	 * Instantiates a new falkordb repository state machine persist.
	 *
	 * @param FalkorDBStateMachineRepository the falkordb state machine repository
	 * @param serialisationService the serialisation service
	 */
	public FalkorDBRepositoryStateMachinePersist(FalkorDBStateMachineRepository FalkorDBStateMachineRepository,
			StateMachineSerialisationService<S, E> serialisationService) {
		super(serialisationService);
		this.FalkorDBStateMachineRepository = FalkorDBStateMachineRepository;
	}

	@Override
	protected StateMachineRepository<FalkorDBRepositoryStateMachine> getRepository() {
		return FalkorDBStateMachineRepository;
	}

	@Override
	protected FalkorDBRepositoryStateMachine build(StateMachineContext<S, E> context, Object contextObj, byte[] serialisedContext) {
		FalkorDBRepositoryStateMachine FalkorDBRepositoryStateMachine = new FalkorDBRepositoryStateMachine();
		FalkorDBRepositoryStateMachine.setId(contextObj.toString());
		FalkorDBRepositoryStateMachine.setMachineId(context.getId());
		FalkorDBRepositoryStateMachine.setState(context.getState().toString());
		FalkorDBRepositoryStateMachine.setStateMachineContext(serialisedContext);
		return FalkorDBRepositoryStateMachine;
	}
}
