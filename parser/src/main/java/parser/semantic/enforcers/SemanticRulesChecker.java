package parser.semantic.enforcers;

import expression.binary.BinaryExpression;
import responses.CorrectResult;
import responses.IncorrectResult;
import responses.Result;
import statements.LetStatementNode;
import statements.PrintStatementNode;
import visitor.RuleVisitor;

import java.util.List;

public class SemanticRulesChecker implements RuleVisitor {
    private final List<Class<? extends SemanticRulesChecker>> rules;

    public SemanticRulesChecker(){
        rules = List.of(CorrectTypeAssignationEnforcer.class, VariablesExistenceRulesChecker.class);
    }

    @Override
    public Result<String> check(LetStatementNode node) {
        try{
            for (Class<? extends SemanticRulesChecker> rule : rules) {
                RuleVisitor checker = rule.getDeclaredConstructor().newInstance();
                Result<String> checkResult = checker.check(node);
                if (!checkResult.isSuccessful()) return checkResult;
            }
        }catch (Exception e){
            return new IncorrectResult<>(e.getMessage());
        }
        return new CorrectResult<String>("AST passed all semantic rules.");
    }

    @Override
    public Result<String> check(PrintStatementNode node) {
        try{
            for (Class<? extends SemanticRulesChecker> rule : rules) {
                RuleVisitor checker = rule.getDeclaredConstructor().newInstance();
                Result<String> checkResult = checker.check(node);
                if (!checkResult.isSuccessful()) return checkResult;
            }
        }catch (Exception e){
            return new IncorrectResult<>(e.getMessage());
        }
        return new CorrectResult<String>("AST passed all semantic rules.");
    }


    @Override
    public Result<String> check(BinaryExpression node) {
        try{
            for (Class<? extends SemanticRulesChecker> rule : rules) {
                RuleVisitor checker = rule.getDeclaredConstructor().newInstance();
                Result<String> checkResult = checker.check(node);
                if (!checkResult.isSuccessful()) return checkResult;
            }
        }catch (Exception e){
            return new IncorrectResult<String>(e.getMessage());
        }
        return new CorrectResult<String>("AST passed all semantic rules.");
    }

}
