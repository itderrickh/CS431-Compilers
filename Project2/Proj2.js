Package Project2;

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
            if = 'if';
            else = 'else';
            while = 'while';
            true = 'true';
            false = 'false';
            this = 'this';
            new = 'new';
            return = 'return';
            public = 'public';
            static = 'static';
            length = 'length';
            void = 'void';
            main = 'main';
            print = 'System.out.println';
            string = 'String';
            bool = 'boolean';
            int = 'int';
            plus = '+';
            minus = '-';
            times = star;
            divide = slash;
            equal = '=';
            exc = '!';
            semicolon = ';';
            period = period;
            comma = ',';
            lparen = '(';
            rparen = ')';
            lcurly = '{';
            rcurly = '}';
            lbracket = '[';
            rbracket = ']';
            lt = '<';
            gt = '>';
            and = '&&';
            class = 'class';
            blank = (sp | enter | lf| tab)+;
            id =
                letter (digit | letter | underscore)*;
            number = 
                digit+ |
                digit+ (period digit*);
            comments = 
                slash slash commentbody |
                slash star commentbodymulti star slash;
Ignored Tokens
            blank;