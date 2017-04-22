/* This file was generated by SableCC (http://www.sablecc.org/). */

package Project5.node;

import Project5.analysis.*;

@SuppressWarnings("nls")
public final class TReturn extends Token
{
    public TReturn()
    {
        super.setText("RETURN");
    }

    public TReturn(int line, int pos)
    {
        super.setText("RETURN");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TReturn(getLine(), getPos());
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTReturn(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TReturn text.");
    }
}
