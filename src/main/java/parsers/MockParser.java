package parsers;

import repositories.CodeRepositoryInterface;

import java.util.List;

public record MockParser(CodeRepositoryInterface repository) implements ParserInterface {
    @Override
    public List<String> parse() {
        return List.of(repository.getCode().split(" "));
    }
}
