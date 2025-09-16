package com.ingsis.printscript.interpreter;

import com.ingsis.printscript.astnodes.visitor.InterpretableNode;
import com.ingsis.printscript.astnodes.visitor.SemanticallyCheckable;
import com.ingsis.printscript.interpreter.visitor.InterpretVisitor;
import com.ingsis.printscript.lexer.Lexical;
import com.ingsis.printscript.peekableiterator.PeekableIterator;
import com.ingsis.printscript.results.CorrectResult;
import com.ingsis.printscript.results.IncorrectResult;
import com.ingsis.printscript.results.Result;
import com.ingsis.printscript.semantic.SemanticAnalyzer;
import com.ingsis.printscript.semantic.enforcers.SemanticRulesChecker;
import com.ingsis.printscript.syntactic.Syntactic;
import com.ingsis.printscript.syntactic.ast.builders.cor.ChanBuilder;
import com.ingsis.printscript.tokenizers.factories.TokenizerFactory;
import com.ingsis.printscript.tokens.TokenInterface;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;


public class InterpreterTest {

    static class CodeRepository implements PeekableIterator<Character> {
        private int index = 0;
        private final String content;

        public CodeRepository(Path path) {
            try {
                this.content = Files.readString(path);
            } catch (IOException e) {
                throw new RuntimeException("Error reading the file: " + path, e);
            }
        }
        public Character peek() {
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            return content.charAt(index);
        }

        public boolean hasNext() {
            return content.length() > index;
        }

        public Character next() {
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            return content.charAt(index++);
        }
    }

    @Test
    void shouldReturnCorrectResultWhenHaveValidNodes() throws URISyntaxException {
        Path path = Path.of(Objects.requireNonNull(getClass().getClassLoader().getResource("text")).toURI());
        CodeRepository repo = new CodeRepository(path);
        Iterator<TokenInterface> tokenIterator = new Lexical(new TokenizerFactory().createDefaultTokenizer(), repo);
        Iterator<SemanticallyCheckable> checkableNodesIterator = new Syntactic(new ChanBuilder().createDefaultChain(), tokenIterator);
        Iterator<InterpretableNode> interpretableNodesIterator = new SemanticAnalyzer(new SemanticRulesChecker(), checkableNodesIterator);
        Interpreter interpreter = new Interpreter(new InterpretVisitor(), interpretableNodesIterator);

        Result<String> result = interpreter.interpreter();
        Assertions.assertTrue(result.isSuccessful());
        Assertions.assertInstanceOf(CorrectResult.class, result);

    }

    @Test
    void shouldReturnIncorrectResultWhenNoNodes() throws URISyntaxException {
        Path path = Path.of(Objects.requireNonNull(getClass().getClassLoader().getResource("testNoValues")).toURI());
        CodeRepository repo = new CodeRepository(path);
        Iterator<TokenInterface> tokenIterator = new Lexical(new TokenizerFactory().createDefaultTokenizer(), repo);
        Iterator<SemanticallyCheckable> checkableNodesIterator = new Syntactic(new ChanBuilder().createDefaultChain(), tokenIterator);
        Iterator<InterpretableNode> interpretableNodesIterator = new SemanticAnalyzer(new SemanticRulesChecker(), checkableNodesIterator);
        Interpreter interpreter = new Interpreter(new InterpretVisitor(), interpretableNodesIterator);

        Result<String> result = interpreter.interpreter();
        Assertions.assertFalse(result.isSuccessful());
        Assertions.assertInstanceOf(IncorrectResult.class, result);
        Assertions.assertEquals("That's not a valid PrintScript line.", result.errorMessage());

    }

    @Test
    void shouldReturnCorrectResultWhenExpressionNodes() throws URISyntaxException {
        Path path = Path.of(Objects.requireNonNull(getClass().getClassLoader().getResource("testExpressionValues")).toURI());
        CodeRepository repo = new CodeRepository(path);
        Iterator<TokenInterface> tokenIterator = new Lexical(new TokenizerFactory().createDefaultTokenizer(), repo);
        Iterator<SemanticallyCheckable> checkableNodesIterator = new Syntactic(new ChanBuilder().createDefaultChain(), tokenIterator);
        Iterator<InterpretableNode> interpretableNodesIterator = new SemanticAnalyzer(new SemanticRulesChecker(), checkableNodesIterator);
        Interpreter interpreter = new Interpreter(new InterpretVisitor(), interpretableNodesIterator);

        Result<String> result = interpreter.interpreter();
        Assertions.assertTrue(result.isSuccessful());
        Assertions.assertInstanceOf(CorrectResult.class, result);

    }

}
