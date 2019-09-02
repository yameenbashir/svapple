package com.dowhile.ebrain.brain;

import java.math.BigDecimal;

import com.dowhile.ebrain.brain.exception.BrainException;
import com.dowhile.ebrain.brain.impl.BrainMapImpl;
import com.google.inject.ImplementedBy;

@ImplementedBy(BrainMapImpl.class)
public interface BrainMap {

    public enum ModerationType {

        IMPROVE, DEGRADE, CERTIFY, NULLIFY
    }

    public enum LinkType {

        CLEANED, NORMALIZED, RELATED_NORMAL
    }

    /**
     * Builds or updates a link between the two entities.
     *
     * @param source
     * @param target
     * @param defaultWeight
     * @throws BrainException
     */
    void buildLink( LinkType linkType,
            BigDecimal defaultWeight) throws BrainException;
}
