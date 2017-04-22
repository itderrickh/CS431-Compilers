/* This file was generated by SableCC (http://www.sablecc.org/). */

package Project5.node;

import Project5.analysis.*;

@SuppressWarnings("nls")
public final class TString extends Token
{
    public TString()
    {
        super.setText("STRING");
    }

    public TString(int line, int pos)
    {
        super.setText("STRING");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TString(getLine(), getPos());
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTString(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TString text.");
    }
}
