grammar Gradle;

CLRF: [\n\r\f]+;
// 公共部分
SPACE: [ \t]+ -> skip;

// 数字
fragment INT: '0' | [1-9][0-9]*; // no leading zeros
fragment SIC: [Ee][+\-]?INT;
NUM: '-'? INT ('.'[0-9]+) ? SIC? ;

// 布尔值
fragment TRUE: 'true';
fragment FALSE: 'false';
BOOL: TRUE | FALSE;

// 字符串
fragment HEX_CHAR: [0-9A-Fa-f];
fragment UNICODE: '\\u' HEX_CHAR HEX_CHAR HEX_CHAR HEX_CHAR;
fragment ESCAPE_CHAR: '\\' ["\\/bfnrt];
fragment SAFE_CODE_POINT: ~["\\\u0000-\u001F];
STRING: '"' (ESCAPE_CHAR | UNICODE | SAFE_CODE_POINT)* '"';

// 注释 要跳过
fragment SINGLE_COMMENT: '//' (~('\r'|'\n'))* ;
fragment MULTIPLINE_COMMENT: '/*' .*? '*/';
COMMENT: (SINGLE_COMMENT | MULTIPLINE_COMMENT) -> skip;

// 空
NULL: 'null';

// 数组
ARRAY
    : '[' ']' // []
    | '[' VALUE (',' VALUE )* ']'
    ;

SYMBOL: [A-Za-z][A-Za-z0-9_]+?;
// hashmap
KEY_VALUE: SYMBOL ':' VALUE;
KEY_VALUES: KEY_VALUE (',' KEY_VALUE)*?;

VALUE
    : KEY_VALUES
    | ARRAY
    | STRING
    | NUM
    | BOOL
    | NULL
    ;

FUNC_ARGS: VALUE;
FUNC_NAME: SYMBOL;

// 闭包
CLOSURE: '{' FUNC_INVOKE*? '}';


// 函数调用
FUNC_INVOKE_WITHOUT_CLOSURE
    : FUNC_NAME FUNC_ARGS   // 一个参数调用
    | FUNC_NAME '(' FUNC_ARGS ')' // 多个参数调用
    | FUNC_NAME; // 无参调用
FUNC_INVOKE_WITH_CLOSURE: FUNC_INVOKE_WITHOUT_CLOSURE CLOSURE;
FUNC_INVOKE: FUNC_INVOKE_WITHOUT_CLOSURE | FUNC_INVOKE_WITH_CLOSURE;

// 定义变量
DEF_VAR: SYMBOL '=' VALUE;
