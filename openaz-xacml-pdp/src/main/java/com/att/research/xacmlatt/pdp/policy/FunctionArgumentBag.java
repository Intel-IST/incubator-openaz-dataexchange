/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */

/*
 *                        AT&T - PROPRIETARY
 *          THIS FILE CONTAINS PROPRIETARY INFORMATION OF
 *        AT&T AND IS NOT TO BE DISCLOSED OR USED EXCEPT IN
 *             ACCORDANCE WITH APPLICABLE AGREEMENTS.
 *
 *          Copyright (c) 2013 AT&T Knowledge Ventures
 *              Unpublished and Not for Publication
 *                     All Rights Reserved
 */
package com.att.research.xacmlatt.pdp.policy;

import java.util.Iterator;

import com.att.research.xacml.api.AttributeValue;
import com.att.research.xacml.api.Status;
import com.att.research.xacml.std.StdStatus;

/**
 * FunctionArgumentBag implements the {@link FunctionArgument} interface for
 * a {@link Bag} objects.
 *
 */
public class FunctionArgumentBag implements FunctionArgument {
    private Bag bag;

    /**
     * Creates a new <code>FunctionArgumentBag</code> from the given <code>Bag</code>.
     *
     * @param bagIn the <code>Bag</code> for the new <code>FunctionArgumentBag</code>.
     */
    public FunctionArgumentBag(Bag bagIn) {
        this.bag        = bagIn;
    }

    @Override
    public Status getStatus() {
        return StdStatus.STATUS_OK;
    }

    @Override
    public boolean isOk() {
        return true;
    }

    @Override
    public boolean isBag() {
        return true;
    }

    @Override
    public AttributeValue<?> getValue() {
        Iterator<AttributeValue<?>> iterAttributeValues = this.bag.getAttributeValues();
        if (iterAttributeValues == null || !iterAttributeValues.hasNext()) {
            return null;
        } else {
            return iterAttributeValues.next();
        }
    }

    @Override
    public Bag getBag() {
        return this.bag;
    }
}
