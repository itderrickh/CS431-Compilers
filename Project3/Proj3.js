Package Project3;

Helpers
            all = [0 .. 0xffff];
            digit = ['0'..'9'];
            letter = ['a'..'z'] | ['A'..'Z'];
            underscore = '_';
            lf = 10; 
            sp = ' ';
            period = '.';
            star = '*';
            slash = '/';
            enter = 13;
            tab = 9;
            commentbody = [all - [lf + enter]]*;
            commentbodymulti = [all - [star + slash]]*;
Tokens
            lt = '<';
            gt = '>';
            comma = ',';
            semicolon = ';';
            print = 'echo';
            lparen = '(';
            rparen = ')';
            times = star;
            divide = slash;
            minus = '-';
            plus = '+';
            mod = '%';
            blank = (sp | enter | lf| tab)+;
            id =
                letter (digit | letter | underscore)*;
            number = 
                digit+ |
                digit+ (period digit*);
Ignored Tokens
            blank;