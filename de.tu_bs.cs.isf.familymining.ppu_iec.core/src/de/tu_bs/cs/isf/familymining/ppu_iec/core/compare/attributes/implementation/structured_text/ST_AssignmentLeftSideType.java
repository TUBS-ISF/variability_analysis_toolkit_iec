package de.tu_bs.cs.isf.familymining.ppu_iec.core.compare.attributes.implementation.structured_text;

import de.tu_bs.cs.isf.e4cf.core.compare.templates.ResultElement;
import de.tu_bs.cs.isf.familymining.ppu_iec.core.compare.interfaces.attributes.language.structured_text.templates.AbstractSTImplAttribute;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.structuredtext.Assignment;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.structuredtext.Statement;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.structuredtextexpression.VariableExpression;

public class ST_AssignmentLeftSideType extends AbstractSTImplAttribute{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5804049127623945449L;
	public static final String UNIT_NAME = "ST Assignment Left Side Type Attribute";
	public static final String UNIT_DESCRIPTION = "This attribute compares the type of the left side of an assignment.";
	
	public ST_AssignmentLeftSideType() {
		super(UNIT_NAME, UNIT_DESCRIPTION);
	}

	@Override
	public ResultElement<Statement> compare(Statement source, Statement target) {
		if(source instanceof Assignment && target instanceof Assignment) {
			VariableExpression sourceExp = ((Assignment)source).getLeft();
			VariableExpression targetExp = ((Assignment)target).getLeft();

			if(sourceExp.getDataType().equals(targetExp.getDataType())) {
				return new ResultElement<Statement>(source, target, 1f ,this);
			} else {
				return new ResultElement<Statement>(source, target, 0 ,this);
			}
		}
		return null;
	}
}
