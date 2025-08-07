package token.TOKEN;

import token.TokenInterfaces.Token;

public class TOKENS implements Token {

    private String name;
    private String value;

    public TOKENS(String token, String value) {
        this.name = token;
        this.value = value;
    }

    @Override
    public String value(){
        return value;
    }

    @Override
    public String name() {
        return name;
    }
}
