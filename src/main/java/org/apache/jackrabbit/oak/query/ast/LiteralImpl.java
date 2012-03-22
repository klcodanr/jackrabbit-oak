/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.jackrabbit.oak.query.ast;

import org.apache.jackrabbit.oak.query.SQL2Parser;
import org.apache.jackrabbit.oak.query.ScalarImpl;
import org.apache.jackrabbit.oak.query.ScalarType;

public class LiteralImpl extends StaticOperandImpl {

    private final ScalarImpl value;

    public LiteralImpl(ScalarImpl value) {
        this.value = value;
    }

    public ScalarImpl getLiteralValue() {
        return value;
    }

    @Override
    boolean accept(AstVisitor v) {
        return v.visit(this);
    }

    @Override
    public String toString() {
        switch (value.getType()) {
        case ScalarType.BINARY:
            return cast("BINARY");
        case ScalarType.BOOLEAN:
            return cast("BOOLEAN");
        case ScalarType.DATE:
            return cast("DATE");
        case ScalarType.DECIMAL:
            return cast("DECIMAL");
        case ScalarType.DOUBLE:
        case ScalarType.LONG:
            return value.getString();
        case ScalarType.NAME:
            return cast("NAME");
        case ScalarType.PATH:
            return cast("PATH");
        case ScalarType.REFERENCE:
            return cast("REFERENCE");
        case ScalarType.STRING:
            return escape();
        case ScalarType.URI:
            return cast("URI");
        case ScalarType.WEAKREFERENCE:
            return cast("WEAKREFERENCE");
        default:
            return escape();
        }
    }

    private String cast(String type) {
        return "CAST(" + escape() + " AS " + type + ')';
    }

    private String escape() {
        return SQL2Parser.escapeStringLiteral(value.getString());
    }

    @Override
    ScalarImpl currentValue() {
        return value;
    }

}
