package parser;

import results.CorrectResult;
import results.IncorrectResult;
import results.Result;
import tokenizers.TokenizerInterface;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public record CodeParser(TokenizerInterface tokenizer) implements CodeParserInterface {
    @Override
    public Result<List<String>> parse(String code) {
        List<String> strings = new ArrayList<>();
        List<Character> charBuffer = new LinkedList<>();
        List<Character> tempCharBuffer = new LinkedList<>();
        boolean validToken = false;
        int charIndex = 0;
        while (charIndex < code.length()) {
            String possibleToken = getBufferString(charBuffer);
            if (validToken) {
                if (!tokenizer().tokenize(possibleToken).isSuccessful()){
                    if (!getBufferString(tempCharBuffer).isEmpty()) {
                        strings.add(getBufferString(tempCharBuffer));
                    }
                    validToken = false;
                    charBuffer.removeAll(tempCharBuffer);
                    charBuffer.removeIf(c -> c == ' ');
                    continue;
                }
            }
            if (tokenizer().tokenize(possibleToken).isSuccessful() && !possibleToken.isEmpty()){
                validToken = true;
                tempCharBuffer.clear();
                tempCharBuffer.addAll(charBuffer);
            }
            charBuffer.add(code.charAt(charIndex));
            charIndex++;
            if (charIndex == code.length()) {
                strings.add(getBufferString(tempCharBuffer));
                charBuffer.clear();
                tempCharBuffer.clear();
                if (code.charAt(charIndex-1) == ';') {
                    strings.add(";");
                } else {
                    return new IncorrectResult<>("Did not ended on ;");
                }
            }
        }
        if (charBuffer.isEmpty()) {
            return new CorrectResult<>(strings);
        } else {
            return new IncorrectResult<>("Unmanageable sequences of chars: " + getBufferString(charBuffer));
        }
    }

    private String getBufferString(List<Character> charBuffer) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Character character : charBuffer) {
            stringBuilder.append(character);
        }
        return stringBuilder.toString();
    }
}
