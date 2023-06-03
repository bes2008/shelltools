grammar Gradle;
CLRF: [\n\r\f]+;
// 公共部分
SPACE: [ \t]+ -> skip;

// 数字
fragment INT: '0' | [1-9][0-9]*; // no leading zeros
fragment SIC: [Ee][+\-]?INT;
Number: '-'? INT ('.'[0-9]+) ? SIC? ;

// 布尔值
fragment TRUE: 'true';
fragment FALSE: 'false';
Bool: TRUE | FALSE;

// 字符串
fragment HEX_CHAR: [0-9A-Fa-f];
fragment UNICODE: '\\u' HEX_CHAR HEX_CHAR HEX_CHAR HEX_CHAR;
fragment ESCAPE_CHAR: '\\' ["\\/bfnrt];
fragment SAFE_CODE_POINT: ~["\\\u0000-\u001F];
String: '"' (ESCAPE_CHAR | UNICODE | SAFE_CODE_POINT)* '"';

// 注释 要跳过
fragment SINGLE_COMMENT: '//' (~('\r'|'\n'))* ;
fragment MULTIPLINE_COMMENT: '/*' .*? '*/';
COMMENT: (SINGLE_COMMENT | MULTIPLINE_COMMENT) -> skip;

// 空
Null: 'null';

// 数组
Array
    : '[' ']' // []
    | '[' Value (',' Value )* ']'
    ;

Symbol: [A-Za-z][A-Za-z0-9_]+?;
// hashmap
KeyValue: Symbol ':' Value;
KeyValues: KeyValue (',' KeyValue)*?;

Value
    : KeyValues
    | Array
    | String
    | Number
    | Bool
    | Null
    ;

FUNC_ARGS: Value;
FUNC_NAME: Symbol;

// 闭包
Closure: '{' FUNC_INVOKE*? '}';


// 函数调用
FUNC_INVOKE_WITHOUT_CLOSURE
    : FUNC_NAME FUNC_ARGS   // 一个参数调用
    | FUNC_NAME '(' FUNC_ARGS ')' // 多个参数调用
    | FUNC_NAME; // 无参调用
FUNC_INVOKE_WITH_CLOSURE: FUNC_INVOKE_WITHOUT_CLOSURE;
FUNC_INVOKE: FUNC_INVOKE_WITHOUT_CLOSURE | FUNC_INVOKE_WITH_CLOSURE;

// 定义变量
DEF_VAR: Symbol '=' Value;
