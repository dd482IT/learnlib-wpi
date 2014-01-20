/* Copyright (C) 2013 TU Dortmund
 * This file is part of LearnLib, http://www.learnlib.de/.
 * 
 * LearnLib is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 3.0 as published by the Free Software Foundation.
 * 
 * LearnLib is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with LearnLib; if not, see
 * <http://www.gnu.de/documents/lgpl.en.html>.
 */
package de.learnlib.filters.reuse.tree;

/**
 * A {@link ReuseEdge} connects two {@link ReuseNode}'s in the {@link ReuseTree}
 * and is labeled with input and output behavior. Please note that a edge may be
 * reflexive if domain knowledge is used (input is invariant and/or output is a
 * failure output).
 * 
 * @author Oliver Bauer <oliver.bauer@tu-dortmund.de>
 *
 * @param <S> system state class
 * @param <I> input symbol class
 * @param <O> output symbol class
 */
public class ReuseEdge<S, I, O> {
	private final ReuseNode<S, I, O> source;
	private final ReuseNode<S, I, O> target;
	private final I input;
	private final O output;

	/**
	 * Default constructor.
	 * 
	 * @param source, not allowed to be <code>null</code>.
	 * @param target, not allowed to be <code>null</code>.
	 * @param input, not allowed to be <code>null</code>.
	 * @param output, in case of quiescence maybe <code>null</code>.
	 */
	public ReuseEdge(final ReuseNode<S, I, O> source,
			final ReuseNode<S, I, O> target, final I input, final O output) {
		if (source == null) {
			throw new IllegalArgumentException("Source not allowed to be null.");
		}
		if (target == null) {
			throw new IllegalArgumentException("Target not allowed to be null.");
		}
		if (input == null) {
			throw new IllegalArgumentException("Input not allowed to be null.");
		}
		this.source = source;
		this.target = target;
		this.input = input;
		this.output = output;
	}

	/**
	 * The source node from this edge.
	 * 
	 * @return source, never <code>null</code>.
	 */
	public final ReuseNode<S, I, O> getSource() {
		return source;
	}

	/**
	 * The target node from this edge.
	 * 
	 * @return target, never <code>null</code>.
	 */
	public final ReuseNode<S, I, O> getTarget() {
		return target;
	}

	/**
	 * The respective input on this edge, never <code>null</code>.
	 * 
	 * @return input, not <code>null</node>
	 */
	public final I getInput() {
		return input;
	}

	/**
	 * The respective output on this edge. In case of quiescence the
	 * output is <code>null</code>.
	 * 
	 * @return output
	 */
	public final O getOutput() {
		return output;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public final String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(source.toString());
		sb.append(" -> ");
		sb.append(target.toString());
		sb.append(" i/o ");
		sb.append(input);
		sb.append("/");
		sb.append(output);
		return sb.toString();
	}
}