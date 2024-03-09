/*
 * Copyright 2016-2023 the original author or authors.
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

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.falkordb.core.FalkorDBHash;
import org.springframework.data.falkordb.core.index.Indexed;
import org.springframework.statemachine.data.RepositoryTransition;
import org.springframework.statemachine.transition.TransitionKind;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * FalkorDB entity for transitions.
 *
 * @author Guy Korland
 *
 */
@FalkorDBHash("FalkorDBRepositoryTransition")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
public class FalkorDBRepositoryTransition extends RepositoryTransition {

	@Id
	private String id;

	@Indexed
	private String machineId = "";

	@Reference
	private FalkorDBRepositoryState source;

	@Reference
	private FalkorDBRepositoryState target;

	private String event;
	private TransitionKind kind;

	@Reference
	private Set<FalkorDBRepositoryAction> actions;

	@Reference
	private FalkorDBRepositoryGuard guard;

	/**
	 * Instantiates a new falkordb repository transition.
	 */
	public FalkorDBRepositoryTransition() {
		this(null, null, null);
	}

	/**
	 * Instantiates a new falkordb repository transition.
	 *
	 * @param source the source
	 * @param target the target
	 * @param event the event
	 */
	public FalkorDBRepositoryTransition(FalkorDBRepositoryState source, FalkorDBRepositoryState target, String event) {
		this(null, source, target, event);
	}

	/**
	 * Instantiates a new falkordb repository transition.
	 *
	 * @param machineId the machine id
	 * @param source the source
	 * @param target the target
	 * @param event the event
	 */
	public FalkorDBRepositoryTransition(String machineId, FalkorDBRepositoryState source, FalkorDBRepositoryState target, String event) {
		this(machineId, source, target, event, null);
	}

	/**
	 * Instantiates a new falkordb repository transition.
	 *
	 * @param machineId the machine id
	 * @param source the source
	 * @param target the target
	 * @param event the event
	 * @param actions the actions
	 */
	public FalkorDBRepositoryTransition(String machineId, FalkorDBRepositoryState source, FalkorDBRepositoryState target, String event, Set<FalkorDBRepositoryAction> actions) {
		this.machineId = machineId == null ? "" : machineId;
		this.source = source;
		this.target = target;
		this.event = event;
		this.actions = actions;
	}

	@Override
	public String getMachineId() {
		return machineId;
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

	@Override
	public FalkorDBRepositoryState getSource() {
		return source;
	}

	public void setSource(FalkorDBRepositoryState source) {
		this.source = source;
	}

	@Override
	public FalkorDBRepositoryState getTarget() {
		return target;
	}

	public void setTarget(FalkorDBRepositoryState target) {
		this.target = target;
	}

	@Override
	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	@Override
	public Set<FalkorDBRepositoryAction> getActions() {
		return actions;
	}

	public void setActions(Set<FalkorDBRepositoryAction> actions) {
		this.actions = actions;
	}

	@Override
	public FalkorDBRepositoryGuard getGuard() {
		return guard;
	}

	public void setGuard(FalkorDBRepositoryGuard guard) {
		this.guard = guard;
	}

	@Override
	public TransitionKind getKind() {
		return kind;
	}

	public void setKind(TransitionKind kind) {
		this.kind = kind;
	}

	@Override
	public String toString() {
		return "FalkorDBRepositoryTransition [id=" + id + ", machineId=" + machineId + ", source=" + source + ", target=" + target + ", event="
				+ event + ", kind=" + kind + ", actions=" + actions + ", guard=" + guard + "]";
	}
}
