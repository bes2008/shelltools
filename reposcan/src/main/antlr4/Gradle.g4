grammar Gradle;

// token 名字是大写
// rule 名字是小写
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
STR_FLAG: '"';
fragment HEX_CHAR: [0-9A-Fa-f];
fragment UNICODE: '\\u' HEX_CHAR HEX_CHAR HEX_CHAR HEX_CHAR;
fragment ESCAPE_CHAR: '\\' ["\\/bfnrt];
fragment SAFE_CODE_POINT: ~[\\\u0000-\u001F];
// STRING: '"' (SPACE | ESCAPE_CHAR | UNICODE | SAFE_CODE_POINT)* '"';
STRING: STR_FLAG (~["])* STR_FLAG;

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

simple_key: (STRING|SYMBOL);
simple_key_value_pair: simple_key SPACE? COLON SPACE? simple_value;
simple_key_value_pairs: SPACE? simple_key_value_pair (SPACE? COMMA SPACE? simple_key_value_pair)*?;
string_to_closure_pair: simple_key SPACE closure;

simple_value
    : STRING
    | NUM
    | BOOL
    | NULL;

sequence: (value | string_to_closure_pair) (SPACE? COMMA SPACE? (value | string_to_closure_pair) )*;



value
    : simple_value
    | array
    | string_to_closure_pair
    | simple_key_value_pairs
    ;

// 数组
array: MIDDLE_BRACE_START SPACE? sequence? SPACE? MIDDLE_BRACE_END;

func_name: SYMBOL;

BIG_BRACE_START: '{';
BIG_BRACE_END: '}';
SMALL_BRACE_START: '(';
SMALL_BRACE_END: ')';

var: SYMBOL;
// 定义变量
def_var: SPACE? var SPACE? EQUALS SPACE? value;

// 函数调用
func_invoke_without_closure
    : SPACE? func_name SPACE? value   // 一个参数调用
    | SPACE? func_name SPACE? SMALL_BRACE_START SPACE? (value|sequence)? SPACE? SMALL_BRACE_END // 多个参数调用
    | SPACE? func_name; // 无参调用

groovy_statement: def_var|func_invoke;
closure_body: groovy_statement*;
closure: (BIG_BRACE_START SPACE? closure_body SPACE? BIG_BRACE_END); // 闭包
func_invoke: func_invoke_without_closure SPACE? closure?;


file: (def_var | func_invoke)+;