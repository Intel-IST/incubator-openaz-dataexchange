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
package com.att.research.xacmlatt.pdp.std.functions;

import java.util.ArrayList;
import java.util.List;

import com.att.research.xacml.api.DataType;
import com.att.research.xacml.api.Identifier;
import com.att.research.xacml.api.Status;
import com.att.research.xacml.std.StdStatus;
import com.att.research.xacml.std.StdStatusCode;
import com.att.research.xacml.std.datatypes.DataTypes;
import com.att.research.xacmlatt.pdp.eval.EvaluationContext;
import com.att.research.xacmlatt.pdp.policy.ExpressionResult;
import com.att.research.xacmlatt.pdp.policy.FunctionArgument;

/**
 * FunctionDefinitionComparison implements {@link com.att.research.xacmlatt.pdp.policy.FunctionDefinition} to
 * implement the XACML comparison predicates as functions taking two arguments of the same type
 * and returning a <code>Boolean</code>.
 *
 * In the first implementation of XACML we had separate files for each XACML Function.
 * This release combines multiple Functions in fewer files to minimize code duplication.
 * This file supports the following XACML codes:
 *              integer-greater-than
 *              integer-greater-than-or-equal
 *              integer-less-than
 *              integer-less-than-or-equal
 *              double-greater-than
 *              double-greater-than-or-equal
 *              double-less-than
 *              double-less-than-or-equal
 *
 *
 *
 * @param <I> the java class for the data type of the function Input arguments
 */
public class FunctionDefinitionComparison<I extends Comparable<I>> extends FunctionDefinitionHomogeneousSimple<Boolean, I> {

    /**
     * List of comparison operations.
     *
     *
     */
    public enum OPERATION {GREATER_THAN, GREATER_THAN_EQUAL, LESS_THAN, LESS_THAN_EQUAL };

    // the operation for this instance of the class
    private OPERATION operation;


    /**
     * Constructor - need dataType input because of java Generic type-erasure during compilation.
     *
     * @param idIn
     * @param dataTypeArgsIn
     */
    public FunctionDefinitionComparison(Identifier idIn, DataType<I> dataTypeArgsIn, OPERATION opIn) {
        super(idIn, DataTypes.DT_BOOLEAN, dataTypeArgsIn, 2);
        operation = opIn;
    }


    @Override
    public ExpressionResult evaluate(EvaluationContext evaluationContext, List<FunctionArgument> arguments) {

        List<I> convertedArguments      = new ArrayList<I>();
        Status status                           = this.validateArguments(arguments, convertedArguments);

        /*
         * If the function arguments are not correct, just return an error status immediately
         */
        if (!status.getStatusCode().equals(StdStatusCode.STATUS_CODE_OK)) {
            return ExpressionResult.newError(getFunctionStatus(status));
        }

        int compareResult;
        try {
            compareResult = ((I)convertedArguments.get(0)).compareTo((I)convertedArguments.get(1));
        } catch (Exception e) {
            String message = e.getMessage();
            if (e.getCause() != null) {
                message = e.getCause().getMessage();
            }
            return ExpressionResult.newError(new StdStatus(StdStatusCode.STATUS_CODE_PROCESSING_ERROR, this.getShortFunctionId() + " " + message));
        }

        switch (operation) {
        case GREATER_THAN:
            if (compareResult > 0) {
                return ER_TRUE;
            } else {
                return ER_FALSE;
            }

        case GREATER_THAN_EQUAL:
            if (compareResult > -1) {
                return ER_TRUE;
            } else {
                return ER_FALSE;
            }

        case LESS_THAN:
            if (compareResult < 0) {
                return ER_TRUE;
            } else {
                return ER_FALSE;
            }

        case LESS_THAN_EQUAL:
            if (compareResult < 1) {
                return ER_TRUE;
            } else {
                return ER_FALSE;
            }
        }

        // switch on enum should handle everything - should never get here
        return ExpressionResult.newError(new StdStatus(StdStatusCode.STATUS_CODE_PROCESSING_ERROR, this.getShortFunctionId() + " ENUM did not cover case of " + operation));

    }



}
