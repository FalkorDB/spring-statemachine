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
import org.springframework.statemachine.data.RepositoryAction;
import org.springframework.statemachine.data.RepositoryState;
import org.springframework.statemachine.state.PseudoStateKind;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * FalkorDB entity for states.
 *
 * @author Guy Korland
 *
 */
@FalkorDBHash("FalkorDBRepositoryState")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
public class FalkorDBRepositoryState extends RepositoryState {

	@Id
	private String id;

	@Indexed
	private String machineId = "";
	private String state;
	private String region;
	private boolean initial;
	private PseudoStateKind kind;
	private String submachineId;

	@Reference
	private FalkorDBRepositoryState parentState;

	@Reference
	private FalkorDBRepositoryAction initialAction;

	@Reference
	private Set<FalkorDBRepositoryAction> stateActions;

	@Reference
	private Set<FalkorDBRepositoryAction> entryActions;

	@Reference
	private Set<FalkorDBRepositoryAction> exitActions;

	private Set<String> deferredEvents;

	/**
	 * Instantiates a new falkordb repository state.
	 */
	public FalkorDBRepositoryState() {
	}

	/**
	 * Instantiates a new falkordb repository state.
	 *
	 * @param state the state
	 */
	public FalkorDBRepositoryState(String state) {
		this.state = state;
	}

	/**
	 * Instantiates a new falkordb repository state.
	 *
	 * @param state the state
	 * @param initial the initial
	 */
	public FalkorDBRepositoryState(String state, boolean initial) {
		this(null, state, initial);
	}

	/**
	 * Instantiates a new falkordb repository state.
	 *
	 * @param machineId the machine id
	 * @param state the state
	 * @param initial the initial
	 */
	public FalkorDBRepositoryState(String machineId, String state, boolean initial) {
		this(machineId, null, state, initial);
	}

	/**
	 * Instantiates a new falkordb repository state.
	 *
	 * @param machineId the machine id
	 * @param parentState the parent state
	 * @param state the state
	 * @param initial the initial
	 */
	public FalkorDBRepositoryState(String machineId, FalkorDBRepositoryState parentState, String state, boolean initial) {
		this(machineId, parentState, state, initial, null, null, null);
	}

	/**
	 * Instantiates a new falkordb repository state.
	 *
	 * @param machineId the machine id
	 * @param parentState the parent state
	 * @param state the state
	 * @param initial the initial
	 * @param stateActions the state actions
	 * @param entryActions the entry actions
	 * @param exitActions the exit actions
	 */
	public FalkorDBRepositoryState(String machineId, FalkorDBRepositoryState parentState, String state, boolean initial, Set<FalkorDBRepositoryAction> stateActions,
			Set<FalkorDBRepositoryAction> entryActions, Set<FalkorDBRepositoryAction> exitActions) {
		this.machineId = machineId == null ? "" : machineId;
		this.parentState = parentState;
		this.state = state;
		this.initial = initial;
		this.stateActions = stateActions;
		this.entryActions = entryActions;
		this.exitActions = exitActions;
	}

	@Override
	public String getMachineId() {
		return machineId;
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

	@Override
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	@Override
	public FalkorDBRepositoryState getParentState() {
		return parentState;
	}

	public void setParentState(FalkorDBRepositoryState parentState) {
		this.parentState = parentState;
	}

	@Override
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public PseudoStateKind getKind() {
		return kind;
	}

	public void setKind(PseudoStateKind kind) {
		this.kind = kind;
	}

	@Override
	public Boolean isInitial() {
		return initial;
	}

	public void setInitial(boolean initial) {
		this.initial = initial;
	}

	@Override
	public RepositoryAction getInitialAction() {
		return initialAction;
	}

	public void setInitialAction(FalkorDBRepositoryAction initialAction) {
		this.initialAction = initialAction;
	}

	@Override
	public Set<FalkorDBRepositoryAction> getStateActions() {
		return stateActions;
	}

	public void setStateActions(Set<FalkorDBRepositoryAction> stateActions) {
		this.stateActions = stateActions;
	}

	@Override
	public Set<FalkorDBRepositoryAction> getEntryActions() {
		return entryActions;
	}

	public void setEntryActions(Set<FalkorDBRepositoryAction> entryActions) {
		this.entryActions = entryActions;
	}

	@Override
	public Set<FalkorDBRepositoryAction> getExitActions() {
		return exitActions;
	}

	public void setExitActions(Set<FalkorDBRepositoryAction> exitActions) {
		this.exitActions = exitActions;
	}

	@Override
	public Set<String> getDeferredEvents() {
		return deferredEvents;
	}

	public void setDeferredEvents(Set<String> deferredEvents) {
		this.deferredEvents = deferredEvents;
	}

	@Override
	public String getSubmachineId() {
		return submachineId;
	}

	public void setSubmachineId(String submachineId) {
		this.submachineId = submachineId;
	}

	@Override
	public String toString() {
		return "FalkorDBRepositoryState [id=" + id + ", machineId=" + machineId + ", state=" + state + ", region=" + region
				+ ", initial=" + initial + ", kind=" + kind + ", submachineId=" + submachineId + ", parentState="
				+ parentState + ", stateActions=" + stateActions + ", entryActions=" + entryActions + ", exitActions="
				+ exitActions + ", deferredEvents=" + deferredEvents + "]";
	}
}
