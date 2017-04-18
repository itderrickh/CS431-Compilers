Package Project5;

Helpers
    sp  = ' ';
    enter = 13;
    lf = 10; 
    tab = 9;
    number = ['0'..'9'];
    letter = ['a'..'z'] | ['A'..'Z'];
    alphanumeric = letter | number;
    underscore = '_';
    digit = number+;
    dot = '.';
    lb = '[';
    rb = ']';
    eq = '=';
    exclam = '!';
    greater = '>';
    less = '<';
    col = ':';
    anychar = [35..255] | sp;

Tokens
    blank = (sp | enter | lf| tab)+;
    begin = 'BEGIN';
    end = 'END';
    clas = 'CLASS';
    if = 'IF';
    else = 'ELSE';
    then = 'THEN';
    new = 'NEW';
    return = 'RETURN';
    int = 'INT';
    real = 'REAL';
    string = 'STRING';
    bool = 'BOOLEAN';
    void = 'VOID';
    switch = 'SWITCH';
    case = 'CASE';
    default = 'DEFAULT';
    break = 'BREAK';
    put = 'PUT';
    true = 'TRUE';
    false = 'FALSE';
    while = 'WHILE';
    for = 'FOR';
    get = 'GET';
    underscore = '_';
    rparen = ')';
    lparen = '(';
    rcurly = '}';
    lcurly = '{';
    rbrace = rb;
    lbrace = lb;
    gt = greater;
    lt = less;
    plus = '+';
    minus = '-';
    times = '*';
    divide = '/';
    colon = col;
    semicolon = ';';
    equals = eq;
    exc = exclam;
    period = dot;
    comma = ',';
    dblquote = '"';
    subset = lb digit+ rb;
    stringlit = '"' anychar+ '"';
    id = letter (letter | number | underscore)*;
    realnum = digit+ dot digit+;
    intnum = digit digit*;
    doubleequals = eq eq;
    notequals = exclam eq;
    gtequals = greater eq;
    ltequals = less eq;
    assign = col eq;

Ignored Tokens
  blank;

Productions
    prog = begin classmethodstmts end;
    classmethodstmts = {morestatements} classmethodstmts classmethodstmt |
                       {empty} ;
    classmethodstmt = {classexpdef} clas id lcurly methodstmtseqs rcurly |
                      {functiondef} type id lparen varlist rparen lcurly stmtseq rcurly |
                      {variabledef} id idtail* colon type semicolon;
    methodstmtseqs = {morestatements} methodstmtseqs methodstmtseq |
                     {empty} ;
    methodstmtseq = {functiondef} type id lparen varlist rparen lcurly stmtseq rcurly |
                    {variabledef} id idtail* colon type semicolon |
                    {methoddef} methodstmt;
    methodstmt = methodexpr;
    methodexpr = id subset? assign expr semicolon;
    stmtexprtail = {increment} id plus [dblplus]:plus |
                   {decrement} id minus [dblminus]:minus |
                   {assign} id assign expr;
    stmtdotidtail = period id lparen varlisttwo rparen;
    stmtseq = {morestatements} stmt stmtseq |
              {empty} ;
    stmt = {assignexp} id subset? assign expr semicolon |
           {assignstring} id subset? assign stringlit semicolon |
           {variabledef} id idtail* colon type subset? semicolon |
           {ifstmt} if lparen idbool rparen then lcurly stmtseq rcurly |
           {ifelsestmt} if lparen idbool rparen then lcurly stmtseq rcurly else [lctwo]:lcurly [stwo]:stmtseq [rctwo]:rcurly |
           {whilestmt} while lparen boolean rparen lcurly stmtseq rcurly |
           {forstmt} for lparen type? id assign expr semicolon boolean [stwo]:semicolon stmtexprtail rparen lcurly stmtseq rcurly |
           {getcommand} id subset? assign get lparen rparen semicolon |
           {putcommand} put lparen id subset? rparen semicolon |
           {increment} id subset? plus [dblplus]:plus semicolon |
           {decrement} id subset? minus [dblminus]:minus semicolon |
           {assignclass} id subset? assign new [rid]:id lparen rparen semicolon |
           {listexp} id lparen varlisttwo rparen semicolon |
           {assignmethod} id subset? period [idtwo]:id lparen varlisttwo rparen stmtdotidtail* semicolon |
           {returnstmt} return expr semicolon |
           {assignboolean} id subset? assign boolean semicolon |
           {switchstmt} switch lparen expr rparen lcurly case [lctwo]:lparen intnum [rcone]:rparen colon stmtseq breakpart? morecases* default [coltwo]:colon [stwo]:stmtseq [rctwo]:rcurly;
    varlisttail = {tailemp} comma id colon type |
                  {largetail} comma id colon type subset;
    varlist = id colon type subset? varlisttail* |
              {empty} ;
    varlisttwotail = {moreexp} comma expr |
                     {morebool} comma boolean;
    varlisttwo = {exptail} expr varlisttwotail* |
                 {booltail} boolean varlisttwotail* |
                 {empty} ;
    idtail = comma id;
    breakpart = break semicolon;
    morecases = case lparen intnum rparen colon stmtseq breakpart?;
    type = {int} int |
           {real} real |
           {string} string |
           {bool} bool |
           {void} void |
           {id} id;
    multop = {multiply} times |
             {divide} divide;
    addop = {plus} plus |
            {minus} minus;
    cond = {dbleql} doubleequals |
           {neql} notequals |
           {gteql} gtequals |
           {lteql} ltequals |
           {gt} gt |
           {lt} lt;
    idbool = {var} id |
              {bool} boolean;
    boolean = {true} true |
              {false} false |
              {cond} expr cond [right]:expr;
    factor = {exp} lparen expr rparen |
            {minusfactor} minus factor |
            {integer} intnum |
            {real} realnum |
            {id} id subset? |
            {list} id lparen varlisttwo rparen |
            {classmethod} id subset? period [nextid]:id lparen varlisttwo rparen;
    term = {multop} term multop factor |
           {factor} factor;
    expr = {addop} expr addop term |
           {term} term;