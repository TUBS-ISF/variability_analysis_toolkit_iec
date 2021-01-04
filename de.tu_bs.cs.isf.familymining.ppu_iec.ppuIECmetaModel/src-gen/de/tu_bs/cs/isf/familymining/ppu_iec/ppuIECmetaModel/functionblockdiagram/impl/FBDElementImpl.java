/**
 */
package de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.functionblockdiagram.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.diagram.impl.DiagramElementImpl;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.functionblockdiagram.FBDElement;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.functionblockdiagram.FBDElementType;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.functionblockdiagram.FunctionBlockDiagramPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>FBD Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.functionblockdiagram.impl.FBDElementImpl#getFBDElementType <em>FBD Element Type</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.functionblockdiagram.impl.FBDElementImpl#getName <em>Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FBDElementImpl extends DiagramElementImpl implements FBDElement {
	/**
	 * The default value of the '{@link #getFBDElementType() <em>FBD Element Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFBDElementType()
	 * @generated
	 * @ordered
	 */
	protected static final FBDElementType FBD_ELEMENT_TYPE_EDEFAULT = FBDElementType.UNDEFINED;

	/**
	 * The cached value of the '{@link #getFBDElementType() <em>FBD Element Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFBDElementType()
	 * @generated
	 * @ordered
	 */
	protected FBDElementType fbdElementType = FBD_ELEMENT_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FBDElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FunctionBlockDiagramPackage.Literals.FBD_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FBDElementType getFBDElementType() {
		return fbdElementType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFBDElementType(FBDElementType newFBDElementType) {
		FBDElementType oldFBDElementType = fbdElementType;
		fbdElementType = newFBDElementType == null ? FBD_ELEMENT_TYPE_EDEFAULT : newFBDElementType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FunctionBlockDiagramPackage.FBD_ELEMENT__FBD_ELEMENT_TYPE, oldFBDElementType, fbdElementType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FunctionBlockDiagramPackage.FBD_ELEMENT__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case FunctionBlockDiagramPackage.FBD_ELEMENT__FBD_ELEMENT_TYPE:
				return getFBDElementType();
			case FunctionBlockDiagramPackage.FBD_ELEMENT__NAME:
				return getName();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case FunctionBlockDiagramPackage.FBD_ELEMENT__FBD_ELEMENT_TYPE:
				setFBDElementType((FBDElementType)newValue);
				return;
			case FunctionBlockDiagramPackage.FBD_ELEMENT__NAME:
				setName((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case FunctionBlockDiagramPackage.FBD_ELEMENT__FBD_ELEMENT_TYPE:
				setFBDElementType(FBD_ELEMENT_TYPE_EDEFAULT);
				return;
			case FunctionBlockDiagramPackage.FBD_ELEMENT__NAME:
				setName(NAME_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case FunctionBlockDiagramPackage.FBD_ELEMENT__FBD_ELEMENT_TYPE:
				return fbdElementType != FBD_ELEMENT_TYPE_EDEFAULT;
			case FunctionBlockDiagramPackage.FBD_ELEMENT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (FBDElementType: ");
		result.append(fbdElementType);
		result.append(", name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //FBDElementImpl