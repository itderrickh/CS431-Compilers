Package ProjFour;

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
    id = letter (letter | number | underscore)*;
    realnum = digit+ dot digit+;
    intnum = digit digit*;
    doubleequals = eq eq;
    notequals = exclam eq;
    gtequals = greater eq;
    ltequals = less eq;
    assign = col eq;
    anychar = [35..255];

Ignored Tokens
  blank;

Productions
    prog = begin classmethodstmts end;
    classmethodstmts = {cmssfirst} classmethodstmts classmethodstmt |
                       {cmsssecond} ;
    classmethodstmt = {cmsfirst} clas id lcurly methodstmtseqs rcurly |
                      {cmssecond} type id lparen varlist rparen lcurly stmtseq rcurly |
                      {cmsthird} id idtail* colon type semicolon;
    methodstmtseqs = {msssfirst} methodstmtseqs methodstmtseq |
                     {mssssecond} ;
    methodstmtseq = {mssfirst} type id lparen varlist rparen lcurly stmtseq rcurly |
                    {msssecond} id idtail* colon type semicolon;
    stmtexprtail = {ipp} id plus [dblplus]:plus |
                   {imm} id minus [dblminus]:minus |
                   {exp} id assign expr;
    stmtdotidtail = period id lparen varlisttwo rparen;
    stmtseq = {ssfirst} stmt stmtseq |
              {sssecond} ;
    stmt = {sfirst} id subset? assign expr semicolon |
           {ssecond} id subset? assign dblquote anychars [enddbl]:dblquote semicolon |
           {sthird} id idtail* colon type subset? semicolon |
           {sfourth} if lparen boolean rparen then lcurly stmtseq rcurly |
           {sfifth} if lparen boolean rparen then lcurly stmtseq rcurly else [lctwo]:lcurly [stwo]:stmtseq [rctwo]:rcurly |
           {ssixth} while lparen boolean rparen lcurly stmtseq rcurly |
           {sseventh} for lparen type? id assign expr semicolon boolean [stwo]:semicolon stmtexprtail rparen lcurly stmtseq rcurly |
           {seighth} id subset? assign get lparen rparen semicolon |
           {sninth} put lparen id subset? rparen semicolon |
           {stenth} id subset? plus [dblplus]:plus semicolon |
           {seleven} id subset? minus [dblminus]:minus semicolon |
           {stwelve} id subset? assign new [rid]:id lparen rparen semicolon |
           {sthirteen} id lparen varlisttwo rparen semicolon |
           {sfourteen} id subset? period [idtwo]:id lparen varlisttwo rparen stmtdotidtail* semicolon |
           {sfifteen} return expr semicolon |
           {ssixteen} id subset? assign boolean semicolon |
           {sseventeen} switch lparen expr rparen lcurly case [lctwo]:lparen intnum [rcone]:rparen colon stmtseq breakpart? morecases* default [coltwo]:colon [stwo]:stmtseq [rctwo]:rcurly;
    varlisttail = {tailemp} comma id colon type |
                  {largetail} comma id colon type subset;
    varlist = id colon type subset? varlisttail* |
              {bl} ;
    varlisttwotail = comma expr;
    varlisttwo = {vlt} expr varlisttwotail* |
                 {blank} ;
    idtail = comma id;
    breakpart = break semicolon;
    morecases = case lparen intnum rparen colon stmtseq breakpart?;
    type = {int} int |
           {real} real |
           {string} string |
           {bool} bool |
           {void} void |
           {id} id;
    multop = {mult} times |
             {div} divide;
    addop = {plus} plus |
            {minus} minus;
    cond = {dbleql} doubleequals |
           {neql} notequals |
           {gteql} gtequals |
           {lteql} ltequals |
           {gt} gt |
           {lt} lt;
    boolean = {true} true |
              {false} false |
              {cond} expr cond [right]:expr;
    factor = {ffirst} lparen expr rparen |
            {fsecond} minus factor |
            {fthird} intnum |
            {ffourth} realnum |
            {fsixth} id subset? |
            {fseventh} id lparen varlisttwo rparen |
            {feighth} id subset? period [nextid]:id lparen varlisttwo rparen;
    term = {tfirst} term multop factor |
           {tsecond} factor;
    expr = {efirst} expr addop term |
           {esecond} term;
    anychars = anychar+;