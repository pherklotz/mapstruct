/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Configured the mapping between two value types.
 * <p><strong>Example:</strong> Using a suffix for enums</p>
 * <pre><code class='java'>
 * public enum CheeseType {
 *     BRIE,
 *     ROQUEFORT
 * }
 *
 * public enum CheeseTypeSuffixed {
 *     BRIE_TYPE,
 *     ROQUEFORT_TYPE
 * }
 *
 * &#64;Mapper
 * public interface CheeseMapper {
 *
 *     &#64;EnumMapping(nameTransformationStrategy = "suffix", configuration = "_TYPE")
 *     CheeseTypeSuffixed map(Cheese cheese);
 *
 *     &#64;InheritInverseConfiguration
 *     Cheese map(CheeseTypeSuffixed cheese);
 *
 * }
 * </code></pre>
 * <pre><code class='java'>
 * // generates
 * public class CheeseMapperImpl implements CheeseMapper {
 *
 *     &#64;Override
 *     public CheeseTypeSuffixed map(Cheese cheese) {
 *         if ( cheese == null ) {
 *             return null;
 *         }
 *
 *         CheeseTypeSuffixed cheeseTypeSuffixed;
 *
 *         switch ( cheese ) {
 *             case BRIE:
 *                 cheeseTypeSuffixed = CheeseTypeSuffixed.BRIE_TYPE;
 *                 break;
 *             case ROQUEFORT:
 *                 cheeseTypeSuffixed = CheeseTypeSuffixed.ROQUEFORT_TYPE;
 *                 break;
 *             default:
 *                 throw new IllegalArgumentException( "Unexpected enum constant: " + cheese );
 *         }
 *
 *         return cheeseTypeSuffixed;
 *     }
 *
 *     &#64;Override
 *     public Cheese map(CheeseTypeSuffixed cheese) {
 *         if ( cheese == null ) {
 *             return null;
 *         }
 *
 *         CheeseType cheeseType;
 *
 *         switch ( cheese ) {
 *             case BRIE_TYPE:
 *                 cheeseType = CheeseType.BRIE;
 *                 break;
 *             case ROQUEFORT_TYPE:
 *                 cheeseType = CheeseType.ROQUEFORT;
 *                 break;
 *             default:
 *                 throw new IllegalArgumentException( "Unexpected enum constant: " + cheese );
 *         }
 *
 *         return cheeseType;
 *     }
 * }
 * </code></pre>
 *
 * @author Filip Hrisafov
 * @since 1.4
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface EnumMapping {

    /**
     * Specifies the name transformation strategy that should be used for implicit mapping between enums.
     * Known strategies are:
     * <ul>
     *     <li>{@link MappingConstants#SUFFIX_TRANSFORMATION} - applies the given {@link #configuration()} as a
     *     suffix to the source enum</li>
     *     <li>{@link MappingConstants#STRIP_SUFFIX_TRANSFORMATION} - strips the the given {@link #configuration()}
     *     from the end of the source enum</li>
     *     <li>{@link MappingConstants#PREFIX_TRANSFORMATION} - applies the given {@link #configuration()} as a
     *     prefix to the source enum</li>
     *     <li>{@link MappingConstants#STRIP_PREFIX_TRANSFORMATION} - strips the given {@link #configuration()} from
     *     the start of the source enum</li>
     * </ul>
     *
     * It is possible to use custom name transformation strategies by implementing the {@code
     * EnumTransformationStrategy} SPI.
     *
     * @return the name transformation strategy
     */
    String nameTransformationStrategy();

    /**
     * The configuration that should be passed on the appropriate name transformation strategy.
     * e.g. a suffix that should be applied to the source enum when doing name based mapping.
     *
     * @return the configuration to use
     */
    String configuration();
}
