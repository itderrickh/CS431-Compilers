/* This file was generated by SableCC (http://www.sablecc.org/). */

package Project5.node;

import Project5.analysis.*;

@SuppressWarnings("nls")
public final class AIntegerFactor extends PFactor
{
    private TIntnum _intnum_;

    public AIntegerFactor()
    {
        // Constructor
    }

    public AIntegerFactor(
        @SuppressWarnings("hiding") TIntnum _intnum_)
    {
        // Constructor
        setIntnum(_intnum_);

    }

    @Override
    public Object clone()
    {
        return new AIntegerFactor(
            cloneNode(this._intnum_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAIntegerFactor(this);
    }

    public TIntnum getIntnum()
    {
        return this._intnum_;
    }

    public void setIntnum(TIntnum node)
    {
        if(this._intnum_ != null)
        {
            this._intnum_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._intnum_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._intnum_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._intnum_ == child)
        {
            this._intnum_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._intnum_ == oldChild)
        {
            setIntnum((TIntnum) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
