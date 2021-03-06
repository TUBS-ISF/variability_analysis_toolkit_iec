package de.tu_bs.cs.isf.familymining.ppu_iec.parts.mutation_injection.injection;

import static de.tu_bs.cs.isf.familymining.ppu_iec.parts.mutation_injection.injection.InjectionParameters.CLUSTER_FACT_ACTION_CHANCE;
import static de.tu_bs.cs.isf.familymining.ppu_iec.parts.mutation_injection.injection.InjectionParameters.CLUSTER_FACT_CONFIG_CHANCE;
import static de.tu_bs.cs.isf.familymining.ppu_iec.parts.mutation_injection.injection.InjectionParameters.CLUSTER_FACT_DECL_CHANCE;
import static de.tu_bs.cs.isf.familymining.ppu_iec.parts.mutation_injection.injection.InjectionParameters.CLUSTER_FACT_EXPR_CHANCE;
import static de.tu_bs.cs.isf.familymining.ppu_iec.parts.mutation_injection.injection.InjectionParameters.CLUSTER_FACT_POU_CHANCE;
import static de.tu_bs.cs.isf.familymining.ppu_iec.parts.mutation_injection.injection.InjectionParameters.CLUSTER_FACT_SFC_ACTION_CHANCE;
import static de.tu_bs.cs.isf.familymining.ppu_iec.parts.mutation_injection.injection.InjectionParameters.CLUSTER_FACT_SFC_CHANCE;
import static de.tu_bs.cs.isf.familymining.ppu_iec.parts.mutation_injection.injection.InjectionParameters.CLUSTER_FACT_SFC_STEP_CHANCE;
import static de.tu_bs.cs.isf.familymining.ppu_iec.parts.mutation_injection.injection.InjectionParameters.CLUSTER_FACT_SFC_TRANS_CHANCE;
import static de.tu_bs.cs.isf.familymining.ppu_iec.parts.mutation_injection.injection.InjectionParameters.CLUSTER_FACT_STATEMENT_CHANCE;
import static de.tu_bs.cs.isf.familymining.ppu_iec.parts.mutation_injection.injection.InjectionParameters.CLUSTER_FACT_ST_CHANCE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.emf.ecore.EObject;

import de.tu_bs.cs.isf.familymining.ppu_iec.parts.mutation_injection.mutation.Randomization;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.configuration.Action;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.configuration.Configuration;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.configuration.Declaration;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.configuration.POU;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.configuration.Variable;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.sequentialfunctionchart.AbstractAction;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.sequentialfunctionchart.SequentialFunctionChart;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.sequentialfunctionchart.Step;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.sequentialfunctionchart.Transition;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.structuredtext.CaseBlock;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.structuredtext.ConditionalBlock;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.structuredtext.Statement;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.structuredtext.StructuredText;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.structuredtextexpression.Expression;

public class RandomizedClusterFactory implements ScenarioObjectClusterFactory {

	private static final float ADD_CONTAINTMENT_CHILD_CHANCE = 0.2f;
	private static final float RECURSION_WEIGHT = 1.0f / 10.0f;

	/**
	 * The blacklist describes the classes that should not be part of a cluster
	 * produced by this factory
	 */
	private static final Class<?>[] CLASS_BLACKLIST = { Expression.class, Transition.class, AbstractAction.class,
			ConditionalBlock.class, CaseBlock.class };

	Randomization randomly = new Randomization();

	private float configChance;
	private float pouChance;
	private float actionChance;
	private float declChance;
	private float stChance;
	private float statementChance;
	private float stExprChance;
	private float sfcChance;
	private float sfcStepChance;
	private float sfcActionChance;
	private float sfcTransChance;


