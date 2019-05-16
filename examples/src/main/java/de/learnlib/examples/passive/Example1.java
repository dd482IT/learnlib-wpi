/* Copyright (C) 2013-2019 TU Dortmund
 * This file is part of LearnLib, http://www.learnlib.de/.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.learnlib.examples.passive;

import java.util.Arrays;
import java.util.Collection;

import de.learnlib.algorithms.rpni.BlueFringeRPNIDFA;
import de.learnlib.api.algorithm.PassiveLearningAlgorithm.PassiveDFALearner;
import net.automatalib.automata.fsa.DFA;
import net.automatalib.visualization.Visualization;
import net.automatalib.words.Alphabet;
import net.automatalib.words.Word;
import net.automatalib.words.impl.Alphabets;

/**
 * Example of setting up a passive DFA learner.
 *
 * @author frohme
 */
public final class Example1 {

    private Example1() {
        // prevent instantiation
    }

    public static void main(String[] args) {

        // define the alphabet
        final Alphabet<Character> alphabet = Alphabets.characters('a', 'b');

        // instantiate learner
        // alternatively one can also use the EDSM variant (BlueFringeEDSMDFA from the learnlib-rpni-edsm artifact)
        // or the MDL variant (BlueFringeMDLDFA from the learnlib-rpni-mdl artifact)
        final PassiveDFALearner<Character> learner = new BlueFringeRPNIDFA<>(alphabet);

        // if no training samples have been provided, only the empty automaton can be constructed
        final DFA<?, Character> emptyModel = learner.computeModel();
        Visualization.visualize(emptyModel, alphabet);

        // add the positive training samples
        learner.addPositiveSamples(getPositiveTrainingSamples());

        // since RPNI is a greedy state-merging algorithm, providing only positive examples results in the trivial
        // one-state acceptor, because there exist no negative "counterexamples" that prevent state merges when
        // generalizing the initial prefix tree acceptor
        final DFA<?, Character> firstModel = learner.computeModel();
        Visualization.visualize(firstModel, alphabet);

        // add negative training samples
        learner.addNegativeSamples(getNegativeTrainingSamples());

        // after adding negative samples (i.e. words that must not be accepted by the model) we get a more "realistic"
        // generalization of the given training set
        final DFA<?, Character> secondModel = learner.computeModel();
        Visualization.visualize(secondModel, alphabet);
    }

    /**
     * Returns the positives samples from the example of chapter 12.4.3 of "Grammatical Inference" by Colin de la
     * Higuera.
     *
     * @return a collection of (positive) training samples
     */
    private static Collection<Word<Character>> getPositiveTrainingSamples() {
        return Arrays.asList(Word.fromCharSequence("aaa"),
                             Word.fromCharSequence("aaba"),
                             Word.fromCharSequence("bba"),
                             Word.fromCharSequence("bbaba"));
    }

    /**
     * Returns the negative samples from the example of chapter 12.4.3 of "Grammatical Inference" by Colin de la
     * Higuera.
     *
     * @return a collection of (negative) training samples
     */
    private static Collection<Word<Character>> getNegativeTrainingSamples() {
        return Arrays.asList(Word.fromCharSequence("a"),
                             Word.fromCharSequence("bb"),
                             Word.fromCharSequence("aab"),
                             Word.fromCharSequence("aba"));
    }

}