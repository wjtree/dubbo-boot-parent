package com.app.core.code;

public enum Symbol {
    /**
     * 逗号
     */
    COMMA(","),
    /**
     * 句号
     */
    DOT("."),
    /**
     * 冒号
     */
    COLON(":"),
    /**
     * 分号
     */
    SEMICOLON(";"),
    /**
     * 斜杠
     */
    SLASH("/"),
    /**
     * 反斜杠
     */
    BACKSLASH("\\"),
    /**
     * 加号
     */
    PLUS("+"),
    /**
     * 减号
     */
    DASH("-"),
    /**
     * 等号
     */
    EQUALS("="),
    /**
     * 下划线
     */
    UNDERSCORE("_"),
    /**
     * 左方括号
     */
    LEFT_SQUARE_BRACKET("["),
    /**
     * 右方括号
     */
    RIGHT_SQUARE_BRACKET("]"),
    /**
     * 左大括号
     */
    LEFT_CURLY_BRACE("{"),
    /**
     * 右大括号
     */
    RIGHT_CURLY_BRACE("}"),
    /**
     * 与
     */
    AMPERSAND("&"),
    /**
     * 重音符(Tab键上面)
     */
    ACCENT("`"),
    /**
     * 换行符
     */
    LINE_BREAK("\r\n");

    private final String symbol;

    Symbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}