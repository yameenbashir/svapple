
package com.dowhile.ebrain.brain.impl;

import java.io.Serializable;
import java.math.BigDecimal;

import com.dowhile.ebrain.brain.BrainMap;
import com.dowhile.ebrain.brain.exception.BrainException;

public class BrainMapImpl implements BrainMap, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -6612577905065765438L;
   
    @Override
    public final void buildLink(
        final LinkType linkType, final BigDecimal defaultWeight)
        throws BrainException {
    	return ;
    }
}
