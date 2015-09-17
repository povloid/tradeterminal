/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tradeterminal.operations.products.incommingandoutcomming.p1.cells_renders_and_editors.controllabel;

import tradeterminal.operations.products.incommingandoutcomming.p1.IncomingProdToDepsModel.WorkStatus.CMP;

/**
 * Name render
 * @author tt
 */
public class NameControlLabelRender extends ControlLabelRender{

    @Override
    protected boolean getCompare(CMP cmp) {
        return cmp == CMP.NO_NAME || cmp == CMP.NO_NAME_AND_MEASURE || cmp == CMP.NO_SCODE;
    }

}
