package de.tu_bs.cs.isf.familymining.ppu_iec.parts.mutation_injection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import de.tu_bs.cs.isf.familymining.ppu_iec.parts.mutation_injection.mutation.MutationPair;

public class MutationContext {
	
	private final BiMap<EObject, EObject> originalToMutatedObjectMapping; 

	private List<EObject> ctxObjects = new ArrayList<>();

	/**
	 * Signals if the eobject tree structure has been modified.
	 */
	private boolean changedTreeStructure = false;

	/**
	 * Maps original to mutated scenario objects. The map is localized to all
	 * scenario objects contained in the list of ctx objects. The map does not keep
	 * track of inserted or removed objects resulting through mutation.
	 */
	private List<MutationPair> mutationPairs = new ArrayList<>();

	public MutationContext(BiMap<EObject, EObject> originalToMutatedObjectMapping) {
		this.originalToMutatedObjectMapping = originalToMutatedObjectMapping;
	}

	public List<EObject> getCtxObjects() {
		return ctxObjects;
	}

	/**
	 * Logs the mutated object. Creates the connection between original and mutated
	 * scenario object.
	 * 
	 * @param toBeChangedObject
	 */
	public void logChange(EObject toBeChangedObject) {
		// to be changed object was inserted before
		Optional<MutationPair> newlyInsertedPair = mutationPairs.stream().filter(pair -> !pair.hasOrigin() && pair.hasMutant())
				.filter(pair -> pair.getMutant().equals(toBeChangedObject)).findFirst();
		if (newlyInsertedPair.isPresent()) {
			return;
		}
		
		// to be changed object was changed before
		boolean isLogged = mutationPairs.stream().anyMatch(pair -> pair.hasMutant() ? pair.getMutant().equals(toBeChangedObject) : false);
		if (isLogged) {
			return;
		}

		// not related to known mutation pair, therefore mark as changed
		EObject originalCounterpart = originalToMutatedObjectMapping.inverse().get(toBeChangedObject);
		mutationPairs.add(new MutationPair(originalCounterpart, toBeChangedObject));
	}

	public void logRemoval(EObject toBeRemovedMutObject) {
		// to be removed object was changed before
		Optional<MutationPair> changedPair = mutationPairs.stream().filter(pair -> pair.hasOrigin() && pair.hasMutant())
				.filter(pair -> pair.getMutant().equals(toBeRemovedMutObject)).findFirst();
		if (changedPair.isPresent()) {
			changedPair.get().setMutant(null);
			return;
		}
		
		// to be removed object was inserted before
		Optional<MutationPair> newlyInsertedPair = mutationPairs.stream().filter(pair -> !pair.hasOrigin() && pair.hasMutant())
				.filter(pair -> pair.getMutant().equals(toBeRemovedMutObject)).findFirst();
		if (newlyInsertedPair.isPresent()) {
			mutationPairs.remove(newlyInsertedPair.get());
			return;
		}

		// not related to known mutation pair, therefore mark as removed
		EObject originalCounterpart = originalToMutatedObjectMapping.inverse().get(toBeRemovedMutObject);
		mutationPairs.add(new MutationPair(originalCounterpart, null));
	}

	public void logInsertion(EObject toBeInsertedObject) {
		// newly generated object should just make a new mutation pair
		mutationPairs.add(new MutationPair(null, toBeInsertedObject));
	}

	/**
	 * Finds the mutated scenario object using the original counterpart.
	 * 
	 * @param originalScenarioObject
	 * @return mutated version of original scenario object or none if removed as a
	 *         consequence of mutation
	 * @throws UnknownObjectException if the object is not known within this
	 *                                context.
	 */
	public Optional<EObject> getMutated(EObject originalScenarioObject) throws UnknownObjectException {
		Optional<MutationPair> targetMutationPair = mutationPairs.stream().filter(pair -> pair.hasOrigin() && pair.getOrigin().equals(originalScenarioObject)).findFirst();
		if (!targetMutationPair.isPresent()) {
			throw new UnknownObjectException(originalScenarioObject, this);
		} else {
			return Optional.ofNullable(targetMutationPair.get().getMutant());
		}
	}

	/**
	 * Finds the original scenario object using the mutated counterpart.
	 * 
	 * @param mutatedScenarioObject
	 * @return original scenario object from which the mutated version was created
	 *         or none if the mutated object was newly generated.
	 * @throws UnknownObjectException if the object is not known within this
	 *                                context.
	 */
	public Optional<EObject> getOriginal(EObject mutatedScenarioObject) throws UnknownObjectException {
		Optional<MutationPair> targetMutationPair = mutationPairs.stream().filter(pair -> pair.hasMutant() && pair.getMutant().equals(mutatedScenarioObject)).findFirst();
		if (!targetMutationPair.isPresent()) {
			throw new UnknownObjectException(mutatedScenarioObject, this);
		} else {
			return Optional.ofNullable(targetMutationPair.get().getOrigin());
		}
	}

	public boolean sharesElementsWith(MutationContext other) {
		List<EObject> thisObjs = this.getCtxObjects();
		List<EObject> otherObjs = other.getCtxObjects();

		for (EObject ctxObject : otherObjs) {
			if (thisObjs.contains(ctxObject)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasChangedTreeStructure() {
		return changedTreeStructure;
	}

	public void setChangedTreeStructure(boolean changedTreeStructure) {
		this.changedTreeStructure = changedTreeStructure;
	}

	public int mutatedEObjects() {
		return mutationPairs.size();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MutationContext other = (MutationContext) obj;
		if (ctxObjects == null) {
			if (other.ctxObjects != null)
				return false;
		} else if (!EcoreUtil.equals(ctxObjects, other.ctxObjects))
			return false;
		return true;
	}

	/**
	 * Deep copy of mutation context.<br>
	 * <br>
	 * <b>Note</b>: copies the entire containment tree of eobjects.
	 */
	@Override
	public MutationContext clone() {
		MutationContext clone = new MutationContext(originalToMutatedObjectMapping);
		clone.getCtxObjects().clear();
		clone.getCtxObjects().addAll(ctxObjects.stream().map(EcoreUtil::copy).collect(Collectors.toList()));

		return clone;
	}

	public List<MutationPair> getMutationPairs() {
		return mutationPairs;
	}

}
