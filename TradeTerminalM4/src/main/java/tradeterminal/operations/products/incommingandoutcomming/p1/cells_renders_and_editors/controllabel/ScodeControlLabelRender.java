/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tradeterminal.operations.products.incommingandoutcomming.p1.cells_renders_and_editors.controllabel;

import tradeterminal.operations.products.incommingandoutcomming.p1.IncomingProdToDepsModel.WorkStatus.CMP;

/**
 * scvode render
 * @author tt
 */
public class ScodeControlLabelRender extends ControlLabelRender{

    @Override
    protected boolean getCompare(CMP cmp) {
        return cmp == CMP.NO_SCODE;
    }

}
