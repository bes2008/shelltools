grammar Gradle;

// token 名字是大写
// rule 名字是小写 或者驼峰
// rule 中可以嵌入 token
// token 中不能嵌入 rule, token， 但可以嵌入 fragment
// rule 中不能直接 使用字面量，必须使用 token


CLRF: [\n\r\f]+ -> skip;
// 公共部分
SPACE: [ \t]+;

// 数字
fragment INT: '0' | [1-9][0-9]*; // no leading zeros
fragment SIC: [Ee][+\-]?INT;
NUM: '-'? INT ('.'[0-9]+) ? SIC? ;

// 布尔值
fragment TRUE: 'true';
fragment FALSE: 'false';
BOOL: TRUE | FALSE;

// 字符串
fragment DOUBLE_QUOTE: '"';
fragment SINGLE_QUOTE: '\'';
fragment HEX_CHAR: [0-9A-Fa-f];
fragment UNICODE: '\\u' HEX_CHAR HEX_CHAR HEX_CHAR HEX_CHAR;
fragment ESCAPE_CHAR: '\\' ["\\/bfnrt];
fragment SAFE_CODE_POINT: ~[\\\u0000-\u001F];
// STRING: '"' (SPACE | ESCAPE_CHAR | UNICODE | SAFE_CODE_POINT)* '"';
STRING
    : (DOUBLE_QUOTE (~["])* DOUBLE_QUOTE)
    | (SINGLE_QUOTE (~['])* SINGLE_QUOTE)
    ;

// 注释 要跳过
fragment SINGLE_COMMENT: '//' (~('\r'|'\n'))* ;
fragment MULTIPLINE_COMMENT: '/*' .*? '*/';
COMMENT: (SINGLE_COMMENT | MULTIPLINE_COMMENT) -> skip;

// 空
NULL: 'null';

COLON: ':';
EQUALS: '=';
COMMA: ',';
MIDDLE_BRACE_START: '[';
MIDDLE_BRACE_END: ']';



SYMBOL: [A-Za-z][A-Za-z0-9_]*;


// hashmap
// keyValue: SYMBOL SPACE? COLON SPACE? value;
//keyValues: SPACE? keyValue (SPACE? COMMA keyValue)*?;

simpleKey: (STRING|SYMBOL);
simpleKeyValuePair: simpleKey SPACE? COLON SPACE? simpleValue;
simpleKeyValuePairs: SPACE? simpleKeyValuePair (SPACE? COMMA SPACE? simpleKeyValuePair)*?;
stringToClosurePair: simpleKey SPACE closure;

simpleValue
    : STRING
    | NUM
    | BOOL
    | NULL;

sequence: SPACE? (value | stringToClosurePair) (SPACE? COMMA SPACE? (value | stringToClosurePair) )*;



value
    : simpleValue
    | array
    | stringToClosurePair
    | simpleKeyValuePairs
    ;

// 数组
array: MIDDLE_BRACE_START SPACE? sequence? SPACE? MIDDLE_BRACE_END;

funcName: SYMBOL;

BIG_BRACE_START: '{';
BIG_BRACE_END: '}';
SMALL_BRACE_START: '(';
SMALL_BRACE_END: ')';

var: SYMBOL;
// 定义变量
defineVariable: SPACE? var SPACE? EQUALS SPACE? value;

groovyStatement: defineVariable|funcInvocation;

closureBody: groovyStatement*;
closure: (BIG_BRACE_START SPACE? closureBody SPACE? BIG_BRACE_END); // 闭包
funcInvocation
    :   (SPACE? funcName SPACE? value   // 一个参数调用
        | SPACE? funcName SPACE? SMALL_BRACE_START SPACE? (value|sequence)? SPACE? SMALL_BRACE_END // 多个参数调用
        | SPACE? funcName // 无参调用
        ) SPACE? closure?;

program: (defineVariable | funcInvocation)+;