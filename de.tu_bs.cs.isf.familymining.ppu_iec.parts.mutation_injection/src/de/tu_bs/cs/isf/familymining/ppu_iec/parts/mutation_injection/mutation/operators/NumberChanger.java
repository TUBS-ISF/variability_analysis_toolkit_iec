package de.tu_bs.cs.isf.familymining.ppu_iec.parts.mutation_injection.mutation.operators;

import static de.tu_bs.cs.isf.familymining.ppu_iec.parts.mutation_injection.mutation.operators.MutationParameters.NUMBER_GENERATED_DIGIT_LENGTH;
import static de.tu_bs.cs.isf.familymining.ppu_iec.parts.mutation_injection.mutation.operators.MutationParameters.NUMBER_MAX_MUTATIONS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.RandomStringUtils;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;

import de.tu_bs.cs.isf.familymining.ppu_iec.parts.mutation_injection.MutationContext;
import de.tu_bs.cs.isf.familymining.ppu_iec.parts.mutation_injection.mutation.Randomization;

public class NumberChanger implements Mutation {

	@Inject
	private AttributeFilter attrFilter;

	private Randomization randomly;
	
	private int maxSymbolsMutations;
	private int generatedDigitLength;

	@PostConstruct
	public void postConstruct(
			@Preference(nodePath = MUTATION_PREF, value = NUMBER_MAX_MUTATIONS) int maxSymbolsMutations,
			@Preference(nodePath = MUTATION_PREF, value = NUMBER_GENERATED_DIGIT_LENGTH) int generatedDigitLength) {
		this.maxSymbolsMutations = maxSymbolsMutations;
		this.generatedDigitLength = generatedDigitLength;
		this.randomly = new Randomization();
	}

	@Override
	public Boolean apply(MutationContext ctx) {
		int symbolMutationCount = 0;
		Set<Number> exclusionList = new TreeSet<>();

		List<EObject> randomized = new ArrayList<>(ctx.getCtxObjects());
		Collections.shuffle(randomized);

		Iterator<EObject> it = randomized.iterator();
		boolean changedContext = false;
		while (it.hasNext() && symbolMutationCount < maxSymbolsMutations) {
			EObject candidate = it.next();
			List<EAttribute> numberAttrs = scanForNumberAttributes(candidate, exclusionList);
			if (!numberAttrs.isEmpty()) {
				EAttribute attr = randomly.pickFrom(numberAttrs);
				
				if (attrFilter.test(candidate, attr)) {
					Number oldValue = (Number) candidate.eGet(attr);
					
					// log change
					ctx.logChange(candidate);
					
					Number newValue = generateNumber(oldValue);
					candidate.eSet(attr, newValue);
					exclusionList.add(newValue);
					
					symbolMutationCount++;		
					changedContext = true;
				}
			}
		}
		return changedContext;
	}

	/**
	 * Generates a new string different from the argument.
	 * 
	 * @param name filter for generated name
	 * @return
	 */
	private Number generateNumber(Number x) {
		Number number = 0;
		do {
			number = Integer.parseInt(RandomStringUtils.randomNumeric(generatedDigitLength));
		} while (number.equals(x));
		return number;
	}

	/**
	 * 
	 * @param eobject    source object for attribute scanning
	 * @param exclusions set of names excluded from scan
	 * @return
	 */
	private <T> List<EAttribute> scanForNumberAttributes(EObject eobject, Set<Number> exclusions) {
		List<EAttribute> attributes = new ArrayList<>();
		for (EAttribute attr : eobject.eClass().getEAllAttributes()) {
			if (eobject.eGet(attr) instanceof Number && !exclusions.contains(eobject.eGet(attr))) {
				attributes.add(attr);
			}
		}
		return attributes;
	}
}
