Package ProjFour;

Helpers
    sp  = ' ';
    number = ['0'..'9'];
    letter = ['a'..'z'] | ['A'..'Z'];
    alphanumeric = letter | number;
    anychars = [35..255]+;
    underscore = '_';
    digit = number+;
    dot = '.';
    lb = '[';
    rb = ']';

Tokens
    id = letter (letter | number | underscore)*;
    realnum = digit+ dot digit+;
    anychars = anychars;
    whitespace = sp+;
    intnum = digit+;
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
    gt = '>';
    lt = '<';
    plus = '+';
    minus = '-';
    times = '*';
    divide = '/';
    colon = ':';
    semicolon = ';';
    equals = '=';
    exc = '!';
    period = dot;
    comma = ',';
    dblquote = '\"';
    subset = (lb digit+ rb)?;

Ignored Tokens
  whitespace;


Productions
    prog = begin classmethodstmts end;
    type = {int} int |
           {real} real |
           {string} string |
           {bool} bool |
           {void} void |
           {id} id;
    multop = {times} times |
             {divide} divide;
    addop = {plus} plus |
            {minus} minus;
    doubleequals = equals [dbleql]:equals;
    notequals = exc equals;
    gtequals = gt equals;
    ltequals = lt equals;
    assign = colon equals;
    cond = {dbleql} doubleequals |
           {neql} notequals |
           {gteql} gtequals |
           {lteql} ltequals |
           {gt} gt |
           {lt} lt;
    boolean = {true} true | {false} false | {cond} expr cond [right]:expr;
    factor = {ffirst} lparen expr rparen |
            {fsecond} minus factor |
            {fthird} intnum |
            {ffourth} realnum |
            {ffifth} id subset |
            {fseventh} id lparen varlisttwo rparen |
            {feighth} id lbrace intnum rbrace period [nextid]:id lparen varlisttwo rparen;

    term = {tfirst} term multop factor |
           {tsecond} factor;
    expr = {efirst} expr addop term |
           {esecond} term;
    varlisttwotail = comma expr;
    varlisttwo = {vlt} expr varlisttwotail* |
                 {blank} ;
    varlisttail = {tailemp} comma id colon type |
                  {largetail} comma id colon type lbrace intnum rbrace ;
    varlist = id colon type subset varlisttail* |
              {bl} ;
    idtail = comma id;
    stmtexprtail = {ipp} id plus [dblplus]:plus |
                   {imm} id minus [dblminus]:minus |
                   {exp} expr;
    stmtdotidtail = period id lparen varlisttwo rparen;
    breakpart = break semicolon;
    morecases = case lparen intnum rparen colon stmtseq breakpart?;
    stmt = {sfirst} id subset assign expr |
           {ssecond} id subset assign dblquote anychars [enddbl]:dblquote |
           {sthird} id idtail* colon type subset |
           {sfourth} if lparen boolean rparen then lcurly stmtseq rcurly |
           {sfifth} if lparen boolean rparen then [lcone]:lcurly [sone]:stmtseq [rcone]:rcurly else [lctwo]:lcurly [stwo]:stmtseq [rctwo]:rcurly |
           {ssixth} while lparen boolean rparen lcurly stmtseq rcurly |
           {sseventh} for lparen type? id assign expr [sone]:semicolon boolean [stwo]:semicolon stmtexprtail lcurly stmtseq rcurly |
           {seighth} id subset assign get lparen rparen semicolon |
           {sninth} put lparen id subset rparen semicolon |
           {stenth} id subset plus [dblplus]:plus |
           {seleven} id subset minus [dblminus]:minus |
           {stwelve} id subset assign new [rid]:id lparen rparen semicolon |
           {sthirteen} id lparen varlisttwo rparen semicolon |
           {sfourteen} id subset period [idtwo]:id lparen varlisttwo rparen stmtdotidtail* semicolon |
           {sfifteen} return expr semicolon |
           {ssixteen} id subset assign boolean semicolon |
           {sseventeen} switch lparen expr rparen [lcone]:lcurly case [lctwo]:lcurly intnum [rcone]:rcurly [colone]:colon [sone]:stmtseq breakpart? morecases* default [coltwo]:colon [stwo]:stmtseq [rctwo]:rcurly;
    stmtseq = {ssfirst} stmt stmtseq | {sssecond} ;
    methodstmtseq = {mssfirst} type id lparen varlist rparen lcurly stmtseq rcurly |
                    {msssecond} id idtail* colon type semicolon;

    methodstmtseqs = {msssfirst} methodstmtseqs methodstmtseq |
                     {mssssecond} ;
    classmethodstmt = {cmsfirst} clas id lcurly methodstmtseqs rcurly |
                      {cmssecond} type id lparen varlist rparen lcurly stmtseq rcurly |
                      {cmsthird} id idtail* colon type;
    classmethodstmts = {cmssfirst} classmethodstmts classmethodstmt |
                       {cmsssecond} ;