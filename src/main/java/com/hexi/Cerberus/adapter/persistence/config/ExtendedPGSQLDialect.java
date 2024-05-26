package com.hexi.Cerberus.adapter.persistence.config;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.PostgreSQLDialect;
import org.hibernate.dialect.function.SqlFunction;
import org.hibernate.engine.jdbc.dialect.internal.StandardDialectResolver;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolutionInfo;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolver;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.query.ReturnableType;
import org.hibernate.query.hql.HqlInterpretationException;
import org.hibernate.query.spi.QueryEngine;
import org.hibernate.query.sqm.function.AbstractSqmSelfRenderingFunctionDescriptor;
import org.hibernate.query.sqm.function.FunctionKind;
import org.hibernate.query.sqm.function.SqmFunctionRegistry;
import org.hibernate.query.sqm.produce.function.*;
import org.hibernate.query.sqm.tree.SqmTypedNode;
import org.hibernate.query.sqm.tree.from.SqmRoot;
import org.hibernate.sql.ast.SqlAstTranslator;
import org.hibernate.sql.ast.spi.SqlAppender;
import org.hibernate.sql.ast.tree.SqlAstNode;
import org.hibernate.sql.ast.tree.expression.ColumnReference;
import org.hibernate.sql.ast.tree.expression.Expression;
import org.hibernate.sql.ast.tree.update.Assignable;
import org.hibernate.type.BasicTypeRegistry;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.spi.TypeConfiguration;

import java.lang.reflect.Type;
import java.sql.Types;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class ExtendedPGSQLDialect extends PostgreSQLDialect {

    public ExtendedPGSQLDialect() {
        super();
    }

    @Override
    public void initializeFunctionRegistry(FunctionContributions functionContributions){
        System.out.println("CUSTOM HIBER DIALECT");
        super.initializeFunctionRegistry(functionContributions);
        BasicTypeRegistry basicTypeRegistry = functionContributions.getTypeConfiguration().getBasicTypeRegistry();
        SqmFunctionRegistry functionRegistry = functionContributions.getFunctionRegistry();
        functionRegistry.register(
                "get_ctid",
                new AbstractSqmSelfRenderingFunctionDescriptor( "get_ctid", StandardArgumentsValidators.exactly( 1 ), StandardFunctionReturnTypeResolvers.invariant( basicTypeRegistry.resolve( StandardBasicTypes.STRING ) ), null ) {

                    @Override
                    public void render(
                            SqlAppender sqlAppender,
                            List<? extends SqlAstNode> arguments,
                            ReturnableType<?> returnType,
                            SqlAstTranslator<?> walker) {
                        final SqlAstNode sqlAstNode = arguments.get(0);
                        final ColumnReference reference;
                        if ( sqlAstNode instanceof Assignable) {
                            final Assignable assignable = (Assignable) sqlAstNode;
                            reference = assignable.getColumnReferences().get(0);
                        }
                        else if ( sqlAstNode instanceof Expression) {
                            final Expression expression = (Expression) sqlAstNode;
                            reference = expression.getColumnReference();
                        }
                        else {
                            throw new HqlInterpretationException( "path did not map to a column" );
                        }
                        sqlAppender.appendSql( reference.getQualifier() );
                        sqlAppender.appendSql( ".ctid" );
                    }
                }
        );



        functionRegistry.register(
                "row_number",
                new AbstractSqmSelfRenderingFunctionDescriptor( "row_number", StandardArgumentsValidators.exactly( 0 ), StandardFunctionReturnTypeResolvers.invariant( basicTypeRegistry.resolve( StandardBasicTypes.BIG_INTEGER ) ), null ) {

                    @Override
                    public void render(
                            SqlAppender sqlAppender,
                            List<? extends SqlAstNode> arguments,
                            ReturnableType<?> returnType,
                            SqlAstTranslator<?> walker) {

                        sqlAppender.appendSql( "ROW_NUMBER() OVER ()" );
                    }
                }
        );

//        functionRegistry.register(
//                "get_ctid",
//
//                new AbstractSqmSelfRenderingFunctionDescriptor(
//                        "get_ctid",
//                        FunctionKind.NORMAL,
//                        //new ArgumentTypesValidator(getCTIDValidator()),
//                        null,
//                        StandardFunctionReturnTypeResolvers.invariant(
//                                functionContributions.getTypeConfiguration().getBasicTypeRegistry().resolve( StandardBasicTypes.LONG )
//                        ),
//                        null
//                ) {
//
//                    @Override
//                    public void render(
//                            SqlAppender sqlAppender,
//                            List<? extends SqlAstNode> sqlAstArguments,
//                            ReturnableType<?> returnType,
//                            SqlAstTranslator<?> walker) {
//                        Set<String> tableNames = walker.getAffectedTableNames();
//                        System.out.println("getAffectedTableNames: " + tableNames.toString());
//                        System.out.println("sqlAstArguments: " + sqlAstArguments.toString());
//                        sqlAstArguments.getFirst().accept(walker);
////                        sqlAppender.append("match");
////                        char separator = '(';
////                        for (int i = 0; i < sqlAstArguments.size() - 1; i++) {
////                            sqlAppender.append(separator);
////                            separator = ',';
////                            sqlAstArguments.get(i).accept(walker);
////                        }
//                        sqlAppender.append("(((ctid::text::point)[0]::bigint << 32) | (ctid::text::point)[1]::bigint)");
////                        sqlAstArguments.get(sqlAstArguments.size() - 1).accept(walker);
////                        sqlAppender.append("in boolean mode)");
//                    }
//                }
//        );

//        functionRegistry.registerPattern(
//                "union",
//                "(?1 union ?2)"
//            );
    }

//    private ArgumentsValidator getCTIDValidator() {
//        return new ArgumentsValidator() {
//            @Override
//            public void validate(
//                    List<? extends SqmTypedNode<?>> arguments,
//                    String functionName,
//                    TypeConfiguration typeConfiguration) {
//                if ( arguments.size() != 1 && arguments.getFirst().get) {
//                    throw new FunctionArgumentException(
//                            String.format(
//                                    Locale.ROOT,
//                                    "Function %s() obtains root, but somthing else given",
//                                    functionName
//                            )
//                    );
//                }
//
//            }
//        };
//    }

}
