/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tradeterminal.operations.products.incommingandoutcomming.p1.cells_renders_and_editors.controllabel;

import tradeterminal.operations.products.incommingandoutcomming.p1.IncomingProdToDepsModel.WorkStatus.CMP;

/**
 * Measure render
 * @author tt
 */
public class MeasureControlLabelRender extends ControlLabelRender{

    @Override
    protected boolean getCompare(CMP cmp) {
       return cmp == CMP.NO_MEASURE || cmp == CMP.NO_NAME_AND_MEASURE || cmp == CMP.NO_SCODE || cmp == CMP.NO_MEASURE_MTYPE;
    }

}