	/**
	 * Sets the chances to create a cluster encountering the corresponding object.
	 * The chances let the factory control the way clusters are generated so that
	 * not every invocation of createFrom* will return a cluster.
	 * 
	 * @param configChance
	 * @param pouChance
	 * @param actionChance
	 * @param declChance
	 * @param stChance
	 * @param statementChance
	 * @param stExprChance
	 * @param sfcChance
	 * @param sfcStepChance
	 * @param sfcActionChance
	 * @param sfcTransChance
	 */
	@PostConstruct
	public void setAcceptanceChances(
			@Preference(nodePath = MUTATION_PREF, value = CLUSTER_FACT_CONFIG_CHANCE) float configChance,
			@Preference(nodePath = MUTATION_PREF, value = CLUSTER_FACT_POU_CHANCE) float pouChance,
			@Preference(nodePath = MUTATION_PREF, value = CLUSTER_FACT_ACTION_CHANCE) float actionChance,
			@Preference(nodePath = MUTATION_PREF, value = CLUSTER_FACT_DECL_CHANCE) float declChance,
			@Preference(nodePath = MUTATION_PREF, value = CLUSTER_FACT_ST_CHANCE) float stChance,
			@Preference(nodePath = MUTATION_PREF, value = CLUSTER_FACT_STATEMENT_CHANCE) float statementChance,
			@Preference(nodePath = MUTATION_PREF, value = CLUSTER_FACT_EXPR_CHANCE) float stExprChance,
			@Preference(nodePath = MUTATION_PREF, value = CLUSTER_FACT_SFC_CHANCE) float sfcChance,
			@Preference(nodePath = MUTATION_PREF, value = CLUSTER_FACT_SFC_STEP_CHANCE) float sfcStepChance,
			@Preference(nodePath = MUTATION_PREF, value = CLUSTER_FACT_SFC_ACTION_CHANCE) float sfcActionChance,
			@Preference(nodePath = MUTATION_PREF, value = CLUSTER_FACT_SFC_TRANS_CHANCE) float sfcTransChance) {
		this.configChance = configChance;
		this.pouChance = pouChance;
		this.actionChance = actionChance;
		this.declChance = declChance;
		this.stChance = stChance;
		this.statementChance = statementChance;
		this.stExprChance = stExprChance;
		this.sfcChance = sfcChance;
		this.sfcStepChance = sfcStepChance;
		this.sfcActionChance = sfcActionChance;
		this.sfcTransChance = sfcTransChance;
	}

	@Override
	public List<EObject> createFromConfiguration(Configuration config) {
		return createClusterByChance(configChance, config);
	}

	@Override
	public List<EObject> createFromPOU(POU pou) {
		return createClusterByChance(pouChance, pou);
	}

	@Override
	public List<EObject> createFromAction(Action action) {
		return createClusterByChance(actionChance, action);
	}

	@Override
	public List<EObject> createFromDeclaration(Declaration declaration) {
		return createClusterByChance(declChance, declaration);
	}

	@Override
	public List<EObject> createFromST(StructuredText st) {
		return createClusterByChance(stChance, st);
	}

	@Override
	public List<EObject> createFromSTStatement(Statement statement) {
		return createClusterByChance(statementChance, statement);
	}

	@Override
	public List<EObject> createFromSTExpression(Expression expression) {
		return createClusterByChance(stExprChance, expression);
	}

	@Override
	public List<EObject> createFromSFC(SequentialFunctionChart sfc) {
		return createClusterByChance(sfcChance, sfc);
	}

	@Override
	public List<EObject> createFromSFCStep(Step step) {
		return createClusterByChance(sfcStepChance, step);
	}

	@Override
	public List<EObject> createFromSFCAction(AbstractAction action) {
		return createClusterByChance(sfcActionChance, action);
	}

	@Override
	public List<EObject> createFromSFCTransition(Transition transition) {
		return createClusterByChance(sfcTransChance, transition);
	}

	private List<EObject> createClusterByChance(float pickChance, EObject scenarioObject) {
		if (randomly.nextFloat() < pickChance) {
			return cluster(scenarioObject, ADD_CONTAINTMENT_CHILD_CHANCE);
		} else {
			return Collections.emptyList();
		}
	}

	private List<EObject> cluster(EObject scenarioObject, float takeContaintmentChance) {
		List<EObject> scenarioObjects = new ArrayList<>();

		if (allowedInCluster(scenarioObject)) {
			scenarioObjects.add(scenarioObject);

			for (EObject child : scenarioObject.eContents()) {
				if (randomly.nextFloat() < takeContaintmentChance) {
					List<EObject> childCluster = cluster(child, takeContaintmentChance * RECURSION_WEIGHT);

					childCluster.removeIf(obj -> !allowedInCluster(obj));
					scenarioObjects.addAll(childCluster);
				}
			}
		}

		return scenarioObjects;
	}

	/**
	 * Filters blacklisted classes that should not be part of the scenario object
	 * cluster.
	 * 
	 * @param scenarioObject
	 * @return true if the object is allowed to be part of the a cluster, false
	 *         otherwise
	 */
	private boolean allowedInCluster(EObject scenarioObject) {
		return Stream.of(CLASS_BLACKLIST)
				.noneMatch(blackListedClass -> blackListedClass.isAssignableFrom(scenarioObject.getClass()));
	}

	@Override
	public List<EObject> createFromVariable(Variable variable) {
		return createClusterByChance(0.3f, variable);
	}
}
